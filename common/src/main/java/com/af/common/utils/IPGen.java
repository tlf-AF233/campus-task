package com.af.common.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 雪花算法生成ID
 *
 * @author Tanglinfeng
 * @date 2022/2/8 18:18
 */
@Slf4j
public class IPGen {

    /**
     * 开始时间戳
     */
    private final long startTimeStamp = 1605665795726L;

    /**
     * 机器ID所占位数
     */
    private final long workIdBits = 5L;

    /**
     * 数据标志Id所占位数
     */
    private final long dataCenterIdBits = 5L;

    /**
     * 支持的机器最大ID，结果是31，这里受机器设置的ID所占位数大小变化
     */
    private final long maxSupportWorkId = -1L ^ (-1L << workIdBits);

    /**
     * 支持的最大数据标识ID，结果是31，这里受数据标识ID所占位数大小变化
     */
    private final long maxSupportDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /**
     * 序列号ID所占位数
     */
    private final long sequenceBits = 12L;

    /**
     * 工作厂房ID向左移12位
     */
    private final long workIdLeftShift = sequenceBits;

    /**
     * 数据标识ID向左移17位（12+5）
     */
    private final long dataCenterIdLeftShift = sequenceBits + dataCenterIdBits;

    /**
     * 时间戳向左移22位（12+5+5）
     */
    private final long timeStampLeftShift = sequenceBits + workIdBits + dataCenterIdBits;

    /**
     * 生成序列的掩码，这里是4095，受序列号位数影响，(0b111111111111=0xfff=4095)2^{12}-1 = 4095 即可以用0、1、2、3、4094 这 4095个数字
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作厂房ID（0-31）
     */
    private long workId;

    /**
     * 数据中心ID(0-31)
     */
    private long dataCenterId;

    /**
     * 毫秒内序列ID（0-4094）
     */
    private long sequence;

    /**
     * 上次生成ID的时间戳
     */
    private long lastTimeStamp = -1L;

    /**
     * 全局ID生成器
     */
    private static IPGen snowflakeId;

    public IPGen() {
    }

    static {
        // 静态加载服务所在系统的ID配置
        LogUtil.info(log, "SnowflakeIdUtil workId : {}, dataCenterId : {}", getWorkId(), getDataCenterId());
        snowflakeId = new IPGen(getWorkId(), getDataCenterId());
    }

    /**
     * 获取全局唯一ID
     *
     * @return
     */
    public static Long generateId() {
        return snowflakeId.nextId();
    }

    /**
     * 初始化构造函数
     *
     * @param workId       指定的工作厂房Id
     * @param dataCenterId 指定的数据中心ID(机器ID)
     */
    public IPGen(long workId, long dataCenterId) {
        if (workId > maxSupportWorkId || workId < 0) {
            throw new IllegalArgumentException(MessageFormat.format("machineId can't greater than {0} or less than 0", maxSupportWorkId));
        }
        if (dataCenterId > maxSupportDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(MessageFormat.format("dataCenterId can't greater than {0} or less than 0", maxSupportDataCenterId));
        }
        this.workId = workId;
        this.dataCenterId = dataCenterId;
    }


    public synchronized long nextId() {
        long timeStamp = getCurrencyTime();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timeStamp < lastTimeStamp) {
            throw new RuntimeException(MessageFormat.format("Clock moved backwards.  Refusing to generate id for {0} milliseconds", (lastTimeStamp - timeStamp)));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimeStamp == timeStamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 阻塞到下一毫秒，获取新的时间戳
                timeStamp = blockNextMills(lastTimeStamp);
            }
            // 时间戳改变，毫秒内序列重置
        } else {
            sequence = 0L;
        }

        lastTimeStamp = timeStamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timeStamp - startTimeStamp) << timeStampLeftShift) |
                (dataCenterId << dataCenterIdLeftShift) |
                (workId << workIdLeftShift) |
                sequence;


    }


    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimeStamp 上次生成的时间戳ID
     * @return 当前时间戳
     */
    private static long blockNextMills(long lastTimeStamp) {
        long timeStamp = getCurrencyTime();
        while (timeStamp <= lastTimeStamp) {
            timeStamp = getCurrencyTime();
        }

        return timeStamp;
    }

    /**
     * 获取当前系统时间
     *
     * @return 毫秒
     */
    private static long getCurrencyTime() {
        return System.currentTimeMillis();
    }


    /**
     * 获取数据中心ID（机器ID）
     *
     * @return 采用本地机器名称取32的模（降低分布式部署工作ID相同）
     */
    private static long getDataCenterId() {
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostName = localHost.getHostName();
            List<Integer> tenNums = string2Integer(hostName);
            if (CollectionUtils.isEmpty(tenNums)) {
                return localRandom.nextInt(0, 31);
            }
            int sums = 0;
            for (int i = 0; i < tenNums.size(); i++) {
                sums += tenNums.get(i);
            }
            return (long) (sums % 32);
        } catch (UnknownHostException e) {
            return localRandom.nextInt(0, 31);
        }
    }


    /**
     * 获取工作工厂ID
     *
     * @return 采用本地IP地址取32的模（降低分布式部署工作ID相同）
     */
    private static long getWorkId() {
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String ip = localHost.getHostAddress();
            List<Integer> tenNums = string2Integer(ip);
            if (CollectionUtils.isEmpty(tenNums)) {
                // IP为空，则采用随机数指定工作ID
                return localRandom.nextInt(0, 31);
            }
            int sums = 0;
            for (int i = 0; i < tenNums.size(); i++) {
                sums += tenNums.get(i);
            }
            return (long) (sums % 32);
        } catch (UnknownHostException e) {
            // ip 获取失败，则采用随机数指定工作ID
            return localRandom.nextInt(0, 31);
        }
    }

    /**
     * 字符串转成10进制的数字集合
     *
     * @param str 字符串
     * @return 10进制的数字集合（这里的是将字符转成对应的ASICC码）
     */
    private static List<Integer> string2Integer(String str) {
        if (StringUtils.isBlank(str)) {
            return Collections.emptyList();
        }
        List<Integer> integers = Lists.newArrayListWithCapacity(str.length());
        for (int i = 0; i < str.length(); i++) {
            integers.add(Integer.valueOf(Integer.toString(str.charAt(i), 10)));
        }
        return integers;
    }

}

package com.af.common.utils;

/**
 * @author Tanglinfeng
 * @date 2022/2/15 1:28
 */
public class UrlUtil {

    public static boolean matching(String pattern, String path) {
        if ("/**".equals(pattern)) {
            return true;
        }
        //按 ** 切割字符串
        String[] reg_split = pattern.split("\\**");
        int index = 0, reg_len = reg_split.length;
        //b代表匹配模式的最后一个字符是否是 '*' ,因为在split方法下最后一个 * 会被舍弃
        boolean b = pattern.charAt(pattern.length() - 1) == '*' ? true : false;
        while (path.length() > 0) {
            if (index == reg_len) {
                if (b)
                    return true;
                else
                    return false;
            }
            String r = reg_split[index++];
            int indexOf = path.indexOf(r);
            if (indexOf == -1)
                return false;
            //前面匹配成功的就可以不用管了,截取掉直接从新地方开始
            path = path.substring(indexOf + r.length());
        }
        return true;
    }
}

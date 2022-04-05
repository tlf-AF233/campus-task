package com.af.code.service;

import com.af.code.api.vo.CodeRequestDto;
import com.af.code.util.DockerUtils;
import com.af.common.utils.LogUtil;
import com.github.dockerjava.api.DockerClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Tanglinfeng
 * @date 2022/4/4 14:33
 */
@Slf4j
@Service
@AllArgsConstructor
public class CodeService {

    private final RedisTemplate redisTemplate;


    public String runCode(CodeRequestDto codeRequestDto) {
        String fileName = "Main" + System.currentTimeMillis();
        LogUtil.info(log, "提交的代码：", codeRequestDto.getSourceCode());
        String source = DockerUtils.writeToFile(fileName, codeRequestDto.getSourceCode());
        System.out.println(source);
        DockerClient dockerClient = DockerUtils.getDockerClient();
        DockerUtils.copyArchiveToContainerCmd(dockerClient, "7fabef390a68", source, "/");
        DockerUtils.runCmdInContainer(dockerClient, "7fabef390a68", "javac", "/" + fileName + ".java");

        String java = DockerUtils.runCmdInContainer(dockerClient, "7fabef390a68", "java", fileName);
        System.out.println(java);
        return java;
    }
}

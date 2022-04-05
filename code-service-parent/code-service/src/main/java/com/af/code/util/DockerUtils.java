package com.af.code.util;

import com.af.common.utils.LogUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.ExecStartCmd;
import com.github.dockerjava.api.command.InspectExecResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tanglinfeng
 * @date 2022/4/4 14:34
 */
@Slf4j
public class DockerUtils {

    public static final String host = "tcp://118.31.34.27:2375";

    private static final Map<String, DockerClient> clientMap = new ConcurrentHashMap<>();

    /**
     * 获取docker连接对象
     *
     * @return
     */
    public static DockerClient getDockerClient() {
        if (clientMap.get(host) == null) {
            DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(host)
                    .withDockerCertPath("D:\\Program Files (x86)\\docker-java-cert\\")
                    .withDockerConfig("D:\\Program Files (x86)\\docker-java-cert\\")
                    .withApiVersion("1.41")
                    .withRegistryUsername("af")
                    .withRegistryPassword("tww2698267.")
                    .withRegistryEmail("352696800@qq.com")
                    .withDockerTlsVerify(true)
                    .build();

            DockerClient client = DockerClientBuilder.getInstance(config).build();
            clientMap.put(host, client);
        }
        return clientMap.get(host);
    }

    /**
     * 创建容器
     *
     * @param dockerClient
     * @param imagesID 镜像ID
     * @return
     */
    public static CreateContainerResponse createContainer(DockerClient dockerClient, String imagesID) {
        return dockerClient.createContainerCmd(imagesID).exec();
    }

    /**
     * 启动容器
     *
     * @param dockerClient DOCKER客户端
     * @param containerID  容器ID
     */
    public static Object startContainerCmd(DockerClient dockerClient, String containerID) {
        return dockerClient.startContainerCmd(containerID).exec();
    }

    public static Object copyArchiveToContainerCmd(DockerClient dockerClient, String containerID, String resource, String remote) {
        return dockerClient.copyArchiveToContainerCmd(containerID)
                .withHostResource(resource)
                .withRemotePath(remote).exec();
    }

    /**
     * 执行cmd命令
     *
     * @param dockerClient
     * @param containerId
     * @param command
     * @return
     */
    public static String runCmdInContainer(DockerClient dockerClient, String containerId, String... command) {
        ExecCreateCmdResponse createCmdResponse = dockerClient.execCreateCmd(containerId)
                .withCmd(command)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .exec();
        System.out.println(Arrays.stream(command).iterator().next());
        String[] res = new String[1];
        CountDownLatch latch = new CountDownLatch(1);
        dockerClient.execStartCmd(createCmdResponse.getId())
                .exec(new ResultCallback<Frame>() {
                    @Override
                    public void onStart(Closeable closeable) {
                        System.out.println("start" + closeable);
                    }

                    @Override
                    public void onNext(Frame frame) {
                        System.out.println("onNext" + frame.getPayload());
                        res[0] = new String(frame.getPayload(), StandardCharsets.UTF_8);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogUtil.error(log, "docker执行命令异常", throwable);
                        latch.countDown();
                    }

                    @Override
                    public void onComplete() {
                        latch.countDown();
                    }

                    @Override
                    public void close() throws IOException {

                        latch.countDown();
                    }
                });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res[0];
    }

    /**
     * 源代码写入文件
     *
     * @param fileName
     * @param sourceCode
     */
    public static String writeToFile(String fileName, String sourceCode) {

        String sourceCodeReplace = sourceCode.replace("Main", fileName);

        String source = "D:\\sourceCode\\" + fileName + ".java";
        try {
            FileUtils.writeByteArrayToFile(new File(source), sourceCodeReplace.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return source;
    }


    public static void main(String[] args) {

//        DockerClient dockerClient = getDockerClient();
//        Info exec = dockerClient.infoCmd().exec();
//        System.out.println(exec);

//        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
//                .withDockerHost("tcp://118.31.34.27:2375")
//                .withDockerTlsVerify(false)
//                .build();
//
//        DockerClient build = DockerClientBuilder.getInstance(config).build();
//
        String sourcecode = "import java.util.*; \n" +
                "             \n " +
                "              public class Main { \n" +
                "                  public static void main(String[] args) { \n" +
                "                      // 在这写代码哦~  \n" +
                "                      while(true) {System.out.println(" + '"' + "Hello, World!" + '"' + ");} \n" +
              //  "                       s[1] = " + '"' + "a" + '"' + ";\n" +
                "                       System.out.println(" + '"' + "Hello, World!" + '"' + "); \n" +
                "                  } \n" +
                "              } \n";
        String str = "";
//        String pattern = "Main";
//
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(sourcecode);
//        System.out.println(m.matches());
//
//        System.out.println(sourcecode.replace(pattern, "public class O"));
//
//        String path = "/codeDir/Main.java";
//        String[] cmd = new String[5];
//        cmd[0] = "/bin/sh -c";
//        cmd[1] = "echo";
//        cmd[2] = "'" + sourcecode + "'";
//        cmd[3] = ">" ;
//        cmd[4] = path;
//        String code = "test";
        // ExecCreateCmdResponse execCreateCmdResponse = build.execCreateCmd("56ade72b9331").withCmd("/bin/sh","-c", "echo '"+sourcecode+"' > "+path).withAttachStdout(true).withAttachStderr(true).exec();
        // runCmdInContainer(build, "56ade72b9331", "/bin/sh","-c", "echo '"+code+"' > "+path);
        CountDownLatch latch = new CountDownLatch(1);
        String[] result = new String[1];
//        build
//                .execStartCmd(execCreateCmdResponse.getId())
//                .withTty(false)
//                .withDetach(false)
//                .exec(new ResultCallback<Frame>() {
//                    @Override
//                    public void onStart(Closeable closeable) {
//
//                    }
//
//                    @Override
//                    public void onNext(Frame object) {
//                        System.out.println(object.getPayload() + "11111111111111");
//                        result[0] = new String(object.getPayload(), StandardCharsets.UTF_8);
//                        System.out.println("************" + result[0]);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        latch.countDown();
//
//                    }
//
//                    @Override
//                    public void close() throws IOException {
//                    }
//                });
//        try {
//            latch.await(5, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(result[0]);
//        try {
//            ExecStartCmd execStartCmd = build.execStartCmd(execCreateCmdResponse.getId());
//            execStartCmd
//                    .exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        String[] c = new String[2];
//        c[0] = "javac";
//        c[1] = "/codeDir/Main.java";
//        // writeToFile(sourcecode);
//        copyArchiveToContainerCmd(build, "56ade72b9331", "D:\\sourceCode\\Main.java", "/");
//        System.out.println("编译后结果" + runCmdInContainer(build, "56ade72b9331", "javac", "/Main.java"));
//        System.out.println("运行后结果" + runCmdInContainer(build, "56ade72b9331", "java", "Main"));
//

        // System.out.println(copyArchiveToContainerCmd(build, "56ade72b9331", "D:\\test.mp4", "/"));
    }
}

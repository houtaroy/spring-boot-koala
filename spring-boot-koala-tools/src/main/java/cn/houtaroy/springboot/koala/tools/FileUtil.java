package cn.houtaroy.springboot.koala.tools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Houtaroy
 */
public class FileUtil extends FileUtils {

    /**
     * 复制文件
     * 二次封装apache commons FileUtils, 不用每次都写new File(filename)
     *
     * @param source 源文件名
     * @param target 目标文件名
     * @throws IOException IO异常
     */
    public static void copyFile(String source, String target) throws IOException {
        copyFile(new File(source), new File(target));
    }

    /**
     * 强制移动文件, 如果目标文件存在则删除
     * 二次封装apache commons FileUtils, 不用每次都写new File(filename)
     *
     * @param source 源文件名
     * @param target 目标文件名
     * @throws IOException IO异常
     */
    public static void forceMoveFile(String source, String target) throws IOException {
        File targetFile = new File(target);
        if (targetFile.exists()) {
            deleteQuietly(targetFile);
        }
        moveFile(new File(source), targetFile);
    }

    /**
     * 静默删除, 不抛出异常
     *
     * @param filePathName 文件全路径名称
     */
    public static void deleteQuietly(String filePathName) {
        if (filePathName == null) {
            return;
        }
        FileUtils.deleteQuietly(new File(filePathName));
    }

}

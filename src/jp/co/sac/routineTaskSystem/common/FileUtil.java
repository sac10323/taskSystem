package jp.co.sac.routineTaskSystem.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import jp.co.sac.routineTaskSystem.constant.Const;

/**
 * ファイル操作のユーティリティ
 *
 * @author shogo_saito
 */
public class FileUtil {
    public static FileInputStream openInput(String filePath) throws FileNotFoundException {
        return openInput(new File(filePath));
    }

    public static FileInputStream openInput(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public static FileOutputStream openOutput(String filePath) throws FileNotFoundException {
        return openOutput(new File(filePath));
    }

    public static FileOutputStream openOutput(File file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }

    public static void prepareDirectory(String dirPath) throws IOException {
        try {
            Path path = Paths.get(dirPath);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static List<String> getCheckTargetFilePaths(String dirPath, boolean recursive) {
        List<String> result = new ArrayList<>();
        File root = new File(dirPath);
        if (root.exists() && root.isDirectory()) {
            for (File file : getFiles(root, recursive)) {
                result.add(file.getPath());
            }
        }
        return result;
    }

    public static List<File> getFiles(File rootDir, boolean recursive) {
        List<File> result = new ArrayList<>();
        for( File file : rootDir.listFiles()) {
            if (file.exists()) {
                if (file.isDirectory()) {
                    if (recursive) {
                        result.addAll(getFiles(file, recursive));
                    }
                } else if (file.isFile()) {
                    result.add(file);
                }
            }
        }
        return result;
    }

    public static class FileInfo {
        String filePath;
        String title;
        String extension;
        String directory;

        public FileInfo(String filePath) {
            this.filePath = filePath;
        }
    }
}

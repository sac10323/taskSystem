package jp.co.sac.routineTaskSystem.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}

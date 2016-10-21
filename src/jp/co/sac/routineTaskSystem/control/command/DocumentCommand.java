package jp.co.sac.routineTaskSystem.control.command;

import java.io.FileInputStream;
import java.io.OutputStream;

/**
 *
 * @author shogo_saito
 */
public interface DocumentCommand<T> {
    T load(String title, FileInputStream in);
    void save(T document, OutputStream out) throws Exception;
    String getFileName(T document);
}

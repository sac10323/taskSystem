package jp.co.sac.routineTaskSystem.manage;

import java.io.FileInputStream;
import java.io.OutputStream;
import jp.co.sac.routineTaskSystem.entity.document.Document;

/**
 * Excelファイル操作を集約
 *
 * @author shogo_saito
 */
public interface DocumentManagerIF<T extends Document> {
    @Deprecated
    public T load(String filePath) throws Exception;
    public T load(String title, FileInputStream in) throws Exception;

    @Deprecated
    public void save(T document) throws Exception;
    @Deprecated
    public void save(T document, String dirPath) throws Exception;
    public void save(T document, OutputStream out) throws Exception;
    public String getFileName(T document);
}

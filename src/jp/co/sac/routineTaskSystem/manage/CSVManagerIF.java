package jp.co.sac.routineTaskSystem.manage;

import java.io.FileInputStream;
import java.io.OutputStream;
import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;

/**
 *
 * @author shogo_saito
 */
public interface CSVManagerIF<T extends CSVEntity> {
    public T load(String filePath);
    public T load(String title, FileInputStream fis);
    public void write(T entity);
    public void write(T entity,String dirPath);
    public void write(T entity, OutputStream out);
    public String getFileName(T entity);
}

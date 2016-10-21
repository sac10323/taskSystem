package jp.co.sac.routineTaskSystem.entity.csv;

import java.util.List;

/**
 *
 * @author shogo_saito
 */
public abstract class CSVEntity<T> {
    private List<T> records;
    protected String title;
    protected String fileError;

    protected CSVEntity(List<T> records) {
        this.records = records;
    }
    public List<T> records() {
        return records;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileError() {
        return fileError;
    }

    public void setFileError(String fileError) {
        this.fileError = fileError;
    }
}

package jp.co.sac.routineTaskSystem.manage.csv;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.manage.CSVManagerIF;
import jp.co.sac.routineTaskSystem.manage.SelectableManager;

/**
 *
 * @author shogo_saito
 */
public abstract class CSVManager<T extends CSVEntity, D extends Document> implements CSVManagerIF<T>, SelectableManager<D>{
    @Override
    public abstract T load(String filePath);
    public List<T> load(List<String> filePathes) {
        List<T> result = new ArrayList<>();
        if (filePathes != null) {
            for (String filePath : filePathes) {
                result.add(load(filePath));
            }
        }
        return result;
    }
    @Override
    public abstract void write(T entity);
    public void write(List<T> entities) {
        if (DataUtil.isNullOrEmpty(entities)) {
            return;
        }
        for (T entity : entities) {
            write(entity);
        }
    }
    public abstract String createFileName(T entity);
    protected void prepareDir() throws Exception {
        try {
            Path dirPath = Paths.get(DataUtil.getCSVSaveDirPath());
            if (!Files.exists(dirPath)) {
                Files.createDirectory(dirPath);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public abstract boolean isTargetOf(String title);

    @Override
    public abstract boolean isTargetOf(D entity);
}

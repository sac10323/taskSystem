package jp.co.sac.routineTaskSystem.manage.csv.extra;

import com.orangesignal.csv.Csv;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.handlers.CsvEntityListHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.entity.csv.extra.IMCSVEntity;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;
import jp.co.sac.routineTaskSystem.manage.csv.CSVManager;
import org.apache.log4j.Logger;

/**
 *
 * @author shogo_saito
 */
public class IMCSVManager extends CSVManager<IMCSVEntity, RosterDocument> {

    private static Logger log = Logger.getLogger("root");

    @Override
    public IMCSVEntity load(String filePath) {
        IMCSVEntity entity = new IMCSVEntity();
        try {
            if (DataUtil.isFilePath(filePath)) {
                File file = new File(filePath);
                try (FileInputStream fis = new FileInputStream(file)) {
                    entity = load(DataUtil.convertToTitleFromFilePath(filePath), fis);
                }
            }
        } catch (Exception ex) {
            log.warn("IMCSVManager#load", ex);
        }
        return entity;
    }

    @Override
    public IMCSVEntity load(String title, FileInputStream fis) {
        IMCSVEntity entity = null;
        try (InputStreamReader isr = new InputStreamReader(fis, "SJIS")) {
            CsvConfig cfg = new CsvConfig(',', '\"', '\"');
            cfg.setUtf8bomPolicy(false);
            cfg.setVariableColumns(true);
            cfg.setIgnoreEmptyLines(true);
            cfg.setIgnoreCaseNullString(true);
            List<IMCSVEntity> records;
            records = Csv.load(isr, cfg, new CsvEntityListHandler<>(IMCSVEntity.class));
            entity = new IMCSVEntity(title, records);
        } catch (Exception ex) {
            log.warn("IMCSVManager#load", ex);
        }
        return entity;
    }

    @Override
    public void write(IMCSVEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String createFileName(IMCSVEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isTargetOf(String title) {
        return DataUtil.isNumeric(DataUtil.convertToTitleFromFilePath(title));
    }

    @Override
    public boolean isTargetOf(RosterDocument entity) {
        // 出力不可
        return false;
    }

    @Override
    public void write(IMCSVEntity entity, OutputStream out) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void write(IMCSVEntity entity, String dirPath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFileName(IMCSVEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

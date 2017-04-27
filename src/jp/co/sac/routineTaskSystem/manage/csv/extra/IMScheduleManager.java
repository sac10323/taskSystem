/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import jp.co.sac.routineTaskSystem.entity.csv.extra.IMScheduleEntity;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;
import jp.co.sac.routineTaskSystem.manage.csv.CSVManager;
import org.apache.log4j.Logger;

/**
 *
 * @author shogo_saito
 */
public class IMScheduleManager extends CSVManager<IMScheduleEntity, RosterDocument> {

    private static Logger log = Logger.getLogger("root");

    @Override
    public IMScheduleEntity load(String filePath) {
        IMScheduleEntity entity = new IMScheduleEntity();
        try {
            if (DataUtil.isFilePath(filePath)) {
                File file = new File(filePath);
                try (FileInputStream fis = new FileInputStream(file)) {
                    entity = load(DataUtil.convertToTitleFromFilePath(filePath), fis);
                }
            }
        } catch (Exception ex) {
            log.warn("IMScheduleManager#load", ex);
        }
        return entity;
    }

    @Override
    public void write(IMScheduleEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String createFileName(IMScheduleEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isTargetOf(String title) {
        if (!DataUtil.isNullOrEmpty(title)) {
            return DataUtil.convertToTitleFromFilePath(title).matches("sch\\d{5}");
        }
        return false;
    }

    @Override
    public boolean isTargetOf(RosterDocument entity) {
        // 出力は想定なし
        return false;
    }

    @Override
    public IMScheduleEntity load(String title, FileInputStream fis) {
        IMScheduleEntity entity = new IMScheduleEntity();
        try (InputStreamReader isr = new InputStreamReader(fis, "SJIS")) {
            CsvConfig cfg = new CsvConfig(',', '\"', '\"');
            cfg.setUtf8bomPolicy(false);
            cfg.setVariableColumns(true);
            cfg.setIgnoreEmptyLines(true);
            cfg.setIgnoreCaseNullString(true);
            List<IMScheduleEntity> records;
            records = Csv.load(isr, cfg, new CsvEntityListHandler<>(IMScheduleEntity.class));
            entity = new IMScheduleEntity(title, records);
        } catch (Exception ex) {
            log.warn("IMScheduleManager#load", ex);
        }
        return entity;
    }

    @Override
    public void write(IMScheduleEntity entity, String dirPath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void write(IMScheduleEntity entity, OutputStream out) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFileName(IMScheduleEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

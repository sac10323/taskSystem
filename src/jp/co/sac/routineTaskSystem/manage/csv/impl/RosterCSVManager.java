package jp.co.sac.routineTaskSystem.manage.csv.impl;

import com.orangesignal.csv.Csv;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.handlers.CsvEntityListHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.constant.RosterConst;
import jp.co.sac.routineTaskSystem.entity.csv.RosterCSVEntity;
import jp.co.sac.routineTaskSystem.entity.csv.RosterCSVRecord;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;
import jp.co.sac.routineTaskSystem.manage.csv.CSVManager;
import org.apache.log4j.Logger;

/**
 *
 * @author shogo_saito
 */
public class RosterCSVManager extends CSVManager<RosterCSVEntity, RosterDocument> {

    private static Logger log = Logger.getLogger("root");

    @Override
    public RosterCSVEntity load(String filePath) {
        RosterCSVEntity entity = new RosterCSVEntity();
        try {
            String fileName = DataUtil.convertToTitleFromFilePath(filePath);
            entity.setTitle(fileName);
            entity.setYearAndMonth(getYearAndMonthFromFileName(fileName));
            entity.setStaffId(getStaffIdFromFileName(fileName));
            if (DataUtil.isFilePath(filePath)) {
                File file = new File(filePath);
                try (FileInputStream fis = new FileInputStream(file)) {
                    entity = load(DataUtil.convertToTitleFromFilePath(filePath), fis);
                }
            }
        } catch (Exception ex) {
            log.error(ex);
            entity.setFileError(RosterConst.FindMessage.ERR_FILE_PART);
        }
        return entity;
    }

    @Override
    public RosterCSVEntity load(String title, FileInputStream fis) {
        RosterCSVEntity entity = new RosterCSVEntity();
        try (InputStreamReader isr = new InputStreamReader(fis, "UTF8")) {
            CsvConfig cfg = RosterConst.getLoadCsvConfig();
            List<RosterCSVRecord> records;
            records = Csv.load(isr, cfg, new CsvEntityListHandler<>(RosterCSVRecord.class));
            entity.records().addAll(records);
        } catch (Exception ex) {
            log.error(ex);
            entity.setFileError(RosterConst.FindMessage.ERR_FILE_PART);
        }
        return entity;
    }

    @Override
    public void write(RosterCSVEntity entity) {
        try {
            write(entity, DataUtil.getCSVSaveFilePath(createFileName(entity) + ".csv"));
        } catch (Exception ex) {
            log.warn("RosterCSVManager#write(RosterCSVEntity)", ex);
        }
    }

    @Override
    public void write(RosterCSVEntity entity, String dirPath) {
        try {
            prepareDir();
            File file = new File(dirPath + File.separator + createFileName(entity) + ".csv");
            try (FileOutputStream out = new FileOutputStream(file)) {
                write(entity, out);
            }
        } catch (Exception ex) {
            log.warn("RosterCSVManager#write(RosterCSVEntity, String)", ex);
        }
    }

    @Override
    public String createFileName(RosterCSVEntity entity) {
        String work = GeneralConfig.rosterCSVFileNamePattern();
        work = work.replace("@YM", entity.getYearAndMonth());
        work = work.replace("@staffId", entity.getStaffId());
        if (!DataUtil.isNullOrEmpty(entity.getName())) {
            work = work.replace("@name", entity.getName());
        }
        return work;
    }

    protected String getYearAndMonthFromFileName(String fileName) {
        if (fileName == null || fileName.length() < 6) {
            return null;
        }
        String yearAndMonth = fileName.substring(0, 6);
        if (DataUtil.isSimpleNumeric(yearAndMonth)) {
            return yearAndMonth;
        } else {
            return null;
        }
    }

    protected String getStaffIdFromFileName(String fileName) {
        if (fileName == null || fileName.length() < 14) {
            return null;
        }
        String staffId = fileName.substring(9, 14);
        if (DataUtil.isSimpleNumeric(staffId)) {
            return staffId;
        } else {
            return null;
        }
    }

    @Override
    public boolean isTargetOf(String title) {
        if (!DataUtil.isNullOrEmpty(title)) {
            String tmp = title.replace("sac", "");
            return title.contains("sac") && tmp.length() == 11 && DataUtil.isNumeric(tmp);
        }
        return false;
    }

    @Override
    public boolean isTargetOf(RosterDocument doc) {
        return doc.getDocType() == RosterConst.DocType.Roster;
    }

    @Override
    public void write(RosterCSVEntity entity, OutputStream out) {
        try {
            CsvConfig cfg = RosterConst.getWriteCsvConfig();
            Csv.save(entity.records(), out, "UTF8", cfg, new CsvEntityListHandler<>(RosterCSVRecord.class));
        } catch (Exception ex) {
            log.warn("RosterCSVManager#write(RosterCSVEntity, String)", ex);
        }
    }

    @Override
    public String getFileName(RosterCSVEntity entity) {
        return createFileName(entity) + ".csv";
    }
}

package jp.co.sac.routineTaskSystem.convert.impl;

import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.constant.RosterConst;
import jp.co.sac.routineTaskSystem.convert.DocumentConverter;
import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;
import jp.co.sac.routineTaskSystem.entity.csv.RosterCSVEntity;
import jp.co.sac.routineTaskSystem.entity.csv.RosterCSVRecord;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;

/**
 *
 * @author shogo_saito
 */
public class RosterConverter extends DocumentConverter<RosterDocument, RosterCSVEntity> {
    @Override
    public RosterCSVEntity toCSVEntity(RosterDocument document) {
        if (document == null) {
            return null;
        }
        RosterCSVEntity csv = new RosterCSVEntity();
        csv.setName(document.get(RosterConst.Category.AuthorName, 0));
        csv.setStaffId(document.get(RosterConst.Category.StaffId, 0));
        csv.setYearAndMonth(document.getYearMonthString());
        csv.setFileError(document.getFileError());
        for (int index = 0; index < document.getMaxDay(); index++) {
            RosterCSVRecord record = new RosterCSVRecord();
            record.setDay(index + 1);
            record.setFromTime(document.get(RosterConst.Category.FROM, index));
            record.setToTime(document.get(RosterConst.Category.TO, index));
            record.setStrangeA(document.get(RosterConst.Category.StrangeA, index));
            record.setStrangeB(document.get(RosterConst.Category.StrangeB, index));
            record.setCause(document.get(RosterConst.Category.Cause, index));
            record.setDestination(document.get(RosterConst.Category.Destination, index));
            csv.records().add(record);
        }
        return csv;
    }
    @Override
    public RosterDocument toDocumentEntity(RosterCSVEntity entity) {
        if (entity == null) {
            return null;
        }
        RosterDocument document = new RosterDocument();
        document.setTitle(entity.getTitle());
        document.put(RosterConst.Category.StaffId, 0, entity.getStaffId());
        document.setExtension("csv");
        document.setMonth(DataUtil.convertToMonthFromYearAndMonthString(entity.getYearAndMonth()));
        document.setYear(DataUtil.convertToYearFromYearAndMonthString(entity.getYearAndMonth()));
        document.setMaxDay(DataUtil.getMaxDayOfMonth(document.getMonth(), document.getYear()));
        document.setFileError(entity.getFileError());
        for (int index = 0; index < entity.records().size(); index++) {
            RosterCSVRecord record = entity.records().get(index);
            document.put(RosterConst.Category.FROM, index, record.getFromTime());
            document.put(RosterConst.Category.TO, index, record.getToTime());
            document.put(RosterConst.Category.StrangeA, index, record.getStrangeA());
            document.put(RosterConst.Category.StrangeB, index, record.getStrangeB());
            document.put(RosterConst.Category.Cause, index, record.getCause());
            document.put(RosterConst.Category.Destination, index, record.getDestination());
        }
        return document;
    }

    @Override
    public boolean isTargetOf(Document doc) {
        return doc.getDocType() == RosterConst.DocType.Roster;
    }

    @Override
    public boolean isTargetOf(CSVEntity csv) {
        return csv instanceof RosterCSVEntity;
    }
}

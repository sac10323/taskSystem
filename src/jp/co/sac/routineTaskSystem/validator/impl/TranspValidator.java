package jp.co.sac.routineTaskSystem.validator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.constant.TranspConst;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.document.TranspDocument;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.findings.TranspFindings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;
import jp.co.sac.routineTaskSystem.validator.DocumentValidator;

/**
 *
 * @author shogo_saito
 */
public class TranspValidator extends DocumentValidator<TranspDocument> {

    @Override
    public List<Findings> doValidate(TranspDocument document) {
        return doValidate(document, null);
    }

    @Override
    public List<Findings> doValidate(TranspDocument document, Staff inspector) {
        List<Findings> finds = new ArrayList<>();
        finds.addAll(existDocument(document));
        if (finds.isEmpty()) {
            finds.addAll(validateDocumentName(document, inspector));
            finds.addAll(validateRegistrant(document, inspector));
            finds.addAll(validateContents(document, inspector));
        }
        return finds;
    }

    @Override
    public List<Findings> existDocument(TranspDocument document) {
        List<Findings> finds = new ArrayList<>();
        if (document == null) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_FILE));
        } else {
            if (document.getFileError() != null) {
                finds.addAll(getFindingsOfFileError(document));
            }
            if (document.getFileType().equals(TranspDocument.FileType.none)) {
                finds.add(new TranspFindings(TranspConst.FindMessage.UN_SUPPORT_FILE));
            }
        }
        return finds;
    }

    @Override
    public List<Findings> validateDocumentName(TranspDocument document, Staff inspector) {
        List<Findings> finds = new ArrayList<>(0);
        TranspDocument.FileType fileType = document.getFileType();
        if (fileType.equals(TranspDocument.FileType.xls)) {
            if (!isValidDocumentName(document)) {
                finds.add(new TranspFindings(TranspConst.FindMessage.ERR_TITLE, document.getTitle()));
            }
        }
        return finds;
    }

    /**
     * ファイル名のチェック
     * 
     * @param document 勤務表
     * @return ファイル名が正しいか
     */
    private boolean isValidDocumentName(TranspDocument document) {
        try {
            Pattern pattern = Pattern.compile(TranspConst.FILE_NAME_REGEX);
            Matcher matcher = pattern.matcher(document.getTitle());
            return matcher.matches();
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public List<Findings> validateRegistrant(TranspDocument document, Staff inspector) {
        List<Findings> finds = new ArrayList<>(0);
        return finds;
    }

    @Override
    public List<Findings> validateContents(TranspDocument document, Staff inspector) {
        List<Findings> finds = new ArrayList<>(0);
        finds.addAll(findOfOverAll(document));
        for (int index = 0; index < document.getMaxDay(); index++) {
            if (isNoRecord(document, index)) {
                break;
            }
            finds.addAll(findOfOneRecord(document, index));
        }
        return finds;
    }

    @Override
    public List<Findings> getFindingsOfFileError(TranspDocument document) {
        List<Findings> finds = new ArrayList<>();
        if (document.getFileError() != null) {
            finds.add(new TranspFindings(document.getFileError()));
        }
        return finds;
    }
    
    public List<Findings> findOfOverAll(TranspDocument document) {
        List<Findings> finds = new ArrayList<>(0);
        // ヘッダ
        String header = document.get(TranspConst.Category.Header, 0);
        if (DataUtil.isNullOrEmpty(header)) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_HEADER));
        }
        // 社員ID
        String staffId = document.get(TranspConst.Category.StaffId, 0);
        if (DataUtil.isNullOrEmpty(staffId)) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_STAFF_ID));
        }
        // 所属
        String affiliation = document.get(TranspConst.Category.Affiliation, 0);
        if (DataUtil.isNullOrEmpty(affiliation)) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_AFFILIATION));
        } else {
            String correctAffiliation = GeneralConfig.getInstance().get("sAffiliation");
            if (DataUtil.isNullOrEmpty(correctAffiliation)) {
                if (!correctAffiliation.equals(affiliation)) {
                    finds.add(new TranspFindings(TranspConst.FindMessage.ERR_AFFILIATION));
                }
            }
        }
        // 氏名
        String name = document.get(TranspConst.Category.Name, 0);
        if (DataUtil.isNullOrEmpty(name)) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_NAME));
        }
        // 期間
        String period = document.get(TranspConst.Category.Period, 0);
        if (DataUtil.isNullOrEmpty(period)) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_PERIOD));
        }
        return finds;
    }

    public List<Findings> findOfOneRecord(TranspDocument document, int idx) {
        List<Findings> finds = new ArrayList<>(0);
//        String strMonthAndDay = document.get(TranspConst.Category.MonthAndDay, idx);
        String strBusiness = document.get(TranspConst.Category.Business, idx);
        String StrDestination = document.get(TranspConst.Category.Destination, idx);
        String strVehicle = document.get(TranspConst.Category.Vehicle, idx);
        String strDeparture = document.get(TranspConst.Category.Departure, idx);
        String strArriving = document.get(TranspConst.Category.Arriving, idx);
        String strAmount = document.get(TranspConst.Category.Amount, idx);

        // 業務
        if (DataUtil.isNullOrEmpty(strBusiness)) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_BUSINESS, idx));
        }

        // 行先
        if (DataUtil.isNullOrEmpty(StrDestination)) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_DESTINATION, idx));
        }

        // 乗物
        if (DataUtil.isNullOrEmpty(strVehicle)) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_VEHICLE, idx));
        }

        // 発
        if (DataUtil.isNullOrEmpty(strDeparture)) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_DEPARTURE, idx));
        }

        // 着
        if (DataUtil.isNullOrEmpty(strArriving)) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_ARRIVING, idx));
        }

        // 金額
        if (DataUtil.isNullOrEmpty(strAmount)) {
            finds.add(new TranspFindings(TranspConst.FindMessage.NO_AMOUNT, idx));
        } else {
            if (!DataUtil.isNumeric(strAmount) || Integer.valueOf(strAmount) < 0) {
                finds.add(new TranspFindings(TranspConst.FindMessage.ERR_AMOUNT, idx));
            }
        }

        return finds;
    }

    public boolean isNoRecord(TranspDocument document, int idx) {
//        for (TranspConst.Category category : TranspConst.Category.values()) {
//            if (category.needIndex()) {
//                if (!DataUtil.isNullOrEmpty(document.get(category, idx))) {
//                    return false;
//                }
//            }
//        }
//        return true;
        return DataUtil.isNullOrEmpty(document.get(TranspConst.Category.MonthAndDay, idx));
    }

    @Override
    public boolean isTargetOf(Document doc) {
        return doc.getDocType() == TranspConst.DocType.Transp;
    }
}

package jp.co.sac.routineTaskSystem.manage.document;

import java.io.File;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.constant.Const;
import jp.co.sac.routineTaskSystem.constant.RosterConst;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 勤務表操作クラス<br>
 * ファイル読み込みと書き込み
 *
 * @author shogo_saito
 */
public class RosterManager extends DocumentManager<RosterDocument> {

    private static Logger log = Logger.getLogger("root");

    @Override
    public RosterDocument load(String filePath) throws Exception {
        String extension = null;
        if (filePath != null) {
            extension = DataUtil.getExtensionFromFilePath(filePath);
            for (String fileType : RosterConst.File_TYPES) {
                if (extension.equals(fileType)) {
                    return super.load(filePath);
                }
            }
        }
        RosterDocument document = new RosterDocument();
        setTitleOfDocument(document, filePath);
        if (extension == null) {
            document.setFileError(RosterConst.NO_FILE_PATH);
        } else {
            document.setFileError(String.format(RosterConst.ERR_EXTENSION, extension));
        }
        return document;
    }

    @Override
    public RosterDocument load(Workbook wb, String fileName) {
        RosterDocument document = new RosterDocument();
        setTitleOfDocument(document, fileName);
        for (RosterConst.Category category : RosterConst.Category.valuesNoHaveDay()) {
                if (category.equals(RosterConst.Category.AuthorName)) {
                    // 社員名は後で取得
                    continue;
                }
                String[] data = getDataSingle(wb.getSheetAt(0), category.getSheetMap());
                if (category.equals(RosterConst.Category.StaffId)) {
                    data[0] = DataUtil.convertToIntStringFromDoubleString(data[0]);
                }
                document.put(category, data);
        }
        
        //社員名はここで設定
        document.put(RosterConst.Category.AuthorName, getAuthorName(wb.getSheetAt(0), document.get(RosterConst.Category.StaffId, 0)));
        
        for (RosterConst.Category category : RosterConst.Category.valuesHaveDay()) {
                String[] data = getData(wb.getSheetAt(0), category.getSheetMap(), document.getMaxDay());
                document.put(category, data);
        }
        return document;
    }

    @Override
    public void save(RosterDocument document, Workbook wb) {
        for (RosterConst.Category category : RosterConst.Category.values()) {
            if (category.equals(RosterConst.Category.AuthorName)) {
                // 社員名はスタッフIDの設定のみでExcelに任せる
                continue;
            }
            if (RosterConst.Category.haveDay(category)) {
                setData(wb.getSheetAt(0), category.getSheetMap(), document.get(category));
            } else {
                setDataSingle(wb.getSheetAt(0), category.getSheetMap(), document.get(category));
            }
        }
        wb.getCreationHelper().createFormulaEvaluator().evaluateAll();
    }

    @Override
    protected void setTitleOfDocument(RosterDocument document, String fileName) {
        super.setTitleOfDocument(document, fileName);
        if (DataUtil.isRosterDocumentName(fileName)) {
            if (fileName.length() > 4) {
                String strYear = fileName.substring(0, 4);
                if (DataUtil.isNumeric(strYear)) {
                    document.setYear(Integer.valueOf(strYear));
                }
            }
            if (fileName.length() > 6) {
                String strMonth = fileName.substring(4, 6);
                if (DataUtil.isNumeric(strMonth)) {
                    document.setMonth(Integer.valueOf(strMonth));
                }
            }
        }
    }

    @Override
    protected String getCellData(Cell cell, SheetMap sheetMap) {
        if (DataUtil.isTimeRoster(sheetMap)) {
            if (cell == null) {
                return null;
            }
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                return DataUtil.convertToTimeStringFromDateForRoster(cell.getDateCellValue());
            } else {
                return null;
            }
        }
        return super.getCellData(cell, sheetMap);
    }

    private String[] getAuthorName(Sheet sheet, String staffId) {
        String[] ret = new String[1];
        if (sheet == null || DataUtil.isNullOrEmpty(staffId)) {
            return ret;
        }
        String[] staffIds = getColumnDataAsc(sheet, RosterConst.lookUpStaffIdMap, RosterConst.lookUpStaffIdMap.getRngRow());
        String[] staffNames = getColumnDataAsc(sheet, RosterConst.lookUpAuthorNameMap, staffIds.length);
        for (int idx = 0; idx < staffIds.length && idx < staffNames.length; idx++) {
            if (staffId.equals(staffIds[idx])) {
                ret[0] = staffNames[idx];
                break;
            }
        }
        return ret;
    }

    @Override
    protected String[] getColumnDataAsc(Sheet sheet, SheetMap sheetMap, int amount) {
        if (RosterConst.lookUpStaffIdMap.equals(sheetMap)) {
            String[] data = new String[amount];
            for (int index = 0; index < amount; index++) {
                if (sheet.getRow(index + sheetMap.getPstRow()) == null) {
                    continue;
                }
                data[index] = getCellData(sheet.getRow(index + sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap);
                data[index] = DataUtil.convertToIntStringFromDoubleString(data[index]);
                if (RosterConst.LAST_STAFF_ID.equals(data[index])) {
                    break;
                }
            }
            return data;
        }
        if (RosterConst.lookUpAuthorNameMap.equals(sheetMap)) {
            String[] data = new String[amount];
            for (int index = 0; index < amount - 1; index++) {
                try {
                    if (sheet.getRow(index + sheetMap.getPstRow()) == null) {
                        continue;
                    }
                    data[index] = getCellData(sheet.getRow(index + sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap);
                } catch (Exception ex) {
                    log.warn("getColumnDataAsc", ex);
                }
            }
            return data;
        }
        return super.getColumnDataAsc(sheet, sheetMap, amount);
    }

    @Override
    protected void setCellData(Cell cell, SheetMap sheetMap, String value) {
        if (cell != null) {
            if (sheetMap != null && Const.CellDataType.TIME_ROSTER.equals(sheetMap.getType())) {
                Double time = DataUtil.convertToDoubleFromTimeString(value);
                if (time != null) {
                    cell.setCellValue(time);
                } else {
                    cell.setCellType(Cell.CELL_TYPE_BLANK);
                }
                return;
            }
        }
        super.setCellData(cell, sheetMap, value);
    }

    @Override
    protected String getTemplateFilePath(RosterDocument document) {
        StringBuilder sb = new StringBuilder();
        sb.append(Const.getRootPath());
        sb.append(RosterConst.TEMPLATE_FOLDER);
        sb.append(File.separator);
        sb.append(String.format(RosterConst.FILE_NAME_TEMPLATE, document.getYearMonthString()));
        sb.append(".xls");
        return sb.toString();
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
    public boolean isTargetOf(Document doc) {
        if (doc != null) {
            return RosterConst.DocType.Roster.equals(doc.getDocType());
        }
        return false;
    }
}

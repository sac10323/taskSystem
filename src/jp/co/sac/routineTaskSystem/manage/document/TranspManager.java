package jp.co.sac.routineTaskSystem.manage.document;

import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.constant.Const;
import jp.co.sac.routineTaskSystem.constant.TranspConst;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.document.TranspDocument;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author shogo_saito
 */
public class TranspManager extends DocumentManager<TranspDocument> {

    @Override
    public TranspDocument load(Workbook wb, String fileName) {
        TranspDocument document = new TranspDocument();
        setTitleOfDocument(document, fileName);
        if (fileName != null && fileName.length() > 5) {
            int[] date = DataUtil.convertToIntFromYearAndMonthString(fileName.substring(0, 6));
            document.setYear(date[0]);
            document.setMonth(date[1]);
//            wb.setSheetName(0, String.format(TranspConst.SHEET_NAME_PATTERN, date[0], date[1]));
        }
        for (TranspConst.Category category : TranspConst.Category.values()) {
            String[] data = getData(wb.getSheetAt(0), category.getSheetMap(), document.getMaxDay());
            document.put(category, data);
        }
        return document;
    }

    @Override
    public void save(TranspDocument document, Workbook wb) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String getCellData(Cell cell, SheetMap sheetMap) {
        if (sheetMap != null && Const.CellDataType.DATE_TRANSP.equals(sheetMap.getType())) {
            if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                int[] date = DataUtil.convertToIntFromDate(cell.getDateCellValue());
                if (date != null) {
                    return date[1] + "/" + date[2];
                }
            }
        }
        return super.getCellData(cell, sheetMap);
    }

    @Override
    public boolean isTargetOf(String title) {
        if (!DataUtil.isNullOrEmpty(title)) {
            return title.contains("交通費請求明細書");
        }
        return false;
    }

    @Override
    public boolean isTargetOf(Document doc) {
        if (doc != null) {
            return TranspConst.DocType.Transp.equals(doc.getDocType());
        }
        return false;
    }

    @Override
    protected String getTemplateFilePath(TranspDocument document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

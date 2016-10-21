package jp.co.sac.routineTaskSystem.manage.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.constant.Const;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.manage.DocumentManagerIF;
import jp.co.sac.routineTaskSystem.manage.SelectableManager;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Excelファイル操作の共通化
 *
 * @author shogo_saito
 */
public abstract class DocumentManager<T extends Document> implements DocumentManagerIF<T>, SelectableManager<Document>{

    private static Logger log = Logger.getLogger("root");

    /**
     * 書類を取得
     * 
     * @return 書類
     */
    @Override
    public T load (String filePath) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public T load(String title, FileInputStream in) throws Exception {
        T document;
        document = load(WorkbookFactory.create(in), title);
        document.setSaveType(Document.FileType.xls);
        document.setExtension(Document.FileType.xls.toString());
        return document;
    }

    public T load (File file) throws Exception {
        throw new UnsupportedOperationException();
    }

    public abstract T load(Workbook wb, String title);

    @Override
    public void save(T document) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(T document, String dirPath) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(T document, OutputStream out) throws Exception {
        setTitleOfDocument(document);
        Workbook wb = getTemplateWorkBook(document);
        if (wb == null) {
            log.warn("保存用のワークブックが存在しないため保存できませんでした[" + document.getTitle() + "]");
            return;
        }
        save(document, wb);
        wb.write(out);
    }

    public abstract void save(T document, Workbook wb);

    protected String[] getData(Sheet sheet, SheetMap sheetMap, int amount) {
        if (Const.Direction.APOINT.equals(sheetMap.getDirect())) {
            return getDataSingle(sheet, sheetMap);
        } else if (Const.Direction.DOWN.equals(sheetMap.getDirect())) {
            return getColumnDataAsc(sheet, sheetMap, amount);
        } else if (Const.Direction.RIGHT.equals(sheetMap.getDirect())) {
            return getRowDataAsc(sheet, sheetMap, amount);
        } else {
            return new String[1];
        }
    }

    protected String[] getRowDataAsc(Sheet sheet, SheetMap sheetMap, int amount) {
        String[] data = new String[amount];
        Row row = sheet.getRow(sheetMap.getPstRow());
        for (int index = 0; index < amount; index++) {
            data[index] = getCellData(row.getCell(index + sheetMap.getPstCol()), sheetMap);
        }
        return data;
    }

    protected String[] getColumnDataAsc(Sheet sheet, SheetMap sheetMap, int amount) {
        String[] data = new String[amount];
        for (int index = 0; index < amount; index++) {
            data[index] = getCellData(sheet.getRow(index + sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap);
        }
        return data;
    }

    protected String[] getDataSingle(Sheet sheet, SheetMap sheetMap) {
        String[] data = new String[1];
        data[0] = getCellData(sheet.getRow(sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap);
        return data;
    }

    protected String getCellData(Cell cell, SheetMap sheetMap) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
        } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
            return "error";
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (DataUtil.isDate(sheetMap)) {
                return cell.getDateCellValue().toString();
            } else if (DataUtil.isIntString(sheetMap)) {
                return DataUtil.convertToIntStringFromDouble(cell.getNumericCellValue());
            } else {
                return String.valueOf(cell.getNumericCellValue());
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return null;
        } else {
            return null;
        }
    }

    protected void setTitleOfDocument(T document) {
        if (DataUtil.isNullOrEmpty(document.getTitle())) {
            document.setTitle("TMP" + String.format("[yyMMddHHmmssSSS]", Calendar.getInstance()));
        }
        if (DataUtil.isNullOrEmpty(document.getExtension())) {
            document.setExtension("xls");
        }
    }

    protected void setTitleOfDocument(T document, String fileName) {
        String extension = DataUtil.getExtensionFromFilePath(fileName);
        document.setTitle(fileName);
        document.setExtension(extension);
    }

    protected void setData(Sheet sheet, SheetMap sheetMap, String[] data) {
        if (data == null) {
            return;
        }
        if (Const.Direction.APOINT.equals(sheetMap.getDirect())) {
            setDataSingle(sheet, sheetMap, data);
        } else if (Const.Direction.DOWN.equals(sheetMap.getDirect())) {
            setColumnDataAsc(sheet, sheetMap, data);
        } else if (Const.Direction.RIGHT.equals(sheetMap.getDirect())) {
            setRowDataAsc(sheet, sheetMap, data);
        }
    }

    protected void setRowDataAsc(Sheet sheet, SheetMap sheetMap, String[] data) {
        Row row = sheet.getRow(sheetMap.getPstRow());
        for (int index = 0; index < data.length; index++) {
            setCellData(row.getCell(index + sheetMap.getPstCol()), sheetMap, data[index]);
        }
    }

    protected void setColumnDataAsc(Sheet sheet, SheetMap sheetMap, String[] data) {
        for (int index = 0; index < data.length; index++) {
            setCellData(sheet.getRow(index + sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap, data[index]);
        }
    }

    protected void setDataSingle(Sheet sheet, SheetMap sheetMap, String[] data) {
        setCellData(sheet.getRow(sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap, data[0]);
    }

    protected void setCellData(Cell cell, SheetMap sheetMap, String value) {
        if (cell == null) {
            return;
        }
        if (sheetMap != null && Const.CellDataType.INT_STRING.equals(sheetMap.getType())) {
            if (value != null && DataUtil.isNumeric(value)) {
                cell.setCellValue(Double.parseDouble(value));
                return;
            }
        }
        cell.setCellValue(value);
    }

    protected Workbook getTemplateWorkBook(T document) {
        if (DataUtil.isNullOrEmpty(document.getYearMonthString())) {
            log.warn("could not get a templatefile cause by year and month is nothing.");
        }
        Path templateFilePath = FileSystems.getDefault().getPath(getTemplateFilePath(document));
        if (Files.exists(templateFilePath)) {
            try {
                try (InputStream in = new FileInputStream(templateFilePath.toFile())) {
                    return WorkbookFactory.create(in);
                }
            } catch (Exception ex) {
                log.error(ex);
            }
        }
        return null;
    }

    @Override
    public abstract boolean isTargetOf(String title);

    @Override
    public abstract boolean isTargetOf(Document document);

    protected abstract String getTemplateFilePath(T document);

    @Override
    public String getFileName(T document) {
        setTitleOfDocument(document);
        return document.getTitle() + ".xls";
    }
}

package jp.co.sac.routineTaskSystem.manage.document;

import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.constant.CommutConst;
import jp.co.sac.routineTaskSystem.entity.document.CommutDocument;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author shogo_saito
 */
public class CommutManager extends DocumentManager<CommutDocument> {

    @Override
    public CommutDocument load(Workbook wb, String title) {
        CommutDocument document = new CommutDocument();
        setTitleOfDocument(document, title);
        if (title != null && title.length() > 5) {
            int[] date = DataUtil.convertToIntFromYearAndMonthString(title.substring(0, 6));
            document.setYear(date[0]);
            document.setMonth(date[1]);
        }
        for (CommutConst.Category category : CommutConst.Category.values()) {
            String[] data = getData(wb.getSheetAt(0), category.getSheetMap(), document.getMaxDay());
            document.put(category, data);
        }
        return document;
    }

    @Override
    public void save(CommutDocument document, Workbook wb) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isTargetOf(String title) {
        if (!DataUtil.isNullOrEmpty(title)) {
            return title.contains("通勤費請求書");
        }
        return false;
    }

    @Override
    public boolean isTargetOf(Document document) {
        if (document != null) {
            return CommutConst.DocType.Commut == document.getDocType();
        }
        return false;
    }

    @Override
    protected String getTemplateFilePath(CommutDocument document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

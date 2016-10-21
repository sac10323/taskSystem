package jp.co.sac.routineTaskSystem.thread;

import javax.naming.OperationNotSupportedException;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.manage.document.DocumentManager;
import org.apache.log4j.Logger;

/**
 * マルチスレッド用（廃止）
 *
 * @author shogo_saito
 */
public class DocumentManagerThread extends Thread {

    public enum Action {
        load,
        save;
    }
    private Action action;
    private String filePath;
    private Document document;
    private DocumentManager documentManager;
    
    private Logger log = Logger.getLogger("root");

    @Override
    public void run() {
        try {
            switch (getAction()) {
                case load:
                    load();
                    break;
                case save:
                    save();
                    break;
                default:
                    throw new Exception("Actionが指定されていません。");
            }
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
        }
    }

    private void save() throws Exception {
        if (document == null) {
            throw new Exception("文書が空のため保存できません。");
        }
        if (documentManager == null) {
            throw new Exception("実行クラスが設定されていません。");
        }
        documentManager.save(document);
    }

    private void load() throws Exception {
        if (DataUtil.isNullOrEmpty(filePath)) {
            throw new Exception("ファイルパスが指定されていません。");
        }
        if (documentManager == null) {
            throw new Exception("実行クラスが設定されていません。");
        }
        document = documentManager.load(filePath);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public DocumentManager getDocumentManager() {
        return documentManager;
    }

    public void setDocumentManager(DocumentManager documentManager) {
        this.documentManager = documentManager;
    }
}

package jp.co.sac.routineTaskSystem.flow;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.common.FileUtil;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.constant.Const;
import jp.co.sac.routineTaskSystem.constant.RosterConst;
import jp.co.sac.routineTaskSystem.control.DocumentControllerIF;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.factory.DocumentControllerFactory;
import jp.co.sac.routineTaskSystem.log.Output;
import org.apache.log4j.Logger;

/**
 *
 * @author shogo_saito
 */
public class IMUpdateRosterFlow {

    private static Logger log = Logger.getLogger("root");

    private GeneralConfig config = GeneralConfig.getInstance();

    /**
     * 
     * @param filePath チェックするファイルのパス
     * @return システムの終了コード
     */
    public int onCheck(String[] filePaths) throws Exception {
        int exitStatus = 0;
        try {
            Output.getInstance().println("IMCSV → 勤務表自動作成開始");
            DocumentControllerIF docCtrl = DocumentControllerFactory.create();

            // 指定フォルダから勤務表読み込み
            Output.getInstance().println("CSV指定フォルダから勤務表読込開始");
            List<Document> docs = new ArrayList<>();
            String readDirPath = config.getString("imCsvFolder");
            if (!DataUtil.isNullOrEmpty(readDirPath)) {
                List<String> targets = FileUtil.getCheckTargetFilePaths(readDirPath, false);
                Map<String, String> pathMap = new ConcurrentHashMap<>();
                for (String filePath : targets) {
                    if (!"csv".equals(DataUtil.getExtensionFromFilePath(filePath))) {
                        continue;
                    }
                    String title = DataUtil.convertToTitleFromFilePath(filePath);
                    if (title != null && title.matches("\\d{8}")) {
                        docs.add(docCtrl.load(filePath));
                    }
                }
            }

            Output.getInstance().println("既存の勤務表読込開始");
            List<Document> oldDocs = new ArrayList<>();
            String outDirPath = config.getString("outputFolderXl", "out");
            if (!DataUtil.isNullOrEmpty(outDirPath)) {
                List<String> targets = FileUtil.getCheckTargetFilePaths(outDirPath, true);
                for (String filePath : targets) {
                    oldDocs.add(docCtrl.load(filePath));
                }
            }

            List<Document> saveDocs = new ArrayList<>();
            // 新旧ファイル突合せ
            Output.getInstance().println("勤務表突合せ処理開始");
            for (Document newDoc : docs) {
                boolean isUpdate = false;
                if (newDoc.getYearMonthString() != null) {
                    for (Document oldDoc : oldDocs) {
                        if (newDoc.getYearMonthString().equals(oldDoc.getYearMonthString())) {
                            // ファイル更新
                            updateDocument(oldDoc, newDoc);
                            saveDocs.add(oldDoc);
                            isUpdate = true;
                        }
                    }
                }
                if (!isUpdate) {
                    saveDocs.add(newDoc);
                }
            }

            // Excel出力
            Output.getInstance().println("Excel出力開始");
            if (!saveDocs.isEmpty()) {
                // フォルダ準備
                String dirPath = Const.getRootPath() + File.separator + outDirPath;
                FileUtil.prepareDirectory(dirPath);
                // Excel出力
                for (Document doc : saveDocs) {
                    try {
                        if (doc != null && RosterConst.DocType.Roster == doc.getDocType()) {
                            docCtrl.save(doc, Document.FileType.xls, dirPath);
                        }
                    } catch (Exception ex) {
                        Output.getInstance().println(" >> Excel出力エラー：[" + (doc == null ? null : doc.getTitle()) + "]");
                        log.warn("Excel書き出しでエラー発生", ex);
                    }
                }
            } else {
                Output.getInstance().println("出力ドキュメントなし");
            }

            Output.getInstance().println("IMCSV → 勤務表自動作成終了");
        } catch (Exception ex) {
            Output.getInstance().println("エラー発生：詳細はログフィルを参照");
            log.warn("エラー発生", ex);
            exitStatus = Const.EXIT_STATUS_EXCEPTION;
        }
        return exitStatus;
    }

    private void updateDocument(Document oldDoc, Document newDoc) {
        log.debug("突合せ前");
        Output.getInstance().printForDebug(new ArrayList<>(Arrays.asList(oldDoc, newDoc)));
        if ("*****".equals((String) oldDoc.get(RosterConst.Category.StaffId, 0))) {
            oldDoc.put(RosterConst.Category.StaffId, 0, newDoc.get(RosterConst.Category.StaffId, 0));
        }
        for (RosterConst.Category category : RosterConst.Category.valuesNoHaveDay()) {
            if (DataUtil.isNullOrEmpty((String) oldDoc.get(category, 0))) {
                oldDoc.put(category, 0, newDoc.get(category, 0));
            }
        }
        for (int index = 0; index < oldDoc.getMaxDay(); index++) {
            for (RosterConst.Category category : RosterConst.Category.valuesHaveDay()) {
                if (category == RosterConst.Category.FROM) {
                    if ("10:00".equals((String) oldDoc.get(category, index))) {
                        oldDoc.put(category, index, newDoc.get(category, index));
                    }
                } else if (category == RosterConst.Category.TO) {
                    if ("19:00".equals((String) oldDoc.get(category, index))) {
                        oldDoc.put(category, index, newDoc.get(category, index));
                    }
                }
                if (DataUtil.isNullOrEmpty((String) oldDoc.get(category, index))) {
                    oldDoc.put(category, index, newDoc.get(category, index));
                }
            }
        }
        log.debug("突合せ後");
        Output.getInstance().printForDebug(new ArrayList<>(Arrays.asList(oldDoc, newDoc)));
    }
}

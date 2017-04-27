package jp.co.sac.routineTaskSystem.flow;

import java.io.File;
import java.text.SimpleDateFormat;
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
import jp.co.sac.routineTaskSystem.entity.csv.extra.IMScheduleEntity;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;
import jp.co.sac.routineTaskSystem.factory.DocumentControllerFactory;
import jp.co.sac.routineTaskSystem.log.Output;
import jp.co.sac.routineTaskSystem.manage.csv.extra.IMScheduleManager;
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

            // スケジュール取得
            applicateSchedule(docs);

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

    private void applicateSchedule(List<Document> docs) {

        Output.getInstance().println("スケジュール取得開始");
        List<IMScheduleEntity> schList = new ArrayList<>();
        String schDirPath = config.getString("imScheduleFolder");
        if (!DataUtil.isNullOrEmpty(schDirPath)) {
            IMScheduleManager imSchMgr = new IMScheduleManager();
            List<String> targets = FileUtil.getCheckTargetFilePaths(schDirPath, false);
            for (String filePath : targets) {
                if (!"csv".equals(DataUtil.getExtensionFromFilePath(filePath))) {
                    continue;
                }
                String title = DataUtil.convertToTitleFromFilePath(filePath);
                if (title != null && title.matches("sch\\d{5}")) {
                    IMScheduleEntity sch = imSchMgr.load(filePath);
                    if (sch != null) {
                        schList.add(sch);
                    }
                }
            }
        }

        if (schList.isEmpty()) {
            return;
        }

        Output.getInstance().println("スケジュール適用開始");
        for (Document doc : docs) {

            IMScheduleEntity schs = null;
            String staffId = (String) doc.get(RosterConst.Category.StaffId, 0);

            for (IMScheduleEntity sch : schList) {
                if (sch.getTitle().equals("sch" + staffId)) {
                    schs = sch;
                }
            }

            if (schs == null) {
                continue;
            }

            String targetYearMonth = doc.getYearMonthString();
            if (DataUtil.isNullOrEmpty(targetYearMonth)) {
                log.error("スケジュール適用に失敗しました、勤務表に年月が設定されていません。 title:" + doc.getTitle());
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
            for (IMScheduleEntity sch : schs.records()) {
                if (sch.getDate() == null) {
                    continue;
                }
                String currentYM = sdf.format(sch.getDate());
                if (!targetYearMonth.equals(currentYM)) {
                    continue;
                }
                Integer day = DataUtil.convertToIntFromDate(sch.getDate())[2];
                if (DataUtil.isNullOrZero(day)) {
                    continue;
                }
                day--;
                if (!DataUtil.isNullOrEmpty(sch.getCause())) {
                    String cause = (String) doc.get(RosterConst.Category.Cause, day);
                    if (cause == null || !cause.contains(sch.getCause())) {
                        cause = (cause == null ? "" : cause) + sch.getCause();
                    }
                    doc.put(RosterConst.Category.Cause, day, cause);
                }
                if (!DataUtil.isNullOrEmpty(sch.getDestination())) {
                    String destination = (String) doc.get(RosterConst.Category.Destination, day);
                    if (destination == null || !destination.contains(sch.getDestination())) {
                        destination = (destination == null ? "" : destination) + sch.getDestination();
                    }
                    doc.put(RosterConst.Category.Destination, day, destination);
                }
            }
        }
    }
}

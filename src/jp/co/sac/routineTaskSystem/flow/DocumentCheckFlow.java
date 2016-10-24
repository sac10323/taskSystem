package jp.co.sac.routineTaskSystem.flow;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.common.FileUtil;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.constant.Const;
import jp.co.sac.routineTaskSystem.constant.RosterConst;
import jp.co.sac.routineTaskSystem.control.DocumentControllerIF;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;
import jp.co.sac.routineTaskSystem.factory.DocumentControllerFactory;
import jp.co.sac.routineTaskSystem.log.Output;
import org.apache.log4j.Logger;

/**
 * ローカル上で勤務表チェックする流れを集約
 *
 * @author shogo_saito
 */
public class DocumentCheckFlow {

    private static Logger log = Logger.getLogger("root");

    private GeneralConfig config = GeneralConfig.getInstance();

    /**
     * 
     * @param filePath チェックするファイルのパス
     * @return システムの終了コード
     */
    public int onCheck(String[] filePaths) throws Exception {
        int exitStatus = 0;
        Thread messageWindow = null;
        try {
            log.info(new Date());
            log.info(Const.getRootPath());
            Output.getInstance().println(" *** 勤務表チェックシステム *** ");
            List<Document> docs = new ArrayList<>();
            DocumentControllerIF docCtrl = DocumentControllerFactory.create();

            if (filePaths != null && filePaths.length != 0) {
                // 引数からファイルを読み込み
                for (String filePath : filePaths) {
                    docs.add(docCtrl.load(filePath));
                }
                if (docs.isEmpty()) {
                    Output.getInstance().println("ファイルが選択されていません");
                    return exitStatus;
                }
            } else {
                // 指定フォルダからファイルを読み込み
                String readDirPath = config.getString("checkFolder");
                if (!DataUtil.isNullOrEmpty(readDirPath)) {
                    readDirPath = Const.getRootPath() + File.separator + readDirPath;
                    List<String> targets = getCheckTargetFilePaths(readDirPath, true);
                    for (String filePath : targets) {
                        docs.add(docCtrl.load(filePath));
                    }
                }
                if (docs.isEmpty()) {
                    Output.getInstance().println("ファイルが存在しません");
                    return  exitStatus;
                }
            }


            Output.getInstance().printForDebug(docs);
            //設定ファイルから社員コードを読み込み
            Staff inspector = null;
            String userId = config.getString(GeneralConfig.Kind.userId);
            if (!DataUtil.isNullOrEmpty(userId) && DataUtil.isNumeric(userId)) {
                //社員コードがある場合は、点検者として設定する
                inspector = new Staff();
                inspector.setStaffId(Integer.parseInt(userId));
            }
            //読み込んだ書類をチェック
            Map docsMap = docCtrl.validateDocuments(docs, inspector);
            //チェック結果を表示
            messageWindow = Output.getInstance().print(docsMap);

            if (config.getBoolean("writeXl")) {
                // フォルダ準備
                String dirPath = getCreateDirPath(config.getString("outputFolderXl", "out"));
                FileUtil.prepareDirectory(dirPath);
                // Excel出力
                for (Document doc : docs) {
                    try {
                        if (doc != null && RosterConst.DocType.Roster == doc.getDocType()) {
                            docCtrl.save(doc, Document.FileType.xls, dirPath);
                        }
                    } catch (Exception ex) {
                        Output.getInstance().println(" >> Excel出力エラー：[" + (doc == null ? null : doc.getTitle()) + "]");
                        log.warn("Excel書き出しでエラー発生", ex);
                    }
                }
            }

            if (config.getBoolean(GeneralConfig.Kind.outputCsv)) {
                // フォルダ準備
                String dirPath = getCreateDirPath(config.getString("outputFolderCsv", "out"));
                FileUtil.prepareDirectory(dirPath);
                // CSV出力
                for (Document doc : docs) {
                    try {
                        if (doc != null && RosterConst.DocType.Roster == doc.getDocType()) {
                            docCtrl.save(doc, Document.FileType.csv, dirPath);
                        }
                    } catch (Exception ex) {
                        Output.getInstance().println(" >> CSV出力エラー：[" + (doc == null ? null : doc.getTitle()) + "]");
                        log.warn("CSV書き出しでエラー発生", ex);
                    }
                }
            }

            //チェックエラーがある場合は終了コードを設定する。
            if (DataUtil.hasNotValidDocument(docsMap)) {
                exitStatus = Const.EXIT_STATUS_CHECKERR;
            }

        } catch (Exception e) {
            //終了コードを設定
            exitStatus = Const.EXIT_STATUS_EXCEPTION;
            Output.getInstance().println(" *** エラーが発生しました。詳細はエラーログを参照してください。 *** ");
            log.warn("エラー詳細 -> \n", e);
        } finally {
            if (messageWindow != null) {
                //ダイアログ表示がある場合は、閉じるまで待機する。
                try {
                    messageWindow.join();
                } catch (Exception ex) {}
            }
            Output.getInstance().println(" *** 終了 *** ");
        }
        return exitStatus;
    }

    private List<String> getCheckTargetFilePaths(String dirPath, boolean recursive) {
        List<String> result = new ArrayList<>();
        File root = new File(dirPath);
        if (root.exists() && root.isDirectory()) {
            for (File file : getFiles(root)) {
                result.add(file.getPath());
            }
        }
        return result;
    }

    private List<File> getFiles(File rootDir) {
        List<File> result = new ArrayList<>();
        for( File file : rootDir.listFiles()) {
            if (file.exists()) {
                if (file.isDirectory()) {
                    result.addAll(getFiles(file));
                } else if (file.isFile()) {
                    result.add(file);
                }
            }
        }
        return result;
    }

    private String getCreateDirPath (String dirName) {
        return Const.getRootPath() + File.separator + dirName;
    }
}

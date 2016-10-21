package jp.co.sac.routineTaskSystem.log;

import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import org.apache.log4j.Logger;

/**
 * 画面表示機能を集約
 * log4jを利用して標準エラー出力として表示する。
 * 表示先などの設定は設定ファイルを使う。
 *
 * @author shogo_saito
 */
public class Output {

    private static Output instance = new Output();
    protected static final Logger log = Logger.getLogger("Output");
    protected static final Logger debugLog = Logger.getLogger("root");
    private static final int TEXT_WIDTH = 35;
    private static final String DIV_TITLE = "―";
    private static String divTitle = null;

    private Output() {
    }

    public static Output getInstance() {
        return instance;
    }

    public void print() {
    }

    public void print(Object o) {
//        System.out.print(o);
        log.info(String.valueOf(o));
    }

    public void println() {
//        System.out.println();
        print(String.valueOf(getLineSeparator()));
    }

    public void println(Object o) {
//        System.out.println(o);
        print(String.valueOf(o) + getLineSeparator());
    }

    public Thread print(Map<Document, List<Findings>> map) {
        Thread ret = null;
        String result = createShowCheckResult(map);
        if (GeneralConfig.isOutputMsgBoxMode()) {
            ret = showMessage(result);
        }
        println(result);
        return ret;
    }

    private String createShowCheckResult(Map<Document, List<Findings>> map) {
        StringBuilder sb = new StringBuilder();
        int maxPrintCount = GeneralConfig.getNumberOfShowFinds();
        sb.append("[チェック結果]").append(getLineSeparator());
        if (map.isEmpty()) {
            sb.append(" - no file... - ").append(getLineSeparator());
        } else {
            sb.append(getLineDivision()).append(getLineSeparator());
            for (Document document : map.keySet()) {
                if (document == null) {
                    sb.append("|").append("null").append("|");
                    sb.append(getLineSeparator());
                } else {
                    sb.append("|").append(document.getPrintTitle()).append("|");
                    sb.append(getLineSeparator());
                    sb.append("  チェック結果：");
                    if (map.get(document).isEmpty()) {
                        sb.append("OK");
                    } else {
                        sb.append("NG").append(getLineSeparator());
                        sb.append("  エラー個数：").append(map.get(document).size());
                    }
                    sb.append(getLineSeparator());
                }
                int prtCount = 0;
                for (Findings find : map.get(document)) {
                    if (maxPrintCount > 0 && prtCount >= maxPrintCount) {
                        sb.append("  and more... ").append(getLineSeparator());
                        break;
                    }
                    sb.append("・").append(find.getMessage()).append(getLineSeparator());
                    prtCount++;
                }
                sb.append(getLineDivision()).append(getLineSeparator());
            }
        }
        return sb.toString();
    }

    public Thread showMessage(String message) {
        try {
            ShowMessage showMessage = new ShowMessage();
            showMessage.setMessage(message);
            showMessage.start();
            return showMessage;
        } catch (Exception ex) {
            log.warn(message);
            return null;
        }
    }

    public void showDocumentForDebug(List<Document> docs) {
        for (Document document : docs) {
            showMessage(document.getPrintAllForDebug());
        }
    }

    public void printForDebug(List<Document> docs) {
        for (Document document : docs) {
            if (document == null) {
                debugLog.debug("document is null...");
            } else {
                debugLog.debug(document.getPrintAllForDebug());
            }
        }
    }

    private String getLineSeparator() {
        return System.lineSeparator();
    }

    private String getLineDivision() {
        if (divTitle == null) {
            StringBuilder sb = new StringBuilder();
            for (int num = 0; num < TEXT_WIDTH; num++) {
                sb.append(DIV_TITLE);
            }
            divTitle = sb.toString();
        }
        return divTitle;
    }
}

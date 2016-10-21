package jp.co.sac.routineTaskSystem.config;

import java.io.File;
import jp.co.sac.routineTaskSystem.constant.Const;

/**
 * 個人設定読み込みクラス
 *
 * @author shogo_saito
 */
public class GeneralConfig {

    private static boolean ready = false;
    private static final String CONFIG_PATH = Const.getRootPath() + File.separator + "settings.properties";
    private static String trueString = "T";
    private static String numberOfShowFindsKey = "numberOfShowFinds";
    private static int numberOfShowFinds = 0;
    private static boolean OutputMsgBox = false;
    private static String OutputMsgBoxKey = "OutputMsgBox";
    private static boolean outputCsv = false;
    private static String outputCsvKey = "outputCsv";
    private static String userId = "";
    private static String userIdKey = "sUserId";
    private static String outputPath = Const.getRootPath();
    private static String outputPathKey = "outputPathKey";
    private static boolean multiThread = false;
    private static String multiThreadKey = "multiThread";
    private static String rosterCSVFileNamePattern = "@YM_勤務表_@staffId_@name";
    private static String rosterCSVFileNamePatternKey = "rosterCSVFileNamePattern";
    private static PropertyManager manager = new PropertyManager(CONFIG_PATH);
    private static GeneralConfig instance = new GeneralConfig();

    public static GeneralConfig getInstance() {
        return instance;
    }

    private GeneralConfig() {
    }

    /**
     * 初期読み込み
     */
    private static void prepare() {
        if (!ready) {
            OutputMsgBox = manager.getBoolean(OutputMsgBoxKey, OutputMsgBox, trueString);
            userId = manager.getString(userIdKey, userId);
            outputPath = manager.getString(outputPathKey, outputPath);
            outputCsv = manager.getBoolean(outputCsvKey, outputCsv, trueString);
            numberOfShowFinds = manager.getInt(numberOfShowFindsKey, numberOfShowFinds);
            multiThread = manager.getBoolean(multiThreadKey, multiThread, trueString);
            rosterCSVFileNamePattern = manager.getString(rosterCSVFileNamePatternKey, rosterCSVFileNamePattern);
            ready = true;
        }
    }

    public String get(String key, String def) {
        return manager.getString(key, def);
    }

    public String get(String key) {
        return manager.getString(key);
    }

    public static boolean isOutputMsgBoxMode() {
        prepare();
        return OutputMsgBox;
    }

    public static boolean isOutputCsvMode() {
        prepare();
        return outputCsv;
    }

    public static String getUserId() {
        prepare();
        return userId;
    }

    public static String getOutputPath() {
        prepare();
        return outputPath;
    }

    public static int getNumberOfShowFinds() {
        prepare();
        return numberOfShowFinds;
    }

    public static boolean multiThread() {
        prepare();
        return multiThread;
    }

    public static String rosterCSVFileNamePattern() {
        prepare();
        return rosterCSVFileNamePattern;
    }
}

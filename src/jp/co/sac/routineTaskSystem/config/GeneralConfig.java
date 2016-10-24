package jp.co.sac.routineTaskSystem.config;

import java.io.File;
import jp.co.sac.routineTaskSystem.constant.Const;

/**
 * 個人設定読み込みクラス
 *
 * @author shogo_saito
 */
public class GeneralConfig {

    private static final String CONFIG_PATH = Const.getRootPath() + File.separator + "settings.properties";
    private static PropertyManager manager = new PropertyManager(CONFIG_PATH);
    private static GeneralConfig instance = new GeneralConfig();

    public enum Kind {
        numberOfShowFinds("numberOfShowFinds", "0"),
        outputMsgBox("OutputMsgBox", "false"),
        outputCsv("outputCsv", "false"),
        userId("sUserId", ""),
        outputPath("outputPathKey", Const.getRootPath()),
        rosterCsvFileNamePattern("rosterCSVFileNamePattern", "@YM_勤務表_@staffId_@name"),
        ;
        private String key;
        private String def;
        Kind(String key, String def) {
            this.key = key;
            this.def = def;
        }
        protected String getKey() {
            return key;
        }
        protected String getDef() {
            return def;
        }
    }

    public static GeneralConfig getInstance() {
        return instance;
    }

    private GeneralConfig() {
    }

    public String getString(Kind kind) {
        return getString(kind.getKey(), kind.getDef());
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String def) {
        return manager.getString(key, def);
    }

    public Integer getInt(Kind kind) {
        return getInt(kind.getKey(), Integer.valueOf(kind.getDef()));
    }

    public Integer getInt(String key) {
        return getInt(key, null);
    }

    public Integer getInt(String key, Integer def) {
        return manager.getInt(key, def);
    }

    public boolean getBoolean(Kind kind) {
        return getBoolean(kind.getKey(), Boolean.valueOf(kind.getDef()));
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        return manager.getBoolean(key, def, "T");
    }
}

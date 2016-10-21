package jp.co.sac.routineTaskSystem.constant;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.regex.Pattern;
import jp.co.sac.routineTaskSystem.log.Output;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;
import org.apache.log4j.Logger;

/**
 *
 * @author shogo_saito
 */
public class Const {

    // システム終了コード
    public static final int EXIT_STATUS_EXCEPTION = 1;       // 例外発生
    public static final int EXIT_STATUS_CHECKERR = -1;       // チェックエラーあり

    private static Logger log = Logger.getLogger("root");

    public static final int MAX_DAY = 31;
    private static Pattern numericPattern = Pattern.compile("\\d+(\\.\\d+)?");
    private static Pattern simpleNumericPattern = Pattern.compile("\\d+");
    public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm:ss";
    private static String rootPath = null;
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    public interface DocType {
    }

    /**
     * 内部データのカテゴリ
     */
    public interface Category {
        public SheetMap getSheetMap();
        public boolean needIndex();
    }

    /**
     * エクセルシート上のデータの方向
     */
    public enum Direction {

        APOINT, // 一点
        DOWN,   // 下方向
        RIGHT;  // 右方向
    }

    /**
     * エクセルシート上のデータ形式
     */
    public enum CellDataType {

        DEFAULT,
        DATE,
        INT_STRING,
        TIME_ROSTER,
        AUTHOR_NAME_ROSTER,
        DATE_TRANSP;
    }

    public enum DayOfWeek {

        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY;

        private static class ShortDay {

            public static String SUNDAY = "日";
            public static String MONDAY = "月";
            public static String TUESDAY = "火";
            public static String WEDNESDAY = "水";
            public static String THURSDAY = "木";
            public static String FRIDAY = "金";
            public static String SATURDAY = "土";
        }

        private static class LongDay {

            public static String SUNDAY = "日曜日";
            public static String MONDAY = "月曜日";
            public static String TUESDAY = "火曜日";
            public static String WEDNESDAY = "水曜日";
            public static String THURSDAY = "木曜日";
            public static String FRIDAY = "金曜日";
            public static String SATURDAY = "土曜日";
        }

        /**
         * 各曜日の定数 1(日)～7(土) 0はないので注意
         */
        private static class DayNumber {

            public static int SUNDAY = Calendar.SUNDAY;
            public static int MONDAY = Calendar.MONDAY;
            public static int TUESDAY = Calendar.TUESDAY;
            public static int WEDNESDAY = Calendar.WEDNESDAY;
            public static int THURSDAY = Calendar.THURSDAY;
            public static int FRIDAY = Calendar.FRIDAY;
            public static int SATURDAY = Calendar.SATURDAY;
        }

        /**
         * 同値メソッド（Calendarクラス対応版）
         *
         * @param obj
         * @return
         */
        public boolean equalsB(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj instanceof DayOfWeek) {
                return this.equals(obj);
            } else if (obj instanceof Integer) {
                return obj.equals(this.toInt());
            }
            return false;
        }

        public String toShortDay() {
            try {
                return ShortDay.class.getField(this.toString()).get(null).toString();
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                Output.getInstance().print(ex);
                return null;
            }
        }

        public String toLongDay() {
            try {
                return LongDay.class.getField(this.toString()).get(null).toString();
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                Output.getInstance().print(ex);
                return null;
            }
        }

        public int toInt() {
            try {
                return LongDay.class.getField(this.toString()).getInt(null);
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                Output.getInstance().print(ex);
                return -1;
            }
        }

        public boolean isWeekEnd() {
            return (this.equals(SUNDAY) || this.equals(SATURDAY));
        }
    }

    public static String getShortDay(DayOfWeek day) {
        return day == null ? null : day.toShortDay();
    }

    public static String getLongDay(DayOfWeek day) {
        return day == null ? null : day.toLongDay();
    }

    public static String getShortDay(int day) {
        return getShortDay(getDayOfWeek(day));
    }

    public static String getLongDay(int day) {
        return getLongDay(getDayOfWeek(day));
    }

    public static DayOfWeek getDayOfWeek(int day) {
        try {
            for (Field f : DayOfWeek.DayNumber.class.getFields()) {
                if (f.getInt(null) == day) {
                    return DayOfWeek.valueOf(f.getName());
                }
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            return null;
        }
        return null;
    }

    public static boolean isWeekEnd(int day) {
        return getDayOfWeek(day).isWeekEnd();
    }

    public static Pattern getNumericPattern() {
        return numericPattern;
    }

    public static Pattern getSimpleNumericPattern() {
        return simpleNumericPattern;
    }

    public static String getRootPath() {
        if (rootPath == null) {
            String jarPath = System.getProperty("java.class.path");
            String dirPath = jarPath.substring(0, jarPath.lastIndexOf(File.separator) + 1);
            //IDEで実行時にファイルパスがずれるので、ファイルパスを変更する
            if (dirPath.indexOf(";") > 0) {
                dirPath = System.getProperty("user.dir") + File.separator;
            }
            rootPath = dirPath;
        }
        return rootPath;
    }

    public static boolean isLinux() {
        return OS_NAME.startsWith("linux");
    }

    public static boolean isMac() {
        return OS_NAME.startsWith("mac");
    }

    public static boolean isWindows() {
        return OS_NAME.startsWith("windows");
    }

    public static boolean isSunOS() {
        return OS_NAME.startsWith("sunos");
    }
}
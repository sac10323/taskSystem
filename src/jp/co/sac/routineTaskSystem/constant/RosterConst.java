package jp.co.sac.routineTaskSystem.constant;

import com.orangesignal.csv.CsvConfig;
import java.util.ArrayList;
import java.util.List;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;

/**
 * 勤務表の定数
 *
 * @author shogo_saito
 */
public class RosterConst {

    public static final int MAX_DAY = 31;
    public static final String TITLE = "勤務表";
    public static final String[] DIV_DESTINATION = {",", "/", "、", "／", "・"};
    public static final String[] PARENTHESIS_FROM = {"(", "（"};
    public static final String[] PARENTHESIS_TO = {")", "）"};
    public static final String HEAD_OFFICE = "本社";
    public static final String TEMPLATE_FOLDER = "template";
    public static final String CSV_FOLDER = "csv";
    public static final String SAVE_FOLDER = "roster";
    public static final String FILE_NAME_TEMPLATE = "%ssacXXXXX";
    public static final String FILE_NAME_REGEX_PRE = "\\d{6}sacXXXXX";
    public static final String FILE_NAME_REGEX = "\\d{6}sac\\d{5}";
    public static final String FILE_NAME_PATTERN = "%ssac%s";
    public static final String PRINT_STRING = "%s %4d年 %2d月";
    public static final String FORMAT_TIME = "%1$Tk:%1$TM";
    public static final String[] File_TYPES = {"xls","csv"};
    public static final String[] EXCEPT_CHECK_SUPER_SIGN = {"10154"};
    public static final SheetMap lookUpStaffIdMap = new SheetMap(55, 8, 1, 300, Const.Direction.DOWN);
    public static final SheetMap lookUpAuthorNameMap = new SheetMap(56, 8, 1, 300, Const.Direction.DOWN);
    public static final String LAST_STAFF_ID = "xxxxx";
    public static final String NO_FILE_PATH = "ファイルパスが指定されませんでした。";
    public static final String ERR_EXTENSION = "形式外のファイルです。拡張子[%s]";

    /*
     * 休暇等
     */
    public static class Cause {

        public static final String PAID_HOLIDAY = "有休";
        public static final String HALF_HOLIDAY = "半休";
        public static final String COMPENSATORY_DAY = "代休";
        public static final String SPECIAL_HOLIDAY = "特休";
        public static final String ABSENCE = "欠勤";
        public static final String DELAY = "遅刻";
        public static final String LEAVE_EARLY = "早退";
        public static final String WORK_HORIDAY = "休出";
        public static final String UNEXCUSED_HOLIDAY = "無欠";
        public static final String SUBSTITUTE_HOLIDAY = "振休";
        public static final String SUBSTITUTE_WORK = "振出";
    }

    /*
     * バリデートの結果
     */
    public static class FindMessage {

        public static final String NO_FILE = "ファイルがありません。";
        public static final String UN_SUPPORT_FILE = "対応外のファイル形式の可能性があります。";
        public static final String ERR_FILE_PART = "ファイルエラーがあります。";
        public static final String ERR_TITLE = "ファイル名が不正[%s]";
        public static final String NO_NAME = "名前が未入力";
        public static final String NO_LOCATION = "勤務地が未入力";
        public static final String NO_STAFF_ID = "社員コードが未入力";
        public static final String NO_TIME_TO = "%d日の退社が未入力";
        public static final String NO_TIME_FROM = "%d日の出社が未入力";
        public static final String NO_DESTINATION = "%d日の行き先（常駐先）欄が未入力";
        public static final String ERR_DESTINATION = "%d日の行き先（常駐先）欄の記述形式に問題がある可能性があります。";
        public static final String NO_REASON_HORIDAY = "%d日の休暇理由が未入力";
        public static final String ERR_REASON_HORIDAY = "%d日の行き先（常駐先）欄の休暇理由が未入力の可能性があります。";
        public static final String ERR_TIME_HORIDAY = "%d日の%sで勤務時間が入力されています。";
        public static final String NO_TIME_WORK_HORIDAY = "%d日の" + Cause.WORK_HORIDAY + "で勤務時間が未入力";
        //TODO:A変・B変の出社時間
        public static final String NO_REASON_PARTIALWORK = "%d日の%s理由が未入力";
        public static final String ERR_REASON_PARTIALWORK = "%d日の行き先（常駐先）欄の%s理由が未入力の可能性があります。";
        public static final String ERR_TIME_PARTIALWORK = "%d日の%sで勤務時間が入力されていません。";
        public static final String NO_SIGN_SUPER = "所属長サインが未入力";
        public static final String NO_SIGN_AUTHOR = "記入者サインが未入力";
    }

    public enum DocType implements Const.DocType {
        Roster;
    }

    /*
     * 入力項目のカテゴリ
     */
    public enum Category implements Const.Category {

        AuthorName (false, new SheetMap(2, 5, 2, 1, Const.Direction.APOINT, Const.CellDataType.AUTHOR_NAME_ROSTER)),
        AuthorSign (false, new SheetMap(14, 60, 3, 3, Const.Direction.APOINT)),
        SuperSign (false, new SheetMap(11, 60, 3, 3, Const.Direction.APOINT)),
        WorkLocation (false, new SheetMap(2, 2, 2, 1, Const.Direction.APOINT)),
        StaffId (false, new SheetMap(2, 4, 2, 1, Const.Direction.APOINT, Const.CellDataType.INT_STRING)),
        FROM (true, new SheetMap(2, 7, 1, 1, Const.Direction.DOWN, Const.CellDataType.TIME_ROSTER)),
        TO (true, new SheetMap(3, 7, 1, 1, Const.Direction.DOWN, Const.CellDataType.TIME_ROSTER)),
        StrangeA (true, new SheetMap(4, 7, 1, 1, Const.Direction.DOWN, Const.CellDataType.INT_STRING)),
        StrangeB (true, new SheetMap(5, 7, 1, 1, Const.Direction.DOWN, Const.CellDataType.INT_STRING)),
        Cause (true, new SheetMap(12, 7, 3, 1, Const.Direction.DOWN)),
        Destination (true, new SheetMap(13, 7, 3, 1, Const.Direction.DOWN)),
        ;
        private final transient boolean needIndex;
        private final transient SheetMap sheetMap;

        Category(boolean needIndex, SheetMap sheetMap) {
            this.needIndex = needIndex;
            this.sheetMap = sheetMap;
        }

        /**
         * 日付項目チェック
         *
         * @param category 項目
         * @return 日付を持つか
         */
        public static boolean haveDay(Category category) {
            return category == null ? false : category.needIndex;
        }

        /**
         * 日付関連の項目を一括取得
         *
         * @return
         */
        public static Category[] valuesHaveDay() {
            List<Category> catList = new ArrayList<>();
            for (Category cat : Category.values()) {
                if (cat.needIndex()) {
                    catList.add(cat);
                }
            }
            return toCategories(catList.toArray());
        }

        /**
         * 日付非関連の項目を一括取得
         *
         * @return
         */
        public static Category[] valuesNoHaveDay() {
            List<Category> catList = new ArrayList<>();
            for (Category cat : Category.values()) {
                if (!cat.needIndex()) {
                    catList.add(cat);
                }
            }
            return toCategories(catList.toArray());
        }

        private static Category[] toCategories(Object[] values) {
            if(values == null || values.length == 0) {
                return new Category[0];
            }
            Category[] categories = new Category[values.length];
            for (int i = 0; i < values.length; i++) {
                categories[i] = (Category)values[i];
            }
            return categories;
        }

        @Override
        public SheetMap getSheetMap() {
            return sheetMap;
        }

        @Override
        public boolean needIndex() {
            return needIndex;
        }
    }

    /**
     * CSVファイル読み込み時の詳細設定
     * @return 
     */
    public static CsvConfig getLoadCsvConfig() {
        CsvConfig csvConfig = new CsvConfig(',', '\"', '\"');
        csvConfig.setUtf8bomPolicy(false);
        csvConfig.setVariableColumns(true);
        csvConfig.setIgnoreEmptyLines(true);
        csvConfig.setIgnoreCaseNullString(true);
        return csvConfig;
    }

    /**
     * CSVファイル出力時の詳細設定
     * @return 
     */
    public static CsvConfig getWriteCsvConfig() {
        CsvConfig csvConfig = new CsvConfig(',', '\"', '\"');
        csvConfig.setUtf8bomPolicy(false);
        return csvConfig;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.sac.routineTaskSystem.constant;

import jp.co.sac.routineTaskSystem.log.Output;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;

/**
 * 交通費請求書の定数
 *
 * @author shogo_saito
 */
public class TranspConst {
    public static final int MAX_DAY = 40;
    public static final String FILE_NAME_REGEX_PRE = "\\d{6}sac00000";
    public static final String FILE_NAME_REGEX = "\\d{6}交通費請求明細書\\d{5}";
    public static final String FILE_NAME_PATTERN = "%s交通費請求明細書%s";
    public static final String FILE_NAME_TEMPLATE = "YYYYMM交通費請求明細書XXXXX";
    public static final String SHEET_NAME_PATTERN = "%04d年%02d月";

    /*
     * バリデートの結果
     */
    public static class FindMessage {

        public static final String NO_FILE = "ファイルがありません。";
        public static final String UN_SUPPORT_FILE = "対応外のファイル形式の可能性があります。";
        public static final String ERR_FILE_PART = "ファイルエラーがあります。";
        public static final String ERR_TITLE = "ファイル名が不正[%s]";
        public static final String NO_HEADER = "ヘッダの年月が未入力";
        public static final String NO_STAFF_ID = "社員IDが未入力";
        public static final String NO_AFFILIATION = "所属が未入力";
        public static final String ERR_AFFILIATION = "所属が誤っています";
        public static final String NO_NAME = "氏名が未入力";
        public static final String NO_PERIOD = "期間が未入力";
        public static final String NO_BUSINESS = "%d日の業務欄が未入力";
        public static final String NO_DESTINATION = "%d日の行先欄が未入力";
        public static final String NO_VEHICLE = "%d日の乗物欄が未入力";
        public static final String NO_DEPARTURE = "%d日の発欄が未入力";
        public static final String NO_ARRIVING = "%d日の着欄が未入力";
        public static final String NO_AMOUNT = "%d日の金額欄が未入力";
        public static final String ERR_AMOUNT = "%d日の金額欄が不正です";
    }

    public enum DocType implements Const.DocType {
        Transp;
    }

    public enum Category implements Const.Category {

        Header,
        StaffId,
        Affiliation,
        Name,
        Period,
        MonthAndDay,
        Business,
        Destination,
        Vehicle,
        Departure,
        Arriving,
        Amount,
        ;

        /*
         * エクセルシート内の座標情報
         */
        private static class SheetMaps {

            public static final SheetMap Header = new SheetMap(5, 4, 1, 1, Const.Direction.APOINT);
            public static final SheetMap StaffId = new SheetMap(2, 7, 1, 1, Const.Direction.APOINT, Const.CellDataType.INT_STRING);
            public static final SheetMap Affiliation = new SheetMap(3, 7, 1, 1, Const.Direction.APOINT);
            public static final SheetMap Name = new SheetMap(4, 7, 1, 1, Const.Direction.APOINT);
            public static final SheetMap Period = new SheetMap(7, 7, 1, 1, Const.Direction.APOINT);
            public static final SheetMap MonthAndDay = new SheetMap(2, 10, 1, 1, Const.Direction.DOWN, Const.CellDataType.DATE_TRANSP);
            public static final SheetMap Business = new SheetMap(3, 10, 1, 1, Const.Direction.DOWN);
            public static final SheetMap Destination = new SheetMap(4, 10, 1, 1, Const.Direction.DOWN);
            public static final SheetMap Vehicle = new SheetMap(5, 10, 1, 1, Const.Direction.DOWN);
            public static final SheetMap Departure = new SheetMap(6, 10, 1, 1, Const.Direction.DOWN);
            public static final SheetMap Arriving = new SheetMap(10, 10, 1, 1, Const.Direction.DOWN);
            public static final SheetMap Amount = new SheetMap(13, 10, 1, 1, Const.Direction.DOWN, Const.CellDataType.INT_STRING);
        }

        @Override
        public SheetMap getSheetMap() {
            try {
                return (SheetMap)SheetMaps.class.getField(this.toString()).get(null);
            } catch (IllegalAccessException| IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                Output.getInstance().print(ex);
                return null;
            }
        }

        @Override
        public boolean needIndex() {
            switch (this) {
                case MonthAndDay:
                case Business:
                case Destination:
                case Vehicle:
                case Departure:
                case Arriving:
                case Amount:
                    return true;
                default:
                    return false;
            }
        }
        
    }
}

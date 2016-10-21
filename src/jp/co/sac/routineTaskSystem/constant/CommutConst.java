package jp.co.sac.routineTaskSystem.constant;

import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;

/**
 *
 * @author shogo_saito
 */
public class CommutConst {
    // XXX:暫定
    public static final int MAX_DAY = 4;

    public enum DocType implements Const.DocType {
        Commut;
    }

    public enum Category implements Const.Category {
        Header (false, new SheetMap(10, 1, 1, 1, Const.Direction.APOINT)),
        Affiliation (false, new SheetMap(2, 4, 1, 1, Const.Direction.APOINT)),
        Name (false, new SheetMap(6, 4, 1, 1, Const.Direction.APOINT)),
        Address (false, new SheetMap(2, 5, 1, 1, Const.Direction.APOINT)),
        WorkLocation (false, new SheetMap(2, 6, 1, 1, Const.Direction.APOINT)),
        CommutDayFrom (false, new SheetMap(10, 6, 1, 1, Const.Direction.APOINT, Const.CellDataType.DATE_TRANSP)),
        Year (true, new SheetMap(2, 11, 1, 1, Const.Direction.DOWN, Const.CellDataType.INT_STRING)),
        Month (true, new SheetMap(3, 11, 1, 1, Const.Direction.DOWN, Const.CellDataType.INT_STRING)),
        Price1 (true, new SheetMap(4, 11, 1, 1, Const.Direction.DOWN, Const.CellDataType.INT_STRING)),
        Price2 (true, new SheetMap(5, 11, 1, 1, Const.Direction.DOWN, Const.CellDataType.INT_STRING)),
        Price3 (true, new SheetMap(6, 11, 1, 1, Const.Direction.DOWN, Const.CellDataType.INT_STRING)),
        Price4 (true, new SheetMap(7, 11, 1, 1, Const.Direction.DOWN, Const.CellDataType.INT_STRING)),
        Price5 (true, new SheetMap(8, 11, 1, 1, Const.Direction.DOWN, Const.CellDataType.INT_STRING)),
        Price6 (true, new SheetMap(9, 11, 1, 1, Const.Direction.DOWN, Const.CellDataType.INT_STRING)),
        SumPrice (true, new SheetMap(10, 11, 1, 1, Const.Direction.DOWN)),
        ;
        private final transient boolean needIndex;
        private final transient SheetMap sheetMap;

        Category(boolean needIndex, SheetMap sheetMap) {
            this.needIndex = needIndex;
            this.sheetMap = sheetMap;
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
}

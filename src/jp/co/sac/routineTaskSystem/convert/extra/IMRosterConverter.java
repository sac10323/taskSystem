package jp.co.sac.routineTaskSystem.convert.extra;

import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.constant.RosterConst;
import jp.co.sac.routineTaskSystem.convert.DocumentConverter;
import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;
import jp.co.sac.routineTaskSystem.entity.csv.extra.IMCSVEntity;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;

/**
 *
 * @author shogo_saito
 */
public class IMRosterConverter extends DocumentConverter<RosterDocument, IMCSVEntity> {
    @Override
    public RosterDocument toDocumentEntity(IMCSVEntity entity) {
        RosterDocument document = new RosterDocument();
        if (entity == null) {
            return document;
        }
        if (DataUtil.isNullOrEmpty(entity.getTitle())) {
            return document;
        }

        String staffId = GeneralConfig.getInstance().getString(GeneralConfig.Kind.userId);
        if (DataUtil.isNullOrEmpty(staffId) || !DataUtil.isNumeric(staffId)) {
            staffId = "XXXXX";
        } else {
            document.put(RosterConst.Category.StaffId, 0, staffId);
        }

        document.setTitle(entity.getTitle());
        if (entity.getTitle().length() > 5) {
            int[] time = DataUtil.convertToIntFromYearAndMonthString(entity.getTitle().substring(0, 6));
            document.setYear(time[0]);
            document.setMonth(time[1]);
            document.setMaxDay(DataUtil.getMaxDayOfMonth(time[0], time[1]));
        }

        document.setTitle(String.format(RosterConst.FILE_NAME_PATTERN, document.getYearMonthString(), staffId));

        // バリデート回避のため
        document.setExtension("csv");
        document.setSaveType(Document.FileType.none);

        document.put(RosterConst.Category.WorkLocation, 0, "東京");

        if (DataUtil.isNullOrEmpty(entity.records())) {
            return document;
        }

        String name = GeneralConfig.getInstance().getString("imName");
        if (DataUtil.isNullOrEmpty(name)) {
            return document;
        }

        boolean hasWorkHoliday = GeneralConfig.getInstance().getBoolean("hasWorkHoliday", true);

        for (IMCSVEntity record : entity.records()) {

            if (!name.equals(record.getName())) {
                continue;
            }

            int[] from = DataUtil.convertToIntFromDate(record.getFrom());
            int[] to = DataUtil.convertToIntFromDate(record.getTo());

            int index = from == null ? -1 : from[2] - 1;

            // 開始時刻
            if (from != null) {
                int hour = from[3];
                int minute = from[4];
                if (hour < 9) {
                    hour = 9;
                    minute = 0;
                }
                if (minute == 0) {
                } else if (minute < 30) {
                    minute = 30;
                } else {
                    hour = hour + 1;
                    minute = 0;
                }
                document.put(RosterConst.Category.FROM, index, hour + ":" + ("0" + minute).substring(minute < 10 ? 0 : 1));
            }

            // 終了時刻
            if (to != null) {
                int hour = to[3];
                int minute = to[4];
                if (minute < 30) {
                    minute = 0;
                } else {
                    minute = 30;
                }
                document.put(RosterConst.Category.TO, index, hour + ":" + ("0" + minute).substring(minute < 10 ? 0 : 1));
            }

            // 事由
            if (from != null && to != null) {
                // 出社が遅い（遅刻・午前休）
                if (from[3] > 9 || (from[3] == 9 && from[4] > 0)) {
                    if (from[3] >= 12) {
                        document.put(RosterConst.Category.Cause, index, hasWorkHoliday ? RosterConst.Cause.HALF_HOLIDAY : RosterConst.Cause.DELAY);
                    } else {
                        document.put(RosterConst.Category.Cause, index, RosterConst.Cause.DELAY);
                    }
                }
                // 勤務終了が早い（早退・午後休）
                if (to[3] < 18) {
                    if (to[3] <= 16) {
                        document.put(RosterConst.Category.Cause, index, hasWorkHoliday ? RosterConst.Cause.HALF_HOLIDAY : RosterConst.Cause.LEAVE_EARLY);
                    } else {
                        document.put(RosterConst.Category.Cause, index, RosterConst.Cause.LEAVE_EARLY);
                    }
                }
            }

            // 行き先
            if (from != null) {
                document.put(RosterConst.Category.Destination, index, "浜松町(インフォマート)");
            }
        }
        // TODO:営業日判定して有休・欠勤自動設定（バリデートと同時利用で入力を促す）
        return document;
    }

    @Override
    public IMCSVEntity toCSVEntity(RosterDocument doc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isTargetOf(Document doc) {
        return false;
    }

    @Override
    public boolean isTargetOf(CSVEntity csv) {
        return csv instanceof IMCSVEntity;
    }
}

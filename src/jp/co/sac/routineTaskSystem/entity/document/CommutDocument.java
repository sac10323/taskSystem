package jp.co.sac.routineTaskSystem.entity.document;

import jp.co.sac.routineTaskSystem.constant.CommutConst;

/**
 *
 * @author shogo_saito
 */
public class CommutDocument extends Document<CommutConst.Category, String> {

    public CommutDocument() {
        super();
        setDocType(CommutConst.DocType.Commut);
        setMaxDay(CommutConst.MAX_DAY);
    }

    @Override
    public String getPrintTitle() {
        return title == null ? "null" : title;
    }

    @Override
    public String getPrintAllForDebug() {
        StringBuilder sb = new StringBuilder();
        sb.append("# title -> ").append(getTitle()).append(System.lineSeparator());
        sb.append("# extension -> ").append(getExtension()).append(System.lineSeparator());
        sb.append("# year -> ").append(getYear()).append(System.lineSeparator());
        sb.append("# month -> ").append(getMonth()).append(System.lineSeparator());
        sb.append("# maxday -> ").append(getMaxDay()).append(System.lineSeparator());
        sb.append("# fileError -> ").append(getFileError()).append(System.lineSeparator());
        sb.append("# data ").append(System.lineSeparator());
        for (CommutConst.Category key : CommutConst.Category.values()) {
            if (!key.needIndex()) {
                sb.append("# ").append(key).append(" -> ").append(get(key, 0)).append(System.lineSeparator());
            }
        }
        for (int idx = 0; idx < getMaxDay(); idx++) {
            sb.append("# [").append(String.format("%2d", idx)).append("] -> ");
            for (CommutConst.Category key : CommutConst.Category.values()) {
                if (key.needIndex()) {
                    sb.append(key).append(" : ").append(get(key, idx)).append(" ; ");
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}

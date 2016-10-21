package jp.co.sac.routineTaskSystem.entity.document;

import jp.co.sac.routineTaskSystem.constant.TranspConst;

/**
 * 交通費クラス
 *
 * @author shogo_saito
 */
public class TranspDocument extends Document<TranspConst.Category, String> {

    public TranspDocument() {
        super();
        setDocType(TranspConst.DocType.Transp);
        // インデックスとして使用されるので用紙の枠数で初期化。
        setMaxDay(TranspConst.MAX_DAY);
    }

    /**
     * 表示用タイトルを取得
     * 
     * @return 表示用タイトル
     */
    @Override
    public String getPrintTitle() {
        return title == null ? "null" : title;
    }

    @Override
    public String[] get(TranspConst.Category key) {
        return super.get(key);
    }

    @Override
    public String get(TranspConst.Category key, int idx) {
        return super.get(key, idx);
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
        for (TranspConst.Category key : TranspConst.Category.values()) {
            if (!key.needIndex()) {
                sb.append("# ").append(key).append(" -> ").append(get(key, 0)).append(System.lineSeparator());
            }
        }
        for (int idx = 0; idx < getMaxDay(); idx++) {
            sb.append("# [").append(String.format("%2d", idx)).append("] -> ");
            for (TranspConst.Category key : TranspConst.Category.values()) {
                if (key.needIndex()) {
                    sb.append(key).append(" : ").append(get(key, idx)).append(" ; ");
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
    
}

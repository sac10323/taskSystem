package jp.co.sac.routineTaskSystem.entity.document;

import jp.co.sac.routineTaskSystem.constant.Const;

/**
 * 書類ダミークラス
 *
 * @author shogo_saito
 */
public class DummyDocument extends Document<Const.Category, Object> {

    public DummyDocument() {
        super();
        setExtension(null);
    }
}

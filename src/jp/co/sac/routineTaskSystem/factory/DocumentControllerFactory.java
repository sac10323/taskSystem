package jp.co.sac.routineTaskSystem.factory;

import jp.co.sac.routineTaskSystem.control.DocumentControllerIF;
import jp.co.sac.routineTaskSystem.control.impl.DocumentControllerImpl;

/**
 * 書類ファイル操作用のインスタンス作成
 *
 * @author shogo_saito
 */
public class DocumentControllerFactory {
    public static DocumentControllerIF create() {
        return new DocumentControllerImpl();
    }
}

package jp.co.sac.routineTaskSystem.control;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;

/**
 * ファイル操作の窓口を集約<br>
 *
 * @author shogo_saito
 */
public interface DocumentControllerIF {
    /**
     * ファイル読み込み<br>
     * ファイル種別、データ形式を意識しないで読み込める。<br>
     * 未対応、対象外のファイルは返却するインスタンスにエラー情報を保持。<br>
     * 
     * @param filePath 読込対象ファイルのパス
     * @return 書類 {@code not null}
     * @throws Exception 想定なし 
     */
    Document load(String filePath) throws Exception;

    /**
     * 書類をファイルストリームから読み込む。<br>
     * 未対応、対象外のファイルは返却するインスタンスにエラー情報を保持。<br>
     * 
     * @param fileName ファイル名
     * @param in 入力ストリーム
     * @return 書類
     * @throws Exception 
     */
    Document load(String fileName, FileInputStream in) throws Exception;

    /**
     * 書類を出力先ディレクトリを指定してストリーム出力<br>
     * 
     * @param document 書類
     * @param saveType 保存方法
     * @param dirPath 出力先ディレクトリ
     * @throws Exception 
     */
    void save(Document document, Document.FileType saveType, String dirPath) throws Exception;

    /**
     * 書類をファイル形式を指定してストリーム出力<br>
     * 
     * @param document 書類
     * @param saveType 保存方法
     * @param out 出力先ストリーム
     * @throws Exception 
     */
    void save(Document document, Document.FileType saveType, OutputStream out) throws Exception;

    /**
     * 書類をバリデート<br>
     * 
     * @param document 書類
     * @param inspector 点検者(バリデート実行者)
     * @return バリデート結果
     */
    List<Findings> validateDocument(Document document, Staff inspector);

    /**
     * 書類を一括バリデート<br>
     * 
     * @param documents 書類
     * @param inspector 点検者(バリデート実行者)
     * @return 書類とバリデート結果のマップ
     */
    Map<Document, List<Findings>> validateDocuments(List<Document> documents, Staff inspector);
}

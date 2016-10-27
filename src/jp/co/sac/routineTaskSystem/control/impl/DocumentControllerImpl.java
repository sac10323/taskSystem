package jp.co.sac.routineTaskSystem.control.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.control.DocumentControllerIF;
import jp.co.sac.routineTaskSystem.control.ValidatorControllerIF;
import jp.co.sac.routineTaskSystem.control.command.DocumentCommand;
import jp.co.sac.routineTaskSystem.control.selector.Selector;
import jp.co.sac.routineTaskSystem.control.selector.impl.CommandSelector;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.document.DummyDocument;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;
import org.apache.log4j.Logger;

/**
 * 書類操作の窓口実装クラス
 * 書類のインスタンスと対応する操作を呼び出す
 *
 * @author shogo_saito
 */
public class DocumentControllerImpl implements DocumentControllerIF {

    Logger log = Logger.getLogger("root");
    Selector selector = new Selector();

    @Override
    public Document load(String filePath) throws Exception {
        Document document = null;
        if (DataUtil.isFilePath(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    document = load(DataUtil.convertToFileNameFromFilePath(filePath), fis);
                    document.setDirPath(DataUtil.convertToDirPathFromFilePath(filePath));
                } catch (Exception ex) {
                    log.warn("DocumentControllerImpl#load(String)", ex);
                }
            }
        }
        if (document == null) {
            document = failedLoad(filePath);
        }
        return document;
    }

    private Document failedLoad(String filePath) {
        Document document = new DummyDocument();
        document.setTitle(DataUtil.convertToTitleFromFilePath(filePath));
        document.setDirPath(DataUtil.convertToDirPathFromFilePath(filePath));
        if (!DataUtil.isFilePath(filePath)) {
            document.setFileError("ファイルパスが不正な可能性があります。");
        } else if (!Files.exists(Paths.get(filePath))) {
            document.setFileError("ファイルが存在しません。");
        }
        return document;
    }

    @Override
    public Document load(String fileName, FileInputStream in) throws Exception {
        Document document = null;
        try {
            String extension = DataUtil.getExtensionFromFilePath(fileName);
            String title = DataUtil.convertToTitleFromFilePath(fileName);
            DocumentCommand<Document> command = new CommandSelector().getCommand(extension);
            if (command != null) {
                document = command.load(title, in);
            }
        } catch (Exception ex) {
            log.warn("DocumentControllerImpl#load(String, FileInputStream)", ex);
        }
        if (document == null) {
            document = new DummyDocument();
            document.setTitle(DataUtil.convertToTitleFromFilePath(fileName));
            document.setFileError("ファイルを読み込めませんでした");
        }
        return document;
    }

    @Override
    public void save(Document document, Document.FileType saveType, String dirPath) throws Exception {
        if (document == null) {
            return;
        }
        DocumentCommand<Document> command = new CommandSelector().getCommand(saveType);
        if (command != null) {
            File file = new File(dirPath + File.separator + command.getFileName(document));
            try (FileOutputStream out = new FileOutputStream(file)) {
                save(document, saveType, out);
            }
        }
    }

    @Override
    public void save(Document document, Document.FileType saveType, OutputStream out) throws Exception {
        if (document == null) {
            return;
        }
        DocumentCommand<Document> command = new CommandSelector().getCommand(saveType);
        if (command != null) {
            command.save(document, out);
        }
    }

    @Override
    public List<Findings> validateDocument(Document document, Staff inspector) {
        ValidatorControllerIF vdtCtrl = new ValidatorControllerImpl();
        return vdtCtrl.doValidate(document, inspector);
    }

    @Override
    public Map<Document, List<Findings>> validateDocuments(List<Document> documents, Staff inspector) {
        Map<Document, List<Findings>> map = new HashMap<>();
        if (documents != null) {
            for (Document doc : documents) {
                map.put(doc, validateDocument(doc, inspector));
            }
        }
        return map;
    }
}

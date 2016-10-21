package jp.co.sac.routineTaskSystem.control.command.impl;

import java.io.FileInputStream;
import java.io.OutputStream;
import jp.co.sac.routineTaskSystem.control.command.DocumentCommand;
import jp.co.sac.routineTaskSystem.control.selector.Selector;
import jp.co.sac.routineTaskSystem.convert.DocumentConverter;
import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.manage.CSVManagerIF;

/**
 *
 * @author shogo_saito
 */
public class CsvCommand implements DocumentCommand<Document> {

    Selector selector = new Selector();

    @Override
    public Document load(String title, FileInputStream in) {
        CSVManagerIF csvManager = new Selector().getCSVManager(title);
        if (csvManager != null) {
            CSVEntity entity = csvManager.load(title, in);
            if (entity != null) {
                DocumentConverter docConverter = selector.getConverter(entity);
                return docConverter.toDocumentEntity(entity);
            }
        }
        return null;
    }

    @Override
    public void save(Document document, OutputStream out) throws Exception {
        DocumentConverter docConverter = selector.getConverter(document);
        CSVEntity entity = docConverter.toCSVEntity(document);
        CSVManagerIF csvManager = new Selector().getCSVManager(document);
        csvManager.write(entity, out);
    }

    @Override
    public String getFileName(Document document) {
        DocumentConverter docConverter = selector.getConverter(document);
        CSVEntity entity = docConverter.toCSVEntity(document);
        CSVManagerIF csvManager = new Selector().getCSVManager(document);
        return csvManager.getFileName(entity);
    }
    
}

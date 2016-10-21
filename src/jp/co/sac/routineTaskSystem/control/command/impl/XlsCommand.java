/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.sac.routineTaskSystem.control.command.impl;

import java.io.FileInputStream;
import java.io.OutputStream;
import jp.co.sac.routineTaskSystem.control.command.DocumentCommand;
import jp.co.sac.routineTaskSystem.control.selector.Selector;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.manage.DocumentManagerIF;
import org.apache.log4j.Logger;

/**
 *
 * @author shogo_saito
 */
public class XlsCommand implements DocumentCommand<Document> {

    Logger log = Logger.getLogger("root");
    Selector selector = new Selector();

    @Override
    public Document load(String title, FileInputStream in) {
        DocumentManagerIF docManager = selector.getDocumentManager(title);
        if (docManager != null) {
            try {
                return docManager.load(title, in);
            } catch (Exception ex) {
                log.warn("XlsCommand#load(String, FileInputStream)", ex);
            }
        }
        return null;
    }

    
    @Override
    public void save(Document document, OutputStream out) throws Exception {
        DocumentManagerIF docManager = selector.getDocumentManager(document);
        docManager.save(document, out);
    }

    @Override
    public String getFileName(Document document) {
        DocumentManagerIF docManager = selector.getDocumentManager(document);
        return docManager.getFileName(document);
    }
    
}

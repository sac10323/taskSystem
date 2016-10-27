package jp.co.sac.routineTaskSystem.control.selector.impl;

import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.control.command.DocumentCommand;
import jp.co.sac.routineTaskSystem.control.command.impl.CsvCommand;
import jp.co.sac.routineTaskSystem.control.command.impl.XlsCommand;
import jp.co.sac.routineTaskSystem.entity.document.Document;

/**
 *
 * @author shogo_saito
 */
public class CommandSelector {
    public enum CommandSelectorCore {
        csvCommand("csv", Document.FileType.csv) {@Override public DocumentCommand getCommand() {return new CsvCommand();}},
        xlsCommand("xls", Document.FileType.xls) {@Override public DocumentCommand getCommand() {return new XlsCommand();}},
        ;
        private final transient String extension;
        private final transient Document.FileType fileType;
        CommandSelectorCore(String extension, Document.FileType fileType) {
            this.extension = extension;
            this.fileType = fileType;
        }
        public String getExtension() {return extension;}
        public Document.FileType getFileType() {return fileType;}
        public abstract DocumentCommand getCommand();
    }

    public DocumentCommand<Document> getCommand(String extension) {
        for (CommandSelectorCore sel : CommandSelectorCore.values()) {
            if (DataUtil.equalsIgnoreTextSize(extension, sel.getExtension())) {
                return sel.getCommand();
            }
        }
        return null;
    }

    public DocumentCommand<Document> getCommand(Document.FileType saveType) {
        for (CommandSelectorCore sel : CommandSelectorCore.values()) {
            if (sel.getFileType().equals(saveType)) {
                return sel.getCommand();
            }
        }
        return null;
    }
}

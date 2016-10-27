package jp.co.sac.routineTaskSystem.control.selector;

import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.control.command.DocumentCommand;
import jp.co.sac.routineTaskSystem.control.command.impl.CsvCommand;
import jp.co.sac.routineTaskSystem.control.command.impl.XlsCommand;
import jp.co.sac.routineTaskSystem.control.selector.impl.CommandSelector;
import jp.co.sac.routineTaskSystem.convert.DocumentConverter;
import jp.co.sac.routineTaskSystem.convert.extra.IMRosterConverter;
import jp.co.sac.routineTaskSystem.convert.impl.RosterConverter;
import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.manage.csv.CSVManager;
import jp.co.sac.routineTaskSystem.manage.csv.extra.IMCSVManager;
import jp.co.sac.routineTaskSystem.manage.csv.impl.RosterCSVManager;
import jp.co.sac.routineTaskSystem.manage.document.CommutManager;
import jp.co.sac.routineTaskSystem.manage.document.DocumentManager;
import jp.co.sac.routineTaskSystem.manage.document.RosterManager;
import jp.co.sac.routineTaskSystem.manage.document.TranspManager;
import jp.co.sac.routineTaskSystem.validator.DocumentValidator;
import jp.co.sac.routineTaskSystem.validator.impl.RosterValidator;
import jp.co.sac.routineTaskSystem.validator.impl.TranspValidator;

/**
 * インスタンス選択処理を集約
 *
 * @author shogo_saito
 */
public class Selector {

    protected enum DocumentManagerSelector {
        rosterManager { @Override public DocumentManager newInstance() { return new RosterManager(); } },
        transpManager { @Override public DocumentManager newInstance() { return new TranspManager(); } },
        commutManager { @Override public DocumentManager newInstance() { return new CommutManager(); } },
        ;
        public abstract DocumentManager newInstance();
    }

    protected enum CSVManagerSelector {
        rosterCSVManager { @Override public CSVManager newInstance() { return new RosterCSVManager(); } },
        IMCsvManager { @Override public CSVManager newInstance() { return new IMCSVManager(); } },
        ;
        public abstract CSVManager newInstance();
    }

    protected enum DocumentConverterSelector {
        rosterConverter { @Override public DocumentConverter newInstance() { return new RosterConverter(); } },
        imRosterConverter { @Override public DocumentConverter newInstance() { return new IMRosterConverter(); } },
        ;
        public abstract DocumentConverter newInstance();
    }

    protected enum DocumentValidatorSelector {
        rosterValidator { @Override public DocumentValidator newInstance() { return new RosterValidator(); } },
        transpValidator { @Override public DocumentValidator newInstance() { return new TranspValidator(); } },
        ;
        public abstract DocumentValidator newInstance();
    }

    public DocumentManager getDocumentManager(String title) {
        for (DocumentManagerSelector sel : DocumentManagerSelector.values()) {
            DocumentManager mgr = sel.newInstance();
            if (mgr.isTargetOf(title)) {
                return mgr;
            }
        }
        return null;
    }

    public DocumentManager getDocumentManager(Document doc) {
        for (DocumentManagerSelector sel : DocumentManagerSelector.values()) {
            DocumentManager mgr = sel.newInstance();
            if (mgr.isTargetOf(doc)) {
                return mgr;
            }
        }
        return null;
    }

    public CSVManager getCSVManager(String title) {
        for (CSVManagerSelector sel : CSVManagerSelector.values()) {
            CSVManager mgr = sel.newInstance();
            if (mgr.isTargetOf(title)) {
                return mgr;
            }
        }
        return null;
    }

    public CSVManager getCSVManager(Document doc) {
        for (CSVManagerSelector sel : CSVManagerSelector.values()) {
            CSVManager mgr = sel.newInstance();
            if (mgr.isTargetOf(doc)) {
                return mgr;
            }
        }
        return null;
    }

    public DocumentConverter getConverter(CSVEntity entity) {
        for (DocumentConverterSelector sel : DocumentConverterSelector.values()) {
            DocumentConverter con = sel.newInstance();
            if (con.isTargetOf(entity)) {
                return con;
            }
        }
        return null;
    }

    public DocumentConverter getConverter(Document doc) {
        for (DocumentConverterSelector sel : DocumentConverterSelector.values()) {
            DocumentConverter con = sel.newInstance();
            if (con.isTargetOf(doc)) {
                return con;
            }
        }
        return null;
    }

    public DocumentValidator getValidator(Document doc) {
        for (DocumentValidatorSelector sel : DocumentValidatorSelector.values()) {
            DocumentValidator vdt = sel.newInstance();
            if (vdt.isTargetOf(doc)) {
                return vdt;
            }
        }
        return null;
    }
}

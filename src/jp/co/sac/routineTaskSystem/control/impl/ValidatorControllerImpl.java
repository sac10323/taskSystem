package jp.co.sac.routineTaskSystem.control.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.control.ValidatorControllerIF;
import jp.co.sac.routineTaskSystem.control.selector.Selector;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;
import jp.co.sac.routineTaskSystem.validator.DocumentValidatorIF;
import org.apache.log4j.Logger;

/**
 *
 * @author shogo_saito
 */
public class ValidatorControllerImpl implements ValidatorControllerIF<Document, Findings> {

    Logger log = Logger.getLogger("root");
    Selector selector = new Selector();

    public Map<Document, List<Findings>> doValidate(List<Document> documents) {
        return doValidate(documents, null);
    }

    public Map<Document, List<Findings>> doValidate(List<Document> documents, Staff inspector) {
        Map<Document, List<Findings>> docsMap = new HashMap<>();
        if (documents != null && !documents.isEmpty()) {
            for (Document document : documents) {
                docsMap.put(document, doValidate(document, inspector));
            }
        }
        return docsMap;
    }

    public List<Findings> doValidate(Document document) {
        return doValidate(document, null);
    }

    @Override
    public List<Findings> doValidate(Document document, Staff inspector) {
        if (document == null) {
            List<Findings> finds = new ArrayList<>(1);
            finds.add(new Findings("チェックするファイルがありません"));
            return finds;
        }
        DocumentValidatorIF docVdt = selector.getValidator(document);
        if (docVdt != null) {
            return docVdt.doValidate(document, inspector);
        }
        List<Findings> finds = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(document.getFileError())) {
            finds.add(new Findings(document.getFileError()));
        } else {
            finds.add(new Findings("チェック非対応のファイルです。"));
        }
        return finds;
    }
}

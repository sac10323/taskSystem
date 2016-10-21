package jp.co.sac.routineTaskSystem.entity.csv;

import java.util.ArrayList;

/**
 * 摘要CSV出力用エンティティ
 *
 * @author shogo_saito
 */
public class SummaryCsvEntity extends CSVEntity<SummaryCsvRecord>{

    public SummaryCsvEntity() {
        super(new ArrayList<SummaryCsvRecord>());
    }

}

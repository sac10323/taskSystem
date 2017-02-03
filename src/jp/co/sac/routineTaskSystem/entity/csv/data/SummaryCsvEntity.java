package jp.co.sac.routineTaskSystem.entity.csv.data;

import java.util.ArrayList;
import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;
import jp.co.sac.routineTaskSystem.entity.csv.record.SummaryCsvRecord;

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

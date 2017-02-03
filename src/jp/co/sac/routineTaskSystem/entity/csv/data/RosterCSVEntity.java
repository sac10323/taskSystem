package jp.co.sac.routineTaskSystem.entity.csv.data;

import java.util.ArrayList;
import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;
import jp.co.sac.routineTaskSystem.entity.csv.record.RosterCSVRecord;

/**
 *
 * @author shogo_saito
 */
public class RosterCSVEntity extends CSVEntity<RosterCSVRecord> {

    private String yearAndMonth;
    private String StaffId;
    private String name;

    public RosterCSVEntity() {
        super(new ArrayList<RosterCSVRecord>());
    }

    public String getYearAndMonth() {
        return yearAndMonth;
    }

    public void setYearAndMonth(String yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
    }

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String StaffId) {
        this.StaffId = StaffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

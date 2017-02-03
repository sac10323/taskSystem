package jp.co.sac.routineTaskSystem.entity.csv.record;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

/**
 * 摘要CSVの1レコードエンティティ
 *
 * @author shogo_saito
 */
@CsvEntity
public class SummaryCsvRecord {

    @CsvColumn(position = 0, name = "社員番号")
    protected String staffId = null;
    @CsvColumn(position = 1, name = "氏名")
    protected String name = null;
    @CsvColumn(position = 2, name = "通勤費")
    protected Integer commutingCosts = null;
    @CsvColumn(position = 3, name = "旅費交通費")
    protected Integer travelCosts = null;
    @CsvColumn(position = 4, name = "交通費計")
    protected Integer travelCostsTotal = null;
    @CsvColumn(position = 5, name = "その他経費")
    protected Integer otherCosts = null;
    @CsvColumn(position = 6, name = "仮払金返済")
    protected Integer repayTemporary = null;
    @CsvColumn(position = 7, name = "合計")
    protected Integer total = null;
    @CsvColumn(position = 8, name = "摘要")
    protected String summary = null;
    @CsvColumn(position = 9, name = "取引先請求予算あり社内Pro")
    protected String costsPro = null;
    @CsvColumn(position = 10, name = "売上原価")
    protected Integer salesCosts = null;
    @CsvColumn(position = 11, name = "販管費")
    protected Integer sga = null;
    @CsvColumn(position = 12, name = "実働")
    protected Double Production = null;
    @CsvColumn(position = 13, name = "残業")
    protected Double overTime = null;
    @CsvColumn(position = 14, name = "深夜残業")
    protected Double overTimeNight = null;
    @CsvColumn(position = 15, name = "日曜残業")
    protected Double overTimeSun = null;
    @CsvColumn(position = 16, name = "日曜深夜")
    protected Double overTimeSunNight = null;
    @CsvColumn(position = 17, name = "遅刻・早退")
    protected Double shortageTime = null;
    @CsvColumn(position = 18, name = "代休控除")
    protected Integer deduction = null;
    @CsvColumn(position = 19, name = "欠勤")
    protected Double absense = null;
    @CsvColumn(position = 20, name = "有休")
    protected Double paidHoliday = null;
    @CsvColumn(position = 21, name = "Section")
    protected String section = null;
    @CsvColumn(position = 22, name = "職位")
    protected String position = null;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCommutingCosts() {
        return commutingCosts;
    }

    public void setCommutingCosts(Integer commutingCosts) {
        this.commutingCosts = commutingCosts;
    }

    public Integer getTravelCosts() {
        return travelCosts;
    }

    public void setTravelCosts(Integer travelCosts) {
        this.travelCosts = travelCosts;
    }

    public Integer getTravelCostsTotal() {
        return travelCostsTotal;
    }

    public void setTravelCostsTotal(Integer travelCostsTotal) {
        this.travelCostsTotal = travelCostsTotal;
    }

    public Integer getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(Integer otherCosts) {
        this.otherCosts = otherCosts;
    }

    public Integer getRepayTemporary() {
        return repayTemporary;
    }

    public void setRepayTemporary(Integer repayTemporary) {
        this.repayTemporary = repayTemporary;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCostsPro() {
        return costsPro;
    }

    public void setCostsPro(String costsPro) {
        this.costsPro = costsPro;
    }

    public Integer getSalesCosts() {
        return salesCosts;
    }

    public void setSalesCosts(Integer salesCosts) {
        this.salesCosts = salesCosts;
    }

    public Integer getSga() {
        return sga;
    }

    public void setSga(Integer sga) {
        this.sga = sga;
    }

    public Double getProduction() {
        return Production;
    }

    public void setProduction(Double Production) {
        this.Production = Production;
    }

    public Double getOverTime() {
        return overTime;
    }

    public void setOverTime(Double overTime) {
        this.overTime = overTime;
    }

    public Double getOverTimeNight() {
        return overTimeNight;
    }

    public void setOverTimeNight(Double overTimeNight) {
        this.overTimeNight = overTimeNight;
    }

    public Double getOverTimeSun() {
        return overTimeSun;
    }

    public void setOverTimeSun(Double overTimeSun) {
        this.overTimeSun = overTimeSun;
    }

    public Double getOverTimeSunNight() {
        return overTimeSunNight;
    }

    public void setOverTimeSunNight(Double overTimeSunNight) {
        this.overTimeSunNight = overTimeSunNight;
    }

    public Double getShortageTime() {
        return shortageTime;
    }

    public void setShortageTime(Double shortageTime) {
        this.shortageTime = shortageTime;
    }

    public Integer getDeduction() {
        return deduction;
    }

    public void setDeduction(Integer deduction) {
        this.deduction = deduction;
    }

    public Double getAbsense() {
        return absense;
    }

    public void setAbsense(Double absense) {
        this.absense = absense;
    }

    public Double getPaidHoliday() {
        return paidHoliday;
    }

    public void setPaidHoliday(Double paidHoliday) {
        this.paidHoliday = paidHoliday;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}

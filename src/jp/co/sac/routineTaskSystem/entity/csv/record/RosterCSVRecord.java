package jp.co.sac.routineTaskSystem.entity.csv.record;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

/**
 *
 * @author shogo_saito
 */
@CsvEntity
public class RosterCSVRecord {
    @CsvColumn(position = 0, name = "日")
    protected String day = null;
    @CsvColumn(position = 1, name = "時間(FROM)")
    protected String fromTime = null;
    @CsvColumn(position = 2, name = "時間(TO)")
    protected String toTime = null;
    @CsvColumn(position = 3, name = "A変")
    protected String strangeA = null;
    @CsvColumn(position = 4, name = "B変")
    protected String strangeB = null;
    @CsvColumn(position = 5, name = "休暇等")
    protected String cause = null;
    @CsvColumn(position = 6, name = "行き先(常駐先)")
    protected String destination = null;

    public RosterCSVRecord() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDay(Integer day) {
        this.day = String.valueOf(day);
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getStrangeA() {
        return strangeA;
    }

    public void setStrangeA(String strangeA) {
        this.strangeA = strangeA;
    }

    public String getStrangeB() {
        return strangeB;
    }

    public void setStrangeB(String strangeB) {
        this.strangeB = strangeB;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
}

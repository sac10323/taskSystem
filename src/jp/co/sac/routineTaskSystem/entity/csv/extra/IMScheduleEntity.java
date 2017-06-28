package jp.co.sac.routineTaskSystem.entity.csv.extra;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;

/**
 * スケジュール設定CSVエンティティ
 *
 * @author shogo_saito
 */
@CsvEntity(header = false)
public class IMScheduleEntity extends CSVEntity<IMScheduleEntity> {
    @CsvColumn(position = 0, format = "yyyy/MM/dd")
    private Date date;
    @CsvColumn(position = 1)
    private String timeFrom;
    @CsvColumn(position = 2)
    private String timeTo;
    @CsvColumn(position = 3)
    private String cause;
    @CsvColumn(position = 4)
    private String destination;

    public IMScheduleEntity() {
        super(new ArrayList<IMScheduleEntity>());
        date = null;
        cause = null;
        destination = null;
    }

    public IMScheduleEntity(String title, List<IMScheduleEntity> records) {
        super(records);
        setTitle(title);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
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

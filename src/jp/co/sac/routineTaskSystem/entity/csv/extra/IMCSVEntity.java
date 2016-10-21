package jp.co.sac.routineTaskSystem.entity.csv.extra;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;

/**
 *
 * @author shogo_saito
 */
@CsvEntity
public class IMCSVEntity extends CSVEntity<IMCSVEntity> {
    @CsvColumn(position = 0)
    private String name;
    @CsvColumn(position = 1, format = "yyyy/MM/dd HH:mm:ss")
    private Date from;
    @CsvColumn(position = 2, format = "yyyy/MM/dd HH:mm:ss")
    private Date to;

    public IMCSVEntity() {
        super(new ArrayList<IMCSVEntity>());
        name = null;
        from = null;
        to = null;
    }

    public IMCSVEntity(String title, List<IMCSVEntity> records) {
        super(records);
        setTitle(title);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}

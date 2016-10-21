package jp.co.sac.routineTaskSystem.dto;

/**
 *
 * @author shogo_saito
 */
public class RosterDayDto {
    private Long documentId;
    private Long timeFrom;
    private Long timeTo;
    private String strangeA;
    private String strangeB;
    private String cause;
    private String destination;

    public RosterDayDto() {
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Long timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Long getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Long timeTo) {
        this.timeTo = timeTo;
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

package jp.co.sac.routineTaskSystem.dto;

import java.util.Date;

/**
 *
 * @author shogo_saito
 */
public class RosterDto {
    private Long documentId;
    private String staffId;
    private Date rosterYM;
    private String location;
    private String authorSign;
    private String superSign;
    private Integer deleteFlg;

    public RosterDto() {
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Date getRosterYM() {
        return rosterYM;
    }

    public void setRosterYM(Date rosterYM) {
        this.rosterYM = rosterYM;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthorSign() {
        return authorSign;
    }

    public void setAuthorSign(String authorSign) {
        this.authorSign = authorSign;
    }

    public String getSuperSign() {
        return superSign;
    }

    public void setSuperSign(String superSign) {
        this.superSign = superSign;
    }

    public Integer getDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(Integer deleteFlg) {
        this.deleteFlg = deleteFlg;
    }
}

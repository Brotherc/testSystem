package ytk.business.pojo.po;

public class StudentSj {
    private String uuid;

    private Long studentid;

    private String sjid;

    private Integer score;

    private String ksgluuid;

    private Integer status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid == null ? null : sjid.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getKsgluuid() {
        return ksgluuid;
    }

    public void setKsgluuid(String ksgluuid) {
        this.ksgluuid = ksgluuid == null ? null : ksgluuid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
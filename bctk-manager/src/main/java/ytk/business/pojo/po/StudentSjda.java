package ytk.business.pojo.po;

public class StudentSjda {
    private String uuid;

    private String sjxitmid;

    private Integer sjtmid;

    private Integer type;

    private String answer;

    private Integer score;

    private String studentsjid;

    private Integer status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getSjxitmid() {
        return sjxitmid;
    }

    public void setSjxitmid(String sjxitmid) {
        this.sjxitmid = sjxitmid == null ? null : sjxitmid.trim();
    }

    public Integer getSjtmid() {
        return sjtmid;
    }

    public void setSjtmid(Integer sjtmid) {
        this.sjtmid = sjtmid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getStudentsjid() {
        return studentsjid;
    }

    public void setStudentsjid(String studentsjid) {
        this.studentsjid = studentsjid == null ? null : studentsjid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
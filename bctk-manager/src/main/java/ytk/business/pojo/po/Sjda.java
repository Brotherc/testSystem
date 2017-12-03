package ytk.business.pojo.po;

public class Sjda {
    private String uuid;

    private String sjid;

    private String sjxitmid;

    private Integer sjtmid;

    private Integer type;

    private String answer;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid == null ? null : sjid.trim();
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
}
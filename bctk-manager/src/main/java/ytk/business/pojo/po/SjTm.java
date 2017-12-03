package ytk.business.pojo.po;

public class SjTm {
    private String uuid;

    private String sjid;

    private String tuuid;

    private Integer score;

    private Integer state;

    private Integer sjtmid;

    private Integer type;

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

    public String getTuuid() {
        return tuuid;
    }

    public void setTuuid(String tuuid) {
        this.tuuid = tuuid == null ? null : tuuid.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
}
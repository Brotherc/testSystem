package ytk.business.pojo.po;

public class TmZsd {
    private String uuid;

    private String tmuuid;

    private String zsduuid;

    private Integer type;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getTmuuid() {
        return tmuuid;
    }

    public void setTmuuid(String tmuuid) {
        this.tmuuid = tmuuid == null ? null : tmuuid.trim();
    }

    public String getZsduuid() {
        return zsduuid;
    }

    public void setZsduuid(String zsduuid) {
        this.zsduuid = zsduuid == null ? null : zsduuid.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
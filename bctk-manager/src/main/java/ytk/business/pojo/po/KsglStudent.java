package ytk.business.pojo.po;

public class KsglStudent {
    private String uuid;

    private String ksgluuid;

    private Long sysuseruuid;

    private Integer status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getKsgluuid() {
        return ksgluuid;
    }

    public void setKsgluuid(String ksgluuid) {
        this.ksgluuid = ksgluuid == null ? null : ksgluuid.trim();
    }

    public Long getSysuseruuid() {
        return sysuseruuid;
    }

    public void setSysuseruuid(Long sysuseruuid) {
        this.sysuseruuid = sysuseruuid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
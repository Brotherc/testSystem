package ytk.base.pojo.po;

public class Nj {
    private Long uuid;

    private String njnane;

    private Integer status;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getNjnane() {
        return njnane;
    }

    public void setNjnane(String njnane) {
        this.njnane = njnane == null ? null : njnane.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
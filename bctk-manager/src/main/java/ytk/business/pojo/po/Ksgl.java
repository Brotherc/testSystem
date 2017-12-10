package ytk.business.pojo.po;

public class Ksgl {
    private String uuid;

    private Long starttime;

    private Long endtime;

    private String sjuuid;

    private Integer status;

    private String teacherid;

    private Long createtime;

    private Long kcuuid;

    private Long time;

    private String kspwd;

    private String jkpwd;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public String getSjuuid() {
        return sjuuid;
    }

    public void setSjuuid(String sjuuid) {
        this.sjuuid = sjuuid == null ? null : sjuuid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid == null ? null : teacherid.trim();
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public Long getKcuuid() {
        return kcuuid;
    }

    public void setKcuuid(Long kcuuid) {
        this.kcuuid = kcuuid;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getKspwd() {
        return kspwd;
    }

    public void setKspwd(String kspwd) {
        this.kspwd = kspwd == null ? null : kspwd.trim();
    }

    public String getJkpwd() {
        return jkpwd;
    }

    public void setJkpwd(String jkpwd) {
        this.jkpwd = jkpwd == null ? null : jkpwd.trim();
    }
}
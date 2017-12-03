package ytk.business.pojo.po;

public class Sj {
    private String uuid;

    private String name;

    private Integer score;

    private Long kcid;

    private Integer ndtype;

    private Long teacherid;

    private Long createtime;

    private Long updatetime;

    private String sjmbid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getKcid() {
        return kcid;
    }

    public void setKcid(Long kcid) {
        this.kcid = kcid;
    }

    public Integer getNdtype() {
        return ndtype;
    }

    public void setNdtype(Integer ndtype) {
        this.ndtype = ndtype;
    }

    public Long getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(Long teacherid) {
        this.teacherid = teacherid;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public Long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }

    public String getSjmbid() {
        return sjmbid;
    }

    public void setSjmbid(String sjmbid) {
        this.sjmbid = sjmbid == null ? null : sjmbid.trim();
    }
}
package ytk.base.pojo.po;

public class Class {
    private String uuid;

    private String classname;

    private Long zyuuid;

    private Long njuuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname == null ? null : classname.trim();
    }

    public Long getZyuuid() {
        return zyuuid;
    }

    public void setZyuuid(Long zyuuid) {
        this.zyuuid = zyuuid;
    }

    public Long getNjuuid() {
        return njuuid;
    }

    public void setNjuuid(Long njuuid) {
        this.njuuid = njuuid;
    }
}
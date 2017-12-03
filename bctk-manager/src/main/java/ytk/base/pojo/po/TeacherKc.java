package ytk.base.pojo.po;

public class TeacherKc {
    private String uuid;

    private Long teacheruuid;

    private Long kcuuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Long getTeacheruuid() {
        return teacheruuid;
    }

    public void setTeacheruuid(Long teacheruuid) {
        this.teacheruuid = teacheruuid;
    }

    public Long getKcuuid() {
        return kcuuid;
    }

    public void setKcuuid(Long kcuuid) {
        this.kcuuid = kcuuid;
    }
}
package ytk.base.pojo.po;

public class TeacherKc {
    private String uuid;

    private String teacheruuid;

    private Long kcuuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getTeacheruuid() {
        return teacheruuid;
    }

    public void setTeacheruuid(String teacheruuid) {
        this.teacheruuid = teacheruuid == null ? null : teacheruuid.trim();
    }

    public Long getKcuuid() {
        return kcuuid;
    }

    public void setKcuuid(Long kcuuid) {
        this.kcuuid = kcuuid;
    }
}
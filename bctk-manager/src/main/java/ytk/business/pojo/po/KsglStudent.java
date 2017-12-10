package ytk.business.pojo.po;

public class KsglStudent {
    private String uuid;

    private String ksgluuid;

    private String studentUuid;

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

    public String getStudentUuid() {
        return studentUuid;
    }

    public void setStudentUuid(String studentUuid) {
        this.studentUuid = studentUuid == null ? null : studentUuid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
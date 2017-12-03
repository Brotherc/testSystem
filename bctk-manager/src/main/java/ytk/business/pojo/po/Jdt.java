package ytk.business.pojo.po;

public class Jdt {
    private String uuid;

    private Integer type;

    private Long kcuuid;

    private String content;

    private String answer;

    private Integer ndtype;

    private Long teacheruuid;

    private Long createtime;

    private Integer status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getKcuuid() {
        return kcuuid;
    }

    public void setKcuuid(Long kcuuid) {
        this.kcuuid = kcuuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Integer getNdtype() {
        return ndtype;
    }

    public void setNdtype(Integer ndtype) {
        this.ndtype = ndtype;
    }

    public Long getTeacheruuid() {
        return teacheruuid;
    }

    public void setTeacheruuid(Long teacheruuid) {
        this.teacheruuid = teacheruuid;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
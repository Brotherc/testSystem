package ytk.business.pojo.po;

public class Dxt {
    private String uuid;

    private Integer type;

    private Long kcuuid;

    private String optiona;

    private String optionb;

    private String optionc;

    private String optiond;

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

    public String getOptiona() {
        return optiona;
    }

    public void setOptiona(String optiona) {
        this.optiona = optiona == null ? null : optiona.trim();
    }

    public String getOptionb() {
        return optionb;
    }

    public void setOptionb(String optionb) {
        this.optionb = optionb == null ? null : optionb.trim();
    }

    public String getOptionc() {
        return optionc;
    }

    public void setOptionc(String optionc) {
        this.optionc = optionc == null ? null : optionc.trim();
    }

    public String getOptiond() {
        return optiond;
    }

    public void setOptiond(String optiond) {
        this.optiond = optiond == null ? null : optiond.trim();
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
package ytk.business.pojo.po;

public class Sjmb {
    private String uuid;

    private Integer dxtcount;

    private Integer dxxztcount;

    private Integer tktcount;

    private Integer jdtcount;

    private Integer dxtscore;

    private Integer dxxztscore;

    private Integer tktscore;

    private Integer jdtscore;

    private Integer score;

    private Integer status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Integer getDxtcount() {
        return dxtcount;
    }

    public void setDxtcount(Integer dxtcount) {
        this.dxtcount = dxtcount;
    }

    public Integer getDxxztcount() {
        return dxxztcount;
    }

    public void setDxxztcount(Integer dxxztcount) {
        this.dxxztcount = dxxztcount;
    }

    public Integer getTktcount() {
        return tktcount;
    }

    public void setTktcount(Integer tktcount) {
        this.tktcount = tktcount;
    }

    public Integer getJdtcount() {
        return jdtcount;
    }

    public void setJdtcount(Integer jdtcount) {
        this.jdtcount = jdtcount;
    }

    public Integer getDxtscore() {
        return dxtscore;
    }

    public void setDxtscore(Integer dxtscore) {
        this.dxtscore = dxtscore;
    }

    public Integer getDxxztscore() {
        return dxxztscore;
    }

    public void setDxxztscore(Integer dxxztscore) {
        this.dxxztscore = dxxztscore;
    }

    public Integer getTktscore() {
        return tktscore;
    }

    public void setTktscore(Integer tktscore) {
        this.tktscore = tktscore;
    }

    public Integer getJdtscore() {
        return jdtscore;
    }

    public void setJdtscore(Integer jdtscore) {
        this.jdtscore = jdtscore;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
package ytk.business.pojo.po;

public class Sjmb {
    private String uuid;

    private Integer dxtcount;

    private Integer tktcount;

    private Integer pdtcount;

    private Integer dxtscore;

    private Integer tktscore;

    private Integer pdtscore;

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

    public Integer getTktcount() {
        return tktcount;
    }

    public void setTktcount(Integer tktcount) {
        this.tktcount = tktcount;
    }

    public Integer getPdtcount() {
        return pdtcount;
    }

    public void setPdtcount(Integer pdtcount) {
        this.pdtcount = pdtcount;
    }

    public Integer getDxtscore() {
        return dxtscore;
    }

    public void setDxtscore(Integer dxtscore) {
        this.dxtscore = dxtscore;
    }

    public Integer getTktscore() {
        return tktscore;
    }

    public void setTktscore(Integer tktscore) {
        this.tktscore = tktscore;
    }

    public Integer getPdtscore() {
        return pdtscore;
    }

    public void setPdtscore(Integer pdtscore) {
        this.pdtscore = pdtscore;
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
package ytk.base.pojo.po;

public class BssSysSysuserrole {
    private String srid;

    private Long sysuserid;

    private String roleid;

    public String getSrid() {
        return srid;
    }

    public void setSrid(String srid) {
        this.srid = srid == null ? null : srid.trim();
    }

    public Long getSysuserid() {
        return sysuserid;
    }

    public void setSysuserid(Long sysuserid) {
        this.sysuserid = sysuserid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }
}
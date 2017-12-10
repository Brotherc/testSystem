package ytk.base.pojo.po;

public class BssSysSysuserrole {
    private String srid;

    private String sysuserid;

    private String roleid;

    public String getSrid() {
        return srid;
    }

    public void setSrid(String srid) {
        this.srid = srid == null ? null : srid.trim();
    }

    public String getSysuserid() {
        return sysuserid;
    }

    public void setSysuserid(String sysuserid) {
        this.sysuserid = sysuserid == null ? null : sysuserid.trim();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }
}
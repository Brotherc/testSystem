package ytk.business.pojo.vo;

import java.util.List;

import ytk.base.pojo.po.Zy;
import ytk.business.pojo.po.Sj;

public class SjCustom extends Sj{
	private String xiName;
	private String zyname;
	private String kcname;
	private String teachername;
	private String ndtypename;
	private List<Zy> zyList;
	private String q;
	
	private Long sysuseruuid;
	
	public String getKcname() {
		return kcname;
	}

	public void setKcname(String kcname) {
		this.kcname = kcname;
	}

	public String getXiName() {
		return xiName;
	}

	public void setXiName(String xiName) {
		this.xiName = xiName;
	}

	public String getZyname() {
		return zyname;
	}

	public void setZyname(String zyname) {
		this.zyname = zyname;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getNdtypename() { 
		return ndtypename;
	}

	public void setNdtypename(String ndtypename) {
		this.ndtypename = ndtypename;
	}

	public List<Zy> getZyList() {
		return zyList;
	}

	public void setZyList(List<Zy> zyList) {
		this.zyList = zyList;
	}

	public Long getSysuseruuid() {
		return sysuseruuid;
	}

	public void setSysuseruuid(Long sysuseruuid) {
		this.sysuseruuid = sysuseruuid;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
	
	
}

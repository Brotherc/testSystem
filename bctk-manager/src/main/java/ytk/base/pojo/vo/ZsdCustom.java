package ytk.base.pojo.vo;

import ytk.base.pojo.po.Zsd;

public class ZsdCustom extends Zsd{
	private String kcname;
	
	private String q;
	
	private Long sysuseruuid;

	public String getKcname() {
		return kcname;
	}

	public void setKcname(String kcname) {
		this.kcname = kcname;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Long getSysuseruuid() {
		return sysuseruuid;
	}

	public void setSysuseruuid(Long sysuseruuid) {
		this.sysuseruuid = sysuseruuid;
	}
	
}

package ytk.base.pojo.vo;

import ytk.base.pojo.po.Kc;
import ytk.util.DateUtil;

public class KcCustom extends Kc{
	private String createtimeMinView;
	private String createtimeMaxView;
	
	private Long createtimeMin;
	private Long createtimeMax;
	private String createtimeView;
	private String zyname;
	private Long zyuuid;
	private Long[] zyuuids;
	private String q;
	private String sysuseruuid;
	
	public Long getCreatetimeMin() {
		return createtimeMin;
	}
	public void setCreatetimeMin(Long createtimeMin) {
		this.createtimeMin = createtimeMin;
	}
	public Long getCreatetimeMax() {
		return createtimeMax;
	}
	public void setCreatetimeMax(Long createtimeMax) {
		this.createtimeMax = createtimeMax;
	}
	public String getCreatetimeView() {
		return createtimeView;
	}
	@Override
	public void setCreatetime(Long createtime) {
		super.setCreatetime(createtime);
		this.createtimeView=DateUtil.formatDate(createtime);
	}
	public Long[] getZyuuids() {
		return zyuuids;
	}
	public void setZyuuids(Long[] zyuuids) {
		this.zyuuids = zyuuids;
	}
	public String getZyname() {
		return zyname;
	}
	public void setZyname(String zyname) {
		this.zyname = zyname;
	}
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	
	
	public String getCreatetimeMinView() {
		return createtimeMinView;
	}
	public void setCreatetimeMinView(String createtimeMinView) {
		if(createtimeMinView!=null&&!createtimeMinView.equals("")){
			this.createtimeMinView = createtimeMinView;
			this.createtimeMin = DateUtil.parseDate(createtimeMinView).getTime();
		}

	}
	public String getCreatetimeMaxView() {
		return createtimeMaxView;
	}
	public void setCreatetimeMaxView(String createtimeMaxView) {
		if(createtimeMaxView!=null&&!createtimeMaxView.equals("")){
			this.createtimeMaxView = createtimeMaxView;
			this.createtimeMax = DateUtil.parseDate(createtimeMaxView).getTime();
		}

	}
	public Long getZyuuid() {
		return zyuuid;
	}
	public void setZyuuid(Long zyuuid) {
		this.zyuuid = zyuuid;
	}
	public void setCreatetimeView(String createtimeView) {
		if(createtimeView!=null&&!createtimeView.equals("")){
			this.createtimeView = createtimeView;
			setCreatetime(DateUtil.parseDate(createtimeView).getTime());
		}
	}
	public String getSysuseruuid() {
		return sysuseruuid;
	}
	public void setSysuseruuid(String sysuseruuid) {
		this.sysuseruuid = sysuseruuid;
	}

	
}

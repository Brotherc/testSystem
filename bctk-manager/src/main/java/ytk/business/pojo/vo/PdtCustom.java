package ytk.business.pojo.vo;

import java.util.List;

import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.po.Zy;
import ytk.business.pojo.po.Pdt;
import ytk.util.DateUtil;

public class PdtCustom extends Pdt{
	private String kcname;
	private String zyname;
	private String ndname;
	private String teachername;
	private String zsdname;
	private String sysuseruuid;
	private List<Zy> zyList;
	private List<Zsd> zsdList;
	
	private String createtimeView;
	private Long createtimeMin;
	private Long createtimeMax;
	private String statusname;

	public String getKcname() {
		return kcname;
	}

	public void setKcname(String kcname) {
		this.kcname = kcname;
	}

	public String getZyname() {
		return zyname;
	}

	public void setZyname(String zyname) {
		this.zyname = zyname;
	}

	public String getNdname() {
		return ndname;
	}

	public void setNdname(String ndname) {
		this.ndname = ndname;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getCreatetimeView() {
		return createtimeView;
	}

	public void setCreatetimeView(String createtimeView) {
		this.createtimeView = createtimeView;
	}

	@Override
	public void setCreatetime(Long createtime) {
		super.setCreatetime(createtime);
		this.createtimeView=DateUtil.formatDate(createtime);
	}

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

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public List<Zy> getZyList() {
		return zyList;
	}

	public void setZyList(List<Zy> zyList) {
		this.zyList = zyList;
	}

	public List<Zsd> getZsdList() {
		return zsdList;
	}

	public void setZsdList(List<Zsd> zsdList) {
		this.zsdList = zsdList;
	}

	public String getZsdname() {
		return zsdname;
	}

	public void setZsdname(String zsdname) {
		this.zsdname = zsdname;
	}

	public String getSysuseruuid() {
		return sysuseruuid;
	}

	public void setSysuseruuid(String sysuseruuid) {
		this.sysuseruuid = sysuseruuid;
	}
	
	
}

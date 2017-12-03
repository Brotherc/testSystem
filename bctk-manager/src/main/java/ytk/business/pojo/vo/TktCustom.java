package ytk.business.pojo.vo;

import java.util.List;

import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.po.Zy;
import ytk.business.pojo.po.Tkt;
import ytk.util.DateUtil;

public class TktCustom extends Tkt{
	private String kcname;
	private String zyname;
	private String ndname;
	private String teachername;
	private String statusname;
	private String zsdname;
	private Long sysuseruuid;
	
	private List<Zy> zyList;
	private List<Zsd> zsdList;
	
	private String[] answer1;
	private String[] answer2;
	private String[] answer3;
	private String[] answer4;
	private String[] answer5;
	
	
	private String createtimeView;
	private Long createtimeMin;
	private Long createtimeMax;

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

	public String getZsdname() {
		return zsdname;
	}

	public void setZsdname(String zsdname) {
		this.zsdname = zsdname;
	}

	public List<Zsd> getZsdList() {
		return zsdList;
	}

	public void setZsdList(List<Zsd> zsdList) {
		this.zsdList = zsdList;
	}

	public String[] getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String[] answer1) {
		this.answer1 = answer1;
	}

	public String[] getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String[] answer2) {
		this.answer2 = answer2;
	}

	public String[] getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String[] answer3) {
		this.answer3 = answer3;
	}

	public String[] getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String[] answer4) {
		this.answer4 = answer4;
	}

	public String[] getAnswer5() {
		return answer5;
	}

	public void setAnswer5(String[] answer5) {
		this.answer5 = answer5;
	}

	public Long getSysuseruuid() {
		return sysuseruuid;
	}

	public void setSysuseruuid(Long sysuseruuid) {
		this.sysuseruuid = sysuseruuid;
	}
}

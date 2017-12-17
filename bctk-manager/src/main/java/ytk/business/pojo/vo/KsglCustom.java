package ytk.business.pojo.vo;

import ytk.business.pojo.po.Ksgl;
import ytk.util.DateUtil;

public class KsglCustom extends Ksgl{

	private String xiname;
	private String sjname;
	private String teachername;
	private String kcname;
	private String njname;
	private String zyname;
	private String classname;
	private String statusname;
	private Long zyuuid;
	private String classuuid;
	private Long njuuid;
	private String sysuseruuid;
	private String timeView;
	
	private Long[] zyuuids;
	
	private String starttimeView;
	private String endtimeView;
	
	public String getXiname() {
		return xiname;
	}
	public void setXiname(String xiname) {
		this.xiname = xiname;
	}
	public String getSjname() {
		return sjname;
	}
	public void setSjname(String sjname) {
		this.sjname = sjname;
	}
	public String getTeachername() {
		return teachername;
	}
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	public String getKcname() {
		return kcname;
	}
	public void setKcname(String kcname) {
		this.kcname = kcname;
	}
	public String getNjname() {
		return njname;
	}
	public void setNjname(String njname) {
		this.njname = njname;
	}
	public String getZyname() {
		return zyname;
	}
	public void setZyname(String zyname) {
		this.zyname = zyname;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	public String getClassuuid() {
		return classuuid;
	}
	public void setClassuuid(String classuuid) {
		this.classuuid = classuuid;
	}
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	
	public Long getZyuuid() {
		return zyuuid;
	}
	public void setZyuuid(Long zyuuid) {
		this.zyuuid = zyuuid;
	}
	
	public Long getNjuuid() {
		return njuuid;
	}
	public void setNjuuid(Long njuuid) {
		this.njuuid = njuuid;
	}
	public String getStarttimeView() {
		return starttimeView;
	}
	public String getEndtimeView() {
		return endtimeView;
	}
	@Override
	public void setStarttime(Long starttime) {
		super.setStarttime(starttime);
		this.starttimeView=DateUtil.formatDateTime(starttime);
	}
	@Override
	public void setEndtime(Long endtime) {
		super.setEndtime(endtime);
		this.endtimeView=DateUtil.formatDateTime(endtime);
	}
	public void setStarttimeView(String starttimeView) {
		if(starttimeView!=null&&!starttimeView.equals("")){
			this.starttimeView = starttimeView;
			starttimeView.replace("+", " ");
			System.out.println(starttimeView);
			setStarttime(DateUtil.parseDateTime(starttimeView).getTime());
		}
	}
	public void setEndtimeView(String endtimeView) {
		if(endtimeView!=null&&!endtimeView.equals("")){
			this.endtimeView = endtimeView;
			endtimeView.replace("+", " ");
			setEndtime(DateUtil.parseDateTime(endtimeView).getTime());
		}
	}
	public Long[] getZyuuids() {
		return zyuuids;
	}
	public void setZyuuids(Long[] zyuuids) {
		this.zyuuids = zyuuids;
	}
	
	
	public String getSysuseruuid() {
		return sysuseruuid;
	}
	public void setSysuseruuid(String sysuseruuid) {
		this.sysuseruuid = sysuseruuid;
	}
	@Override
	public void setTime(Long time) {
		super.setTime(time);
		this.timeView=DateUtil.formatMin(time);
	}
	public String getTimeView() {
		return timeView;
	}	
	
	
}

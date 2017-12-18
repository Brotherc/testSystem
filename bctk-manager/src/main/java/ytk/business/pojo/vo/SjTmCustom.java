package ytk.business.pojo.vo;

import java.util.List;

import ytk.base.pojo.po.Zsd;
import ytk.business.pojo.po.SjTm;

public class SjTmCustom extends SjTm{
	private Long kcuuid;
	private Long zyuuid;
	private Long zjuuid;
	private String content;
	private Integer ndtype;
	private String teacheruuid;
	private String kcname;
	private String zyname;
	private String zjname;
	private String teachername;
	private String typename;
	private String ndtypename;
	private String statusname;
	private Integer sjtmid;
	private String zsdname;
	
	private List<Zsd> zsdList;

	public Long getKcuuid() {
		return kcuuid;
	}

	public void setKcuuid(Long kcuuid) {
		this.kcuuid = kcuuid;
	}

	public Long getZyuuid() {
		return zyuuid;
	}

	public void setZyuuid(Long zyuuid) {
		this.zyuuid = zyuuid;
	}

	public Long getZjuuid() {
		return zjuuid;
	}

	public void setZjuuid(Long zjuuid) {
		this.zjuuid = zjuuid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getNdtype() {
		return ndtype;
	}

	public void setNdtype(Integer ndtype) {
		this.ndtype = ndtype;
	}

	public String getTeacheruuid() {
		return teacheruuid;
	}

	public void setTeacheruuid(String teacheruuid) {
		this.teacheruuid = teacheruuid;
	}

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

	public String getZjname() {
		return zjname;
	}

	public void setZjname(String zjname) {
		this.zjname = zjname;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getNdtypename() {
		return ndtypename;
	}

	public void setNdtypename(String ndtypename) {
		this.ndtypename = ndtypename;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public Integer getSjtmid() {
		return sjtmid;
	}

	public void setSjtmid(Integer sjtmid) {
		this.sjtmid = sjtmid;
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
	

	
	
}

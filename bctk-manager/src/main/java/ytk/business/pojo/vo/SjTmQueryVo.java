package ytk.business.pojo.vo;

import java.util.List;

import ytk.base.pojo.vo.PageQuery;
import ytk.business.pojo.po.SjTm;

public class SjTmQueryVo {
	private SjTmCustom sjTmCustom;
	private PageQuery pageQuery;
	private List<SjTm>sjTmList;
	private SjCustom sjCustom;
	

	public SjTmCustom getSjTmCustom() {
		return sjTmCustom;
	}
	public void setSjTmCustom(SjTmCustom sjTmCustom) {
		this.sjTmCustom = sjTmCustom;
	}
	public PageQuery getPageQuery() {
		return pageQuery;
	}
	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}
	
	public List<SjTm> getSjTmList() {
		return sjTmList;
	}
	public void setSjTmList(List<SjTm> sjTmList) {
		this.sjTmList = sjTmList;
	}
	public SjCustom getSjCustom() {
		return sjCustom;
	}
	public void setSjCustom(SjCustom sjCustom) {
		this.sjCustom = sjCustom;
	}
	
	
}

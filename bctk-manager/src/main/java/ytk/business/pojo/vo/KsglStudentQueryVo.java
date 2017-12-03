package ytk.business.pojo.vo;

import java.util.List;

import ytk.base.pojo.vo.PageQuery;
import ytk.business.pojo.po.KsglStudent;

public class KsglStudentQueryVo {
	private KsglStudentCustom ksglStudentCustom;
	private PageQuery pageQuery;
	
	private List<KsglStudent>ksglStudentList;
	
	public KsglStudentCustom getKsglStudentCustom() {
		return ksglStudentCustom;
	}
	public void setKsglStudentCustom(KsglStudentCustom ksglStudentCustom) {
		this.ksglStudentCustom = ksglStudentCustom;
	}
	public PageQuery getPageQuery() {
		return pageQuery;
	}
	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}
	public List<KsglStudent> getKsglStudentList() {
		return ksglStudentList;
	}
	public void setKsglStudentList(List<KsglStudent> ksglStudentList) {
		this.ksglStudentList = ksglStudentList;
	}
	
	
}

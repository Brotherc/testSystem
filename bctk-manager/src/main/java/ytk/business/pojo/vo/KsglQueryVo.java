package ytk.business.pojo.vo;

import ytk.base.pojo.vo.PageQuery;

public class KsglQueryVo {
	private KsglCustom ksglCustom;
	private PageQuery pageQuery;
	private Long[] zyList;
	public KsglCustom getKsglCustom() {
		return ksglCustom;
	}
	public void setKsglCustom(KsglCustom ksglCustom) {
		this.ksglCustom = ksglCustom;
	}
	public PageQuery getPageQuery() {
		return pageQuery;
	}
	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}
	public Long[] getZyList() {
		return zyList;
	}
	public void setZyList(Long[] zyList) {
		this.zyList = zyList;
	}
	
	
}

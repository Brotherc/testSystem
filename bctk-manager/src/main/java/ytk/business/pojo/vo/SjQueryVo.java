package ytk.business.pojo.vo;


import ytk.base.pojo.vo.PageQuery;

public class SjQueryVo {
	private SjmbCustom sjmbCustom;
	private SjCustom sjCustom;
	private Long[] zyList;
	private PageQuery pageQuery;
	public SjCustom getSjCustom() {
		return sjCustom;
	}
	public void setSjCustom(SjCustom sjCustom) {
		this.sjCustom = sjCustom;
	}
	public PageQuery getPageQuery() {
		return pageQuery;
	}
	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}
	public SjmbCustom getSjmbCustom() {
		return sjmbCustom;
	}
	public void setSjmbCustom(SjmbCustom sjmbCustom) {
		this.sjmbCustom = sjmbCustom;
	}
	public Long[] getZyList() {
		return zyList;
	}
	public void setZyList(Long[] zyList) {
		this.zyList = zyList;
	}
	
	
}

package ytk.base.pojo.vo;


public class KcQueryVo {
	private KcCustom kcCustom;
	private PageQuery pageQuery;
	private Long[] zyList;
	
	public KcCustom getKcCustom() {
		return kcCustom;
	}
	public void setKcCustom(KcCustom kcCustom) {
		this.kcCustom = kcCustom;
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

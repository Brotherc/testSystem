package ytk.business.pojo.vo;

import java.util.List;

import ytk.base.pojo.vo.PageQuery;

public class StudentSjdaQueryVo {
	private StudentSjdaCustom studentSjdaCustom;
	private PageQuery pageQuery;
	
	private List<String> dxtList;
	private List<String> pdtList;
	private List<String> dxxztList;
	private List<List<String>> tktList;
	private List<String> jdtList;
	public StudentSjdaCustom getStudentSjdaCustom() {
		return studentSjdaCustom;
	}
	public void setStudentSjdaCustom(StudentSjdaCustom studentSjdaCustom) {
		this.studentSjdaCustom = studentSjdaCustom;
	}
	public PageQuery getPageQuery() {
		return pageQuery;
	}
	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}
	public List<String> getDxtList() {
		return dxtList;
	}
	public void setDxtList(List<String> dxtList) {
		this.dxtList = dxtList;
	}
	public List<String> getDxxztList() {
		return dxxztList;
	}
	public void setDxxztList(List<String> dxxztList) {
		this.dxxztList = dxxztList;
	}
	public List<String> getJdtList() {
		return jdtList;
	}
	public void setJdtList(List<String> jdtList) {
		this.jdtList = jdtList;
	}
	public List<List<String>> getTktList() {
		return tktList;
	}
	public void setTktList(List<List<String>> tktList) {
		this.tktList = tktList;
	}
	public List<String> getPdtList() {
		return pdtList;
	}
	public void setPdtList(List<String> pdtList) {
		this.pdtList = pdtList;
	}

	
	
}

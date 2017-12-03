package ytk.business.pojo.vo;

import java.util.List;

import ytk.base.pojo.vo.PageQuery;

public class StudentSjQueryVo {
	private StudentSjCustom studentSjCustom;
	private PageQuery pageQuery;
	private List<StudentSjCustom>studentSjList;
	
	public StudentSjCustom getStudentSjCustom() {
		return studentSjCustom;
	}
	public void setStudentSjCustom(StudentSjCustom studentSjCustom) {
		this.studentSjCustom = studentSjCustom;
	}
	public PageQuery getPageQuery() {
		return pageQuery;
	}
	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}
	public List<StudentSjCustom> getStudentSjList() {
		return studentSjList;
	}
	public void setStudentSjList(List<StudentSjCustom> studentSjList) {
		this.studentSjList = studentSjList;
	}
	
	
}

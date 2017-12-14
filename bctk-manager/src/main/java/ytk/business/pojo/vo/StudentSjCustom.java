package ytk.business.pojo.vo;

import ytk.business.pojo.po.StudentSj;

public class StudentSjCustom extends StudentSj{
	private String studentId;
	private String classuuid;
	private Integer scoreMax;
	private Integer scoreMin;
	private String statusname;
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getClassuuid() {
		return classuuid;
	}
	public void setClassuuid(String classuuid) {
		this.classuuid = classuuid;
	}
	public Integer getScoreMax() {
		return scoreMax;
	}
	public void setScoreMax(Integer scoreMax) {
		this.scoreMax = scoreMax;
	}
	public Integer getScoreMin() {
		return scoreMin;
	}
	public void setScoreMin(Integer scoreMin) {
		this.scoreMin = scoreMin;
	}
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	
	
}

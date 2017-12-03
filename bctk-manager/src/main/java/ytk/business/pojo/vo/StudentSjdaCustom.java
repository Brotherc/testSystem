package ytk.business.pojo.vo;

import java.util.List;

import ytk.business.pojo.po.StudentSjda;

public class StudentSjdaCustom extends StudentSjda{
	private String statusname;
	private Integer scoreMin;
	private Integer scoreMax;
	private String typename;
	private List<String> dxxztAnswerList;

	public List<String> getDxxztAnswerList() {
		return dxxztAnswerList;
	}

	public void setDxxztAnswerList(List<String> dxxztAnswerList) {
		this.dxxztAnswerList = dxxztAnswerList;
	}

	public void setAnswerByDxxztAnswerList(){
		if(dxxztAnswerList!=null&&dxxztAnswerList.size()>0){
			String answer="";
			for(String dxxztAnswer:dxxztAnswerList){
				if(dxxztAnswer!=null&&!dxxztAnswer.trim().equals(""))
					answer+=dxxztAnswer;
			}
			setAnswer(answer);
		}
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public Integer getScoreMin() {
		return scoreMin;
	}

	public void setScoreMin(Integer scoreMin) {
		this.scoreMin = scoreMin;
	}

	public Integer getScoreMax() {
		return scoreMax;
	}

	public void setScoreMax(Integer scoreMax) {
		this.scoreMax = scoreMax;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	
}

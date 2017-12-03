package ytk.business.business;

import java.util.List;

import ytk.business.pojo.vo.StudentSjCustom;
import ytk.business.pojo.vo.StudentSjQueryVo;

public interface StudentSjEbo {
	//添加学生考试信息,返回uuid
	public String addStudentSj(Long studentid,String sjid,String ksgluuid) throws Exception;
	
	//根据条件查询学生考试信息
	public List<StudentSjCustom> findStudentSjList(StudentSjQueryVo studentSjQueryVo) throws Exception;
	
	//根据条件查询学生考试信息数量
	public int findStudentSjListCount(StudentSjQueryVo studentSjQueryVo) throws Exception;
	
	//根据学生试卷uuid自动为试卷评分(单选题，填空题)
	public void autoPStudentSj(String uuid) throws Exception;
}

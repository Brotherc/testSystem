package ytk.business.business;

import java.util.List;
import java.util.Map;

import ytk.business.pojo.po.StudentSjda;
import ytk.business.pojo.vo.StudentSjdaCustom;
import ytk.business.pojo.vo.StudentSjdaQueryVo;

public interface StudentSjdaEbo {
	//添加学生考试试卷答案
	public void addStudentSjda(String sjid,String studentsjid,List<StudentSjdaCustom> dxtList) throws Exception;
	
	//根据条件查询学生考试试卷答案
	public List<StudentSjdaCustom> findStudentSjdaList(StudentSjdaQueryVo studentSjdaQueryVo) throws Exception;
	
	//根据条件查询学生考试试卷答案数量
	public int findStudentSjdaListCount(StudentSjdaQueryVo studentSjdaQueryVo) throws Exception;
	
	//根据uuid查询学生考试试卷答案信息
	public StudentSjda findStudentSjdaByUuid(String uuid) throws Exception;
	
	//根据uuid为学生试卷答案评分
	public void pScore(String studentSjdaUuid,Integer score,Integer mscore) throws Exception;
	
	//添加学生试卷答案(单选题)到缓存中
	public void addStudentSjdaDxt(Long sysuseruuid,String ksgluuid,List<String> dxtList,Integer dxtSize) throws Exception;
	
	//查询学生试卷答案（单选题）
	public Map<Integer, String> findStudentSjDaDxt(Long sysuseruuid,String ksgluuid) throws Exception;

	//添加学生试卷答案(单选题)到缓存中
	public void addStudentSjdaTkt(Long sysuseruuid, String ksgluuid,List<List<String>> tktList) throws Exception;

	//查询学生试卷答案（填空题）
	public Map<Integer, List> findStudentSjDaTkt(Long sysuseruuid, String ksgluuid);
}

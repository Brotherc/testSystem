package ytk.base.business;

import ytk.base.pojo.po.Student;
import ytk.base.pojo.vo.StudentCustom;

public interface StudentEbo {
	public StudentCustom loginCheck(String studentId) throws Exception;
	
	public Student findStudentByStudentId(String studentId) throws Exception;
	
	//根据学号查询学生姓名
	public String findStudentNameByStudentId(String studentId) throws Exception;
}

package ytk.business.dao.mapper;

import java.util.List;

import ytk.business.pojo.vo.StudentSjCustom;
import ytk.business.pojo.vo.StudentSjQueryVo;

public interface StudentSjMapperCustom {
	public List<StudentSjCustom> findStudentSjList(StudentSjQueryVo studentSjQueryVo) throws Exception;
	
	public int findStudentSjListCount(StudentSjQueryVo studentSjQueryVo) throws Exception;
}
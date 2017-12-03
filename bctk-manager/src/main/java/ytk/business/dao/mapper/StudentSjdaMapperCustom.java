package ytk.business.dao.mapper;

import java.util.List;

import ytk.business.pojo.vo.StudentSjdaCustom;
import ytk.business.pojo.vo.StudentSjdaQueryVo;

public interface StudentSjdaMapperCustom {
	public List<StudentSjdaCustom> findStudentSjdaList(StudentSjdaQueryVo studentSjdaQueryVo) throws Exception;
	
	public int findStudentSjdaListCount(StudentSjdaQueryVo studentSjdaQueryVo) throws Exception;
}
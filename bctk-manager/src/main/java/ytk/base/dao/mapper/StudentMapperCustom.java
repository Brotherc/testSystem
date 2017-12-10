package ytk.base.dao.mapper;

import java.util.List;
import ytk.base.pojo.vo.StudentCustom;
import ytk.base.pojo.vo.StudentQueryVo;

public interface StudentMapperCustom {
	public int findKsglStudentAddListCount(StudentQueryVo studentQueryVo) throws Exception;
	
	public List<StudentCustom> findKsglStudentAddList(StudentQueryVo studentQueryVo) throws Exception;
}
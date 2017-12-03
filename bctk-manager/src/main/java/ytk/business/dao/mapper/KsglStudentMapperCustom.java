package ytk.business.dao.mapper;

import java.util.List;

import ytk.business.pojo.vo.KsglStudentCustom;
import ytk.business.pojo.vo.KsglStudentQueryVo;

public interface KsglStudentMapperCustom {
	public List<KsglStudentCustom> findKsglStudentList(KsglStudentQueryVo ksglStudentQueryVo) throws Exception;
	
	public int findKsglStudentListCount(KsglStudentQueryVo ksglStudentQueryVo) throws Exception;
	
}
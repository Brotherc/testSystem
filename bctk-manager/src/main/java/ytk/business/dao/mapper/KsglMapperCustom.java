package ytk.business.dao.mapper;

import java.util.List;


import ytk.business.pojo.vo.KsglCustom;
import ytk.business.pojo.vo.KsglQueryVo;

public interface KsglMapperCustom {
	public List<KsglCustom> findKsglList(KsglQueryVo ksglQueryVo) throws Exception;
	public int findKsglListCount(KsglQueryVo ksglQueryVo) throws Exception;
	
	public Long findKsglMinStarttime(KsglQueryVo ksglQueryVo) throws Exception;
	
	public List<KsglCustom> findKsglListBySysuserUuid(KsglQueryVo ksglQueryVo) throws Exception;
	
	public int findKsglListCountBySysuserUuid(KsglQueryVo ksglQueryVo) throws Exception;
}
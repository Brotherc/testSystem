package ytk.base.dao.mapper;

import java.util.List;

import ytk.base.pojo.vo.KcCustom;
import ytk.base.pojo.vo.KcQueryVo;

public interface KcMapperCustom {
	public List<KcCustom> findKcList(KcQueryVo kcQueryVo) throws Exception;
	
	public int findKcListCount(KcQueryVo kcQueryVo) throws Exception;
	
	public List<KcCustom> findKcListByQ(KcQueryVo kcQueryVo) throws Exception;
	
	public List<KcCustom> findKcListNoZy(KcQueryVo kcQueryVo) throws Exception;
	
	public List<KcCustom> findKcListNoZyByQ(KcQueryVo kcQueryVo) throws Exception;
}
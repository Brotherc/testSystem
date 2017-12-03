package ytk.base.dao.mapper;

import java.util.List;

import ytk.base.pojo.vo.ZyCustom;
import ytk.base.pojo.vo.ZyQueryVo;


public interface ZyMapperCustom {
	public List<ZyCustom> findZyList(ZyQueryVo zyQueryVo) throws Exception;
	
	public int findZyListCount(ZyQueryVo zyQueryVo) throws Exception;
	
	public List<ZyCustom> findZyListByQ(ZyQueryVo zyQueryVo) throws Exception;
}
package ytk.business.dao.mapper;

import java.util.List;

import ytk.business.pojo.vo.TktCustom;
import ytk.business.pojo.vo.TktQueryVo;

public interface TktMapperCustom {
	public List<TktCustom> findTktList(TktQueryVo tktQueryVo) throws Exception;
	
	public int findTktListCount(TktQueryVo TktQueryVo) throws Exception;
}
package ytk.business.dao.mapper;

import java.util.List;

import ytk.business.pojo.vo.DxtCustom;
import ytk.business.pojo.vo.DxtQueryVo;

public interface DxtMapperCustom {
	public List<DxtCustom> findDxtList(DxtQueryVo dxtQueryVo) throws Exception;
	
	public int findDxtListCount(DxtQueryVo dxtQueryVo) throws Exception;
}
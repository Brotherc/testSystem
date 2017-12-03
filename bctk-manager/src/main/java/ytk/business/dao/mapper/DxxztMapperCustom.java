package ytk.business.dao.mapper;

import java.util.List;

import ytk.business.pojo.vo.DxxztCustom;
import ytk.business.pojo.vo.DxxztQueryVo;

public interface DxxztMapperCustom {
	public List<DxxztCustom> findDxxztList(DxxztQueryVo dxxztQueryVo) throws Exception;
	
	public int findDxxztListCount(DxxztQueryVo dxxztQueryVo) throws Exception;
}
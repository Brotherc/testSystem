package ytk.base.dao.mapper;

import java.util.List;

import ytk.base.pojo.vo.ZsdCustom;
import ytk.base.pojo.vo.ZsdQueryVo;

public interface ZsdMapperCustom {
	public List<ZsdCustom> findZsdList(ZsdQueryVo zsdQueryVo) throws Exception;
	
	public int findZsdListCount(ZsdQueryVo zsdQueryVo) throws Exception;

	public List<ZsdCustom> findZsdListByQ(ZsdQueryVo zsdQueryVo) throws Exception;
}
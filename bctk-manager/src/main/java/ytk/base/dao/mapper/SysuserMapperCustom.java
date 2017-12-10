package ytk.base.dao.mapper;

import java.util.List;

import ytk.base.pojo.vo.Menu;
import ytk.base.pojo.vo.Operation;
import ytk.base.pojo.vo.SysuserCustom;
import ytk.base.pojo.vo.SysuserQueryVo;

public interface SysuserMapperCustom {
	public List<SysuserCustom> findSysuserList(SysuserQueryVo sysuserQueryVo) throws Exception;
	
	public int findSysuserListCount(SysuserQueryVo sysuserQueryVo) throws Exception;
	
	public List<Menu> findMenuBySysuserUuid(String sysuserUuid) throws Exception;
	
	public List<Operation> findOperatBySysuserUuid(String sysuserUuid) throws Exception;
	
}
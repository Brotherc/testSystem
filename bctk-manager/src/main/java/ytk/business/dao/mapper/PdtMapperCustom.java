package ytk.business.dao.mapper;

import java.util.List;

import ytk.business.pojo.vo.PdtCustom;
import ytk.business.pojo.vo.PdtQueryVo;

public interface PdtMapperCustom {
	public List<PdtCustom> findPdtList(PdtQueryVo pdtQueryVo) throws Exception;
	
	public int findPdtListCount(PdtQueryVo pdtQueryVo) throws Exception;
}
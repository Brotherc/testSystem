package ytk.business.dao.mapper;

import java.util.List;

import ytk.business.pojo.vo.SjTmCustom;
import ytk.business.pojo.vo.SjTmQueryVo;

public interface SjTmMapperCustom { 
	public List<SjTmCustom> findSjTmList(SjTmQueryVo sjTmQueryVo)  throws Exception;

	public int findSjTmListCount(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	public Integer findSjTmScoreByType(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	public int findSjTmCountByType(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	public int findAddSjTmListCount(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	public List<SjTmCustom> findAddSjTmList(SjTmQueryVo sjTmQueryVo) throws Exception;
}
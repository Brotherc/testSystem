package ytk.business.dao.mapper;

import java.util.List;

import ytk.business.pojo.vo.SjCustom;
import ytk.business.pojo.vo.SjQueryVo;


public interface SjMapperCustom {
	//根据查询条件查询试卷信息
	public List<SjCustom> findSjList(SjQueryVo sjQueryVo) throws Exception;
	//根据查询条件查询试卷数量
	public int findSjListCount(SjQueryVo sjQueryVo) throws Exception;
}
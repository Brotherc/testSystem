package ytk.business.dao.mapper;

import java.util.List;

import ytk.business.pojo.vo.JdtCustom;
import ytk.business.pojo.vo.JdtQueryVo;

public interface JdtMapperCustom {
	public List<JdtCustom> findJdtList(JdtQueryVo jdtQueryVo) throws Exception;
	
	public int findJdtListCount(JdtQueryVo jdtQueryVo) throws Exception;
}
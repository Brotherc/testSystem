package ytk.base.dao.mapper;

import java.util.List;

import ytk.base.pojo.vo.ClassCustom;
import ytk.base.pojo.vo.ClassQueryVo;


public interface ClassMapperCustom {
	public List<ClassCustom> findClassList(ClassQueryVo classQueryVo) throws Exception;
	
	public int findClassListCount(ClassQueryVo classQueryVo) throws Exception;
}
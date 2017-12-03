package ytk.base.business;

import java.util.List;

import ytk.base.pojo.vo.ClassCustom;
import ytk.base.pojo.vo.ClassQueryVo;

public interface ClassEbo {
	//根据条件查询所有班级信息
	public List<ClassCustom> findClassList(ClassQueryVo classQueryVo) throws Exception;
	
	//根据条件查询所有班级信息数量
	public int findClassListCount(ClassQueryVo classQueryVo) throws Exception;
	
	//根据uuid查询班级信息
	public ClassCustom findClassByUuid(String uuid) throws Exception;

	//根据uuid删除班级信息
	public void deleteClass(String uuid) throws Exception;

	//添加班级信息
	public void addClass(ClassCustom classCustom, int i) throws Exception;
}

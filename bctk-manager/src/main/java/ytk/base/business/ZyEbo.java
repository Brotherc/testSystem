package ytk.base.business;

import java.util.List;

import ytk.base.pojo.po.Zy;
import ytk.base.pojo.vo.ZyCustom;
import ytk.base.pojo.vo.ZyQueryVo;

public interface ZyEbo {
	//查询所有专业信息
	public List<Zy> findZyList() throws Exception;
	
	//根据条件查询专业信息
	public List<ZyCustom> findZyList(ZyQueryVo zyQueryVo) throws Exception;

	//根据uuid查询专业信息
	public ZyCustom  findZyByUuid(Long uuid) throws Exception;

	//根据系uuid查询专业信息
	public List<Zy> findZyByXiUuid(Long uuid) throws Exception;

	//根据简码查询课程信息
	public List<ZyCustom> findZyListByQ(ZyQueryVo zyQueryVo,String q) throws Exception;
	
	//根据条件查询专业数量
	public int findZyListCount(ZyQueryVo zyQueryVo) throws Exception;
	
	//添加专业信息
	public void addZy(ZyQueryVo zyQueryVo) throws Exception;
	
	//修改专业信息
	public void updateZy(Long uuid,ZyCustom zyCustom) throws Exception;
	
	//根据uuid删除用户信息
	public void deleteZy(Long uuid) throws Exception;
	
}


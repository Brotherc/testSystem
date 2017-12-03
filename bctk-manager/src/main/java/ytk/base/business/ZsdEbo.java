package ytk.base.business;

import java.util.List;

import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.vo.ZsdCustom;
import ytk.base.pojo.vo.ZsdQueryVo;

public interface ZsdEbo {
	
	//根据条件查询知识点信息
	public List<ZsdCustom> findZsdList(ZsdQueryVo zsdQueryVo) throws Exception;
	
	//根据条件查询知识点信息数量
	public int findZsdListCount(ZsdQueryVo zsdQueryVo) throws Exception;
	
	//添加知识点
	public void addZsd(ZsdQueryVo zsdQueryVo) throws Exception;

	//根据简码查询知识点信息
	public List<ZsdCustom> findZsdListByQ(ZsdQueryVo zsdQueryVo, String q) throws Exception;

	//删除知识点
	public void deleteZsd(String uuid) throws Exception;
	
	//根据uuid查询知识点信息
	public ZsdCustom findZsdByUuid(String uuid) throws Exception;

	//根据uuid更新知识点信息
	public void updateZsd(String uuid, ZsdCustom zsdCustom) throws Exception;
	
	//根据课程uuid查询知识点信息
	public List<Zsd> findZsdListByKcUuid(Long kcuuid) throws Exception;
	
}

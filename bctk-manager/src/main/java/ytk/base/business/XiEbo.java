package ytk.base.business;

import java.util.List;

import ytk.base.pojo.po.Xi;
import ytk.base.pojo.vo.XiCustom;
import ytk.base.pojo.vo.XiQueryVo;

public interface XiEbo {
	//查询所有系信息
	public List<Xi> findXiList() throws Exception;
	
	//根据条件查询系信息
	public List<Xi>findXiList(XiQueryVo xiQueryVo) throws Exception;
	
	//根据uuid查询系信息
	public Xi findXiByUuid(Long uuid) throws Exception;
	
	//根据条件查询系数量
	public int findXiListCount(XiQueryVo xiQueryVo) throws Exception;
	
	//添加系信息
	public void addXi(XiQueryVo xiQueryVo) throws Exception;
	
	//根据uuid修改系信息
	public void updateXi(Long uuid,XiCustom xiCustom) throws Exception;
	
	//根据uuid删除系信息
	public void deleteXi(Long uuid) throws Exception;
}

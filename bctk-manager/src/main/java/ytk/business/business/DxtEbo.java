package ytk.business.business;

import java.util.List;

import ytk.base.pojo.po.Sysuser;
import ytk.business.pojo.vo.DxtCustom;
import ytk.business.pojo.vo.DxtQueryVo;

public interface DxtEbo {
	//根据条件查询单选题信息
	public List<DxtCustom> findDxtList(DxtQueryVo dxtQueryVo) throws Exception;
	
	//根据条件查询单选题数量
	public int findDxtListCount(DxtQueryVo dxtQueryVo) throws Exception;
	
	//根据单选题的uuid查询信息
	public DxtCustom findDxtByUuid(String uuid) throws Exception;
	
	//添加单选题
	public void addDxt(DxtCustom dxtCustom,Sysuser sysuser,String[] zsdList) throws Exception;
	
	//修改单选题
	public void updateDxt(String uuid,DxtCustom dxtCustom,Sysuser sysuser,String[] zsdList) throws Exception;
	
	//删除单选题
	public void deleteDxt(String uuid,Long sysuseruuid) throws Exception;
	
	//审核通过题目
	public void checkDxt(DxtQueryVo dxtQueryVo,Sysuser sysuser) throws Exception;
	
	//撤销通过题目
	public void outCheckDxt(DxtQueryVo dxtQueryVo,Sysuser sysuser) throws Exception;
	
}

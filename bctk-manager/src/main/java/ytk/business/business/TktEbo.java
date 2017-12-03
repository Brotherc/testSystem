package ytk.business.business;

import java.util.List;

import ytk.base.pojo.po.Sysuser;
import ytk.business.pojo.vo.TktCustom;
import ytk.business.pojo.vo.TktQueryVo;

public interface TktEbo {
	//根据条件查询填空题信息
	public List<TktCustom> findTktList(TktQueryVo tktQueryVo) throws Exception;
	
	//根据条件查询填空题数量
	public int findTktListCount(TktQueryVo tktQueryVo) throws Exception;
	
	//根据填空题的uuid查询信息
	public TktCustom findTktByUuid(String uuid) throws Exception;
	
	//添加填空题
	public void addTkt(TktCustom tktCustom,Sysuser sysuser,String[] zsdList) throws Exception;
	
	//修改填空题
	public void updateTkt(String uuid,TktCustom tktCustom,Sysuser sysuser,String[] zsdList) throws Exception;
	
	//删除填空题
	public void deleteTkt(String uuid,Long sysuseruuid) throws Exception;
	
	//审核通过题目
	public void checkTkt(TktQueryVo tktQueryVo,Sysuser sysuser) throws Exception;
	
	//撤销通过题目
	public void outCheckTkt(TktQueryVo tktQueryVo,Sysuser sysuser) throws Exception;
}

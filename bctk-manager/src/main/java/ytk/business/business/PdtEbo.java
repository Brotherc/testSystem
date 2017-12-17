package ytk.business.business;

import java.util.List;

import ytk.base.pojo.po.Sysuser;
import ytk.business.pojo.vo.PdtCustom;
import ytk.business.pojo.vo.PdtQueryVo;

public interface PdtEbo {
	//根据条件查询判断题信息
	public List<PdtCustom> findPdtList(PdtQueryVo pdtQueryVo) throws Exception;
	
	//根据条件查询判断题数量
	public int findPdtListCount(PdtQueryVo pdtQueryVo) throws Exception;
	
	//根据判断题的uuid查询信息
	public PdtCustom findPdtByUuid(String uuid) throws Exception;
	
	//添加判断题
	public void addPdt(PdtCustom pdtCustom,Sysuser sysuser,String[] zsdList) throws Exception;
	
	//修改判断题
	public void updatePdt(String uuid,PdtCustom pdtCustom,Sysuser sysuser,String[] zsdList) throws Exception;
	
	//删除判断题
	public void deletePdt(String uuid,String sysuseruuid) throws Exception;
	
	//审核通过题目
	public void checkPdt(PdtQueryVo pdtQueryVo,Sysuser sysuser) throws Exception;
	
	//撤销通过题目
	public void outCheckPdt(PdtQueryVo pdtQueryVo,Sysuser sysuser) throws Exception;
}

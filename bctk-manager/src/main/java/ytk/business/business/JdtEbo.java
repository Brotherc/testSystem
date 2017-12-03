package ytk.business.business;

import java.util.List;

import ytk.base.pojo.po.Sysuser;
import ytk.business.pojo.vo.JdtCustom;
import ytk.business.pojo.vo.JdtQueryVo;

public interface JdtEbo {
	//根据条件查询简答题信息
	public List<JdtCustom> findJdtList(JdtQueryVo jdtQueryVo) throws Exception;
	
	//根据条件查询简答题数量
	public int findJdtListCount(JdtQueryVo jdtQueryVo) throws Exception;
	
	//根据简答题的uuid查询信息
	public JdtCustom findJdtByUuid(String uuid) throws Exception;
	
	//添加简答题
	public void addJdt(JdtCustom jdtCustom,Sysuser sysuser,String[] zsdList) throws Exception;
	
	//修改简答题
	public void updateJdt(String uuid,JdtCustom jdtCustom,Sysuser sysuser,String[] zsdList) throws Exception;
	
	//删除简答题
	public void deleteJdt(String uuid,Long sysuseruuid) throws Exception;
	
	//审核通过题目
	public void checkJdt(JdtQueryVo jdtQueryVo,Sysuser sysuser) throws Exception;
	
	//撤销通过题目
	public void outCheckJdt(JdtQueryVo jdtQueryVo,Sysuser sysuser) throws Exception;
}

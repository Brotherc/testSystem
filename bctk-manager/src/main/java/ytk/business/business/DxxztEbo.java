package ytk.business.business;

import java.util.List;

import ytk.base.pojo.po.Sysuser;
import ytk.business.pojo.vo.DxxztCustom;
import ytk.business.pojo.vo.DxxztQueryVo;

public interface DxxztEbo {
	//根据条件查询多项选择题信息
	public List<DxxztCustom> findDxxztList(DxxztQueryVo dxxztQueryVo) throws Exception;
	
	//根据条件查询多项选择题数量
	public int findDxxztListCount(DxxztQueryVo dxxztQueryVo) throws Exception;
	
	//根据多项选择题的uuid查询信息
	public DxxztCustom findDxxztByUuid(String uuid) throws Exception;
	
	//添加多项选择题
	public void addDxxzt(DxxztCustom dxxztCustom,Sysuser sysuser,String[] zsdList) throws Exception;
	
	//修改多项选择题
	public void updateDxxzt(String uuid,DxxztCustom dxxztCustom,Sysuser sysuser,String[] zsdList) throws Exception;
	
	//删除多项选择题
	public void deleteDxxzt(String uuid,String sysuseruuid) throws Exception;
	
	//审核通过题目
	public void checkDxxzt(DxxztQueryVo dxxztQueryVo,Sysuser sysuser) throws Exception;
	
	//撤销通过题目
	public void outCheckDxxzt(DxxztQueryVo dxxztQueryVo,Sysuser sysuser) throws Exception;
}

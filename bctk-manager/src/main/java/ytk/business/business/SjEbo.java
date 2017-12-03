package ytk.business.business;

import java.util.List;

import ytk.base.pojo.po.Sysuser;
import ytk.business.pojo.vo.SjCustom;
import ytk.business.pojo.vo.SjQueryVo;
import ytk.business.pojo.vo.SjmbCustom;

public interface SjEbo {
	//添加试卷信息
	public void addSj(SjCustom sjCustom,SjmbCustom sjmbCustom,Sysuser sysuser) throws Exception;
	
	//根据查询条件查询试卷信息
	public List<SjCustom> findSjList(SjQueryVo sjQueryVo) throws Exception;
	
	//根据查询条件查询试卷数量
	public int findSjListCount(SjQueryVo sjQueryVo) throws Exception;
	
	//根据uuid删除试卷信息
	public void deleteSj(String uuid) throws Exception;
	
	//将试卷导出为doc
	public String exportSj(SjQueryVo sjQueryVo,String filePath) throws Exception;
	
	//根据uuid查询试卷信息
	public SjCustom findSjCustomByUuid(String uuid) throws Exception;

	//修改试卷信息
	public void updateSj(SjCustom sjCustom, SjmbCustom sjmbCustom) throws Exception;
	
}

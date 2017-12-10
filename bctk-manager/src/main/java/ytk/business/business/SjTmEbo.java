package ytk.business.business;

import java.util.List;

import ytk.business.pojo.po.Dxt;
import ytk.business.pojo.po.Dxxzt;
import ytk.business.pojo.po.Jdt;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.Tkt;
import ytk.business.pojo.vo.SjCustom;
import ytk.business.pojo.vo.SjTmCustom;
import ytk.business.pojo.vo.SjTmQueryVo;
import ytk.business.pojo.vo.SjmbCustom;

public interface SjTmEbo {
	//根据查询条件查询试卷系题目信息
	public List<SjTmCustom> findSjTmList(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	//根据查询条件查询试卷系题目数量
	public int findSjTmListCount(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	//添加试卷题目
	public void addSjTm(SjCustom sjCustom,SjTm sjTm) throws Exception;
	
	//根据试卷id与系题目id查询试卷系题目信息
	public SjTm findSjTmBySjIdAndTmuuid(String sjId,String tmuuid) throws Exception;
	
	//根据查询条件查询可添加的试卷系题目信息
	public List<SjTmCustom> findAddSjTmList(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	//根据查询条件查询可添加的试卷题目数量
	public int findAddSjTmListCount(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	//根据uuid删除试卷题目信息
	public void deleteSjTm(String uuid) throws Exception;
	
	//跳转试卷题目添加页前进行有效性检验
	public void sjTmListPre(SjCustom sjCustom,SjmbCustom sjmbCustom) throws Exception;
	
	//根据uuid查询试卷题目信息
	public SjTm findSjTmByUuid(String uuid) throws Exception;
	
	//根据uuid修改试卷题目分数
	public void updateSjTmScore(String uuid,SjTmQueryVo sjTmQueryVo) throws Exception;
	
	//根据试卷uuid查询单选题信息
	public List<Dxt> findDxtBySjUuid(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	//根据试卷uuid查询多项选择题信息
	public List<Dxxzt> findDxxztBySjUuid(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	//根据试卷uuid查询填空题信息
	public List<Tkt> findTktBySjUuid(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	//根据试卷uuid查询简答题信息
	public List<Jdt> findJdtBySjUuid(SjTmQueryVo sjTmQueryVo) throws Exception;
	
	//根据试卷uuid删除该试卷的临时状态的题目信息
	public void deleteSjTmBySjUuidAndStatus(String sjUuid) throws Exception;
	
	//根据题目类型获取该类型题目的顺序
	public List<Integer> getSjTmOrderByType(String sysuseruuid,String ksgluuid,Integer type,Integer size) throws Exception;

	//根据uuid修改试卷题目编号
	public void updateSjTmSjtmid(String uuid, SjTmQueryVo sjTmQueryVo) throws Exception;
	
	//根据状态删除试卷题目信息
	public void deleteSjTmByStatus(Integer status) throws Exception;
	
}

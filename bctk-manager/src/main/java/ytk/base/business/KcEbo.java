package ytk.base.business;

import java.util.List;

import ytk.base.pojo.po.Kc;
import ytk.base.pojo.vo.KcCustom;
import ytk.base.pojo.vo.KcQueryVo;

public interface KcEbo {
	
	//根据条件查询课程信息
	public List<KcCustom>findKcList(KcQueryVo kcQueryVo) throws Exception;
	
	//根据条件查询课程数量
	public int findKcListCount(KcQueryVo kcQueryVo) throws Exception;
	
	//添加课程信息
	public void addKc(KcQueryVo kcQueryVo,String[] teacherList) throws Exception;
	
	//根据uuid修改课程信息
	public void updateKc(Long uuid,KcCustom kcCustom,Long[] zyList,String[] teacherList) throws Exception;
	
	//根据课程uuid和专业uuid删除课程专业信息
	public void deleteKc(Long uuid,Long zyuuid) throws Exception;

	//根据uuid查询课程信息
	public KcCustom findKcByUuid(Long uuid) throws Exception;

	//查询课程信息
	public List<Kc> findKcList() throws Exception;

	//根据简码查询课程信息
	public List<KcCustom> findKcListByQ(KcQueryVo kcQueryVo,String q) throws Exception;
	
	//查询不带专业的课程信息
	public List<KcCustom> findKcListNoZy(KcQueryVo kcQueryVo) throws Exception;
	
	//根据简码查询不带专业的课程信息
	public List<KcCustom> findKcListNoZyByQ(KcQueryVo kcQueryVo,String q) throws Exception;
	
}

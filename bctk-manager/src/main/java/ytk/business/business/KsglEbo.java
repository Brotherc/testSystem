package ytk.business.business;

import java.util.List;

import ytk.business.pojo.po.Ksgl;
import ytk.business.pojo.vo.KsglCustom;
import ytk.business.pojo.vo.KsglQueryVo;

public interface KsglEbo {
	
	//根据条件查询考试信息
	public List<KsglCustom> findKsglList(KsglQueryVo ksglQueryVo) throws Exception;
	
	//根据条件查询考试信息数量
	public int findKsglListCount(KsglQueryVo ksglQueryVo) throws Exception;
	
	//删除考试
	public void deleteKsgl(String uuid) throws Exception;
	
	//添加考试
	public void addKsgl(KsglQueryVo ksglQueryVo,String teacherUuid) throws Exception;
	
	//根据条件与uuid查询考试信息
	public KsglCustom findKsglByUuid(KsglQueryVo ksglQueryVo) throws Exception;
	
	//更新考试
	public void updateKsgl(String uuid, KsglCustom ksglCustom) throws Exception;
	
	//考试考试前时间校验(返回是否进入考试界面过)
	public boolean ksPre(String ksgluuid,String sysuseruuid) throws Exception;
	
	//启动考试
	public void startKsgl(String ksgluuid) throws Exception;
	
	//根据条件与用户id查询考试
	public List<KsglCustom> findKsglListBySysuserUuid(KsglQueryVo ksglQueryVo) throws Exception;
	
	//根据条件与用户id查询考试数量
	public int findKsglListCountBySysuserUuid(KsglQueryVo ksglQueryVo) throws Exception;
	
	//提交试卷
	public void sjSubmit(String studentUuid,String ksgluuid,String sjid) throws Exception;
	
	//检测考试密码是否正确
	public void checkKsPwd(String ksgluuid,String ksPwd) throws Exception;
	
	//检测监考密码是否正确
	public void checkJkPwd(String ksgluuid, String jkPwd) throws Exception;
	
	//根据uuid查询考试管理信息
	public Ksgl findKsglByUuid(String ksglUuid) throws Exception;
}

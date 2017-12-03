package ytk.base.business;

import java.util.List;
import java.util.Map;

import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.vo.ClassCustom;
import ytk.base.pojo.vo.SysuserCustom;
import ytk.base.pojo.vo.SysuserQueryVo;

public interface SysuserEbo {
	//根据条件查询系统用户信息
	public List<SysuserCustom> findSysuserList(SysuserQueryVo sysuserQueryVo) throws Exception;
	
	//根据条件查询系统用户数量
	public int findSysuserListCount(SysuserQueryVo sysuserQueryVo) throws Exception;
	
	//查询系统用户性别类型
	public Map<Integer, String>findSysuserGenderType() throws Exception; 
	
	//查询系统用户状态类型
	public Map<String, String>findSysuserStateType() throws Exception; 
	
	//添加用户
	public void addSysuser(SysuserCustom sysuserCustom,ClassCustom classCustom) throws Exception;
	
	//根据uuid查询用户
	public SysuserCustom findSysuserByUuid(Long uuid) throws Exception;
	
	//根据用户账号查询用户
	public Sysuser findSysuserByUserid(String userid) throws Exception;
	
	//跟新用户信息
	public void updateSysuser(Long uuid,SysuserCustom sysuserCustom,ClassCustom classCustom) throws Exception;
	
	//根据uuid删除用户
	public void deleteSysuserByUuid(Long uuid) throws Exception;
	
	//进行用户登录身份校验
	public SysuserCustom loginCheck(String userid,String pwd) throws Exception;
	
	//修改密码
	public void changePwd(SysuserCustom sysuserCustom,String pwd,String newpwd,String newpwdTwo) throws Exception;
	
	//根据用户类型查询用户
	public List<Sysuser> findSysuserByGroupid(String groupid) throws Exception;
}

package ytk.base.business.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.ClassEbo;
import ytk.base.business.SystemConfigEbo;
import ytk.base.business.SysuserEbo;
import ytk.base.dao.mapper.BssSysSysuserroleMapper;
import ytk.base.dao.mapper.SysuserMapper;
import ytk.base.dao.mapper.SysuserMapperCustom;
import ytk.base.dao.mapper.XiMapper;
import ytk.base.dao.mapper.ZyMapperCustom;
import ytk.base.pojo.po.BssSysSysuserrole;
import ytk.base.pojo.po.BssSysSysuserroleExample;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.SysuserExample;
import ytk.base.pojo.po.SysuserExample.Criteria;
import ytk.base.pojo.po.Xi;
import ytk.base.pojo.vo.Menu;
import ytk.base.pojo.vo.Operation;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.pojo.vo.Role;
import ytk.base.pojo.vo.SysuserCustom;
import ytk.base.pojo.vo.SysuserQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.business.dao.mapper.DxtMapper;
import ytk.business.dao.mapper.DxxztMapper;
import ytk.business.dao.mapper.JdtMapper;
import ytk.business.dao.mapper.KsglMapper;
import ytk.business.dao.mapper.KsglStudentMapper;
import ytk.business.dao.mapper.SjMapper;
import ytk.business.dao.mapper.TktMapper;
import ytk.business.pojo.po.Dxt;
import ytk.business.pojo.po.DxtExample;
import ytk.business.pojo.po.Dxxzt;
import ytk.business.pojo.po.DxxztExample;
import ytk.business.pojo.po.Jdt;
import ytk.business.pojo.po.JdtExample;
import ytk.business.pojo.po.Ksgl;
import ytk.business.pojo.po.KsglExample;
import ytk.business.pojo.po.Sj;
import ytk.business.pojo.po.SjExample;
import ytk.business.pojo.po.Tkt;
import ytk.business.pojo.po.TktExample;
import ytk.util.MD5;
import ytk.util.MyUtil;
import ytk.util.UUIDBuild;

public class SysuserEbi implements SysuserEbo{

	@Autowired
	private SysuserMapperCustom sysuserMapperCustom;
	@Autowired 
	private SysuserMapper sysuserMapper;
	@Autowired
	private SystemConfigEbo systemConfigEbo;
	@Autowired
	private XiMapper xiMapper;
	@Autowired
	private ClassEbo classEbo;
	@Autowired
	private ZyMapperCustom zyMapperCustom;
	@Autowired
	private KsglStudentMapper ksglStudentMapper;
	@Autowired
	private DxtMapper dxtMapper;
	@Autowired
	private DxxztMapper dxxztMapper;
	@Autowired
	private TktMapper tktMapper;
	@Autowired
	private JdtMapper jdtMapper;
	@Autowired
	private SjMapper sjMapper;
	@Autowired
	private KsglMapper ksglMapper;
	@Autowired
	private BssSysSysuserroleMapper bssSysSysuserroleMapper;
	
	@Override
	public List<SysuserCustom> findSysuserList(SysuserQueryVo sysuserQueryVo) throws Exception{
		SysuserCustom sysuserCustom = sysuserQueryVo.getSysuserCustom();
		if(sysuserCustom!=null){
			Long birthdayMax = sysuserCustom.getBirthdayMax();
			if(birthdayMax!=null&&!birthdayMax.equals(""))
				birthdayMax=birthdayMax+86400000-1;
			sysuserCustom.setBirthdayMax(birthdayMax);
		}
		List<SysuserCustom> sysuserList = sysuserMapperCustom.findSysuserList(sysuserQueryVo);
		
		PageQuery pageQuery = sysuserQueryVo.getPageQuery();
		if(pageQuery!=null){
			int lastIndex=pageQuery.getPageQuery_start()+pageQuery.getPageQuery_pageSize();
			if(pageQuery.getPageQuery_pageSize()>sysuserList.size()-pageQuery.getPageQuery_start())
				lastIndex=sysuserList.size();
			List<SysuserCustom> subList = sysuserList.subList(pageQuery.getPageQuery_start(), lastIndex);
			return subList;
		}
		return sysuserList;
	}

	@Override
	public int findSysuserListCount(SysuserQueryVo sysuserQueryVo)
			throws Exception {
		SysuserCustom sysuserCustom = sysuserQueryVo.getSysuserCustom();
		if(sysuserCustom!=null){
			Long birthdayMax = sysuserCustom.getBirthdayMax();
			if(birthdayMax!=null&&!birthdayMax.equals(""))
				birthdayMax=birthdayMax+86400000-1;
			sysuserCustom.setBirthdayMax(birthdayMax);
		}
		return sysuserMapperCustom.findSysuserListCount(sysuserQueryVo);
	}

	@Override
	public Map<Integer, String> findSysuserGenderType() throws Exception {
		return SysuserCustom.genderMap;
	}

	@Override
	public Map<String, String> findSysuserStateType() throws Exception {
		return SysuserCustom.userstateMap;
	}

	@Override
	public void addSysuser(SysuserCustom sysuserCustom,String[] roleList,String teacherRoleUuid) throws Exception {
		
		boolean isTeacher = checkIsTeacher(roleList, teacherRoleUuid);
		
		//参数校验
		//通用的参数合法校验，非空校验，长度校验
		checkNull(sysuserCustom,roleList,isTeacher); 
		if(!MyUtil.isNotNullAndEmptyByTrim(sysuserCustom.getPwd())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户密码"}));
		}
		
		//数据业务合法性校验
		//账号唯一性校验，查询数据库校验出来
		//思路：根据用户账号查询sysuser表，如果查询到说明 账号重复
		String addUserid = sysuserCustom.getUserid();
		SysuserExample sysuserExample=new SysuserExample();
		Criteria criteria = sysuserExample.createCriteria();
		criteria.andUseridEqualTo(addUserid);
		List<Sysuser> list = sysuserMapper.selectByExample(sysuserExample);
		if(list!=null&&list.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 208, null));
		
		//密码加密
		String md5Pwd=new MD5().getMD5ofStr(sysuserCustom.getPwd());
		sysuserCustom.setPwd(md5Pwd);
		
		//如果用户是教师
		if(isTeacher){
			Xi xi = xiMapper.selectByPrimaryKey(sysuserCustom.getXuuid());
			if(xi==null)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 204, null));
		}
		else{
			sysuserCustom.setXuuid(null);
		}

		//设置uuid
		String sysuserUuid = UUIDBuild.getUUID();
		sysuserCustom.setUuid(sysuserUuid);
		
		//添加用户
		sysuserMapper.insert(sysuserCustom);
		
		//遍历该用户角色信息
		for(String roleId:roleList){
			BssSysSysuserrole bssSysSysuserrole=new BssSysSysuserrole();
			bssSysSysuserrole.setSrid(UUIDBuild.getUUID());
			bssSysSysuserrole.setSysuserid(sysuserCustom.getUuid());
			bssSysSysuserrole.setRoleid(roleId);
			
			//添加用户角色信息
			bssSysSysuserroleMapper.insert(bssSysSysuserrole);
		}
	}

	@Override
	public SysuserCustom findSysuserByUuid(String uuid) throws Exception {
		SysuserQueryVo sysuserQueryVo=new SysuserQueryVo();
		SysuserCustom sysuserCustom=new SysuserCustom();
		sysuserCustom.setUuid(uuid);
		sysuserQueryVo.setSysuserCustom(sysuserCustom);
		List<SysuserCustom> sysuserList = sysuserMapperCustom.findSysuserList(sysuserQueryVo);
		if(sysuserList==null||sysuserList.size()<1)
			return null;
		return sysuserList.get(0);
	}
	
	@Override
	public Sysuser findSysuserByUserid(String userid) throws Exception {
		SysuserExample sysuserExample=new SysuserExample();
		Criteria criteria = sysuserExample.createCriteria();
		criteria.andUseridEqualTo(userid);
		List<Sysuser> sysuserList = sysuserMapper.selectByExample(sysuserExample);
		if(sysuserList!=null&&sysuserList.size()>0)
			return sysuserList.get(0);
		return null;
	}

	@Override
	public void updateSysuser(String uuid, SysuserCustom sysuserCustom,String[] roleList,String teacherRoleUuid)
			throws Exception {
		boolean isTeacher = checkIsTeacher(roleList, teacherRoleUuid);
		
		checkNull(sysuserCustom,roleList,isTeacher); 
		
		//修改的用户账号信息必须存在
		Sysuser sysuser = sysuserMapper.selectByPrimaryKey(uuid);
		if(sysuser==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 215, null));
		
		//如果修改的账号与数据库中存在的账号不想同
		//则修改的账号不能与已存在的账号重复
		String updateUserid=sysuserCustom.getUserid();
		String userid=sysuser.getUserid();
		if(!updateUserid.equals(userid)){
			//查询出与修改账号相同的用户信息，则提示异常信息
			if(findSysuserByUserid(updateUserid)!=null)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 213, null));
			sysuser.setUserid(updateUserid);
		}

		//如果用户是教师，校验更新系信息存在
		if(isTeacher){
			Xi xi = xiMapper.selectByPrimaryKey(sysuserCustom.getXuuid());
			if(xi==null)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 204, null));
		}
		else{
			sysuserCustom.setXuuid(null);
		}

		//如果密码进行了修改，则对密码进行加密，并更新字段
		String updatePwd=sysuserCustom.getPwd();
		if(MyUtil.isNotNullAndEmptyByTrim(updatePwd)){
			//加密
			String md5Pwd = new MD5().getMD5ofStr(updatePwd);
			sysuser.setPwd(md5Pwd);
		}

		sysuser.setAddr(sysuserCustom.getAddr());
		if(sysuserCustom.getBirthday()!=null)
			sysuser.setBirthday(sysuserCustom.getBirthday());
		sysuser.setEmail(sysuserCustom.getEmail());
		sysuser.setGender(sysuserCustom.getGender());
		sysuser.setName(sysuserCustom.getName());
		sysuser.setTele(sysuserCustom.getTele());
		sysuser.setUsername(sysuserCustom.getUsername());
		sysuser.setUserstate(sysuserCustom.getUserstate());
		sysuser.setXuuid(sysuserCustom.getXuuid());
			

		//更新用户信息
		sysuserMapper.updateByPrimaryKey(sysuser);
		
		//删除该用户原先的角色信息
		BssSysSysuserroleExample bssSysSysuserroleExample=new BssSysSysuserroleExample();
		BssSysSysuserroleExample.Criteria criteria = bssSysSysuserroleExample.createCriteria();
		criteria.andSysuseridEqualTo(uuid);
		bssSysSysuserroleMapper.deleteByExample(bssSysSysuserroleExample);
		
		//添加新的角色信息
		for(String roleId:roleList){
			BssSysSysuserrole bssSysSysuserrole=new BssSysSysuserrole();
			bssSysSysuserrole.setSrid(UUIDBuild.getUUID());
			bssSysSysuserrole.setSysuserid(uuid);
			bssSysSysuserrole.setRoleid(roleId);
			
			//添加用户角色信息
			bssSysSysuserroleMapper.insert(bssSysSysuserrole);
		}
	}

	@Override
	public void deleteSysuserByUuid(String uuid,String teacherRoleUuid) throws Exception {
		//删除的用户账号信息必须存在
		SysuserCustom sysuserCustom = findSysuserByUuid(uuid);

		if(sysuserCustom==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 212, null));
			
		List<Role> roleList = sysuserCustom.getRoleList();
		
		boolean isTeacher=false;
		for(Role role:roleList){
			if(role.getRoleId().equals(teacherRoleUuid)){
				isTeacher=true;
				break;
			}
		}

		//该用户是教师，如果该教师存在添加的题目，试卷，考试管理，则不允许删除
		if(isTeacher){
			DxtExample dxtExample=new DxtExample();
			DxtExample.Criteria dxtCriteria = dxtExample.createCriteria();
			dxtCriteria.andTeacheruuidEqualTo(uuid);
			List<Dxt> dxtList = dxtMapper.selectByExample(dxtExample);
			if(dxtList!=null&&dxtList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 220, null));
			
			DxxztExample dxxztExample=new DxxztExample();
			DxxztExample.Criteria dxxztCriteria = dxxztExample.createCriteria();
			dxxztCriteria.andTeacheruuidEqualTo(uuid);
			List<Dxxzt> dxxztList = dxxztMapper.selectByExample(dxxztExample);
			if(dxxztList!=null&&dxxztList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 220, null));
			
			TktExample tktExample=new TktExample();
			TktExample.Criteria tktCriteria = tktExample.createCriteria();
			tktCriteria.andTeacheruuidEqualTo(uuid);
			List<Tkt> tktList = tktMapper.selectByExample(tktExample);
			if(tktList!=null&&tktList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 220, null));
			
			JdtExample jdtExample=new JdtExample();
			JdtExample.Criteria jdtCriteria = jdtExample.createCriteria();
			jdtCriteria.andTeacheruuidEqualTo(uuid);
			List<Jdt> jdtList = jdtMapper.selectByExample(jdtExample);
			if(jdtList!=null&&jdtList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 220, null));
			
			SjExample sjExample=new SjExample();
			SjExample.Criteria sjCriteria = sjExample.createCriteria();
			sjCriteria.andTeacheridEqualTo(uuid);
			List<Sj> sjList = sjMapper.selectByExample(sjExample);
			if(sjList!=null&&sjList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 221, null));
			
			KsglExample ksglExample=new KsglExample();
			KsglExample.Criteria ksglCriteria = ksglExample.createCriteria();
			ksglCriteria.andTeacheridEqualTo(uuid);
			List<Ksgl> ksglList = ksglMapper.selectByExample(ksglExample);
			if(ksglList!=null&&ksglList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 222, null));
		}
		sysuserMapper.deleteByPrimaryKey(uuid);
		
		//删除该用户角色信息
		BssSysSysuserroleExample bssSysSysuserroleExample=new BssSysSysuserroleExample();
		BssSysSysuserroleExample.Criteria criteria = bssSysSysuserroleExample.createCriteria();
		criteria.andSysuseridEqualTo(uuid);
		bssSysSysuserroleMapper.deleteByExample(bssSysSysuserroleExample);
	}

	@Override
	public SysuserCustom loginCheck(String userid, String pwd) throws Exception {
		//检验该用户是否存在
		Sysuser sysuser=findSysuserByUserid(userid);
		if(sysuser==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 101, null));
		//若存在，密码是否正确
		//现将页面的密码进行加密，再与查询出来的用户密码进行比较
		String md5Pwd = new MD5().getMD5ofStr(pwd);
		String sysuserPwd=sysuser.getPwd();
		if(!md5Pwd.equals(sysuserPwd))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 114, null));
		SysuserCustom sysuserCustom=new SysuserCustom();
		BeanUtils.copyProperties(sysuser, sysuserCustom);
		
		
		// 取出用户的菜单
		//根据角色id获取菜单
		List<Menu> menu_list = sysuserMapperCustom.findMenuBySysuserUuid(sysuser.getUuid());
		for(Menu menu:menu_list){
			System.out.println(menu.getMenuname());
		}
		Menu menu = new Menu();
		menu.setMenus(menu_list);
		sysuserCustom.setMenu(menu);//将用户菜单存入用户身份对象中
		
		//根据用户角色获取操作权限
		List<Operation> operations = sysuserMapperCustom.findOperatBySysuserUuid(sysuser.getUuid());
		for(Operation operation:operations){
			System.out.println(operation.getOperationName());
		}
		sysuserCustom.setOperationList(operations);//将用户操作权限存入用户身份对象中
		
		
		return sysuserCustom;
	}

	//角色非空判断，并且判断角色中是否包含教师角色
	private boolean checkIsTeacher(String[] roleList,String teacherRoleUuid) throws Exception{
		if(roleList==null||roleList.length<1){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户角色"}));
		}
		//默认添加的用户不是教师
		boolean isTeacher=false;
		for(String roleUuid:roleList){
			if(roleUuid.equals(teacherRoleUuid)){
				isTeacher=true;
				break;
			}
		}
		return isTeacher;
	}	
	
	private void checkNull(SysuserCustom sysuserCustom,String[] roleList,boolean isTeacher) throws Exception{
		if(!MyUtil.isNotNullAndEmptyByTrim(sysuserCustom.getUserid())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户账号"}));
		}
		if(!MyUtil.isNotNullAndEmptyByTrim(sysuserCustom.getUsername())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户名称"}));
		}
		//如果用户是教师
		if(isTeacher){
			if(sysuserCustom.getXuuid()==null){
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户单位"}));
			}
		}
		if(!MyUtil.isNotNullAndEmptyByTrim(sysuserCustom.getUserstate())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户状态"}));
		}
	}

	@Override
	public void changePwd(SysuserCustom sysuserCustom, String pwd,String newpwd,
			String newpwdTwo) throws Exception {
		//非空判断
		if(!MyUtil.isNotNullAndEmptyByTrim(pwd)){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"原始密码"}));
		}
		if(!MyUtil.isNotNullAndEmptyByTrim(newpwd)){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"新密码"}));
		}
		if(!MyUtil.isNotNullAndEmptyByTrim(newpwdTwo)){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"第二次输入新密码"}));
		}
		//对用户输入的原始密码进行加密，再与登录用户的密码进行校验，一致则进行下一步验证
		String md5Pwd = new MD5().getMD5ofStr(pwd);
		String sysuserPwd=sysuserCustom.getPwd();
		if(!md5Pwd.trim().equals(sysuserPwd.trim()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 124, null));
		//判断两次新密码输入是否一致
		if(!newpwd.trim().equals(newpwdTwo.trim()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 123, null));
		//对新密码进行加密，修改用户密码
		String md5NewPwd = new MD5().getMD5ofStr(newpwd);
		sysuserCustom.setPwd(md5NewPwd);
		sysuserMapper.updateByPrimaryKey(sysuserCustom);
	}

	@Override
	public List<SysuserCustom> findSysuserByRoleId(String roleId) throws Exception {
		SysuserQueryVo sysuserQueryVo=new SysuserQueryVo();
		SysuserCustom sysuserCustom=new SysuserCustom();
		sysuserCustom.setRoleId(roleId);
		sysuserQueryVo.setSysuserCustom(sysuserCustom);
		return sysuserMapperCustom.findSysuserList(sysuserQueryVo);
		
	}

}

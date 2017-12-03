package ytk.base.business.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.ClassEbo;
import ytk.base.business.SystemConfigEbo;
import ytk.base.business.SysuserEbo;
import ytk.base.dao.mapper.SysuserMapper;
import ytk.base.dao.mapper.SysuserMapperCustom;
import ytk.base.dao.mapper.XiMapper;
import ytk.base.dao.mapper.ZyMapperCustom;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.SysuserExample;
import ytk.base.pojo.po.SysuserExample.Criteria;
import ytk.base.pojo.po.Xi;
import ytk.base.pojo.vo.ClassCustom;
import ytk.base.pojo.vo.ClassQueryVo;
import ytk.base.pojo.vo.Menu;
import ytk.base.pojo.vo.Operation;
import ytk.base.pojo.vo.SysuserCustom;
import ytk.base.pojo.vo.SysuserQueryVo;
import ytk.base.pojo.vo.ZyCustom;
import ytk.base.pojo.vo.ZyQueryVo;
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
import ytk.business.pojo.po.KsglStudent;
import ytk.business.pojo.po.KsglStudentExample;
import ytk.business.pojo.po.Sj;
import ytk.business.pojo.po.SjExample;
import ytk.business.pojo.po.Tkt;
import ytk.business.pojo.po.TktExample;
import ytk.util.MD5;
import ytk.util.MyUtil;

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
	
	@Override
	public List<SysuserCustom> findSysuserList(SysuserQueryVo sysuserQueryVo) throws Exception{
		SysuserCustom sysuserCustom = sysuserQueryVo.getSysuserCustom();
		if(sysuserCustom!=null){
			Long birthdayMax = sysuserCustom.getBirthdayMax();
			if(birthdayMax!=null&&!birthdayMax.equals(""))
				birthdayMax=birthdayMax+86400000-1;
			sysuserCustom.setBirthdayMax(birthdayMax);
		}
		return sysuserMapperCustom.findSysuserList(sysuserQueryVo);
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
	public void addSysuser(SysuserCustom sysuserCustom,ClassCustom classCustom) throws Exception {
		//参数校验
		//通用的参数合法校验，非空校验，长度校验
		checkNull(sysuserCustom,classCustom);
		
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
		
		//如果用户是教师学生，校验系信息存在
		String groupid = sysuserCustom.getGroupid();
		
		//如果用户是教师或学生
		if(groupid.equals("1")||groupid.equals("3")){
			Xi xi = xiMapper.selectByPrimaryKey(sysuserCustom.getXuuid());
			if(xi==null)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 204, null));
		}
		else{
			sysuserCustom.setXuuid(null);
		}
		
		//如果用户是学生校验班级，年级，专业存在
		if(groupid.equals("3")){
			
			//学生所属系存在对应学生专业
			ZyQueryVo zyQueryVo=new ZyQueryVo();
			ZyCustom zyCustom=new ZyCustom();
			zyCustom.setXuuid(sysuserCustom.getXuuid());
			zyCustom.setName(classCustom.getZyname());
			zyQueryVo.setZyCustom(zyCustom);
			List<ZyCustom> zyList = zyMapperCustom.findZyList(zyQueryVo);
			if(zyList==null||zyList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 218, null));
			
			//班级信息存在
			ClassQueryVo classQueryVo=new ClassQueryVo();
			classQueryVo.setClassCustom(classCustom);
			List<ClassCustom> classList = classEbo.findClassList(classQueryVo);
			if(classList==null||classList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
			ClassCustom classCustom2 = classList.get(0);
			sysuserCustom.setClassuuid(classCustom2.getUuid());
		}		
		
		//添加用户
		sysuserMapper.insert(sysuserCustom);
	}

	@Override
	public SysuserCustom findSysuserByUuid(Long uuid) throws Exception {
		Sysuser sysuser = sysuserMapper.selectByPrimaryKey(uuid);
		//若查询出的要修改的对象为空，则抛出异常
		if(sysuser==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 215, null));
		SysuserCustom sysuserCustom=new SysuserCustom();
		BeanUtils.copyProperties(sysuser, sysuserCustom);
		return sysuserCustom;
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
	public void updateSysuser(Long uuid, SysuserCustom sysuserCustom,ClassCustom classCustom)
			throws Exception {
		checkNull(sysuserCustom,classCustom);
		
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

		//如果用户是教师学生，校验更新系信息存在
		String groupid = sysuserCustom.getGroupid();
		if(groupid.equals("1")||groupid.equals("3")){
			Xi xi = xiMapper.selectByPrimaryKey(sysuserCustom.getXuuid());
			if(xi==null)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 204, null));
		}
		else{
			sysuserCustom.setXuuid(null);
		}
		//如果用户是学生校验班级，年级，专业存在
		if(groupid.equals("3")){
			
			//学生所属系用该存在对应学生专业
			ZyQueryVo zyQueryVo=new ZyQueryVo();
			ZyCustom zyCustom=new ZyCustom();
			zyCustom.setXuuid(sysuserCustom.getXuuid());
			zyCustom.setName(classCustom.getZyname());
			zyQueryVo.setZyCustom(zyCustom);
			List<ZyCustom> zyList = zyMapperCustom.findZyList(zyQueryVo);
			if(zyList==null||zyList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 218, null));
				
			ClassQueryVo classQueryVo=new ClassQueryVo();
			classQueryVo.setClassCustom(classCustom);
			List<ClassCustom> classList = classEbo.findClassList(classQueryVo);
			if(classList==null||classList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
			ClassCustom classCustom2 = classList.get(0);
			sysuserCustom.setClassuuid(classCustom2.getUuid());
		}	

		//如果密码进行了修改，则对密码进行加密，并更新字段
		String updatePwd=sysuserCustom.getPwd();
		String pwd=sysuser.getPwd();
		if(!updatePwd.equals(pwd))
			if(updatePwd!=null||!updatePwd.trim().equals("")){
				//加密
				String md5Pwd = new MD5().getMD5ofStr(updatePwd);
				sysuser.setPwd(md5Pwd);
			}
		sysuser.setAddr(sysuserCustom.getAddr());
		if(sysuserCustom.getBirthday()!=null)
			sysuser.setBirthday(sysuserCustom.getBirthday());
		sysuser.setEmail(sysuserCustom.getEmail());
		sysuser.setGender(sysuserCustom.getGender());
		sysuser.setGroupid(sysuserCustom.getGroupid());
		sysuser.setName(sysuserCustom.getName());
		sysuser.setTele(sysuserCustom.getTele());
		sysuser.setUsername(sysuserCustom.getUsername());
		sysuser.setUserstate(sysuserCustom.getUserstate());
		sysuser.setXuuid(sysuserCustom.getXuuid());
			

		//更新用户信息
		sysuserMapper.updateByPrimaryKey(sysuser);
	}

	@Override
	public void deleteSysuserByUuid(Long uuid) throws Exception {
		//删除的用户账号信息必须存在
		Sysuser sysuser = sysuserMapper.selectByPrimaryKey(uuid);
		if(sysuser==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 212, null));
		
		String groupid = sysuser.getGroupid();
		//该用户是学生，如果该学生存在考试管理学生信息，试卷，试卷答案，则不允许删除
		//（删除考试学生信息时，会将后面的试卷，试卷答案信息删除）
		if(groupid!=null&&groupid.equals("3")){
			KsglStudentExample ksglStudentExample=new KsglStudentExample();
			KsglStudentExample.Criteria ksglStudentCriteria = ksglStudentExample.createCriteria();
			ksglStudentCriteria.andSysuseruuidEqualTo(uuid);
			List<KsglStudent> ksglStudentList = ksglStudentMapper.selectByExample(ksglStudentExample);
			if(ksglStudentList!=null&&ksglStudentList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 219, null));
		}
			
		//该用户是教师，如果该教师存在添加的题目，试卷，考试管理，则不允许删除
		if(groupid!=null&&groupid.equals("1")){
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
		//根据用户类型id从数据字典表取出角色id
		String roleid = systemConfigEbo.findDictinfoByDictcode("s01", sysuser.getGroupid()).getRemark();
		System.out.println(roleid);
		//根据角色id获取菜单
		List<Menu> menu_list = sysuserMapperCustom.findMenuByroleid(roleid);
		for(Menu menu:menu_list){
			System.out.println(menu.getMenuname());
		}
		Menu menu = new Menu();
		menu.setMenus(menu_list);
		sysuserCustom.setMenu(menu);//将用户菜单存入用户身份对象中
		
		//根据用户角色获取操作权限
		List<Operation> operations = sysuserMapperCustom.findOperatByRoleid(roleid);
		for(Operation operation:operations){
			System.out.println(operation.getOperationName());
		}
		sysuserCustom.setOperationList(operations);//将用户操作权限存入用户身份对象中
		
		
		return sysuserCustom;
	}

	private void checkNull(SysuserCustom sysuserCustom,ClassCustom classCustom) throws Exception{
		if(!MyUtil.isNotNullAndEmptyByTrim(sysuserCustom.getUserid())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户账号"}));
		}
		if(!MyUtil.isNotNullAndEmptyByTrim(sysuserCustom.getPwd())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户密码"}));
		}
		if(!MyUtil.isNotNullAndEmptyByTrim(sysuserCustom.getUsername())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户名称"}));
		}
		if(!MyUtil.isNotNullAndEmptyByTrim(sysuserCustom.getGroupid())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户类型"}));
		}
		String groupid = sysuserCustom.getGroupid();
		System.out.println(groupid);
		//如果用户是教师或学生
		if(groupid.equals("1")||groupid.equals("3")){
			if(sysuserCustom.getXuuid()==null){
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户单位"}));
			}
		}
		//如果用户是学生
		//校验班级，年级，专业信息不为空
		if(groupid.equals("3")){
			if(classCustom.getNjuuid()==null){
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"年级"}));
			}		
			if(!MyUtil.isNotNullAndEmptyByTrim(classCustom.getZyname())){
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"专业"}));
			}
			if(!MyUtil.isNotNullAndEmptyByTrim(classCustom.getClassname())){
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"班级"}));
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
	public List<Sysuser> findSysuserByGroupid(String groupid) throws Exception {
		SysuserExample sysuserExample=new SysuserExample();
		Criteria sysuserCriteria = sysuserExample.createCriteria();
		sysuserCriteria.andGroupidEqualTo(groupid);
		sysuserCriteria.andUserstateEqualTo("1");
		return sysuserMapper.selectByExample(sysuserExample);
	}

}

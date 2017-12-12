package ytk.base.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.ClassEbo;
import ytk.base.business.SystemConfigEbo;
import ytk.base.business.SysuserEbo;
import ytk.base.pojo.po.Dictinfo;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.pojo.vo.Role;
import ytk.base.pojo.vo.SysuserCustom;
import ytk.base.pojo.vo.SysuserQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;

@Controller
public class SysuserAction {
	
	@Autowired 
	private SysuserEbo sysuserEbo;
	@Autowired 
	private SystemConfigEbo systemConfigEbo;
	@Autowired
	private ClassEbo classEbo;
	
	//进入系统用户列表页，加载页面信息
	@RequestMapping("/sysuserList")
	public String toSysuserList(Model model) throws Exception{ 
		return "/base/sysuser/list";
	}
	
	//查询系统用户信息，返回json数据
	@RequestMapping("/sysuser/query")
	public @ResponseBody DataGridResultInfo querySysuser(SysuserQueryVo sysuserQueryVo,int page,int rows) throws Exception{
		//非空校验
		sysuserQueryVo=sysuserQueryVo==null?new SysuserQueryVo():sysuserQueryVo;
		//查询列表的总数
		int total = sysuserEbo.findSysuserListCount(sysuserQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		sysuserQueryVo.setPageQuery(pageQuery);

		List<SysuserCustom> sysuserList = sysuserEbo.findSysuserList(sysuserQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(sysuserList);
		return dataGridResultInfo;
	}
	
	//进入系统用户添加页，加载页面信息
	@RequestMapping("/sysuserInput")
	public String toSysuserInput(Model model) throws Exception{
		//加载用户状态信息
		Map<String, String> sysuserStateTypeMap = sysuserEbo.findSysuserStateType();
		model.addAttribute("sysuserStateTypeMap", sysuserStateTypeMap);
		return "/base/sysuser/input";
	}
	
	//根据页面传送的数据添加用户，返回响应信息
	@RequestMapping("/sysuser/add")
	public @ResponseBody SubmitResultInfo addSysuser(SysuserQueryVo sysuserQueryVo,String[] roleList) throws Exception{
		
		//查询教师角色的uuid
		String teacherRoleUuid=systemConfigEbo.findDictinfoByDictcode("s01", "1").getRemark();
		
		//添加用户
		sysuserEbo.addSysuser(sysuserQueryVo.getSysuserCustom(),roleList,teacherRoleUuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//进入系统用户修改页，根据uuid加载用户信息
	@RequestMapping("/sysuserEdit")
	public String toSysuserEdit(String uuid,Model model) throws Exception{
		
		//查询修改用户信息
		SysuserCustom sysuserCustom = sysuserEbo.findSysuserByUuid(uuid);
		model.addAttribute("sysuserCustom", sysuserCustom);

		List<Role> roleList = sysuserCustom.getRoleList();
		
		//加载用户角色信息
		String s="[";
		for(int i=0;i<roleList.size();i++){
			s+="'"+roleList.get(i).getRoleId()+"'";
			if(i!=roleList.size()-1)
				s+=",";
		}
		s+="]";
		System.out.println(s);
		model.addAttribute("roleList", s);
		
		return "/base/sysuser/edit";
	}
	//根据页面传递的数据，修改用户信息
	@RequestMapping("/sysuser/edit")
	public @ResponseBody SubmitResultInfo editSysuser(String uuid,SysuserQueryVo sysuserQueryVo,String[] roleList) throws Exception{
		
		//查询教师角色的uuid
		String teacherRoleUuid=systemConfigEbo.findDictinfoByDictcode("s01", "1").getRemark();
		
		SysuserCustom sysuserCustom = sysuserQueryVo.getSysuserCustom();
		sysuserEbo.updateSysuser(uuid, sysuserCustom,roleList,teacherRoleUuid);
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	@RequestMapping("/sysuser/delete")
	public @ResponseBody SubmitResultInfo deleteSysuser(String uuid) throws Exception{
		//查询教师角色的uuid
		String teacherRoleUuid=systemConfigEbo.findDictinfoByDictcode("s01", "1").getRemark();
		
		sysuserEbo.deleteSysuserByUuid(uuid,teacherRoleUuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	@RequestMapping("/sysuserchangePwd")
	public String tochangePwd() throws Exception{
		return "/base/changepwd";
	}
	
	@RequestMapping("/changepwd")
	public @ResponseBody SubmitResultInfo changePwd(HttpSession session,String pwd,String newpwd,String newpwdtwo) throws Exception{
		SysuserCustom sysuserCustom=(SysuserCustom) session.getAttribute(Config.LOGINUSER_KEY);
		sysuserEbo.changePwd(sysuserCustom, pwd, newpwd, newpwdtwo);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	@RequestMapping("/sysuser/type")
	public @ResponseBody List<Dictinfo> findSysuserTypeNoStudent() throws Exception{
		return systemConfigEbo.findSysuserTypeDictinfoNoStudent();
	}
	//获取json格式用户信息
	@RequestMapping("/sysuser/jsonList")
	public @ResponseBody List<SysuserCustom> getSysuserJsonListByDictcode(String dictcode) throws Exception{
		//查询用户角色的uuid
		String roleUuid=systemConfigEbo.findDictinfoByDictcode("s01", dictcode).getRemark();
		
		return sysuserEbo.findSysuserByRoleId(roleUuid);
	}
}

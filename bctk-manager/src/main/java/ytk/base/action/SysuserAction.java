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
import ytk.base.business.XiEbo;
import ytk.base.pojo.po.Dictinfo;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.vo.ClassCustom;
import ytk.base.pojo.vo.ClassQueryVo;
import ytk.base.pojo.vo.PageQuery;
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
	private XiEbo xiEbo;
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
	public @ResponseBody SubmitResultInfo addSysuser(SysuserQueryVo sysuserQueryVo,ClassQueryVo classQueryVo) throws Exception{
		
		//添加用户
		sysuserEbo.addSysuser(sysuserQueryVo.getSysuserCustom(),classQueryVo.getClassCustom());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//进入系统用户修改页，根据uuid加载用户信息
	@RequestMapping("/sysuserEdit")
	public String toSysuserEdit(Long uuid,Model model) throws Exception{
		
		//查询修改用户信息
		SysuserCustom sysuserCustom = sysuserEbo.findSysuserByUuid(uuid);
		model.addAttribute("sysuserCustom", sysuserCustom);
		
		//查询用户班级信息
		String classuuid = sysuserCustom.getClassuuid();
		if(classuuid!=null&&!classuuid.trim().equals("")){
			ClassCustom classCustom = classEbo.findClassByUuid(classuuid);
			model.addAttribute("classCustom", classCustom);
		}

		return "/base/sysuser/edit";
	}
	//根据页面传递的数据，修改用户信息
	@RequestMapping("/sysuser/edit")
	public @ResponseBody SubmitResultInfo editSysuser(Long uuid,SysuserQueryVo sysuserQueryVo,ClassQueryVo classQueryVo) throws Exception{
		SysuserCustom sysuserCustom = sysuserQueryVo.getSysuserCustom();
		ClassCustom classCustom = classQueryVo.getClassCustom();
		sysuserEbo.updateSysuser(uuid, sysuserCustom,classCustom);
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	@RequestMapping("/sysuser/delete")
	public @ResponseBody SubmitResultInfo deleteSysuser(Long uuid) throws Exception{
		sysuserEbo.deleteSysuserByUuid(uuid);
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
	public @ResponseBody List<Dictinfo> findSysuserType() throws Exception{
		return systemConfigEbo.findSysuserTypeDictinfo();
	}
	//获取json格式课程信息
	@RequestMapping("/sysuser/jsonList")
	public @ResponseBody List<Sysuser> getSysuserJsonList(String groupid) throws Exception{
		List<Sysuser> sysuserList = sysuserEbo.findSysuserByGroupid(groupid);
		return sysuserList;
	}
}

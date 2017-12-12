package ytk.business.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.TeacherKcEbo;
import ytk.base.business.ZsdEbo;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.business.JdtEbo;
import ytk.business.pojo.vo.JdtCustom;
import ytk.business.pojo.vo.JdtQueryVo;

@Controller
public class JdtAction {
	
	@Autowired
	private JdtEbo jdtEbo;
	@Autowired
	private ZsdEbo zsdEbo;
	@Autowired
	private TeacherKcEbo teacherKcEbo;
	
	
	//跳转简答题列表页，加载页面所需信息
	@RequestMapping("/jdtList")
	public String toJdtList(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		String teacherUuid=sysuser.getUuid();
		
		//加载用户uuid
		model.addAttribute("sysuseruuid", teacherUuid);
		
		//加载任课教师一门任课课程
		Kc kc = teacherKcEbo.findTeacherKcOneByTeacherUuid(teacherUuid);
		model.addAttribute("kc", kc);
		return "/business/jdt/list";
	}
	
	//将查询简答题信息返回列表页
	@RequestMapping("/jdt/query")
	public @ResponseBody DataGridResultInfo queryJdt(JdtQueryVo jdtQueryVo,int page,int rows) throws Exception{
		//非空校验
		jdtQueryVo=jdtQueryVo==null?new JdtQueryVo():jdtQueryVo;
		
		//查询列表的总数
		int total = jdtEbo.findJdtListCount(jdtQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		jdtQueryVo.setPageQuery(pageQuery);

		List<JdtCustom> jdtCustomList = jdtEbo.findJdtList(jdtQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(jdtCustomList);

		return dataGridResultInfo;
	}
	
	//跳转到简答题添加页
	@RequestMapping("/jdtInput")
	public String toJdtInput(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);

		
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		return "/business/jdt/input";
	}
	
	//添加简答题信息
	@RequestMapping("/jdt/add")
	public @ResponseBody SubmitResultInfo addJdt(JdtQueryVo jdtQueryVo,HttpSession session,String[] zsdList) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		jdtEbo.addJdt(jdtQueryVo.getJdtCustom(),sysuser,zsdList);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转修改页面
	@RequestMapping("/jdtEdit")
	public String toEditJdt(Model model,String uuid,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);

		//加载简答题信息
		JdtCustom jdtCustom = jdtEbo.findJdtByUuid(uuid);
		model.addAttribute("jdtCustom",jdtCustom);
		
		List<Zsd> zsdList = jdtCustom.getZsdList();
		
		//加载题目对应知识点
		String s="[";
		for(int i=0;i<zsdList.size();i++){
			s+="'"+zsdList.get(i).getUuid()+"'";
			if(i!=zsdList.size()-1)
				s+=",";
		}
		s+="]";
		System.out.println(s);
		model.addAttribute("zsdList", s);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		
		return "/business/jdt/edit";
	}
	
	//修改简答题信息
	@RequestMapping("/jdt/edit")
	public @ResponseBody SubmitResultInfo updateJdt(String uuid,JdtQueryVo jdtQueryVo,HttpSession session,String[] zsdList) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		jdtEbo.updateJdt(uuid, jdtQueryVo.getJdtCustom(),sysuser,zsdList);
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//删除简答题
	@RequestMapping("/jdt/delete")
	public @ResponseBody SubmitResultInfo deleteJdt(String uuid,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		jdtEbo.deleteJdt(uuid,sysuser.getUuid());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
	
	//跳转简答题审核列表页，加载页面所需信息
	@RequestMapping("/jdtShList")
	public String toCheckJdtList(Model model,HttpSession session) throws Exception{
		
		//加载用户uuid
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		
		return "/business/jdt/checklist";
		
	}
	
	//审核通过题目
	@RequestMapping("/jdt/check")
	public @ResponseBody SubmitResultInfo checkJdt(JdtQueryVo jdtQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		jdtEbo.checkJdt(jdtQueryVo,sysuser);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
	//审核撤销通过题目
	@RequestMapping("/jdt/outcheck")
	public @ResponseBody SubmitResultInfo outCheckJdt(JdtQueryVo jdtQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		jdtEbo.outCheckJdt(jdtQueryVo,sysuser);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
}

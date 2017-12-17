package ytk.business.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.TeacherKcEbo;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.business.PdtEbo;
import ytk.business.pojo.vo.PdtCustom;
import ytk.business.pojo.vo.PdtQueryVo;

@Controller
public class PdtAction {
	
	@Autowired
	private PdtEbo pdtEbo;
	@Autowired
	private TeacherKcEbo teacherKcEbo;
	
	
	//跳转判断题列表页，加载页面所需信息
	@RequestMapping("/pdtList")
	public String toPdtList(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		String teacherUuid=sysuser.getUuid();
		
		//加载用户uuid
		model.addAttribute("sysuseruuid", teacherUuid);
		
		//加载任课教师一门任课课程
		Kc kc = teacherKcEbo.findTeacherKcOneByTeacherUuid(teacherUuid);
		model.addAttribute("kc", kc);
		return "/business/pdt/list";
	}
	
	//将查询判断题信息返回列表页
	@RequestMapping("/pdt/query")
	public @ResponseBody DataGridResultInfo queryPdt(PdtQueryVo pdtQueryVo,int page,int rows) throws Exception{
		//非空校验
		pdtQueryVo=pdtQueryVo==null?new PdtQueryVo():pdtQueryVo;
		
		//查询列表的总数
		int total = pdtEbo.findPdtListCount(pdtQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		pdtQueryVo.setPageQuery(pageQuery);

		List<PdtCustom> pdtCustomList = pdtEbo.findPdtList(pdtQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(pdtCustomList);

		return dataGridResultInfo;
	}
	
	//跳转到判断题添加页
	@RequestMapping("/pdtInput")
	public String toPdtInput(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);

		
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		return "/business/pdt/input";
	}
	
	//添加判断题信息
	@RequestMapping("/pdt/add")
	public @ResponseBody SubmitResultInfo addPdt(PdtQueryVo pdtQueryVo,HttpSession session,String[] zsdList) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		pdtEbo.addPdt(pdtQueryVo.getPdtCustom(),sysuser,zsdList);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转修改页面
	@RequestMapping("/pdtEdit")
	public String toEditPdt(Model model,String uuid,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);

		//加载判断题信息
		PdtCustom pdtCustom = pdtEbo.findPdtByUuid(uuid);
		model.addAttribute("pdtCustom",pdtCustom);
		
		List<Zsd> zsdList = pdtCustom.getZsdList();
		
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
		
		return "/business/pdt/edit";
	}
	
	//修改判断题信息
	@RequestMapping("/pdt/edit")
	public @ResponseBody SubmitResultInfo updatePdt(String uuid,PdtQueryVo pdtQueryVo,HttpSession session,String[] zsdList) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		pdtEbo.updatePdt(uuid, pdtQueryVo.getPdtCustom(),sysuser,zsdList);
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//删除判断题
	@RequestMapping("/pdt/delete")
	public @ResponseBody SubmitResultInfo deletePdt(String uuid,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		pdtEbo.deletePdt(uuid,sysuser.getUuid());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
	
	//跳转判断题审核列表页，加载页面所需信息
	@RequestMapping("/pdtShList")
	public String toCheckPdtList(Model model,HttpSession session) throws Exception{
		
		//加载用户uuid
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		
		return "/business/pdt/checklist";
		
	}
	
	//审核通过题目
	@RequestMapping("/pdt/check")
	public @ResponseBody SubmitResultInfo checkPdt(PdtQueryVo pdtQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		pdtEbo.checkPdt(pdtQueryVo,sysuser);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
	//审核撤销通过题目
	@RequestMapping("/pdt/outcheck")
	public @ResponseBody SubmitResultInfo outCheckPdt(PdtQueryVo pdtQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		pdtEbo.outCheckPdt(pdtQueryVo,sysuser);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
}

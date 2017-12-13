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
import ytk.business.business.DxxztEbo;
import ytk.business.pojo.vo.DxxztCustom;
import ytk.business.pojo.vo.DxxztQueryVo;

@Controller
public class DxxztAction {
	
	@Autowired
	private DxxztEbo dxxztEbo;
	@Autowired
	private TeacherKcEbo teacherKcEbo;
	
	
	//跳转多项选择题列表页，加载页面所需信息
	@RequestMapping("/dxxztList")
	public String toDxxztList(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		String teacherUuid=sysuser.getUuid();
		
		//加载用户uuid
		model.addAttribute("sysuseruuid", teacherUuid);
		
		//加载任课教师一门任课课程
		Kc kc = teacherKcEbo.findTeacherKcOneByTeacherUuid(teacherUuid);
		model.addAttribute("kc", kc);
		return "/business/dxxzt/list";
	}
	
	//将查询多项选择题信息返回列表页
	@RequestMapping("/dxxzt/query")
	public @ResponseBody DataGridResultInfo queryDxxzt(DxxztQueryVo dxxztQueryVo,int page,int rows) throws Exception{
		//非空校验
		dxxztQueryVo=dxxztQueryVo==null?new DxxztQueryVo():dxxztQueryVo;
		
		//查询列表的总数
		int total = dxxztEbo.findDxxztListCount(dxxztQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		dxxztQueryVo.setPageQuery(pageQuery);

		List<DxxztCustom> dxxztCustomList = dxxztEbo.findDxxztList(dxxztQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(dxxztCustomList);

		return dataGridResultInfo;
	}
	
	//跳转到多项选择题添加页
	@RequestMapping("/dxxztInput")
	public String toDxxztInput(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		return "/business/dxxzt/input";
	}
	
	//添加多项选择题信息
	@RequestMapping("/dxxzt/add")
	public @ResponseBody SubmitResultInfo addDxt(DxxztQueryVo dxxztQueryVo,HttpSession session,String[] zsdList) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		dxxztEbo.addDxxzt(dxxztQueryVo.getDxxztCustom(),sysuser,zsdList);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转修改页面
	@RequestMapping("/dxxztEdit")
	public String toEditDxxzt(Model model,String uuid,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		//加载多项选择题信息
		DxxztCustom dxxztCustom = dxxztEbo.findDxxztByUuid(uuid);
		model.addAttribute("dxxztCustom",dxxztCustom);
		
		List<Zsd> zsdList = dxxztCustom.getZsdList();
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
		return "/business/dxxzt/edit";
	}
	
	//修改多项选择题信息
	@RequestMapping("/dxxzt/edit")
	public @ResponseBody SubmitResultInfo updateDxxzt(String uuid,DxxztQueryVo dxxztQueryVo,HttpSession session,String[] zsdList) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		dxxztEbo.updateDxxzt(uuid, dxxztQueryVo.getDxxztCustom(),sysuser,zsdList);
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//删除多项选择题
	@RequestMapping("/dxxzt/delete")
	public @ResponseBody SubmitResultInfo deleteDxxzt(String uuid,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		dxxztEbo.deleteDxxzt(uuid,sysuser.getUuid());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
	
	//跳转多项选择题审核列表页，加载页面所需信息
	@RequestMapping("/dxxztShList")
	public String toCheckDxxztList(Model model,HttpSession session) throws Exception{
		
		//加载用户uuid
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		
		return "/business/dxxzt/checklist";
		
	}
	
	//审核通过题目
	@RequestMapping("/dxxzt/check")
	public @ResponseBody SubmitResultInfo checkDxxzt(DxxztQueryVo dxxztQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		dxxztEbo.checkDxxzt(dxxztQueryVo,sysuser);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
	//审核撤销通过题目
	@RequestMapping("/dxxzt/outcheck")
	public @ResponseBody SubmitResultInfo outCheckDxxzt(DxxztQueryVo dxxztQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		dxxztEbo.outCheckDxxzt(dxxztQueryVo,sysuser);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
}

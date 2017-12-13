package ytk.business.action;

import java.util.List;
import java.util.Map;

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
import ytk.business.business.TktEbo;
import ytk.business.pojo.vo.TktCustom;
import ytk.business.pojo.vo.TktQueryVo;
import ytk.util.JsonUtils;

@Controller
public class TktAction {
	
	@Autowired
	private TktEbo tktEbo;
	@Autowired
	private TeacherKcEbo teacherKcEbo;
	
	//跳转单选题列表页，加载页面所需信息
	@RequestMapping("/tktList")
	public String toTktList(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		String teacherUuid=sysuser.getUuid();
		
		//加载用户uuid
		model.addAttribute("sysuseruuid", teacherUuid);

		//加载任课教师一门任课课程
		Kc kc = teacherKcEbo.findTeacherKcOneByTeacherUuid(teacherUuid);
		model.addAttribute("kc", kc);
		return "/business/tkt/list";
	}
	
	//将查询单选题信息返回列表页
	@RequestMapping("/tkt/query")
	public @ResponseBody DataGridResultInfo queryTkt(TktQueryVo tktQueryVo,int page,int rows) throws Exception{
		//非空校验
		tktQueryVo=tktQueryVo==null?new TktQueryVo():tktQueryVo;
		
		//查询列表的总数
		int total = tktEbo.findTktListCount(tktQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		tktQueryVo.setPageQuery(pageQuery);

		List<TktCustom> tktCustomList = tktEbo.findTktList(tktQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(tktCustomList);

		return dataGridResultInfo;
	}
	
	//跳转到单选题添加页
	@RequestMapping("/tktInput")
	public String toTktInput(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		return "/business/tkt/input";
	}
	
	//添加单选题信息
	@RequestMapping("/tkt/add")
	public @ResponseBody SubmitResultInfo addTkt(TktQueryVo tktQueryVo,HttpSession session,String[] zsdList) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		tktEbo.addTkt(tktQueryVo.getTktCustom(),sysuser,zsdList);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转修改页面
	@RequestMapping("/tktEdit")
	public String toEditTkt(Model model,String uuid,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		
		//加载填空题题信息
		TktCustom tktCustom = tktEbo.findTktByUuid(uuid);
		model.addAttribute("tktCustom",tktCustom);
		System.out.println("---"+tktCustom.getIsprogram());
		//获取答案，解析
		String answer = tktCustom.getAnswer();
		Map<Integer, List> answerMap = JsonUtils.jsonToMap(answer, Integer.class, List.class);
		for(Integer i:answerMap.keySet()){
			List<String> list = answerMap.get(i);
			model.addAttribute("answer"+i, list);
		}
		
		List<Zsd> zsdList = tktCustom.getZsdList();

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
		return "/business/tkt/edit";
	}
	
	//修改单选题信息
	@RequestMapping("/tkt/edit")
	public @ResponseBody SubmitResultInfo updateTkt(String uuid,TktQueryVo tktQueryVo,HttpSession session,String[] zsdList) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		tktEbo.updateTkt(uuid, tktQueryVo.getTktCustom(),sysuser,zsdList);
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//删除单选题
	@RequestMapping("/tkt/delete")
	public @ResponseBody SubmitResultInfo deleteTkt(String uuid,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		tktEbo.deleteTkt(uuid,sysuser.getUuid());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
	
	//跳转填空题审核列表页，加载页面所需信息
	@RequestMapping("/tktShList")
	public String toCheckDxtList(Model model,HttpSession session) throws Exception{
		
		//加载用户uuid
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		
		return "/business/tkt/checklist";
		
	}
	
	//审核通过题目
	@RequestMapping("/tkt/check")
	public @ResponseBody SubmitResultInfo checkTkt(TktQueryVo tktQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		tktEbo.checkTkt(tktQueryVo,sysuser);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
	//审核撤销通过题目
	@RequestMapping("/tkt/outcheck")
	public @ResponseBody SubmitResultInfo outCheckTkt(TktQueryVo tktQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		tktEbo.outCheckTkt(tktQueryVo,sysuser);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
}

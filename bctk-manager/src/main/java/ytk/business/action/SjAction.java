package ytk.business.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.SystemConfigEbo;
import ytk.base.business.XiEbo;
import ytk.base.business.ZyEbo;
import ytk.base.pojo.po.Dictinfo;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.Xi;
import ytk.base.pojo.po.Zy;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.business.SjEbo;
import ytk.business.business.SjTmEbo;
import ytk.business.business.SjmbEbo;
import ytk.business.pojo.po.Sjmb;
import ytk.business.pojo.vo.SjCustom;
import ytk.business.pojo.vo.SjQueryVo;
import ytk.util.UUIDBuild;


@Controller
public class SjAction {

	@Autowired
	private  ZyEbo zyEbo;	
	@Autowired
	private XiEbo xiEbo;	
	@Autowired
	private SystemConfigEbo systemConfigEbo;	
	@Autowired
	private SjEbo sjEbo;	
	@Autowired
	private SjmbEbo sjmbEbo;
	@Autowired
	private SjTmEbo sjTmEbo;
	
	@RequestMapping("/sjInput")
	public String toSjInput(Model model,HttpSession session) throws Exception{
		//删除临时状态的试卷题目信息
		sjTmEbo.deleteSjTmByStatus(2);
		
		//为试卷生成uuid
		String sjid = UUIDBuild.getUUID();
		model.addAttribute("sjid", sjid);
		
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		return "/business/sj/input";
	}
	
	@RequestMapping("/sj/add")
	public @ResponseBody SubmitResultInfo addSj(SjQueryVo sjQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		sjEbo.addSj(sjQueryVo.getSjCustom(),sjQueryVo.getSjmbCustom(),sysuser); 
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	@RequestMapping("/sjList")
	public String sjList(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		//加载系信息
		List<Xi> xiList = xiEbo.findXiList();
		model.addAttribute("xiList",xiList);
		return "/business/sj/list";
	}
	
	@RequestMapping("/sj/query")
	public @ResponseBody DataGridResultInfo querySjList(SjQueryVo sjQueryVo,int rows,int page) throws Exception{
		sjQueryVo=sjQueryVo==null?new SjQueryVo():sjQueryVo;
		
		int total= sjEbo.findSjListCount(sjQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		sjQueryVo.setPageQuery(pageQuery);
		List<SjCustom> list = sjEbo.findSjList(sjQueryVo);
		
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);
		return dataGridResultInfo;		
	}
	
	@RequestMapping("/sjdetail")
	public String toSjDetail(String sjuuid,Model model) throws Exception{
		SjCustom sjCustom = sjEbo.findSjCustomByUuid(sjuuid);
		String kcname = sjCustom.getKcname();
		//加载试卷课程信息
		model.addAttribute("kcname", kcname);
		//加载试卷id
		model.addAttribute("sjid", sjuuid);
		//加载题目难度类别
		List<Dictinfo> ndTypeList = systemConfigEbo.findNdTypeDictinfo();
		model.addAttribute("ndTypeList",ndTypeList);
		//加载题目类型
		List<Dictinfo> xttypeList = systemConfigEbo.findDictinfoByTypeCode("001");
		model.addAttribute("xttypeList", xttypeList);
		//加载专业信息
		List<Zy> zyList = zyEbo.findZyList();
		model.addAttribute("zyList",zyList);
		//加载系题目状态信息
		List<Dictinfo> xtstatusList = systemConfigEbo.findDictinfoByTypeCode("003");
		model.addAttribute("xtstatusList", xtstatusList);
		return "/business/sj/detail";
	}
	
	@RequestMapping("/mySjList")
	public String toMySjList(HttpSession session,Model model) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("teacherid", sysuser.getUuid());
		return "/business/sj/mylist";
	}
	
	@RequestMapping("/sj/delete")
	public @ResponseBody SubmitResultInfo deleteSj(String sjuuid) throws Exception{
		sjEbo.deleteSj(sjuuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转修改页面
	@RequestMapping("/sjEdit")
	public String toEditSj(Model model,String uuid,HttpSession session) throws Exception{
		
		//将上回修改时为保存的临时题目删除
		sjTmEbo.deleteSjTmBySjUuidAndStatus(uuid);
		
		//加载试卷信息
		SjCustom sjCustom = sjEbo.findSjCustomByUuid(uuid);
		model.addAttribute("sjCustom", sjCustom);
		
		//根据试卷模板id查询试卷模板
		Sjmb sjmb = sjmbEbo.findSjmbByUuid(sjCustom.getSjmbid());
		//加载试卷模板
		model.addAttribute("sjmb", sjmb);
		
		//加载试卷uuid
		model.addAttribute("sjid", uuid);
		return "/business/sj/edit";
	}
	
	@RequestMapping("/sj/edit")
	public @ResponseBody SubmitResultInfo updateSj(SjQueryVo sjQueryVo,HttpSession session) throws Exception{
		sjEbo.updateSj(sjQueryVo.getSjCustom(),sjQueryVo.getSjmbCustom()); 
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	
	//根据条件查询试卷信息（json）
	@RequestMapping("/sj/jsonList")
	public @ResponseBody List<SjCustom> getSjJsonList(SjQueryVo sjQueryVo) throws Exception{
		sjQueryVo=sjQueryVo==null?new SjQueryVo():sjQueryVo;
		SjCustom sjCustom=sjQueryVo.getSjCustom();
		if(sjCustom!=null&&sjCustom.getKcname()!=null){
			String kcname = sjCustom.getKcname();
			kcname=new String(kcname.getBytes("iso-8859-1"),"utf-8");
			sjQueryVo.getSjCustom().setKcname(kcname);
		}
		return sjEbo.findSjList(sjQueryVo);
	}
	
	//将试卷导出为doc
	@RequestMapping("/sj/export")
	public @ResponseBody SubmitResultInfo exportSj(SjQueryVo sjQueryVo,HttpSession session) throws Exception{
		//获取导出路径
		Dictinfo dictinfo = systemConfigEbo.findDictinfoByDictcode("011", "1");
		String filePath=dictinfo.getRemark();
		String path = sjEbo.exportSj(sjQueryVo, filePath);
		//获取下载路径
		String webPath=systemConfigEbo.findDictinfoByDictcode("011", "2").getRemark();
		webPath+=path;
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 1031, new Object[]{webPath}));
	}
	
	//获取试卷难度信息(json)
	@RequestMapping("/sj/ndTypeJsonList")
	public @ResponseBody List<Dictinfo> getSjNdTypeJsonList() throws Exception{
		return systemConfigEbo.findDictinfoByTypeCode("006");
	}
	
	
}

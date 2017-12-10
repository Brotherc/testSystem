package ytk.business.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.SystemConfigEbo;
import ytk.base.business.ZsdEbo;
import ytk.base.business.ZyEbo;
import ytk.base.pojo.po.Dictinfo;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.po.Zy;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.business.DxtEbo;
import ytk.business.pojo.vo.DxtCustom;
import ytk.business.pojo.vo.DxtQueryVo;

@Controller
public class DxtAction {
	
	@Autowired
	private DxtEbo dxtEbo;
	@Autowired
	private SystemConfigEbo systemConfigEbo;
	@Autowired
	private ZyEbo zyEbo; 
	@Autowired
	private ZsdEbo zsdEbo;
	
	
	//跳转单选题列表页，加载页面所需信息
	@RequestMapping("/dxtList")
	public String toDxtList(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		//加载用户uuid
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		//加载专业信息
		List<Zy> zyList = zyEbo.findZyList();
		model.addAttribute("zyList",zyList);
		return "/business/dxt/list";
	}
	
	//将查询单选题信息返回列表页
	@RequestMapping("/dxt/query")
	public @ResponseBody DataGridResultInfo queryDxt(DxtQueryVo dxtQueryVo,int page,int rows) throws Exception{
		
		//非空校验
		dxtQueryVo=dxtQueryVo==null?new DxtQueryVo():dxtQueryVo;
		
		//查询列表的总数
		int total = dxtEbo.findDxtListCount(dxtQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		dxtQueryVo.setPageQuery(pageQuery);

		List<DxtCustom> dxtCustomList = dxtEbo.findDxtList(dxtQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(dxtCustomList);

		return dataGridResultInfo;
	}
	
	//跳转到单选题添加页
	@RequestMapping("/dxtInput")
	public String toDxtInput(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		//加载题目难度类型信息
		List<Dictinfo> ndTypeList = systemConfigEbo.findNdTypeDictinfo();
		model.addAttribute("ndTypeList",ndTypeList);
		//加载指定系的专业信息
		List<Zy> zyList = zyEbo.findZyByXiUuid(sysuser.getXuuid());
		model.addAttribute("zyList",zyList);
		
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		return "/business/dxt/input";
	}
	
	//添加单选题信息
	@RequestMapping("/dxt/add")
	public @ResponseBody SubmitResultInfo addDxt(DxtQueryVo dxtQueryVo,HttpSession session,String[] zsdList) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		dxtEbo.addDxt(dxtQueryVo.getDxtCustom(),sysuser,zsdList);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转修改页面
	@RequestMapping("/dxtEdit")
	public String toEditDxt(Model model,String uuid,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		
		//加载题目难度类型信息
		List<Dictinfo> ndTypeList = systemConfigEbo.findNdTypeDictinfo();
		model.addAttribute("ndTypeList",ndTypeList);
		//加载专业信息
		List<Zy> zyList = zyEbo.findZyList();
		model.addAttribute("zyList",zyList);
		//加载单选题信息
		DxtCustom dxtCustom = dxtEbo.findDxtByUuid(uuid);
		model.addAttribute("dxtCustom",dxtCustom);

		List<Zsd> zsdList = dxtCustom.getZsdList();
		
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
		return "/business/dxt/edit";
	}
	
	//修改单选题信息
	@RequestMapping("/dxt/edit")
	public @ResponseBody SubmitResultInfo updateDxt(String uuid,DxtQueryVo dxtQueryVo,HttpSession session,String[] zsdList) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		dxtEbo.updateDxt(uuid, dxtQueryVo.getDxtCustom(),sysuser,zsdList);
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//删除单选题
	@RequestMapping("/dxt/delete")
	public @ResponseBody SubmitResultInfo deleteDxt(String uuid,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		dxtEbo.deleteDxt(uuid,sysuser.getUuid());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
	
	//跳转单选题审核列表页，加载页面所需信息
	@RequestMapping("/dxtShList")
	public String toCheckDxtList(Model model,HttpSession session) throws Exception{
		//加载题目难度类型信息
		List<Dictinfo> ndTypeList = systemConfigEbo.findNdTypeDictinfo();
		model.addAttribute("ndTypeList",ndTypeList);
		//加载专业信息
		List<Zy> zyList = zyEbo.findZyList();
		model.addAttribute("zyList",zyList);
		
		//加载题目状态
		List<Dictinfo> statusList = systemConfigEbo.findDictinfoByTypeCode("008");
		model.addAttribute("statusList", statusList);
		
		//加载用户uuid
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		
		return "/business/dxt/checklist";
		
	}
	
	//审核通过题目
	@RequestMapping("/dxt/check")
	public @ResponseBody SubmitResultInfo checkDxt(DxtQueryVo dxtQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		dxtEbo.checkDxt(dxtQueryVo,sysuser);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
	//审核撤销通过题目
	@RequestMapping("/dxt/outcheck")
	public @ResponseBody SubmitResultInfo outCheckDxt(DxtQueryVo dxtQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		dxtEbo.outCheckDxt(dxtQueryVo,sysuser);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906,  null));
	}
	//获取单选题难度信息(json)
	@RequestMapping("/dxt/ndTypeJsonList")
	public @ResponseBody List<Dictinfo> getDxtNdTypeJsonList() throws Exception{
		return systemConfigEbo.findNdTypeDictinfo();
	}
}

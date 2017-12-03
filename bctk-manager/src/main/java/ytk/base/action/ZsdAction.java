package ytk.base.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.ZsdEbo;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.pojo.vo.ZsdCustom;
import ytk.base.pojo.vo.ZsdQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.util.MyUtil;

@Controller
public class ZsdAction {
	@Autowired
	private ZsdEbo zsdEbo;
	
	//跳转到知识点列表页
	@RequestMapping("/zsdList")
	public String toZsdList(Model model,HttpSession session){
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		return "/base/zsd/list";
	}
	
	@RequestMapping("/zsd/query")
	public @ResponseBody DataGridResultInfo queryZsd(ZsdQueryVo zsdQueryVo,int page,int rows) throws Exception{
		//非空校验
		zsdQueryVo=zsdQueryVo==null?new ZsdQueryVo():zsdQueryVo;
		
		//查询列表的总数
		int total = zsdEbo.findZsdListCount(zsdQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		zsdQueryVo.setPageQuery(pageQuery);

		List<ZsdCustom> zsdList = zsdEbo.findZsdList(zsdQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(zsdList);
		return dataGridResultInfo;
	}	
	
	//跳转到知识点添加页
	@RequestMapping("/zsdInput")
	public String toZsdInput(Model model,HttpSession session){
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		return "/base/zsd/input";
	}
	
	//添加知识点信息
	@RequestMapping("/zsd/add")
	public @ResponseBody SubmitResultInfo addZsd(ZsdQueryVo zsdQueryVo) throws Exception{
		
		zsdEbo.addZsd(zsdQueryVo);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	@RequestMapping("/zsd/jsonList")
	public @ResponseBody List<ZsdCustom> getZsdJsonList(ZsdQueryVo zsdQueryVo,String q) throws Exception{
		zsdQueryVo=zsdQueryVo==null?new ZsdQueryVo():zsdQueryVo;
		ZsdCustom zsdCustom=zsdQueryVo.getZsdCustom();
		if(zsdCustom!=null&&zsdCustom.getKcname()!=null){
			String kcname = zsdCustom.getKcname();
			kcname=new String(kcname.getBytes("iso-8859-1"),"utf-8");
			zsdQueryVo.getZsdCustom().setKcname(kcname);
		}
		if(MyUtil.isNotNullAndEmptyByTrim(q)){
			//根据q进行模糊查询
			return zsdEbo.findZsdListByQ(zsdQueryVo,q);
		}
		return zsdEbo.findZsdList(zsdQueryVo);
	}
	
	//删除知识点信息
	@RequestMapping("/zsd/delete")
	public @ResponseBody SubmitResultInfo deleteZsd(String uuid) throws Exception{
		zsdEbo.deleteZsd(uuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转到知识点修改页
	@RequestMapping("/zsdEdit")
	public String toZsdEdit(String uuid,Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		//根据uuid加载知识点信息
		ZsdCustom zsdCustom = zsdEbo.findZsdByUuid(uuid);
		System.out.println(zsdCustom.getName());
		System.out.println(zsdCustom.getKcname());
		model.addAttribute("zsdCustom",zsdCustom);
		return "/base/zsd/edit";
	}
	
	//修改系信息
	@RequestMapping("/zsd/edit")
	public @ResponseBody SubmitResultInfo editZsd(String uuid,ZsdQueryVo zsdQueryVo) throws Exception{
		zsdEbo.updateZsd(uuid, zsdQueryVo.getZsdCustom());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
}

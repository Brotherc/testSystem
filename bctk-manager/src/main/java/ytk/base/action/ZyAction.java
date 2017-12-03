package ytk.base.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ytk.base.business.XiEbo;
import ytk.base.business.ZyEbo;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.pojo.vo.ZyCustom;
import ytk.base.pojo.vo.ZyQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.util.MyUtil;

@Controller
public class ZyAction {
	
	@Autowired
	private ZyEbo zyEbo;
	@Autowired
	private XiEbo xiEbo;
	
	//跳转转业列表页
	@RequestMapping("/zyList")
	public String toZyList(Model model) throws Exception{
		return "/base/zy/list";
	}
	
	@RequestMapping("/zy/query")
	public @ResponseBody DataGridResultInfo queryXi(ZyQueryVo zyQueryVo,int page,int rows) throws Exception{
		//非空校验
		zyQueryVo=zyQueryVo==null?new ZyQueryVo():zyQueryVo;
		
		//查询列表的总数
		int total = zyEbo.findZyListCount(zyQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		zyQueryVo.setPageQuery(pageQuery);

		List<ZyCustom> zyList = zyEbo.findZyList(zyQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(zyList);
		return dataGridResultInfo;
	}
	
	//跳转到专业添加页
	@RequestMapping("/zyInput")
	public String toZyInput(Model model) throws Exception{
		return "/base/zy/input";
	}
	
	//添加专业信息
	@RequestMapping("/zy/add")
	public @ResponseBody SubmitResultInfo addXi(ZyQueryVo zyQueryVo) throws Exception{
		zyEbo.addZy(zyQueryVo);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转到专业修改页
	@RequestMapping("/zyEdit")
	public String toZyEdit(Long uuid,Model model) throws Exception{
		//加载专业信息
		ZyCustom zyCustom = zyEbo.findZyByUuid(uuid);
		model.addAttribute("zyCustom", zyCustom);
		
		return "/base/zy/edit";
	}
	
	//修改专业信息
	@RequestMapping("/zy/edit")
	public @ResponseBody SubmitResultInfo editZy(Long uuid,ZyQueryVo zyQueryVo) throws Exception{
		zyEbo.updateZy(uuid, zyQueryVo.getZyCustom());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//删除专业信息
	@RequestMapping("/zy/delete")
	public @ResponseBody SubmitResultInfo deleteXi(Long uuid) throws Exception{
		zyEbo.deleteZy(uuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	@RequestMapping("/zy/jsonList")
	public @ResponseBody List<ZyCustom> getZyJsonList(ZyQueryVo zyQueryVo,String q) throws Exception{
		if(MyUtil.isNotNullAndEmptyByTrim(q)){
			//根据q进行模糊查询
			return zyEbo.findZyListByQ(zyQueryVo,q);
		}
		return zyEbo.findZyList(zyQueryVo);
	}
}

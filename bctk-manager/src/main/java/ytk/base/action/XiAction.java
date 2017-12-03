package ytk.base.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.XiEbo;
import ytk.base.pojo.po.Xi;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.pojo.vo.XiQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;

@Controller
public class XiAction {
	
	@Autowired
	private XiEbo xiEbo;
	
	//跳转到系列表页
	@RequestMapping("/xiList")
	public String toXiList(){
		return "/base/xi/list";
	}
	
	@RequestMapping("/xi/query")
	public @ResponseBody DataGridResultInfo queryXi(XiQueryVo xiQueryVo,int page,int rows) throws Exception{
		//非空校验
		xiQueryVo=xiQueryVo==null?new XiQueryVo():xiQueryVo;
		
		//查询列表的总数
		int total = xiEbo.findXiListCount(xiQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		xiQueryVo.setPageQuery(pageQuery);

		List<Xi> xiList = xiEbo.findXiList(xiQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(xiList);
		return dataGridResultInfo;
	}
	
	//跳转到系添加页
	@RequestMapping("/xiInput")
	public String toXiInput(){
		return "/base/xi/input";
	}
	
	//添加系信息
	@RequestMapping("/xi/add")
	public @ResponseBody SubmitResultInfo addXi(XiQueryVo xiQueryVo) throws Exception{
		xiEbo.addXi(xiQueryVo);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转到系修改页
	@RequestMapping("/xiEdit")
	public String toXiEdit(Long uuid,Model model) throws Exception{
		//根据uuid加载系信息
		Xi xi = xiEbo.findXiByUuid(uuid);
		model.addAttribute("xiCustom",xi);
		return "/base/xi/edit";
	}
	
	//修改系信息
	@RequestMapping("/xi/edit")
	public @ResponseBody SubmitResultInfo editXi(Long uuid,XiQueryVo xiQueryVo) throws Exception{
		xiEbo.updateXi(uuid, xiQueryVo.getXiCustom());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//删除系信息
	@RequestMapping("/xi/delete")
	public @ResponseBody SubmitResultInfo deleteXi(Long uuid) throws Exception{
		xiEbo.deleteXi(uuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//查询所有系信息（json）
	@RequestMapping("/xi/jsonList")
	public @ResponseBody List<Xi> getXiJsonList() throws Exception{
		return xiEbo.findXiList();
	}
}

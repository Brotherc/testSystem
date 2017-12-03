package ytk.base.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.NjEbo;
import ytk.base.pojo.po.Nj;
import ytk.base.pojo.vo.NjQueryVo;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;

@Controller
public class NjAction {
	
	@Autowired
	private NjEbo njEbo;
	
	@RequestMapping("/nj/jsonList")
	public @ResponseBody List<Nj> getNjJsonList() throws Exception{
		return njEbo.findNjListByStatus(1);
	}

	//跳转到年级列表页
	@RequestMapping("/njList")
	public String toNjList(){
		return "/base/nj/list";
	}
	
	@RequestMapping("/nj/query")
	public @ResponseBody DataGridResultInfo queryNj(NjQueryVo njQueryVo,int page,int rows) throws Exception{
		//非空校验
		njQueryVo=njQueryVo==null?new NjQueryVo():njQueryVo;
		
		//查询列表的总数
		int total = njEbo.findNjListCount(njQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		njQueryVo.setPageQuery(pageQuery);

		List<Nj> njList = njEbo.findNjList(njQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(njList);
		return dataGridResultInfo;
	}
	
	//跳转到年级添加页
	@RequestMapping("/njInput")
	public String toNjInput(){
		return "/base/nj/input";
	}
	
	//添加年级信息
	@RequestMapping("/nj/add")
	public @ResponseBody SubmitResultInfo addNj(NjQueryVo njQueryVo) throws Exception{
		njEbo.addNj(njQueryVo.getNj());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//修改年级信息状态
	@RequestMapping("/nj/updateStatus")
	public @ResponseBody SubmitResultInfo updateNjStatus(Long uuid) throws Exception{
		njEbo.updateNjStatus(uuid,2);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
}

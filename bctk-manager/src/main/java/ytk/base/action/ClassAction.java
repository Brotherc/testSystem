package ytk.base.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.ClassEbo;
import ytk.base.pojo.vo.ClassCustom;
import ytk.base.pojo.vo.ClassQueryVo;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ExceptionResultInfo;
import ytk.base.process.result.ResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;

@Controller
public class ClassAction {
	
	@Autowired
	private ClassEbo classEbo;
	
	//跳转到班级列表页
	@RequestMapping("/classList")
	public String toClassList(){
		return "/base/class/list";
	}
	
	@RequestMapping("/class/query")
	public @ResponseBody DataGridResultInfo queryClass(ClassQueryVo classQueryVo,int page,int rows) throws Exception{
		//非空校验
		classQueryVo=classQueryVo==null?new ClassQueryVo():classQueryVo;
		
		//查询列表的总数
		int total = classEbo.findClassListCount(classQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		classQueryVo.setPageQuery(pageQuery);

		List<ClassCustom> classList = classEbo.findClassList(classQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(classList);
		return dataGridResultInfo;
	}
	
	//跳转到班级添加页
	@RequestMapping("/classInput")
	public String toClassInput(){
		return "/base/class/input";
		
	}
	
	//添加系信息
	@RequestMapping("/class/add")
	public @ResponseBody SubmitResultInfo addClass(ClassQueryVo classQueryVo,Integer classnameStart,Integer classnameEnd) throws Exception{
		//处理成功的数量
		int count_success = 0;
		//处理失败的数量
		int count_error = 0;
		
		//处理失败的原因
		List<ResultInfo> msgs_error = new ArrayList<ResultInfo>();
		for(int i=classnameStart;i<=classnameEnd;i++){
			
			ResultInfo resultInfo = null;
			try {
				classEbo.addClass(classQueryVo.getClassCustom(),i);
			} catch (Exception e) {
				e.printStackTrace();
			
				//进行异常解析
				if(e instanceof ExceptionResultInfo){
					resultInfo = ((ExceptionResultInfo)e).getResultInfo();
				}else{
					//构造未知错误异常
					resultInfo = ResultUtil.createFail(Config.MESSAGE, 900, null);
				}
			}
			if(resultInfo == null){
				//说明成功
				count_success++;
			}else{
				count_error++;
				//记录失败原因
				msgs_error.add(resultInfo);
			}
		}		
		//改成返回详细信息
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
				count_success,
				count_error
		}), msgs_error);
	}
	
	//删除班级信息
	@RequestMapping("/class/delete")
	public @ResponseBody SubmitResultInfo deleteClass(String uuid) throws Exception{
		classEbo.deleteClass(uuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
}

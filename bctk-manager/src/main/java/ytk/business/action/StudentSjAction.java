package ytk.business.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ExceptionResultInfo;
import ytk.base.process.result.ResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.business.StudentSjEbo;
import ytk.business.business.StudentSjdaEbo;
import ytk.business.pojo.vo.StudentSjCustom;
import ytk.business.pojo.vo.StudentSjQueryVo;

@Controller
public class StudentSjAction {
	@Autowired
	private StudentSjEbo studentSjEbo;
	@Autowired
	private StudentSjdaEbo studentSjdaEbo;

	
	//跳转到学生考试试卷页面
	@RequestMapping("/studentSjList")
	public String toStudentSj(String ksgluuid,String classuuid,Model model) throws Exception{
		model.addAttribute("ksgluuid", ksgluuid);
		System.out.println(classuuid);
		return "/business/studentSj/list";
	};
	
	@RequestMapping("/studentSj/query")
	public @ResponseBody DataGridResultInfo queryStudentSj(StudentSjQueryVo studentSjQueryVo,int page,int rows) throws Exception{
		//非空校验
		studentSjQueryVo=studentSjQueryVo==null?new StudentSjQueryVo():studentSjQueryVo;
		
		//查询列表的总数
		int total = studentSjEbo.findStudentSjListCount(studentSjQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		studentSjQueryVo.setPageQuery(pageQuery);

		List<StudentSjCustom> studentSjList = studentSjEbo.findStudentSjList(studentSjQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(studentSjList);
		return dataGridResultInfo;
	}
	
	//自动评分学生考试试卷
	@RequestMapping("/studentSj/autoPScore")
	public @ResponseBody SubmitResultInfo autoPScore(HttpSession session,
			int[] indexs,//接收页面选中的行序号(多个)
			StudentSjQueryVo studentSjQueryVo//页面提交的业务数据，保存在list中
			) throws Exception{
		//页面提交的业务数据（多个），要处理的业务数据，页面中传入的参数
		List<StudentSjCustom> studentSjList = studentSjQueryVo.getStudentSjList();
		//处理成功的数量
		int count_success = 0;
		//处理失败的数量
		int count_error = 0;
		//处理失败的原因
		List<ResultInfo> msgs_error = new ArrayList<ResultInfo>();
		for(int i=0;i<indexs.length;i++){
			System.out.println(indexs[i]);
			ResultInfo resultInfo = null;
			//根据选中行的序号获取要处理的业务数据(单个)
			StudentSjCustom studentSjCustom = studentSjList.get(indexs[i]);
			
			System.out.println(studentSjList.get(indexs[i]).getUuid());
			try {
				studentSjEbo.autoPStudentSj(studentSjCustom.getUuid());
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
		if(count_success<=0)
			return ResultUtil.createSubmitResult(ResultUtil.createFail(Config.MESSAGE, 911, new Object[]{
					count_error
			}), msgs_error);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
				count_success,
				count_error
		}), msgs_error);
	}

}

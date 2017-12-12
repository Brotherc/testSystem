package ytk.base.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.StudentEbo;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;

@Controller
public class StudentAction {
	
	@Autowired
	private StudentEbo studentEbo;
	
	//查询学号对应姓名
	@RequestMapping("/student/queryStudentName")
	public @ResponseBody SubmitResultInfo queryStudentName(String studentId) throws Exception{
		String studentName = studentEbo.findStudentNameByStudentId(studentId);
		if(studentName==null)
			return ResultUtil.createSubmitResult(ResultUtil.createFail(Config.MESSAGE, 108, null));
		return ResultUtil.createSubmitResult(new ResultInfo(1, 906, studentName));
	}
}

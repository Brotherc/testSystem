package ytk.business.action;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.business.SjTmEbo;
import ytk.business.business.SjdaEbo;
import ytk.business.business.StudentSjdaEbo;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.Sjda;
import ytk.business.pojo.po.StudentSjda;
import ytk.business.pojo.vo.StudentSjdaCustom;
import ytk.business.pojo.vo.StudentSjdaQueryVo;

@Controller
public class StudentSjdaAction {
	@Autowired
	private StudentSjdaEbo studentSjdaEbo;
	@Autowired 
	private SjTmEbo sjXitmEbo;
	@Autowired
	private SjdaEbo sjdaEbo;

	//跳转到
	@RequestMapping("/studentSjda/List")
	public String toStudentSjda(String studentSjUuid,Model model) throws Exception{
		model.addAttribute("studentSjUuid", studentSjUuid);
		System.out.println(studentSjUuid);
		return "/business/studentSj/dalist";
	};
	
	@RequestMapping("/studentSjda/query")
	public @ResponseBody DataGridResultInfo queryStudentSjda(StudentSjdaQueryVo studentSjdaQueryVo,int page,int rows) throws Exception{
		//非空校验
		studentSjdaQueryVo=studentSjdaQueryVo==null?new StudentSjdaQueryVo():studentSjdaQueryVo;
		
		//查询列表的总数
		int total = studentSjdaEbo.findStudentSjdaListCount(studentSjdaQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		studentSjdaQueryVo.setPageQuery(pageQuery);

		List<StudentSjdaCustom> studentSjdaList = studentSjdaEbo.findStudentSjdaList(studentSjdaQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(studentSjdaList);
		return dataGridResultInfo;
	}
	
	//跳转到学生考试试卷评分
	@RequestMapping("/studentSjda/pScore")
	public String toStudentSjdaPScore(String studentSjdaUuid,Model model) throws Exception{
		//加载学生考试试卷答案信息
		StudentSjda studentSjda = studentSjdaEbo.findStudentSjdaByUuid(studentSjdaUuid);
		model.addAttribute("studentSjda", studentSjda);
		//加载学生考试试卷系题目信息
		SjTm sjTm = sjXitmEbo.findSjTmByUuid(studentSjda.getSjxitmid());
		model.addAttribute("sjXitm", sjTm);
		//加载试卷答案信息
		Sjda sjda = sjdaEbo.findSjdaBySjXitmid(studentSjda.getSjxitmid());
		model.addAttribute("sjda", sjda);
		return "/business/studentSj/pScore";
	};
	
	
	
	//评分
	@RequestMapping("/studentSjda/pScoreSubmit")
	public @ResponseBody SubmitResultInfo pScoreStudentSjda(String studentSjdaUuid,Integer score,Integer mscore) throws Exception{
		studentSjdaEbo.pScore(studentSjdaUuid, score, mscore);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转到
	@RequestMapping("/studentSjda/Detail")
	public String toStudentSjdaDetail(String studentSjUuid,Model model) throws Exception{
		model.addAttribute("studentSjUuid", studentSjUuid);
		return "/business/studentSj/dadetaillist";
	};
}

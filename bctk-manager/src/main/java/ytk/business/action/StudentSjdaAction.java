package ytk.business.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.result.DataGridResultInfo;
import ytk.business.business.StudentSjdaEbo;
import ytk.business.pojo.vo.StudentSjdaCustom;
import ytk.business.pojo.vo.StudentSjdaQueryVo;

@Controller
public class StudentSjdaAction {
	
	@Autowired
	private StudentSjdaEbo studentSjdaEbo;

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
	
	//跳转到
	@RequestMapping("/studentSjda/Detail")
	public String toStudentSjdaDetail(String studentSjUuid,Model model) throws Exception{
		model.addAttribute("studentSjUuid", studentSjUuid);
		return "/business/studentSj/dadetaillist";
	};
}

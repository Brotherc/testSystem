package ytk.business.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ytk.base.business.SystemConfigEbo;
import ytk.base.pojo.vo.ClassQueryVo;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.pojo.vo.StudentCustom;
import ytk.base.pojo.vo.StudentQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ExceptionResultInfo;
import ytk.base.process.result.ResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.business.KsglEbo;
import ytk.business.business.KsglStudentEbo;
import ytk.business.pojo.po.Ksgl;
import ytk.business.pojo.po.KsglStudent;
import ytk.business.pojo.vo.KsglStudentCustom;
import ytk.business.pojo.vo.KsglStudentQueryVo;
import ytk.util.UUIDBuild;

@Controller
public class KsglStudentAction {
	
	@Autowired
	private KsglStudentEbo ksglStudentEbo;
	@Autowired
	private SystemConfigEbo systemConfigEbo;
	@Autowired
	private KsglEbo ksglEbo;
	
	//跳转考试学生列表页，加载页面所需信息
	@RequestMapping("/ksglStudentList")
	public String toKsglStudentList(Model model,String ksgluuid) throws Exception{
		model.addAttribute("ksgluuid", ksgluuid);
		return "/business/ksglstudent/list";
	}
	
	//跳转监考考试学生列表页，加载页面所需信息
	@RequestMapping("/jkKsglStudentList")
	public String toJkKsglStudentList(Model model,String ksgluuid,HttpSession session) throws Exception{
		model.addAttribute("ksgluuid", ksgluuid);
		
		//加载考试剩余时间
		Ksgl ksgl = ksglEbo.findKsglByUuid(ksgluuid);
		if(ksgl!=null){
			Long leftTime=ksgl.getEndtime()-System.currentTimeMillis();
			session.setAttribute("leftTime", leftTime);
		}
		return "/business/ksglstudent/jklist";
	}
	
	//将查询考试学生信息返回列表页
	@RequestMapping("/ksglStudent/query")
	public @ResponseBody DataGridResultInfo queryKsglStudent(KsglStudentQueryVo ksglStudentQueryVo,int page,int rows) throws Exception{
		
		//非空校验
		ksglStudentQueryVo=ksglStudentQueryVo==null?new KsglStudentQueryVo():ksglStudentQueryVo;
		
		//查询列表的总数
		int total = ksglStudentEbo.findKsglStudentListCount(ksglStudentQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		ksglStudentQueryVo.setPageQuery(pageQuery);

		List<KsglStudentCustom> KsglStudentCustomList = ksglStudentEbo.findKsglStudentList(ksglStudentQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(KsglStudentCustomList);

		return dataGridResultInfo;
	}
	
	//将查询考试学生信息返回列表页
	@RequestMapping("/jkKsglStudent/query")
	public @ResponseBody DataGridResultInfo queryJkKsglStudent(KsglStudentQueryVo ksglStudentQueryVo,int page,int rows) throws Exception{
		
		//非空校验
		ksglStudentQueryVo=ksglStudentQueryVo==null?new KsglStudentQueryVo():ksglStudentQueryVo;
		
		//查询列表的总数
		int total = ksglStudentEbo.findJkKsglStudentListCount(ksglStudentQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		ksglStudentQueryVo.setPageQuery(pageQuery);

		List<KsglStudentCustom> KsglStudentCustomList = ksglStudentEbo.findJkKsglStudentList(ksglStudentQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(KsglStudentCustomList);

		return dataGridResultInfo;
	}
	
	//删除考试学生信息
	@RequestMapping("/ksglStudent/delete")
	public @ResponseBody SubmitResultInfo deleteKsglStudent(String uuid,String ksgluuid) throws Exception{
		ksglStudentEbo.deleteKsglStudent(uuid,ksgluuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转添加考试学生页
	@RequestMapping("/ksglStudentInput")
	public String toKsglStudentInput(String ksgluuid,Model model){
		model.addAttribute("ksgluuid", ksgluuid);
		return "/business/ksglstudent/input";
	}
	
	//添加考试学生信息
	@RequestMapping("/ksglStudent/addCustom")
	public @ResponseBody SubmitResultInfo addKsglStudent(String ksgluuid,StudentQueryVo studentQueryVo,ClassQueryVo classQueryVo) throws Exception{
		
		//查询学生角色的uuid
		String teacherRoleUuid=systemConfigEbo.findDictinfoByDictcode("s01", "3").getRemark();
		
		ksglStudentEbo.addKsglStudentCustom(ksgluuid,studentQueryVo.getStudentCustom(),classQueryVo.getClassCustom(),teacherRoleUuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转到考试学生修改页
	@RequestMapping("/ksglStudentEdit")
	public String toKsglStudentEdit(String uuid,Model model) throws Exception{
		//根据uuid加载考试学生信息
		KsglStudentCustom ksglStudentCustom = ksglStudentEbo.findKsglStudentListByUuid(uuid);
		model.addAttribute("ksglStudentCustom",ksglStudentCustom);
		return "/business/ksglstudent/edit";
	}
	
	//修改考试学生信息
	@RequestMapping("/ksglStudent/edit")
	public @ResponseBody SubmitResultInfo editKsglStudent(String uuid,StudentQueryVo studentQueryVo,ClassQueryVo classQueryVo) throws Exception{
		ksglStudentEbo.updateKsglStudent(uuid, studentQueryVo.getStudentCustom(),classQueryVo.getClassCustom());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	
	//跳转到考试学生可添加页面
	@RequestMapping("/ksglStudentAddList")
	public String toKsglStudentAddList(String ksgluuid,Model model,HttpSession session) throws Exception{
		model.addAttribute("ksgluuid", ksgluuid);
		return "/business/ksglstudent/addlist";
	}
	
	//将查询考试学生可添加信息返回列表页
	@RequestMapping("/ksglStudentAdd/query")
	public @ResponseBody DataGridResultInfo queryKsglStudentAdd(StudentQueryVo studentQueryVo,int page,int rows) throws Exception{
		
		//非空校验
		studentQueryVo=studentQueryVo==null?new StudentQueryVo():studentQueryVo;
		
		//查询列表的总数
		int total = ksglStudentEbo.findKsglStudentAddListCount(studentQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		studentQueryVo.setPageQuery(pageQuery);

		List<StudentCustom> ksglStudentAddList = ksglStudentEbo.findKsglStudentAddList(studentQueryVo); 
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(ksglStudentAddList);

		return dataGridResultInfo;
	}
	
	//添加考试学生信息
	@RequestMapping("/ksglStudent/add")
	public @ResponseBody SubmitResultInfo addKsglStudentChoose(int[] indexs,KsglStudentQueryVo ksglStudentQueryVo,String ksgluuid) throws Exception{
		List<KsglStudent> ksglStudentList = ksglStudentQueryVo.getKsglStudentList();
		//处理成功的数量
		long count_success = 0;
		//处理失败的数量
		int count_error = 0;
		//处理失败的原因
		List<ResultInfo> msgs_error = new ArrayList<ResultInfo>();
		for(int i=0;i<indexs.length;i++){
			ResultInfo resultInfo = null;
			try {
				ksglStudentEbo.addKsglStudentChoose(ksgluuid,ksglStudentList.get(indexs[i]).getStudentUuid());
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
				System.out.println(resultInfo.getType());
				System.out.println(resultInfo.getMessageCode());
				System.out.println(resultInfo.getMessage());
				System.out.println(resultInfo.getIndex());
				System.out.println(resultInfo.getDetails());
				System.out.println(resultInfo.getSysdata());
				msgs_error.add(resultInfo);
			}
			
		}
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
				count_success,
				count_error
		}), msgs_error);
	}
	
	@RequestMapping("/ksglStudentImport")
	public String toKsglStudentImport(String ksgluuid,Model model) throws Exception{
		model.addAttribute("ksgluuid", ksgluuid);
		return "/business/ksglstudent/import";
	}
	
	//题目导入
	@RequestMapping("/ksglStudent/import")
	public @ResponseBody SubmitResultInfo importKsglStudent(
			//写上传的文件
			MultipartFile ksglStudentImportFile,
			String ksgluuid
			)throws Exception{
		
		//将上传的文件写到磁盘
		String originalFilename  = ksglStudentImportFile.getOriginalFilename();
		//写入磁盘的文件
		String importPath=systemConfigEbo.findDictinfoByDictcode("011", "4").getRemark();
		File file = new File(importPath+UUIDBuild.getUUID()+originalFilename.substring(originalFilename.lastIndexOf(".")));
		if(!file.exists()){
			//如果文件目录 不存在则创建
			file.mkdirs();
		}
		
		//将内存中的文件写磁盘
		ksglStudentImportFile.transferTo(file);
		//上传文件磁盘上路径 
		String absolutePath = file.getAbsolutePath();
		
		return ksglStudentEbo.importKsglStudent(absolutePath,ksgluuid);
/*		String[] msgs = msg.split("_");
		System.out.println(msgs[0]+" "+msgs[1]);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 317, new Object[]{
				msgs[0],
				msgs[1]
		}));*/
	
	}
}

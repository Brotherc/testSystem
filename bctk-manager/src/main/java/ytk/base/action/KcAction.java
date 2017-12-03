package ytk.base.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.KcEbo;
import ytk.base.business.TeacherKcEbo;
import ytk.base.business.ZyEbo;
import ytk.base.pojo.po.TeacherKc;
import ytk.base.pojo.po.Zy;
import ytk.base.pojo.vo.KcCustom;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.pojo.vo.KcQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.util.MyUtil;

@Controller
public class KcAction {
	
	@Autowired
	private KcEbo kcEbo;
	@Autowired
	private ZyEbo zyEbo;
	@Autowired
	private TeacherKcEbo teacherKcEbo;
	
	
	//跳转到课程列表页
	@RequestMapping("/kcList")
	public String toKcList(){
		return "/base/kc/list";
	}
	
	@RequestMapping("/kc/query")
	public @ResponseBody DataGridResultInfo queryKc(KcQueryVo kcQueryVo,int page,int rows) throws Exception{
		//非空校验
		kcQueryVo=kcQueryVo==null?new KcQueryVo():kcQueryVo;
		
		//查询列表的总数
		int total = kcEbo.findKcListCount(kcQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		kcQueryVo.setPageQuery(pageQuery);

		List<KcCustom> kcList = kcEbo.findKcList(kcQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(kcList);
		return dataGridResultInfo;
	}
	
	//跳转到课程添加页
	@RequestMapping("/kcInput")
	public String toKcInput(Model model) throws Exception{
		//加载所有专业信息
		List<Zy> zyList = zyEbo.findZyList();
		model.addAttribute("zyList", zyList);
		
		return "/base/kc/input";
	}
	
	//添加课程信息
	@RequestMapping("/kc/add")
	public @ResponseBody SubmitResultInfo addKc(KcQueryVo kcQueryVo,String[] teacherList) throws Exception{
		kcEbo.addKc(kcQueryVo,teacherList);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转到课程修改页
	@RequestMapping("/kcEdit")
	public String toKcEdit(Long uuid,Model model) throws Exception{
		
		//根据uuid加载课程信息
		KcCustom kcCustom = kcEbo.findKcByUuid(uuid);
		model.addAttribute("kcCustom",kcCustom);
		
		//加载所有专业信息
		List<Zy> zyList = zyEbo.findZyList();
		model.addAttribute("zyList", zyList);
		
		//加载对应课程所添加到的专业
		Long[] zyuuids = kcCustom.getZyuuids();
		String string ="";
		for(Long zyuuid:zyuuids){
			string+=string.valueOf(zyuuid)+",";
		}
		model.addAttribute("zyuuids", string);
		
		//加载课程对应任课教师
		List<TeacherKc> teacherKcList = teacherKcEbo.findTeacherKcByKcUuid(kcCustom.getUuid());
		String s="[";
		for(int i=0;i<teacherKcList.size();i++){
			s+="'"+teacherKcList.get(i).getTeacheruuid().toString()+"'";
			if(i!=teacherKcList.size()-1)
				s+=",";
		}
		s+="]";
		model.addAttribute("teacherList", s);
		return "/base/kc/edit";
	}
	
	//修改课程信息
	@RequestMapping("/kc/edit")
	public @ResponseBody SubmitResultInfo editKc(Long uuid,KcQueryVo kcQueryVo,String[] teacherList) throws Exception{
		kcEbo.updateKc(uuid, kcQueryVo.getKcCustom(),kcQueryVo.getZyList(),teacherList);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//删除课程信息
	@RequestMapping("/kc/delete")
	public @ResponseBody SubmitResultInfo deleteKc(Long uuid,Long zyuuid) throws Exception{
		kcEbo.deleteKc(uuid,zyuuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//获取json格式课程信息
	@RequestMapping("/kc/jsonList")
	public @ResponseBody List<KcCustom> getKcJsonList(String q,KcQueryVo kcQueryVo) throws Exception{
		kcQueryVo=kcQueryVo==null?new KcQueryVo():kcQueryVo;
		KcCustom kcCustom=kcQueryVo.getKcCustom();
		if(kcCustom!=null&&kcCustom.getSysuseruuid()==null){
			String zyname = kcQueryVo.getKcCustom().getZyname();
			if(zyname!=null)
				zyname=new String(zyname.getBytes("iso-8859-1"),"utf-8");
			kcQueryVo.getKcCustom().setZyname(zyname);
			
			if(MyUtil.isNotNullAndEmptyByTrim(q)){
				//根据q进行模糊查询
				return kcEbo.findKcListByQ(kcQueryVo,q);
			}
			return kcEbo.findKcList(kcQueryVo);
		}
		if(MyUtil.isNotNullAndEmptyByTrim(q)){
			//根据q进行模糊查询
			return kcEbo.findKcListNoZyByQ(kcQueryVo, q);
		}
		return kcEbo.findKcListNoZy(kcQueryVo);
	}
}

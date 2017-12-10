package ytk.business.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.SystemConfigEbo;
import ytk.base.business.XiEbo;
import ytk.base.business.ZyEbo;
import ytk.base.pojo.po.Dictinfo;
import ytk.base.pojo.po.Student;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.vo.Menu;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.pojo.vo.StudentCustom;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.business.KsglEbo;
import ytk.business.business.KsglStudentEbo;
import ytk.business.business.SjEbo;
import ytk.business.business.SjTmEbo;
import ytk.business.business.SjmbEbo;
import ytk.business.business.StudentSjEbo;
import ytk.business.business.StudentSjdaEbo;
import ytk.business.pojo.po.Dxt;
import ytk.business.pojo.po.Dxxzt;
import ytk.business.pojo.po.Jdt;
import ytk.business.pojo.po.Sjmb;
import ytk.business.pojo.po.Tkt;
import ytk.business.pojo.vo.KsglCustom;
import ytk.business.pojo.vo.KsglQueryVo;
import ytk.business.pojo.vo.SjCustom;
import ytk.business.pojo.vo.SjTmQueryVo;
import ytk.business.pojo.vo.StudentSjdaQueryVo;
import ytk.util.CookieUtils;

@Controller
public class KsglAction {
	
	@Autowired
	private KsglEbo ksglEbo; 
	@Autowired
	private SystemConfigEbo systemConfigEbo;
	@Autowired
	private XiEbo xiEbo;
	@Autowired
	private ZyEbo zyEbo;
	@Autowired
	private SjEbo sjEbo;
	@Autowired
	private SjTmEbo sjXitmEbo;
	@Autowired
	private StudentSjEbo studentSjEbo;
	@Autowired
	private StudentSjdaEbo studentSjdaEbo;
	@Autowired
	private SjmbEbo sjmbEbo;
	@Autowired
	private KsglStudentEbo ksglStudentEbo;
	
	private static final Integer TM_TYPE_ONE=1;
	private static final Integer TM_TYPE_TWO=2;
	private static final Integer TM_TYPE_THREE=3;
	private static final Integer TM_TYPE_FOUR=4;
	
	private static final String TM_TYPE_ONEVIEW="一";
	private static final String TM_TYPE_TWOVIEW="二";
	private static final String TM_TYPE_THREEVIEW="三";
	private static final String TM_TYPE_FOURVIEW="四";
	
	public static final Map< Integer, String>tmTypeMap=new HashMap<Integer, String>();
	
	static{
		tmTypeMap.put(TM_TYPE_ONE, TM_TYPE_ONEVIEW);
		tmTypeMap.put(TM_TYPE_TWO, TM_TYPE_TWOVIEW);
		tmTypeMap.put(TM_TYPE_THREE, TM_TYPE_THREEVIEW);
		tmTypeMap.put(TM_TYPE_FOUR, TM_TYPE_FOURVIEW);
	}
	
	//跳转到考试管理列表页
	@RequestMapping("/ksglList")
	public String toKsglList(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		/*return "/business/ksgl/list";*/
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		return "/business/ksgl/list2";
	}
	
	@RequestMapping("/ksgl/query")
	public @ResponseBody DataGridResultInfo queryKsgl(KsglQueryVo ksglQueryVo,int page,int rows) throws Exception{
		//非空校验
		ksglQueryVo=ksglQueryVo==null?new KsglQueryVo():ksglQueryVo;
		
		//查询列表的总数
		int total = ksglEbo.findKsglListCount(ksglQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		ksglQueryVo.setPageQuery(pageQuery);

		List<KsglCustom> ksglList = ksglEbo.findKsglList(ksglQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(ksglList);
		return dataGridResultInfo;
	}
	
	@RequestMapping("/ksgl/status")
	public @ResponseBody List<Dictinfo> findKsglStatus() throws Exception{
		return systemConfigEbo.findDictinfoByTypeCode("010");
	}
	
	//删除考试
	@RequestMapping("/ksgl/delete")
	public @ResponseBody SubmitResultInfo deleteKsgl(String uuid) throws Exception{
		ksglEbo.deleteKsgl(uuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转到考试管理添加页
	@RequestMapping("/ksglInput")
	public String toKsglInput(Model model,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		//加载用户信息
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		return "/business/ksgl/input2";
	}
	
	//添加课程信息
	@RequestMapping("/ksgl/add")
	public @ResponseBody SubmitResultInfo addKsgl(KsglQueryVo ksglQueryVo,HttpSession session) throws Exception{
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		ksglEbo.addKsgl(ksglQueryVo,sysuser.getUuid());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转到考试修改页
	@RequestMapping("/ksglEdit")
	public String toKsglEdit(KsglQueryVo ksglQueryVo,Model model,HttpSession session) throws Exception{
		//根据uuid加载考试信息
		KsglCustom ksglCustom = ksglEbo.findKsglByUuid(ksglQueryVo);
		model.addAttribute("ksglCustom",ksglCustom);
		
		Sysuser sysuser=(Sysuser) session.getAttribute(Config.LOGINUSER_KEY);
		model.addAttribute("sysuseruuid", sysuser.getUuid());
		return "/business/ksgl/edit2";
	}
	
	//修改考试信息
	@RequestMapping("/ksgl/edit")
	public @ResponseBody SubmitResultInfo editKsgl(String uuid,KsglQueryVo ksglQueryVo) throws Exception{
		//Long[] list = ksglQueryVo.getZyList();
		ksglEbo.updateKsgl(uuid, ksglQueryVo.getKsglCustom());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转到考试列表页
	@RequestMapping("/ksglKsList")
	public String toKsglKsList(Model model,HttpSession session) throws Exception{
		StudentCustom studentCustom=(StudentCustom) session.getAttribute(Config.LOGINSTUDENT_KEY);
		model.addAttribute("sysuseruuid",studentCustom.getUuid());
		return "/business/ksgl/ks";
	}
	
	//跳转到考试试卷页
	@RequestMapping("/ksglKsSj2")
	public String toKsglKsSj2(Model model,HttpSession session,SjTmQueryVo sjTmQueryVo,String ksgluuid) throws Exception{
		//修改学生该门考试的状态
		Student student=(Student) session.getAttribute(Config.LOGINSTUDENT_KEY);
		ksglStudentEbo.updateKsglStudentStatus(ksgluuid,student.getUuid(),3);
		
		//题目类型编号
		Integer tmTypeId=1;
		
		//加载试卷信息
		SjCustom sjCustom = sjEbo.findSjCustomByUuid(sjTmQueryVo.getSjTmCustom().getSjid());
		model.addAttribute("sjCustom", sjCustom);
		
		//加载试卷单选题信息
		List<Dxt> dxtList = sjXitmEbo.findDxtBySjUuid(sjTmQueryVo);
		if(dxtList!=null&&dxtList.size()>0){
			model.addAttribute("dxtListTypeId", tmTypeMap.get(tmTypeId));
			tmTypeId++;
		}
		model.addAttribute("dxtList", dxtList);
		
		//加载试卷多项选择题信息
		List<Dxxzt> dxxztList = sjXitmEbo.findDxxztBySjUuid(sjTmQueryVo);
		if(dxxztList!=null&&dxxztList.size()>0){
			model.addAttribute("dxxztListTypeId", tmTypeMap.get(tmTypeId));
			tmTypeId++;
		}
		model.addAttribute("dxxztList", dxxztList);
		
		//加载试卷填空题信息
		List<Tkt> tktList = sjXitmEbo.findTktBySjUuid(sjTmQueryVo);
		if(tktList!=null&&tktList.size()>0){
			model.addAttribute("tktListTypeId", tmTypeMap.get(tmTypeId));
			tmTypeId++;
		}
		model.addAttribute("tktList", tktList);
		
		//加载试卷简答题信息
		List<Jdt> jdtList = sjXitmEbo.findJdtBySjUuid(sjTmQueryVo);
		if(jdtList!=null&&jdtList.size()>0){
			model.addAttribute("jdtListTypeId", tmTypeMap.get(tmTypeId));
			tmTypeId++;
		}
		model.addAttribute("jdtList", jdtList);
		
		//加载考试管理uuid
		model.addAttribute("ksgluuid", ksgluuid);
		
		//加载试卷模板信息
		Sjmb sjmb = sjmbEbo.findSjmbByUuid(sjCustom.getSjmbid());
		model.addAttribute("sjmb", sjmb);
		return "/business/ksgl/kssj";
	}
	
	//跳转到考试试卷页
	@RequestMapping("/ksglKsSjPre")
	public @ResponseBody SubmitResultInfo toKsglKsSjPre(Model model,HttpSession session,String ksgluuid,HttpServletRequest request) throws Exception{
		Student student=(Student) session.getAttribute(Config.LOGINSTUDENT_KEY);
		boolean isKs = ksglEbo.ksPre(ksgluuid,student.getUuid());

		if(isKs){
			//判断是不是第一次在本机登录
			if(CookieUtils.getCookValue(request, student.getUuid()+"_"+ksgluuid)!=null){
				return ResultUtil.createSubmitResult(new ResultInfo(1, 0, "操作成功"));
			}
			//在另一台机器进行登录
			return ResultUtil.createSubmitResult(new ResultInfo(1, -1, "操作成功"));
		}else{
			//返回提示输入考试密码的结果
			return ResultUtil.createSubmitResult(new ResultInfo(1, 1, "操作成功"));
		}
	}
	
	//考试试卷提交
	@RequestMapping("/ksgl/ks")
	public @ResponseBody SubmitResultInfo KsglKs(HttpSession session,StudentSjdaQueryVo studentSjdaQueryVo,String sjuuid,String ksgluuid) throws Exception{
		//修改学生该门考试的状态
		Student student=(Student) session.getAttribute(Config.LOGINSTUDENT_KEY);
		ksglStudentEbo.updateKsglStudentStatus(ksgluuid,student.getUuid(),2);
		//添加学生考试信息
		studentSjEbo.addStudentSj(student.getUuid(), sjuuid,ksgluuid);
		
		//获取学生试卷单选题答案
/*		List<StudentSjdaCustom> dxtList = studentSjdaQueryVo.getDxtList();
		if(dxtList!=null&&dxtList.size()>0)
			studentSjdaEbo.addStudentSjda(sjuuid,studentsjid,dxtList);*/
		
		//获取学生试卷多项选择题答案
/*			List<StudentSjdaCustom> dxxztList = studentSjdaQueryVo.getDxxztList();
			if(dxxztList!=null&&dxxztList.size()>0){
				//将多项选择题的答案拼接
				for(int i=1;i<dxxztList.size();i++){
					System.out.println(dxxztList.get(i).getDxxztAnswerList().get(0));
					System.out.println(dxxztList.get(i).getDxxztAnswerList().get(1));
					System.out.println(dxxztList.get(i).getDxxztAnswerList().get(2));
					dxxztList.get(i).setAnswerByDxxztAnswerList();
				}
				studentSjdaEbo.addStudentSjda(sjuuid,studentsjid,dxxztList);
			}*/


		
		//获取学生试卷填空题答案
/*		List<StudentSjdaCustom> tktList = studentSjdaQueryVo.getTktList();
		if(tktList!=null&&tktList.size()>0)
			studentSjdaEbo.addStudentSjda(sjuuid,studentsjid,tktList);*/
		
		//获取学生试卷简答题答案
/*		List<StudentSjdaCustom> jdtList = studentSjdaQueryVo.getJdtList();
		if(jdtList!=null&&jdtList.size()>0)
			studentSjdaEbo.addStudentSjda(sjuuid,studentsjid,jdtList);*/
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}	
	
	//启动考试
	@RequestMapping("/ksgl/start")
	public @ResponseBody SubmitResultInfo startKsgl(String uuid) throws Exception{
		ksglEbo.startKsgl(uuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//查询某考试人员对应的考试
	@RequestMapping("/ksgl/query2")
	public @ResponseBody DataGridResultInfo queryKsgl2(KsglQueryVo ksglQueryVo,int page,int rows) throws Exception{
		//非空校验
		ksglQueryVo=ksglQueryVo==null?new KsglQueryVo():ksglQueryVo;
		
		//查询列表的总数
		int total = ksglEbo.findKsglListCountBySysuserUuid(ksglQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		ksglQueryVo.setPageQuery(pageQuery);

		List<KsglCustom> ksglList = ksglEbo.findKsglListBySysuserUuid(ksglQueryVo);
		//最终DataGridResultInfo通过@ResponseBody将java对象转成json
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(ksglList);
		return dataGridResultInfo;
	}
	
	@RequestMapping("/ksglKsSj")
	public @ResponseBody Menu toKsglKsSj(Model model,HttpSession session,SjTmQueryVo sjTmQueryVo,String ksgluuid,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		//修改学生该门考试的状态
		Student student=(Student) session.getAttribute(Config.LOGINSTUDENT_KEY);
		ksglStudentEbo.updateKsglStudentStatus(ksgluuid,student.getUuid(),3);

		//加载试卷信息
		SjCustom sjCustom = sjEbo.findSjCustomByUuid(sjTmQueryVo.getSjTmCustom().getSjid());
		model.addAttribute("sjCustom", sjCustom);
		
		//添加学生进入考试cookie，并保存本地
		String studentKsglCookie = CookieUtils.getCookValue(request, student.getUuid()+"_"+ksgluuid);
		if(studentKsglCookie!=null){
			int num=Integer.valueOf(studentKsglCookie)+1;
			Cookie cookie=new Cookie(student.getUuid()+"_"+ksgluuid, num+"");
			cookie.setMaxAge(60*60*3);
			cookie.setPath("/");
			response.addCookie(cookie);
		}else{
			Cookie cookie=new Cookie(student.getUuid()+"_"+ksgluuid, "1");
			cookie.setMaxAge(60*60*3);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		
		//添加学生试卷信息
		studentSjEbo.addStudentSj(student.getUuid(), sjTmQueryVo.getSjTmCustom().getSjid(),ksgluuid);
		
		//加载考试管理uuid
		model.addAttribute("ksgluuid", ksgluuid);
		
		//加载试卷模板信息
		Sjmb sjmb = sjmbEbo.findSjmbByUuid(sjCustom.getSjmbid());
		//根据试卷模板信息加载菜单
		model.addAttribute("sjmb", sjmb);
		
		//查询试卷模板菜单
		Menu menu = sjmbEbo.findSjmbMenuBySjmb(sjmb);
		menu.setMenuname(sjCustom.getName());
		//补全试卷模板菜单
		List<Menu> menus = menu.getMenus();
		for(Menu m:menus){
			m.setUrl(m.getUrl()+"?sjTmCustom.sjid="+sjTmQueryVo.getSjTmCustom().getSjid()+"&ksgluuid="+ksgluuid);
		}
		return menu;
	}

	@RequestMapping("/sjSubmitList")
	public  String toSjSubmit(Model model,HttpSession session,SjTmQueryVo sjTmQueryVo,String ksgluuid) throws Exception{	
		model.addAttribute("ksgluuid",ksgluuid);
		model.addAttribute("sjid", sjTmQueryVo.getSjTmCustom().getSjid());
		return "/business/sj/sjsubmit";
	}
	
	@RequestMapping("/sjSubmit")
	public @ResponseBody SubmitResultInfo sjSubmit(HttpSession session,String sjuuid,String ksgluuid,
			HttpServletRequest request,HttpServletResponse response) throws Exception{

		Student student=(Student) session.getAttribute(Config.LOGINSTUDENT_KEY);
		
		//修改学生该门考试的状态
		ksglStudentEbo.updateKsglStudentStatus(ksgluuid,student.getUuid(),2);
		
		//将学生考试cookie删除
		Cookie cookie = CookieUtils.getCook(request, student.getUuid()+"_"+ksgluuid);
		if(cookie!=null){
			cookie.setValue(null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		
		//将缓存中学生试卷题目保存到数据库并删除缓存中的信息
		ksglEbo.sjSubmit(student.getUuid(), ksgluuid, sjuuid);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}		
	@RequestMapping("/ksglKsPwdList")
	public  String toKsPwd(Model model,HttpSession session,String ksgluuid,String sjid) throws Exception{	
		model.addAttribute("ksgluuid",ksgluuid);
		model.addAttribute("sjid",sjid);
		return "/business/ksgl/kspwd";
	}	
	
	@RequestMapping("/ksgl/ksPwd")
	public @ResponseBody SubmitResultInfo ksPwd(HttpSession session,String ksgluuid,String ksPwd) throws Exception{
		ksglEbo.checkKsPwd(ksgluuid, ksPwd);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}	
	
	@RequestMapping("/ksglJkPwdList")
	public  String toJkPwd(Model model,HttpSession session,String ksgluuid,String sjid) throws Exception{	
		model.addAttribute("ksgluuid",ksgluuid);
		model.addAttribute("sjid",sjid);
		return "/business/ksgl/jkpwd";
	}	
	
	@RequestMapping("/ksgl/jkPwd")
	public @ResponseBody SubmitResultInfo jkPwd(HttpSession session,String ksgluuid,String jkPwd) throws Exception{
		ksglEbo.checkJkPwd(ksgluuid, jkPwd);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
}

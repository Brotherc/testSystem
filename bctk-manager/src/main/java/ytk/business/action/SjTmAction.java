package ytk.business.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.SystemConfigEbo;
import ytk.base.business.XiEbo;
import ytk.base.business.ZyEbo;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.pojo.vo.SysuserCustom;
import ytk.base.process.context.Config;
import ytk.base.process.result.DataGridResultInfo;
import ytk.base.process.result.ExceptionResultInfo;
import ytk.base.process.result.ResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.business.SjEbo;
import ytk.business.business.SjTmEbo;
import ytk.business.business.StudentSjdaEbo;
import ytk.business.pojo.po.Dxt;
import ytk.business.pojo.po.Dxxzt;
import ytk.business.pojo.po.Jdt;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.Tkt;
import ytk.business.pojo.vo.SjCustom;
import ytk.business.pojo.vo.SjQueryVo;
import ytk.business.pojo.vo.SjTmCustom;
import ytk.business.pojo.vo.SjTmQueryVo;
import ytk.business.pojo.vo.SjmbCustom;
import ytk.business.pojo.vo.SjmbQueryVo;
import ytk.business.pojo.vo.StudentSjdaQueryVo;

@Controller
public class SjTmAction {
	
	@Autowired
	private SjTmEbo sjTmEbo;
	@Autowired 
	private SystemConfigEbo systemConfigEbo;
	@Autowired
	private ZyEbo zyEbo;
	@Autowired
	private XiEbo xiEbo;
	@Autowired
	private StudentSjdaEbo studentSjdaEbo;
	@Autowired
	private SjEbo sjEbo;
	
	//查询试卷系题目信息
	@RequestMapping("/sjxitm/list")
	public @ResponseBody DataGridResultInfo querySjTm(SjTmQueryVo sjTmQueryVo,int page,int rows) throws Exception{
		
		sjTmQueryVo=sjTmQueryVo==null?new SjTmQueryVo():sjTmQueryVo;
		
		int total= sjTmEbo.findSjTmListCount(sjTmQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		sjTmQueryVo.setPageQuery(pageQuery);
		List<SjTmCustom> list = sjTmEbo.findSjTmList(sjTmQueryVo);
		
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);
		return dataGridResultInfo;
	}
	
	//查询可添加的试卷系题目信息
	@RequestMapping("/sjXitmAdd/list")
	public @ResponseBody DataGridResultInfo querySjTmAddList(SjTmQueryVo sjTmQueryVo,int page,int rows) throws Exception{
		sjTmQueryVo=sjTmQueryVo==null?new SjTmQueryVo():sjTmQueryVo;
		
		int total= sjTmEbo.findAddSjTmListCount(sjTmQueryVo);
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		sjTmQueryVo.setPageQuery(pageQuery);
		List<SjTmCustom> list = sjTmEbo.findAddSjTmList(sjTmQueryVo);
		
		DataGridResultInfo dataGridResultInfo=new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);
		return dataGridResultInfo;
	}
	
	//跳转试卷系题目添加页面前调用
	@RequestMapping("/sjXitmListPre")
	public @ResponseBody SubmitResultInfo sjTmListPre(SjQueryVo sjQueryVo,SjmbQueryVo sjmbQueryVo) throws Exception{
		sjTmEbo.sjTmListPre(sjQueryVo.getSjCustom(),sjQueryVo.getSjmbCustom());  
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//跳转试卷题目添加页面
	@RequestMapping("/sjXitmList")
	public String sjTmList(Model model,String kcname,String sjid,SjmbCustom sjmbCustom) throws Exception{
		
		//加载课程信息
		kcname=new String(kcname.getBytes("iso-8859-1"),"utf-8");
		model.addAttribute("kcname", kcname);
	
		//加载试卷uuid
		model.addAttribute("sjuuid",sjid);
		
		//加载试卷模板
		if(sjmbCustom.getDxtcount()!=0)
			sjmbCustom.setDxtscoreA(sjmbCustom.getDxtscore()/sjmbCustom.getDxtcount());
		if(sjmbCustom.getDxxztcount()!=0)
			sjmbCustom.setDxxztscoreA(sjmbCustom.getDxxztscore()/sjmbCustom.getDxxztcount());
		if(sjmbCustom.getTktcount()!=0)
			sjmbCustom.setTktscoreA(sjmbCustom.getTktscore()/sjmbCustom.getTktcount());
		if(sjmbCustom.getJdtcount()!=0)		
			sjmbCustom.setJdtscoreA(sjmbCustom.getJdtscore()/sjmbCustom.getJdtcount());
		model.addAttribute("sjmb",sjmbCustom);
		return "/business/sj/sjxitmlist";
	}
	
	@RequestMapping("/sjxitm/add")
	public @ResponseBody SubmitResultInfo addSjTm(int indexs[],SjTmQueryVo sjTmQueryVo,String sjuuid) throws Exception{
		List<SjTm> sjTmList = sjTmQueryVo.getSjTmList();
		SjCustom sjCustom = sjTmQueryVo.getSjCustom();
		//处理成功的数量
		int count_success = 0;
		//处理失败的数量
		int count_error = 0;
		
		//处理失败的原因
		List<ResultInfo> msgs_error = new ArrayList<ResultInfo>();
		for(int i=0;i<indexs.length;i++){
			
			ResultInfo resultInfo = null;
			//根据选中行的序号获取要处理的业务数据(单个)
			SjTm sjTm = sjTmList.get(indexs[i]);
			try {
				sjTmEbo.addSjTm(sjCustom, sjTm);
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
	
	@RequestMapping("/sjxitm/delete")
	public @ResponseBody SubmitResultInfo deleteSjTm(int[] indexs,SjTmQueryVo sjTmQueryVo) throws Exception{
	//页面提交的业务数据（多个），要处理的业务数据，页面中传入的参数
	List<SjTm> sjTmList = sjTmQueryVo.getSjTmList();
	//处理成功的数量
	int count_success = 0;
	//处理失败的数量
	int count_error = 0;
	//处理失败的原因
	List<ResultInfo> msgs_error = new ArrayList<ResultInfo>();
	for(int i=0;i<indexs.length;i++){
		
		ResultInfo resultInfo = null;
		//根据选中行的序号获取要处理的业务数据(单个)
		SjTm sjTm = sjTmList.get(indexs[i]);
		try {
			sjTmEbo.deleteSjTm(sjTm.getUuid());
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
	
	@RequestMapping("/sjxitmEditScore")
	public String toEditScore(String uuid,Model model) throws Exception{
		//根据uuid加载试卷题目信息
		SjTm sjTm = sjTmEbo.findSjTmByUuid(uuid);
		model.addAttribute("sjXitm",sjTm);
		return "/business/sj/editScore";
	}
	
	@RequestMapping("/sjxitmEditSjtmid")
	public String toEditSjtmid(String uuid,Model model) throws Exception{
		//根据uuid加载试卷题目信息
		SjTm sjTm = sjTmEbo.findSjTmByUuid(uuid);
		model.addAttribute("sjXitm",sjTm);
		return "/business/sj/editSjtmid";
	}
	
	
	@RequestMapping("/sjxitm/editScore")
	public @ResponseBody SubmitResultInfo editScore(String uuid,SjTmQueryVo sjTmQueryVo) throws Exception{
		sjTmEbo.updateSjTmScore(uuid, sjTmQueryVo);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	@RequestMapping("/sjxitm/editSjtmid")
	public @ResponseBody SubmitResultInfo editSjtmid(String uuid,SjTmQueryVo sjTmQueryVo) throws Exception{
		sjTmEbo.updateSjTmSjtmid(uuid, sjTmQueryVo);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	@RequestMapping("/sjDxt")
	public String toSjDxt(SjTmQueryVo sjTmQueryVo,Model model,HttpSession session,String ksgluuid) throws Exception{
		//加载试卷单选题信息
		List<Dxt> dxtList = sjTmEbo.findDxtBySjUuid(sjTmQueryVo);
		SysuserCustom sysuserCustom=(SysuserCustom) session.getAttribute(Config.LOGINUSER_KEY);
		List<Integer> orderList = sjTmEbo.getSjTmOrderByType(sysuserCustom.getUuid(), ksgluuid, 1, dxtList.size());
		Map< Integer, Dxt>dxtMap=new HashMap<Integer, Dxt>();
		
		//为题目随机生成顺序
		int index=1;
		for(Integer i:orderList){
			dxtMap.put(index, dxtList.get(i-1));
			index++;
		}
		model.addAttribute("dxtList",dxtMap);
		model.addAttribute("ksgluuid", ksgluuid);
		
		//加载学生试卷单选题答案
		Map<Integer, String> dxtDa = studentSjdaEbo.findStudentSjDaDxt(sysuserCustom.getUuid(), ksgluuid);
		model.addAttribute("dxtDa", dxtDa);
		
		//加载试卷信息
		SjCustom sjCustom = sjEbo.findSjCustomByUuid(sjTmQueryVo.getSjTmCustom().getSjid());
		model.addAttribute("sjCustom", sjCustom);
		
		model.addAttribute("dxtSize",dxtList.size());
		return "/business/sj/sjdxt";
	}	
	@RequestMapping("/sjDxxzt")
	public String toSjDxxzt(SjTmQueryVo sjXitmQueryVo,Model model) throws Exception{
		//加载试卷多项选择题信息
		List<Dxxzt> dxxztList = sjTmEbo.findDxxztBySjUuid(sjXitmQueryVo);		
		return "/business/sj/sjdxxzt";
	}	
	@RequestMapping("/sjTkt")
	public String toSjTkt(SjTmQueryVo sjTmQueryVo,Model model,HttpSession session,String ksgluuid) throws Exception{
		//加载试卷填空题信息
		List<Tkt> tktList = sjTmEbo.findTktBySjUuid(sjTmQueryVo);
		SysuserCustom sysuserCustom=(SysuserCustom) session.getAttribute(Config.LOGINUSER_KEY);
		List<Integer> orderList = sjTmEbo.getSjTmOrderByType(sysuserCustom.getUuid(), ksgluuid, 3, tktList.size());
		Map< Integer, Tkt>tktMap=new HashMap<Integer, Tkt>();
		
		//为题目随机生成顺序
		int index=1;
		for(Integer i:orderList){
			tktMap.put(index, tktList.get(i-1));
			index++;
		}
		model.addAttribute("tktList",tktMap);
		model.addAttribute("ksgluuid", ksgluuid);
		
		//加载学生试卷填空题答案
		Map<Integer, List> tktDa = studentSjdaEbo.findStudentSjDaTkt(sysuserCustom.getUuid(), ksgluuid);
		model.addAttribute("tktDa", tktDa);
		
		//加载试卷信息
		SjCustom sjCustom = sjEbo.findSjCustomByUuid(sjTmQueryVo.getSjTmCustom().getSjid());
		model.addAttribute("sjCustom", sjCustom);
		return "/business/sj/sjtkt";
	}
	@RequestMapping("/sjJdt")
	public String toSjJdt(SjTmQueryVo sjXitmQueryVo,Model model) throws Exception{
		//加载试卷简答题信息
		List<Jdt> jdtList = sjTmEbo.findJdtBySjUuid(sjXitmQueryVo);
		return "/business/sj/sjjdt";
	}	
	@RequestMapping("/sjDxtSubmit")
	public @ResponseBody SubmitResultInfo sjDxtSubmit(String sjuuid,String ksgluuid,HttpSession session, StudentSjdaQueryVo studentSjdaQueryVo,Integer dxtSize) throws Exception{
		SysuserCustom sysuserCustom=(SysuserCustom) session.getAttribute(Config.LOGINUSER_KEY);
		//获取学生试卷单选题答案
		List<String> dxtList=studentSjdaQueryVo.getDxtList();
		
		//保存学生试卷单选题答案
		studentSjdaEbo.addStudentSjdaDxt(sysuserCustom.getUuid(),ksgluuid,dxtList,dxtSize);
			
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	@RequestMapping("/sjTktSubmit")
	public @ResponseBody SubmitResultInfo sjTktSubmit(String sjuuid,String ksgluuid,HttpSession session, StudentSjdaQueryVo studentSjdaQueryVo) throws Exception{
		SysuserCustom sysuserCustom=(SysuserCustom) session.getAttribute(Config.LOGINUSER_KEY);
		//获取学生试卷填空题答案
		List<List<String>> tktList = studentSjdaQueryVo.getTktList();
		
		//保存学生试卷填空题答案
		studentSjdaEbo.addStudentSjdaTkt(sysuserCustom.getUuid(),ksgluuid,tktList);

		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
}

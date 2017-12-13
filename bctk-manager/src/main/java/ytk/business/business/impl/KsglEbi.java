package ytk.business.business.impl;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ytk.base.dao.mapper.ClassMapper;
import ytk.base.dao.mapper.KcMapper;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.KcExample;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.business.business.KsglEbo;
import ytk.business.dao.mapper.KsglMapper;
import ytk.business.dao.mapper.KsglMapperCustom;
import ytk.business.dao.mapper.KsglStudentMapper;
import ytk.business.dao.mapper.SjMapper;
import ytk.business.dao.mapper.SjMapperCustom;
import ytk.business.dao.mapper.SjTmMapper;
import ytk.business.dao.mapper.StudentSjMapper;
import ytk.business.dao.mapper.StudentSjdaMapper;
import ytk.business.dao.mapper.TktMapper;
import ytk.business.pojo.po.Ksgl;
import ytk.business.pojo.po.KsglExample;
import ytk.business.pojo.po.KsglStudentExample;
import ytk.business.pojo.po.KsglStudentExample.Criteria;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.SjTmExample;
import ytk.business.pojo.po.StudentSj;
import ytk.business.pojo.po.StudentSjExample;
import ytk.business.pojo.po.StudentSjda;
import ytk.business.pojo.po.StudentSjdaExample;
import ytk.business.pojo.po.Tkt;
import ytk.business.pojo.vo.KsglCustom;
import ytk.business.pojo.vo.KsglQueryVo;
import ytk.business.pojo.vo.SjCustom;
import ytk.business.pojo.vo.SjQueryVo;
import ytk.jedis.JedisClient;
import ytk.util.JsonUtils;
import ytk.util.MyUtil;
import ytk.util.RandomUtils;
import ytk.util.UUIDBuild;

public class KsglEbi implements KsglEbo{

	@Autowired
	private KsglMapperCustom ksglMapperCustom;
	@Autowired
	private KsglMapper ksglMapper;
	@Autowired
	private KcMapper kcMapper;
	@Autowired
	private SjMapper sjMapper;
	@Autowired
	private ClassMapper classMapper;
	@Autowired
	private SjMapperCustom sjMapperCustom;
	@Autowired
	private StudentSjMapper studentSjMapper;
	@Autowired
	private KsglStudentMapper ksglStudentMapper;
	@Autowired
	private StudentSjdaMapper studentSjdaMapper;
	@Autowired
	private SjTmMapper sjTmMapper;
	@Autowired
	private TktMapper tktMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${DXT_ANSWER}")
	private String DXT_ANSWER;
	@Value("${DXT_ORDER}")
	private String DXT_ORDER;
	@Value("${STUDENT_SJ}")
	private String STUDENT_SJ;
	@Value("${TKT_ANSWER}")
	private String TKT_ANSWER;
	@Value("${TKT_ORDER}")
	private String TKT_ORDER;
	
	@Override
	public List<KsglCustom> findKsglList(KsglQueryVo ksglQueryVo)
			throws Exception {
		return ksglMapperCustom.findKsglList(ksglQueryVo);
	}

	@Override
	public int findKsglListCount(KsglQueryVo ksglQueryVo) throws Exception {
		return ksglMapperCustom.findKsglListCount(ksglQueryVo);
	}

	@Override
	public void deleteKsgl(String uuid) throws Exception {
		//删除的考试必须存在
		Ksgl ksgl = ksglMapper.selectByPrimaryKey(uuid);
		if(ksgl==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1101, null));
		
		//删除的考试状态只能为待考或已考
		Integer status = ksgl.getStatus();
		if(status==3)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1102, null));
		
		ksglMapper.deleteByPrimaryKey(uuid);
		
		//删除对应的考试人员
		KsglStudentExample ksglStudentExample=new KsglStudentExample();
		Criteria ksglStudentCriteria = ksglStudentExample.createCriteria();
		ksglStudentCriteria.andKsgluuidEqualTo(uuid);
		ksglStudentMapper.deleteByExample(ksglStudentExample);
		
		//删除对应考试人员的试卷信息
		StudentSjExample studentSjExample=new StudentSjExample();
		StudentSjExample.Criteria studentSjCriteria = studentSjExample.createCriteria();
		studentSjCriteria.andKsgluuidEqualTo(uuid);
		List<StudentSj> studentSjList = studentSjMapper.selectByExample(studentSjExample);
		
		//先将该考试人员的试卷答案删除
		for(StudentSj studentSj:studentSjList){
			StudentSjdaExample studentSjdaExample=new StudentSjdaExample();
			StudentSjdaExample.Criteria studentSjDaCriteria = studentSjdaExample.createCriteria();
			studentSjDaCriteria.andStudentsjidEqualTo(studentSj.getUuid());
			studentSjdaMapper.deleteByExample(studentSjdaExample);
		}
		studentSjMapper.deleteByExample(studentSjExample);
	}

	@Override
	public void addKsgl(KsglQueryVo ksglQueryVo, String teacherUuid)
			throws Exception {
		KsglCustom ksglCustom = ksglQueryVo.getKsglCustom();
		//非空校验,有效性检验
		checkNull(ksglCustom);
		
		//课程必须存在
		KcExample kcExample=new KcExample();
		KcExample.Criteria kcCriteria = kcExample.createCriteria();
		kcCriteria.andNameEqualTo(ksglCustom.getKcname());
		List<Kc> kcList = kcMapper.selectByExample(kcExample);
		if(kcList==null||kcList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1103, null));
		
		//试卷必须存在
		SjQueryVo sjQueryVo=new SjQueryVo();
		SjCustom sjCustom=new SjCustom();
		sjCustom.setName(ksglCustom.getSjname());
		sjQueryVo.setSjCustom(sjCustom);
		List<SjCustom> sjList = sjMapperCustom.findSjList(sjQueryVo);
		if(sjList==null||sjList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1104, null));

		SjCustom sj = sjList.get(0);		
		
		//考试课程必须与试卷相同
		if(!ksglCustom.getKcname().equals(sj.getKcname()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1106, null));
		
		//添加试卷
		Ksgl ksgl=new Ksgl();
		//生成uuid
		String ksglUuid = UUIDBuild.getUUID();
		ksgl.setUuid(ksglUuid);
		Long starttime = ksglCustom.getStarttime();
		ksgl.setStarttime(starttime);
		Long endtime = ksglCustom.getEndtime();
		ksgl.setEndtime(endtime);
		ksgl.setSjuuid(sj.getUuid());
		ksgl.setStatus(1);
		ksgl.setTeacherid(teacherUuid);
		ksgl.setCreatetime(System.currentTimeMillis());
		ksgl.setTime(endtime-starttime);
		ksgl.setKcuuid(sj.getKcid());
		//添加考试密码
		String ksPwd = RandomUtils.FourPwd();
		ksgl.setKspwd(ksPwd);
		//添加监考密码
		String jkPwd=RandomUtils.FourPwd();
		ksgl.setJkpwd(jkPwd);
		
		ksglMapper.insert(ksgl);
	}

	@Override
	public KsglCustom findKsglByUuid(KsglQueryVo ksglQueryVo) throws Exception {
		List<KsglCustom> ksglList = findKsglList(ksglQueryVo);
		Long[] zyuuids=new Long[ksglList.size()];
		//获取该考试的专业信息
		int i=0;
		for(KsglCustom ksglCustom:ksglList){
			zyuuids[i++]=ksglCustom.getZyuuid();
		}
		KsglCustom ksglCustom = ksglList.get(0);
		ksglCustom.setZyuuids(zyuuids);
		return ksglCustom;
	}

	@Override
	public void updateKsgl(String uuid, KsglCustom ksglCustom) throws Exception{
		
		//修改的考试信息必须存在
		Ksgl ksgl = ksglMapper.selectByPrimaryKey(uuid);
		if(ksgl==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1113, null));
		
		//更新的考试状态只能为待考
		Integer status = ksgl.getStatus();
		if(status==2||status==3)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1115, null));
		
		//非空校验,有效性检验
		checkNull(ksglCustom);
			
		//课程必须存在
		KcExample kcExample=new KcExample();
		KcExample.Criteria kcCriteria = kcExample.createCriteria();
		kcCriteria.andNameEqualTo(ksglCustom.getKcname());
		List<Kc> kcList = kcMapper.selectByExample(kcExample);
		if(kcList==null||kcList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1103, null));
		//试卷必须存在
		SjQueryVo sjQueryVo=new SjQueryVo();
		SjCustom sjCustom=new SjCustom();
		sjCustom.setName(ksglCustom.getSjname());
		sjQueryVo.setSjCustom(sjCustom);
		List<SjCustom> sjList = sjMapperCustom.findSjList(sjQueryVo);
		if(sjList==null||sjList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1104, null));

		Long starttime = ksglCustom.getStarttime();
		Long endtime = ksglCustom.getEndtime();
		
		SjCustom sj = sjList.get(0);
		//考试课程必须与试卷相同
		System.out.println(sj.getKcname());
		if(!ksglCustom.getKcname().equals(sj.getKcname()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1106, null));
		
		ksgl.setStarttime(starttime);
		ksgl.setEndtime(endtime);
		ksgl.setKcuuid(sj.getKcid());
		ksgl.setSjuuid(sj.getUuid());
		ksgl.setTime(endtime-starttime);
		//更新考试
		ksglMapper.updateByPrimaryKey(ksgl);
		
	}

	@Override
	public boolean ksPre(String ksgluuid,String sysuseruuid) throws Exception {
		//获取当前考试信息
		Ksgl ksgl = ksglMapper.selectByPrimaryKey(ksgluuid);
		//获取考试开始时间
		Long starttime = ksgl.getStarttime();
		//如果当前时间<考试时间则不允许考试
		if(System.currentTimeMillis()<starttime)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1109, null));
		
		Long endtime = ksgl.getEndtime();
		//如果当前时间>考试结束时间则不允许考试
		if(System.currentTimeMillis()>=endtime)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1110, null));

		Boolean exists = jedisClient.exists(sysuseruuid+"_"+ksgluuid);
		if(exists)
			return true;
		return false;
	}

/*	@Override
	public void ksglEnd(String ksgluuid) throws Exception {
		//获取当前考试信息
		Ksgl ksgl = ksglMapper.selectByPrimaryKey(ksgluuid);
		ksgl.setStatus(2);
		ksglMapper.updateByPrimaryKey(ksgl);
	}*/

	private void checkNull(KsglCustom ksglCustom) throws Exception{
		if(!MyUtil.isNotNullAndEmptyByTrim(ksglCustom.getKcname())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"考试课程名称"}));
		}
		if(!MyUtil.isNotNullAndEmptyByTrim(ksglCustom.getSjname())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"考试试卷名称"}));
		}
		if(ksglCustom.getStarttime()==null){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"考试开始时间"}));
		}
		if(ksglCustom.getEndtime()==null){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"考试结束时间"}));
		}
	}

	@Override
	public void startKsgl(String ksgluuid) throws Exception {
		
		//启动的考试存在
		Ksgl ksgl = ksglMapper.selectByPrimaryKey(ksgluuid);
		if(ksgl==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1113, null));
		
		//只能启动待考的考试
		Integer status = ksgl.getStatus();
		if(status==2||status==3)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1116, null));
		if(System.currentTimeMillis()<ksgl.getStarttime()||System.currentTimeMillis()>=ksgl.getEndtime())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1117, null));
		
		//修改状态为进行中
		ksgl.setStatus(3);
		ksglMapper.updateByPrimaryKey(ksgl);
		
		//启动一个定时器，在考试结束时，修改考试管理状态
		Timer timer=new Timer();
		final Ksgl ksglTimer=ksgl;
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				//修改考试管理状态
				ksglTimer.setStatus(2);
				ksglMapper.updateByPrimaryKey(ksglTimer);
			}
		}, ksgl.getTime());
	}

	@Override
	public List<KsglCustom> findKsglListBySysuserUuid(KsglQueryVo ksglQueryVo)
			throws Exception {
		return ksglMapperCustom.findKsglListBySysuserUuid(ksglQueryVo);
	}

	@Override
	public int findKsglListCountBySysuserUuid(KsglQueryVo ksglQueryVo)
			throws Exception {
		return ksglMapperCustom.findKsglListCountBySysuserUuid(ksglQueryVo);
	}

	@Override
	public void sjSubmit(String studentUuid, String ksgluuid,String sjid) throws Exception {
		String key=studentUuid+"_"+ksgluuid;
		String studentSjUuid = jedisClient.hget(key, STUDENT_SJ);
		String dxt_order = jedisClient.hget(key, DXT_ORDER);
		List<Integer> dxtOrder=null;
		Map<Integer, String> dxtAnswer=null;
		if(StringUtils.isNoneBlank(dxt_order)){
			dxtOrder = JsonUtils.jsonToList(dxt_order, Integer.class);
			dxtAnswer = JsonUtils.jsonToMap(jedisClient.hget(key, DXT_ANSWER), Integer.class, String.class);
		}

		
		String tkt_order = jedisClient.hget(key, TKT_ORDER);
		List<Integer> tktOrder=null;
		Map<Integer, List> tktAnswer=null;
		if(StringUtils.isNoneBlank(tkt_order)){
			tktOrder = JsonUtils.jsonToList(tkt_order, Integer.class);
			tktAnswer = JsonUtils.jsonToMap(jedisClient.hget(key, TKT_ANSWER), Integer.class, List.class);
		}
		
		int index=1;
		
		if(dxt_order!=null)
		for(Integer i:dxtOrder){
			//单选题提交
			StudentSjda studentSjda=new StudentSjda();
			//设置uuid
			String uuid = UUIDBuild.getUUID();
			studentSjda.setUuid(uuid);
			//设置学生试卷答案的考试试卷id
			studentSjda.setStudentsjid(studentSjUuid);
			//设置答案
			studentSjda.setAnswer(dxtAnswer.get(index));
			//设置题目编号
			Integer sjtmid = i;
			studentSjda.setSjtmid(sjtmid);
			
			//设置试卷系题目id
			SjTmExample sjTmExample = new SjTmExample();
			SjTmExample.Criteria sjTmCriteria = sjTmExample.createCriteria();
			sjTmCriteria.andSjidEqualTo(sjid);
			sjTmCriteria.andSjtmidEqualTo(sjtmid);
			sjTmCriteria.andTypeEqualTo(1);
			
			List<SjTm> sjTmList = sjTmMapper.selectByExample(sjTmExample);
			if(sjTmList==null||sjTmList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1040, null));
			
			studentSjda.setSjxitmid(sjTmList.get(0).getUuid());
			studentSjda.setScore(0);
			studentSjda.setStatus(1);
			studentSjda.setType(1);
			
			studentSjdaMapper.insert(studentSjda);		
			index++;
		}
		index=1;

		if(tktOrder!=null)
		for(Integer i:tktOrder){
			//填空题提交
			StudentSjda studentSjda=new StudentSjda();
			//设置uuid
			String uuid = UUIDBuild.getUUID();
			studentSjda.setUuid(uuid);
			//设置学生试卷答案的考试试卷id
			studentSjda.setStudentsjid(studentSjUuid);
			//设置题目编号
			Integer sjtmid = i;
			studentSjda.setSjtmid(sjtmid);
			
			//设置试卷系题目id
			SjTmExample sjTmExample = new SjTmExample();
			SjTmExample.Criteria sjTmCriteria = sjTmExample.createCriteria();
			sjTmCriteria.andSjidEqualTo(sjid);
			sjTmCriteria.andSjtmidEqualTo(sjtmid);
			sjTmCriteria.andTypeEqualTo(3);
			
			List<SjTm> sjTmList = sjTmMapper.selectByExample(sjTmExample);
			if(sjTmList==null||sjTmList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1040, null));
			SjTm sjTm=sjTmList.get(0);
			studentSjda.setSjxitmid(sjTm.getUuid());
			
			List<String> list = tktAnswer.get(index);
			//判断该题目是否是程序填空题
			Tkt tkt = tktMapper.selectByPrimaryKey(sjTm.getTuuid());
			System.out.println(tkt.getIsprogram());
			if(tkt!=null){
				if(tkt.getIsprogram().equals("2")){
					System.out.println("程序填空题");
					for(int j=0;j<list.size();j++){
						//取出所有空格
						String s=list.get(j).replaceAll(" ", "");
						list.set(j, s);
						System.out.println(s);
					}
				}else{
					for(int j=0;j<list.size();j++){
						//取出前后空格
						String s=list.get(j).trim();
						list.set(j, s);
						System.out.println(s);
					}
				}
			}
			//设置答案
			String json = JsonUtils.objectToJson(list);
			studentSjda.setAnswer(json);
			studentSjda.setScore(0);
			studentSjda.setStatus(1);
			studentSjda.setType(3);
			
			studentSjdaMapper.insert(studentSjda);		
			index++;
		}
		
		//将缓存中该学生考试相关信息删除
		jedisClient.del(key);
	}

	@Override
	public void checkKsPwd(String ksgluuid,String ksPwd) throws Exception {
		//非空判断
		if(!StringUtils.isNoneBlank(ksPwd))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1120, null));
		Ksgl ksgl = ksglMapper.selectByPrimaryKey(ksgluuid);
		if(ksgl==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1113, null));
		if(!ksgl.getKspwd().equals(ksPwd))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1119, null));
	}

	@Override
	public void checkJkPwd(String ksgluuid, String jkPwd) throws Exception {
		//非空判断
		if(!StringUtils.isNoneBlank(jkPwd))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1121, null));
		Ksgl ksgl = ksglMapper.selectByPrimaryKey(ksgluuid);
		if(ksgl==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1113, null));
		if(!ksgl.getJkpwd().equals(jkPwd))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1122, null));
		
	}

	@Override
	public Ksgl findKsglByUuid(String ksglUuid) throws Exception {
		KsglExample ksglExample=new KsglExample();
		KsglExample.Criteria ksglCriteria = ksglExample.createCriteria();
		ksglCriteria.andUuidEqualTo(ksglUuid);
		return ksglMapper.selectByPrimaryKey(ksglUuid);
	}
}

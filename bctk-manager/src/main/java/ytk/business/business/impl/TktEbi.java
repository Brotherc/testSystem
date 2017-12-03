package ytk.business.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.dao.mapper.KcMapper;
import ytk.base.dao.mapper.KcZyMapper;
import ytk.base.dao.mapper.KcZyMapperCustom;
import ytk.base.dao.mapper.SysuserMapper;
import ytk.base.dao.mapper.TeacherKcMapper;
import ytk.base.dao.mapper.ZsdMapper;
import ytk.base.dao.mapper.ZyMapper;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.KcExample;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.TeacherKc;
import ytk.base.pojo.po.TeacherKcExample;
import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.po.ZsdExample;
import ytk.base.pojo.po.KcExample.Criteria;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.business.business.TktEbo;
import ytk.business.dao.mapper.SjTmMapper;
import ytk.business.dao.mapper.TktMapper;
import ytk.business.dao.mapper.TktMapperCustom;
import ytk.business.dao.mapper.TmZsdMapper;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.SjTmExample;
import ytk.business.pojo.po.Tkt;
import ytk.business.pojo.po.TktExample;
import ytk.business.pojo.po.TmZsd;
import ytk.business.pojo.po.TmZsdExample;
import ytk.business.pojo.vo.TktCustom;
import ytk.business.pojo.vo.TktQueryVo;
import ytk.util.JsonUtils;
import ytk.util.MyUtil;
import ytk.util.UUIDBuild;

public class TktEbi implements TktEbo{

	@Autowired
	private TktMapperCustom tktMapperCustom;
	@Autowired 
	private KcMapper kcMapper;
	@Autowired
	private KcZyMapper kcZyMapper;
	@Autowired
	private TktMapper tktMapper;
	@Autowired
	private ZyMapper zyMapper;
	@Autowired
	private SysuserMapper sysuserMapper;
	@Autowired
	private SjTmMapper sjTmMapper;
	@Autowired
	private KcZyMapperCustom kcZyMapperCustom;
	@Autowired
	private ZsdMapper zsdMapper;
	@Autowired
	private TmZsdMapper tmZsdMapper;
	@Autowired
	private TeacherKcMapper teacherKcMapper;
	
	@Override
	public List<TktCustom> findTktList(TktQueryVo tktQueryVo) throws Exception {
		TktCustom tktCustom = tktQueryVo.getTktCustom();
		if(tktCustom!=null){
			Long createtimeMax = tktCustom.getCreatetimeMax();
			if(createtimeMax!=null&&!createtimeMax.equals(""))
				createtimeMax=createtimeMax+86400000-1;
			tktCustom.setCreatetimeMax(createtimeMax);
		}
		List<TktCustom> tktList = tktMapperCustom.findTktList(tktQueryVo);
		
		PageQuery pageQuery = tktQueryVo.getPageQuery();
		
		if(pageQuery!=null){
			int lastIndex=pageQuery.getPageQuery_start()+pageQuery.getPageQuery_pageSize();
			if(pageQuery.getPageQuery_pageSize()>tktList.size()-pageQuery.getPageQuery_start())
				lastIndex=tktList.size();
			List<TktCustom> subList = tktList.subList(pageQuery.getPageQuery_start(), lastIndex);
			
			return subList;
		}
		return tktList;
	}

	@Override
	public int findTktListCount(TktQueryVo tktQueryVo) throws Exception {
		TktCustom tktCustom = tktQueryVo.getTktCustom();
		if(tktCustom!=null){
			Long createtimeMax = tktCustom.getCreatetimeMax();
			if(createtimeMax!=null&&!createtimeMax.equals(""))
				createtimeMax=createtimeMax+86400000-1;
			tktCustom.setCreatetimeMax(createtimeMax);
		}
		return tktMapperCustom.findTktListCount(tktQueryVo);
	}

	@Override
	public void addTkt(TktCustom tktCustom,Sysuser sysuser,String[] zsdList) throws Exception {
		//非空判断
		checkNull(tktCustom);
		if(zsdList==null||zsdList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 326, null));
		
		//添加的课程名必须存在,先根据kcname查询对应的题目信息
		String kcname = tktCustom.getKcname();
		KcExample kcExample=new KcExample();
		Criteria criteria = kcExample.createCriteria();
		criteria.andNameEqualTo(kcname);
		List<Kc> kcList = kcMapper.selectByExample(kcExample);
		
		//若不存在该课程抛出异常
		if(kcList==null||kcList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 302, null));

		Kc kc = kcList.get(0);
		Long kcUuid=kc.getUuid();

		//教师添加题目只能添加自己任课的题目
		TeacherKcExample teacherKcExample=new TeacherKcExample();
		TeacherKcExample.Criteria teacherKcCriteria = teacherKcExample.createCriteria();
		teacherKcCriteria.andKcuuidEqualTo(kcUuid);
		teacherKcCriteria.andTeacheruuidEqualTo(sysuser.getUuid());
		List<TeacherKc> teacherKcList = teacherKcMapper.selectByExample(teacherKcExample);
		if(teacherKcList==null||teacherKcList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 320, null));
		
		//不允许添加相同题目内容，选项相同的题目
		TktExample tktExample=new TktExample();
		TktExample.Criteria tktCriteria = tktExample.createCriteria();
		String content = tktCustom.getContent();
		tktCriteria.andContentEqualTo(content);
		tktCriteria.andKcuuidEqualTo(kcUuid);
		List<Tkt> tktList = tktMapper.selectByExample(tktExample);
		if(tktList!=null&&tktList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 305, null));
		
		Tkt tkt=new Tkt();
		String tktUuid=UUIDBuild.getUUID();
		tkt.setUuid(tktUuid);
		tkt.setType(3);
		//添加对应的kcuuid
		tkt.setKcuuid(kcUuid);
		tkt.setContent(content);
		//解析答案
		int n=tktCustom.getAnswernum();
		Map<Integer,List<String>>answerMap =new HashMap<Integer, List<String>>();
		if(n!=0){
			String[] answer1 = tktCustom.getAnswer1();
			List<String> list = clearNullAnswer(answer1);
			answerMap.put(1,list );
			n--;
		}
		if(n!=0){
			String[] answer2 = tktCustom.getAnswer2();
			List<String> list = clearNullAnswer(answer2);
			answerMap.put(2, list);
			n--;
		}
		if(n!=0){
			String[] answer3 = tktCustom.getAnswer3();
			List<String> list = clearNullAnswer(answer3);
			answerMap.put(3, list);
			n--;
		}
		if(n!=0){
			String[] answer4 = tktCustom.getAnswer4();
			List<String> list = clearNullAnswer(answer4);
			answerMap.put(4, list);
			n--;
		}
		if(n!=0){
			String[] answer5 = tktCustom.getAnswer5();
			List<String> list = clearNullAnswer(answer5);
			answerMap.put(5, list);
			n--;
		}
		String json = JsonUtils.objectToJson(answerMap);
		tkt.setAnswer(json);
		//设置是否是填空程序题
		tkt.setIsprogram(tktCustom.getIsprogram());
		tkt.setNdtype(tktCustom.getNdtype());
		//保存添加题目用户信息，即登录人信息
		tkt.setTeacheruuid(sysuser.getUuid());
		//添加创建题目的时间
		Long createtime=System.currentTimeMillis();
		tkt.setCreatetime(createtime);
		//添加题目状态
		tkt.setStatus(2);
		//添加题目答案数量
		tkt.setAnswernum(tktCustom.getAnswernum());
		tktMapper.insert(tkt);

		//添加题目知识点
		for(String zsdUuid:zsdList){
			Zsd zsd = zsdMapper.selectByPrimaryKey(zsdUuid);
			if(zsd==null)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1405, null));
			
			//添加的知识点必须在所添加题目所对应课程中
			ZsdExample zsdExample=new ZsdExample();
			ZsdExample.Criteria zsdCriteria = zsdExample.createCriteria();
			zsdCriteria.andUuidEqualTo(zsdUuid);
			zsdCriteria.andKcuuidEqualTo(kcUuid);
			List<Zsd> zsdListTemp = zsdMapper.selectByExample(zsdExample);
			if(zsdListTemp==null||zsdListTemp.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 327, null));
			
			//添加题目知识点
			TmZsd tmZsd=new TmZsd();
			String uuid = UUIDBuild.getUUID();
			tmZsd.setUuid(uuid);
			tmZsd.setTmuuid(tktUuid);
			tmZsd.setType(3);
			tmZsd.setZsduuid(zsdUuid);
			tmZsdMapper.insert(tmZsd);
		}
	}

	@Override
	public TktCustom findTktByUuid(String uuid) throws Exception {
		TktQueryVo tktQueryVo=new TktQueryVo();
		TktCustom tktCustom=new TktCustom();
		tktCustom.setUuid(uuid);
		tktQueryVo.setTktCustom(tktCustom);
		List<TktCustom> tktList = tktMapperCustom.findTktList(tktQueryVo);
		if(tktList!=null&&tktList.size()>0)
			return tktList.get(0);
		return null;
	}

	@Override
	public void updateTkt(String uuid, TktCustom tktCustom,Sysuser sysuser,String[] zsdList) throws Exception {
		//非空判断
		checkNull(tktCustom);
		if(zsdList==null||zsdList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 326, null));
		
		//修改的用户必须是创建该题的人才具有修改权利
		
		//获取未修改前的题目信息
		TktCustom tktCustomDb = findTktByUuid(uuid);
		
		//修改的单选题信息必须存在,不存在则抛异常
		if(tktCustomDb==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 316,null));
		
		//若题目状态为审核未通过，则可修改
		if(tktCustomDb.getStatus()==2){
			//将登陆者的uuid与该题创建者的uuid进行比较
			Long userid = sysuser.getUuid();
			Long createteacherUuid=tktCustomDb.getTeacheruuid();
			
			//如果不相等，则无权限修改
			if(!userid.equals(createteacherUuid))
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 301, null));
			
			//修改的课程名必须存在,先根据kcname查询对应的题目信息
			String kcname = tktCustom.getKcname();
			KcExample kcExample=new KcExample();
			Criteria criteria = kcExample.createCriteria();
			criteria.andNameEqualTo(kcname);
			List<Kc> kcList = kcMapper.selectByExample(kcExample);
			
			//若不存在该课程抛出异常
			if(kcList==null||kcList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 307, null));
			
			Kc kc = kcList.get(0);
			Long kcUuid=kc.getUuid();

			//教师修改题目只能修改自己任课的题目
			TeacherKcExample teacherKcExample=new TeacherKcExample();
			TeacherKcExample.Criteria teacherKcCriteria = teacherKcExample.createCriteria();
			teacherKcCriteria.andKcuuidEqualTo(kcUuid);
			teacherKcCriteria.andTeacheruuidEqualTo(sysuser.getUuid());
			List<TeacherKc> teacherKcList = teacherKcMapper.selectByExample(teacherKcExample);
			if(teacherKcList==null||teacherKcList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 320, null));
			
			//如果修改了题目的唯一性，不允许修改相同题目内容，选项相同的题目
			String content = tktCustom.getContent();
			if(!tktCustomDb.getContent().equals(content)||!tktCustomDb.getKcuuid().equals(kcUuid)){
				
				TktExample tktExample=new TktExample();
				TktExample.Criteria dxtCriteria = tktExample.createCriteria();
				dxtCriteria.andContentEqualTo(content);
				dxtCriteria.andKcuuidEqualTo(kcUuid);
				List<Tkt> tktList = tktMapper.selectByExample(tktExample); 
				if(tktList!=null&&tktList.size()>0)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 305, null));
			}
			
			tktCustomDb.setKcuuid(kcUuid);
			tktCustomDb.setNdtype(tktCustom.getNdtype());
			tktCustomDb.setContent(tktCustom.getContent());
			tktCustomDb.setAnswer(tktCustom.getAnswer());
			//设置是否是填空程序题
			tktCustomDb.setIsprogram(tktCustom.getIsprogram());
			
			tktCustomDb.setAnswernum(tktCustom.getAnswernum());
			//解析答案
			int n=tktCustom.getAnswernum();
			Map<Integer,List<String>>answerMap =new HashMap<Integer, List<String>>();
			if(n!=0){
				String[] answer1 = tktCustom.getAnswer1();
				List<String> list = clearNullAnswer(answer1);
				answerMap.put(1,list );
				n--;
			}
			if(n!=0){
				String[] answer2 = tktCustom.getAnswer2();
				List<String> list = clearNullAnswer(answer2);
				answerMap.put(2, list);
				n--;
			}
			if(n!=0){
				String[] answer3 = tktCustom.getAnswer3();
				List<String> list = clearNullAnswer(answer3);
				answerMap.put(3, list);
				n--;
			}
			if(n!=0){
				String[] answer4 = tktCustom.getAnswer4();
				List<String> list = clearNullAnswer(answer4);
				answerMap.put(4, list);
				n--;
			}
			if(n!=0){
				String[] answer5 = tktCustom.getAnswer5();
				List<String> list = clearNullAnswer(answer5);
				answerMap.put(5, list);
				n--;
			}
			String json = JsonUtils.objectToJson(answerMap);
			tktCustomDb.setAnswer(json);
			
			//修改题目状态为未通过
			tktCustomDb.setStatus(2);
			tktMapper.updateByPrimaryKey(tktCustomDb);
	
			//修改题目知识点
			//现将之前该题目对应知识点删除，添加新的知识点
			TmZsdExample tmZsdExample=new TmZsdExample();
			TmZsdExample.Criteria tmZsdCriteria = tmZsdExample.createCriteria();
			tmZsdCriteria.andTmuuidEqualTo(tktCustomDb.getUuid());
			tmZsdCriteria.andTypeEqualTo(3);
			tmZsdMapper.deleteByExample(tmZsdExample);
			
			for(String zsdUuid:zsdList){
				Zsd zsd = zsdMapper.selectByPrimaryKey(zsdUuid);
				if(zsd==null)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1405, null));
				//添加的知识点必须在所添加题目所对应课程中
				ZsdExample zsdExample=new ZsdExample();
				ZsdExample.Criteria zsdCriteria = zsdExample.createCriteria();
				zsdCriteria.andUuidEqualTo(zsdUuid);
				zsdCriteria.andKcuuidEqualTo(kcUuid);
				List<Zsd> zsdListTemp = zsdMapper.selectByExample(zsdExample);
				if(zsdListTemp==null||zsdListTemp.size()<1)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 327, null));
				
				TmZsd tmZsd=new TmZsd();
				String uuidZsd = UUIDBuild.getUUID();
				tmZsd.setUuid(uuidZsd);
				tmZsd.setTmuuid(tktCustomDb.getUuid());
				tmZsd.setType(3);
				tmZsd.setZsduuid(zsdUuid);
				tmZsdMapper.insert(tmZsd);
			}
		}
		//如果审核已通过，则无需修改
		else{
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 312, null));
		}
	}

	@Override
	public void deleteTkt(String uuid,Long sysuseruuid) throws Exception {
		//获取要删除的题目信息
		TktCustom tktCustomDb = findTktByUuid(uuid);
		
		//删除的单选题信息必须存在,不存在则抛异常
		if(tktCustomDb==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 306,null));
		
		//必须是删除的题目的创建者，否则无权利删除
		Long teacheruuid = tktCustomDb.getTeacheruuid();
		if(!teacheruuid.equals(sysuseruuid))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 310,null));
		
		//删除的题目状态必须是未通过（未通过的题目不会出现在试卷中）
		if(tktCustomDb.getStatus()!=2)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 313,null));

		tktMapper.deleteByPrimaryKey(uuid);
		
		//删除题目知识点
		TmZsdExample tmZsdExample=new TmZsdExample();
		TmZsdExample.Criteria tmZsdCriteria = tmZsdExample.createCriteria();
		tmZsdCriteria.andTmuuidEqualTo(uuid);
		tmZsdCriteria.andTypeEqualTo(3);
		tmZsdMapper.deleteByExample(tmZsdExample);
	}
	private void checkNull(TktCustom tktCustom) throws Exception{
		if(!MyUtil.isNotNullAndEmptyByTrim(tktCustom.getKcname()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"课程名称"}));
		if(tktCustom.getNdtype()==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"难度类型"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(tktCustom.getContent()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"内容"}));
		if(tktCustom.getAnswernum()==null||tktCustom.getAnswernum()==0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"答案个数"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(tktCustom.getIsprogram()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"填空题类型"}));
		
		int n=tktCustom.getAnswernum();
		if(n!=0){
			String[] answer1 = tktCustom.getAnswer1();
			if(answer1==null||answer1.length<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"答案1"}));
			n--;
		}
		if(n!=0){
			String[] answer2 = tktCustom.getAnswer2();
			if(answer2==null||answer2.length<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"答案2"}));
			n--;
		}
		if(n!=0){
			String[] answer3 = tktCustom.getAnswer3();
			if(answer3==null||answer3.length<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"答案3"}));
			n--;
		}
		if(n!=0){
			String[] answer4 = tktCustom.getAnswer4();
			if(answer4==null||answer4.length<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"答案4"}));
			n--;
		}
		if(n!=0){
			String[] answer5 = tktCustom.getAnswer5();
			if(answer5==null||answer5.length<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"答案5"}));
			n--;
		}
	}
	
	@Override
	public void checkTkt(TktQueryVo tktQueryVo,Sysuser sysuser) throws Exception {
		
		//审核的题目必须存在
		List<TktCustom> tktList = tktMapperCustom.findTktList(tktQueryVo);
		if(tktList==null||tktList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 311,null));

		TktCustom tktCustom = tktList.get(0);

		//修改单选题的状态为审核通过
		tktCustom.setStatus(1);
		tktMapper.updateByPrimaryKey(tktCustom);
	}

	@Override
	public void outCheckTkt(TktQueryVo tktQueryVo,Sysuser sysuser) throws Exception {
		//审核的题目必须存在
		List<TktCustom> tktList = tktMapperCustom.findTktList(tktQueryVo);
		if(tktList==null||tktList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 311,null));
		
		//获取单选题题目信息
		TktCustom tktCustom = tktList.get(0);

		//如果该题目在试卷中存在，则不允许修改题目状态为未通过
		SjTmExample sjTmExample=new SjTmExample();
		SjTmExample.Criteria sjTmCriteria = sjTmExample.createCriteria();
		sjTmCriteria.andTuuidEqualTo(tktCustom.getUuid());
		List<SjTm> sjTmList = sjTmMapper.selectByExample(sjTmExample);
		if(sjTmList!=null&&sjTmList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 328, null));
		
		//修改单选题的状态为审核未通过
		tktCustom.setStatus(2);
		tktMapper.updateByPrimaryKey(tktCustom);
		
	}
	
	private List<String> clearNullAnswer(String [] arr){
		List<String> list=new ArrayList<String>();
		for(String s:arr){
			if(StringUtils.isNoneBlank(s))
				list.add(s.trim());
		}
		return list;
	}
	
}

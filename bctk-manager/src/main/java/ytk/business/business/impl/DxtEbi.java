package ytk.business.business.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ytk.base.dao.mapper.KcMapper;
import ytk.base.dao.mapper.TeacherKcMapper;
import ytk.base.dao.mapper.ZsdMapper;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.KcExample;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.TeacherKc;
import ytk.base.pojo.po.TeacherKcExample;
import ytk.base.pojo.po.ZsdExample;
import ytk.base.pojo.po.KcExample.Criteria;
import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.business.business.DxtEbo;
import ytk.business.dao.mapper.DxtMapper;
import ytk.business.dao.mapper.DxtMapperCustom;
import ytk.business.dao.mapper.SjTmMapper;
import ytk.business.dao.mapper.TmZsdMapper;
import ytk.business.pojo.po.Dxt;
import ytk.business.pojo.po.DxtExample;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.SjTmExample;
import ytk.business.pojo.po.TmZsd;
import ytk.business.pojo.po.TmZsdExample;
import ytk.business.pojo.vo.DxtCustom;
import ytk.business.pojo.vo.DxtQueryVo;
import ytk.util.MyUtil;
import ytk.util.UUIDBuild;

public class DxtEbi implements DxtEbo{

	@Autowired
	private DxtMapperCustom dxtMapperCustom;
	@Autowired 
	private KcMapper kcMapper;
	@Autowired
	private DxtMapper dxtMapper;
	@Autowired
	private SjTmMapper sjTmMapper;
	@Autowired
	private TmZsdMapper tmZsdMapper;
	@Autowired
	private ZsdMapper zsdMapper;
	@Autowired
	private TeacherKcMapper teacherKcMapper;
	
	@Override
	public List<DxtCustom> findDxtList(DxtQueryVo dxtQueryVo) throws Exception {
		DxtCustom dxtCustom = dxtQueryVo.getDxtCustom();
		if(dxtCustom!=null){
			Long createtimeMax = dxtCustom.getCreatetimeMax();
			if(createtimeMax!=null&&!createtimeMax.equals(""))
				createtimeMax=createtimeMax+86400000-1;
			dxtCustom.setCreatetimeMax(createtimeMax);
		}
		List<DxtCustom> dxtList = dxtMapperCustom.findDxtList(dxtQueryVo);
		PageQuery pageQuery = dxtQueryVo.getPageQuery();
		if(pageQuery!=null){
			int lastIndex=pageQuery.getPageQuery_start()+pageQuery.getPageQuery_pageSize();
			if(pageQuery.getPageQuery_pageSize()>dxtList.size()-pageQuery.getPageQuery_start())
				lastIndex=dxtList.size();
			List<DxtCustom> subList = dxtList.subList(pageQuery.getPageQuery_start(), lastIndex);
			return subList;
		}
		return dxtList;
	}

	@Override
	public int findDxtListCount(DxtQueryVo dxtQueryVo) throws Exception {
		DxtCustom dxtCustom = dxtQueryVo.getDxtCustom();
		if(dxtCustom!=null){
			Long createtimeMax = dxtCustom.getCreatetimeMax();
			if(createtimeMax!=null&&!createtimeMax.equals(""))
				createtimeMax=createtimeMax+86400000-1;
			dxtCustom.setCreatetimeMax(createtimeMax);
		}
		return dxtMapperCustom.findDxtListCount(dxtQueryVo);
	}

	@Override
	public void addDxt(DxtCustom dxtCustom,Sysuser sysuser,String[] zsdList) throws Exception {
		//非空判断
		checkNull(dxtCustom);
		if(zsdList==null||zsdList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 326, null));
		
		//添加的课程名必须存在,先根据kcname查询对应的题目信息
		String kcname = dxtCustom.getKcname();
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
		
		//该门课程中不允许添加相同题目内容，选项相同的题目
		DxtExample dxtExample=new DxtExample();
		DxtExample.Criteria dxtCriteria = dxtExample.createCriteria();
		String content = dxtCustom.getContent();
		String optiona = dxtCustom.getOptiona();
		String optionb = dxtCustom.getOptionb();
		String optionc = dxtCustom.getOptionc();
		String optiond = dxtCustom.getOptiond();
		dxtCriteria.andContentEqualTo(content);
		dxtCriteria.andOptionaEqualTo(optiona);
		dxtCriteria.andOptionbEqualTo(optionb);
		dxtCriteria.andOptioncEqualTo(optionc);
		dxtCriteria.andOptiondEqualTo(optiond);
		dxtCriteria.andKcuuidEqualTo(kcUuid);
		List<Dxt> dxtList = dxtMapper.selectByExample(dxtExample);
		if(dxtList!=null&&dxtList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 305, null));
		
		Dxt dxt=new Dxt();
		//设置uuid
		String dxtUuid=UUIDBuild.getUUID();
		dxt.setUuid(dxtUuid);
		dxt.setType(1);
		//添加对应的kcuuid
		dxt.setKcuuid(kcUuid);
		dxt.setOptiona(optiona);
		dxt.setOptionb(optionb);
		dxt.setOptionc(optionc);
		dxt.setOptiond(optiond);
		dxt.setContent(content);
		dxt.setAnswer(dxtCustom.getAnswer());
		dxt.setNdtype(dxtCustom.getNdtype());
		//保存添加题目用户信息，即登录人信息
		dxt.setTeacheruuid(sysuser.getUuid());
		//添加创建题目的时间
		Long createtime=System.currentTimeMillis();
		dxt.setCreatetime(createtime);
		//添加题目状态
		dxt.setStatus(2);
		dxtMapper.insert(dxt);
		
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
			tmZsd.setTmuuid(dxtUuid);
			tmZsd.setType(1);
			tmZsd.setZsduuid(zsdUuid);
			tmZsdMapper.insert(tmZsd);
		}
		
	}

	@Override
	public DxtCustom findDxtByUuid(String uuid) throws Exception {
		DxtQueryVo dxtQueryVo=new DxtQueryVo();
		DxtCustom dxtCustom=new DxtCustom();
		dxtCustom.setUuid(uuid);
		dxtQueryVo.setDxtCustom(dxtCustom);
		List<DxtCustom> dxtList = dxtMapperCustom.findDxtList(dxtQueryVo);
		if(dxtList!=null&&dxtList.size()>0)
			return dxtList.get(0);
		return null;
	}

	@Override
	public void updateDxt(String uuid, DxtCustom dxtCustom,Sysuser sysuser,String[] zsdList) throws Exception {
		//非空判断
		checkNull(dxtCustom);
		if(zsdList==null||zsdList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 326, null));

		//获取未修改前的题目信息
		DxtCustom dxtCustomDb = findDxtByUuid(uuid);
			
		//修改的单选题信息必须存在,不存在则抛异常
		if(dxtCustomDb==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 316,null));
		
		//若题目状态为审核未通过，则可修改
		if(dxtCustomDb.getStatus()==2){
			//将登陆者的uuid与该题创建者的uuid进行比较
			String userid = sysuser.getUuid();
			String createteacherUuid=dxtCustomDb.getTeacheruuid();
			
			//如果不相等，则无权限修改
			if(!userid.equals(createteacherUuid))
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 301, null));
			
			//修改的课程名必须存在,先根据kcname查询对应的题目信息
			String kcname = dxtCustom.getKcname();
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
			String content = dxtCustom.getContent();
			String optiona = dxtCustom.getOptiona();
			String optionb = dxtCustom.getOptionb();
			String optionc = dxtCustom.getOptionc();
			String optiond = dxtCustom.getOptiond();
			if(!dxtCustomDb.getContent().equals(content)||!dxtCustomDb.getOptiona().equals(optiona)||!dxtCustomDb.getOptionb().equals(optionb)
					||!dxtCustomDb.getOptionc().equals(optionc)||!dxtCustomDb.getOptiond().equals(optiond)||!dxtCustomDb.getKcuuid().equals(kcUuid)){
				
				DxtExample dxtExample=new DxtExample();
				DxtExample.Criteria dxtCriteria = dxtExample.createCriteria();
				dxtCriteria.andContentEqualTo(content);
				dxtCriteria.andOptionaEqualTo(optiona);
				dxtCriteria.andOptionbEqualTo(optionb);
				dxtCriteria.andOptioncEqualTo(optionc);
				dxtCriteria.andOptiondEqualTo(optiond);
				dxtCriteria.andKcuuidEqualTo(kcUuid);
				List<Dxt> dxtList = dxtMapper.selectByExample(dxtExample);
				if(dxtList!=null&&dxtList.size()>0)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 305, null));
			}
			
			dxtCustomDb.setKcuuid(kcUuid);
			dxtCustomDb.setNdtype(dxtCustom.getNdtype());
			dxtCustomDb.setOptiona(dxtCustom.getOptiona());
			dxtCustomDb.setOptionb(dxtCustom.getOptionb());
			dxtCustomDb.setOptionc(dxtCustom.getOptionc());
			dxtCustomDb.setOptiond(dxtCustom.getOptiond());
			dxtCustomDb.setContent(dxtCustom.getContent());
			dxtCustomDb.setAnswer(dxtCustom.getAnswer());
			
			//修改题目状态为未通过
			dxtCustomDb.setStatus(2);
			
			//更新知识点
			dxtMapper.updateByPrimaryKey(dxtCustomDb);
			
			//修改题目知识点
			//现将之前该题目对应知识点删除，添加新的知识点
			TmZsdExample tmZsdExample=new TmZsdExample();
			TmZsdExample.Criteria tmZsdCriteria = tmZsdExample.createCriteria();
			tmZsdCriteria.andTmuuidEqualTo(dxtCustomDb.getUuid());
			tmZsdCriteria.andTypeEqualTo(1);
			
			//删除知识点
			tmZsdMapper.deleteByExample(tmZsdExample);
			
			for(String zsdUuid:zsdList){
				Zsd zsd = zsdMapper.selectByPrimaryKey(zsdUuid);
				if(zsd==null)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1405, null));
				
				//修改的知识点必须在所添加题目所对应课程中
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
				tmZsd.setTmuuid(dxtCustomDb.getUuid());
				tmZsd.setType(1);
				tmZsd.setZsduuid(zsdUuid);
				
				//添加知识点
				tmZsdMapper.insert(tmZsd);
			}
		}
		//如果审核已通过，则无需修改
		else{
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 312, null));
		}

	}

	@Override
	public void deleteDxt(String uuid,String sysuseruuid) throws Exception {
		//获取要删除的题目信息
		DxtCustom dxtCustomDb = findDxtByUuid(uuid);
		
		//删除的单选题信息必须存在,不存在则抛异常
		if(dxtCustomDb==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 306,null));
		
		//必须是删除的题目的创建者，否则无权利删除
		String teacheruuid = dxtCustomDb.getTeacheruuid();
		if(!teacheruuid.equals(sysuseruuid))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 310,null));
		
		//删除的题目状态必须是未通过(未通过的题目不会出现在试卷中)
		if(dxtCustomDb.getStatus()!=2)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 313,null));

		//删除题目信息
		dxtMapper.deleteByPrimaryKey(uuid);
		
		//删除题目知识点
		TmZsdExample tmZsdExample=new TmZsdExample();
		TmZsdExample.Criteria tmZsdCriteria = tmZsdExample.createCriteria();
		tmZsdCriteria.andTmuuidEqualTo(uuid);
		tmZsdCriteria.andTypeEqualTo(1);
		tmZsdMapper.deleteByExample(tmZsdExample);
	}
	private void checkNull(DxtCustom dxtCustom) throws Exception{
		if(!MyUtil.isNotNullAndEmptyByTrim(dxtCustom.getKcname()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"课程名称"}));
		if(dxtCustom.getNdtype()==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"难度类型"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxtCustom.getContent()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"内容"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxtCustom.getOptiona()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项A"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxtCustom.getOptionb()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项B"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxtCustom.getOptionc()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项C"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxtCustom.getOptiond()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项D"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxtCustom.getAnswer()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"答案"}));
	}

	@Override
	public void checkDxt(DxtQueryVo dxtQueryVo,Sysuser sysuser) throws Exception {
		
		//审核的题目必须存在
		List<DxtCustom> dxtList = dxtMapperCustom.findDxtList(dxtQueryVo);
		if(dxtList==null||dxtList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 311,null));

		DxtCustom dxtCustom = dxtList.get(0);
		
		//修改单选题的状态为审核通过
		dxtCustom.setStatus(1);
		dxtMapper.updateByPrimaryKey(dxtCustom);
	}

	@Override
	public void outCheckDxt(DxtQueryVo dxtQueryVo,Sysuser sysuser) throws Exception {
		//审核的题目必须存在
		List<DxtCustom> dxtList = dxtMapperCustom.findDxtList(dxtQueryVo);
		if(dxtList==null||dxtList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 311,null));
		
		//获取单选题题目信息
		DxtCustom dxtCustom = dxtList.get(0);

		//如果该题目在试卷中存在，则不允许修改题目状态为未通过
		SjTmExample sjTmExample=new SjTmExample();
		SjTmExample.Criteria sjTmCriteria = sjTmExample.createCriteria();
		sjTmCriteria.andTuuidEqualTo(dxtCustom.getUuid());
		List<SjTm> sjTmList = sjTmMapper.selectByExample(sjTmExample);
		if(sjTmList!=null&&sjTmList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 328, null));
		
		//修改单选题的状态为审核未通过
		dxtCustom.setStatus(2);
		dxtMapper.updateByPrimaryKey(dxtCustom);
		
	}

}

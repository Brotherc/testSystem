package ytk.business.business.impl;

import java.util.List;

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
import ytk.business.business.DxxztEbo;
import ytk.business.dao.mapper.DxxztMapper;
import ytk.business.dao.mapper.DxxztMapperCustom;
import ytk.business.dao.mapper.SjTmMapper;
import ytk.business.dao.mapper.TmZsdMapper;
import ytk.business.pojo.po.Dxxzt;
import ytk.business.pojo.po.DxxztExample;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.SjTmExample;
import ytk.business.pojo.po.TmZsd;
import ytk.business.pojo.po.TmZsdExample;
import ytk.business.pojo.vo.DxxztCustom;
import ytk.business.pojo.vo.DxxztQueryVo;
import ytk.util.MyUtil;
import ytk.util.UUIDBuild;

public class DxxztEbi implements DxxztEbo{

	@Autowired
	private DxxztMapperCustom dxxztMapperCustom;
	@Autowired 
	private KcMapper kcMapper;
	@Autowired
	private KcZyMapper kcZyMapper;
	@Autowired
	private DxxztMapper dxxztMapper;
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
	public List<DxxztCustom> findDxxztList(DxxztQueryVo dxxztQueryVo) throws Exception {
		DxxztCustom dxxztCustom = dxxztQueryVo.getDxxztCustom();
		if(dxxztCustom!=null){
			Long createtimeMax = dxxztCustom.getCreatetimeMax();
			if(createtimeMax!=null&&!createtimeMax.equals(""))
				createtimeMax=createtimeMax+86400000-1;
			dxxztCustom.setCreatetimeMax(createtimeMax);
		}
		List<DxxztCustom> dxxztList = dxxztMapperCustom.findDxxztList(dxxztQueryVo);
		
		PageQuery pageQuery = dxxztQueryVo.getPageQuery();
		
		if(pageQuery!=null){
			int lastIndex=pageQuery.getPageQuery_start()+pageQuery.getPageQuery_pageSize();
			if(pageQuery.getPageQuery_pageSize()>dxxztList.size()-pageQuery.getPageQuery_start())
				lastIndex=dxxztList.size();
			List<DxxztCustom> subList = dxxztList.subList(pageQuery.getPageQuery_start(), lastIndex);
			
			return subList; 
		}

		
		return dxxztList; 
	}

	@Override
	public int findDxxztListCount(DxxztQueryVo dxxztQueryVo) throws Exception {
		DxxztCustom dxxztCustom = dxxztQueryVo.getDxxztCustom();
		if(dxxztCustom!=null){
			Long createtimeMax = dxxztCustom.getCreatetimeMax();
			if(createtimeMax!=null&&!createtimeMax.equals(""))
				createtimeMax=createtimeMax+86400000-1;
			dxxztCustom.setCreatetimeMax(createtimeMax);
		}
		return dxxztMapperCustom.findDxxztListCount(dxxztQueryVo);
	}

	@Override
	public void addDxxzt(DxxztCustom dxxztCustom,Sysuser sysuser,String[] zsdList) throws Exception {
		//非空判断
		checkNull(dxxztCustom);
		if(zsdList==null||zsdList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 326, null));
		
		//添加的课程名必须存在,先根据kcname查询对应的题目信息
		String kcname = dxxztCustom.getKcname();
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
		DxxztExample dxxztExample=new DxxztExample();
		DxxztExample.Criteria dxxztCriteria = dxxztExample.createCriteria();
		String content = dxxztCustom.getContent();
		String optiona = dxxztCustom.getOptiona();
		String optionb = dxxztCustom.getOptionb();
		String optionc = dxxztCustom.getOptionc();
		String optiond = dxxztCustom.getOptiond();
		String optione = dxxztCustom.getOptione();
		String optionf = dxxztCustom.getOptionf();
		dxxztCriteria.andContentEqualTo(content);
		dxxztCriteria.andOptionaEqualTo(optiona);
		dxxztCriteria.andOptionbEqualTo(optionb);
		dxxztCriteria.andOptioncEqualTo(optionc);
		dxxztCriteria.andOptiondEqualTo(optiond);
		dxxztCriteria.andOptioneEqualTo(optione);
		dxxztCriteria.andOptionfEqualTo(optionf);
		dxxztCriteria.andKcuuidEqualTo(kcUuid);
		List<Dxxzt> dxxztList = dxxztMapper.selectByExample(dxxztExample);
		if(dxxztList!=null&&dxxztList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 305, null));
		
		Dxxzt dxxzt=new Dxxzt();
		//设置uuid
		String dxxztUuid=UUIDBuild.getUUID();
		dxxzt.setUuid(dxxztUuid);
		dxxzt.setType(2);
		//添加对应的kcuuid
		dxxzt.setKcuuid(kcUuid);
		dxxzt.setOptiona(optiona);
		dxxzt.setOptionb(optionb);
		dxxzt.setOptionc(optionc);
		dxxzt.setOptiond(optiond);
		dxxzt.setOptione(optione);
		dxxzt.setOptionf(optionf);
		dxxzt.setContent(content);
		dxxzt.setAnswer(dxxztCustom.getAnswer());
		dxxzt.setNdtype(dxxztCustom.getNdtype());
		//保存添加题目用户信息，即登录人信息
		dxxzt.setTeacheruuid(sysuser.getUuid());
		//添加创建题目的时间
		Long createtime=System.currentTimeMillis();
		dxxzt.setCreatetime(createtime);
		//添加题目状态
		dxxzt.setStatus(2);
		dxxztMapper.insert(dxxzt);

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
			tmZsd.setTmuuid(dxxztUuid);
			tmZsd.setType(2);
			tmZsd.setZsduuid(zsdUuid);
			tmZsdMapper.insert(tmZsd);
		}		
	}

	@Override
	public DxxztCustom findDxxztByUuid(String uuid) throws Exception {
		DxxztQueryVo dxxztQueryVo=new DxxztQueryVo();
		DxxztCustom dxxztCustom=new DxxztCustom();
		dxxztCustom.setUuid(uuid);
		dxxztQueryVo.setDxxztCustom(dxxztCustom);
		List<DxxztCustom> dxxztList = dxxztMapperCustom.findDxxztList(dxxztQueryVo);
		if(dxxztList!=null&&dxxztList.size()>0)
			return dxxztList.get(0);
		return null;
	}

	@Override
	public void updateDxxzt(String uuid, DxxztCustom dxxztCustom,Sysuser sysuser,String[] zsdList) throws Exception {
		//非空判断
		checkNull(dxxztCustom);
		if(zsdList==null||zsdList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 326, null));
		
		//修改的用户必须是创建该题的人才具有修改权利
		
		//获取未修改前的题目信息
		DxxztCustom dxxztCustomDb = findDxxztByUuid(uuid);
		
		//修改的多项选择题信息必须存在,不存在则抛异常
		if(dxxztCustomDb==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 316,null));
		
		//若题目状态为审核未通过，则可修改
		if(dxxztCustomDb.getStatus()==2){
			//将登陆者的uuid与该题创建者的uuid进行比较
			Long userid = sysuser.getUuid();
			Long createteacherUuid=dxxztCustomDb.getTeacheruuid();
			
			//如果不相等，则无权限修改
			if(!userid.equals(createteacherUuid))
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 301, null));
			
			//修改的课程名必须存在,先根据kcname查询对应的题目信息
			String kcname = dxxztCustom.getKcname();
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
			String content = dxxztCustom.getContent();
			String optiona = dxxztCustom.getOptiona();
			String optionb = dxxztCustom.getOptionb();
			String optionc = dxxztCustom.getOptionc();
			String optiond = dxxztCustom.getOptiond();
			String optione = dxxztCustom.getOptione();
			String optionf = dxxztCustom.getOptionf();
			if(!dxxztCustomDb.getContent().equals(content)||!dxxztCustomDb.getOptiona().equals(optiona)||!dxxztCustomDb.getOptionb().equals(optionb)
					||!dxxztCustomDb.getOptionc().equals(optionc)||!dxxztCustomDb.getOptiond().equals(optiond)||!dxxztCustomDb.getKcuuid().equals(kcUuid)){
				
				DxxztExample dxxztExample=new DxxztExample();
				DxxztExample.Criteria dxxztCriteria = dxxztExample.createCriteria();
				dxxztCriteria.andContentEqualTo(content);
				dxxztCriteria.andOptionaEqualTo(optiona);
				dxxztCriteria.andOptionbEqualTo(optionb);
				dxxztCriteria.andOptioncEqualTo(optionc);
				dxxztCriteria.andOptiondEqualTo(optiond);
				dxxztCriteria.andOptioneEqualTo(optione);
				dxxztCriteria.andOptionfEqualTo(optionf);
				dxxztCriteria.andKcuuidEqualTo(kcUuid);
				List<Dxxzt> dxxztList = dxxztMapper.selectByExample(dxxztExample);
				if(dxxztList!=null&&dxxztList.size()>0)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 305, null));
			}
			
			
			dxxztCustomDb.setKcuuid(kcUuid);
			dxxztCustomDb.setNdtype(dxxztCustom.getNdtype());
			dxxztCustomDb.setOptiona(dxxztCustom.getOptiona());
			dxxztCustomDb.setOptionb(dxxztCustom.getOptionb());
			dxxztCustomDb.setOptionc(dxxztCustom.getOptionc());
			dxxztCustomDb.setOptiond(dxxztCustom.getOptiond());
			dxxztCustomDb.setOptione(dxxztCustom.getOptione());
			dxxztCustomDb.setOptionf(dxxztCustom.getOptionf());
			dxxztCustomDb.setContent(dxxztCustom.getContent());
			dxxztCustomDb.setAnswer(dxxztCustom.getAnswer());
			//修改题目状态为未通过
			dxxztCustomDb.setStatus(2);
			dxxztMapper.updateByPrimaryKey(dxxztCustomDb);
			
			//修改题目知识点
			//现将之前该题目对应知识点删除，添加新的知识点
			TmZsdExample tmZsdExample=new TmZsdExample();
			TmZsdExample.Criteria tmZsdCriteria = tmZsdExample.createCriteria();
			tmZsdCriteria.andTmuuidEqualTo(dxxztCustomDb.getUuid());
			tmZsdCriteria.andTypeEqualTo(2);
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
				tmZsd.setTmuuid(dxxztCustomDb.getUuid());
				tmZsd.setType(2);
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
	public void deleteDxxzt(String uuid,Long sysuseruuid) throws Exception {
		//获取要删除的题目信息
		DxxztCustom dxxztCustomDb = findDxxztByUuid(uuid);
		
		//删除的多项选择题信息必须存在,不存在则抛异常
		if(dxxztCustomDb==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 306,null));
		
		//必须是删除的题目的创建者，否则无权利删除
		Long teacheruuid = dxxztCustomDb.getTeacheruuid();
		if(!teacheruuid.equals(sysuseruuid))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 310,null));
		
		//删除的题目状态必须是未通过（未通过的题目不会出现在试卷中）
		if(dxxztCustomDb.getStatus()!=2)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 313,null));

		dxxztMapper.deleteByPrimaryKey(uuid);
		
		//删除题目知识点
		TmZsdExample tmZsdExample=new TmZsdExample();
		TmZsdExample.Criteria tmZsdCriteria = tmZsdExample.createCriteria();
		tmZsdCriteria.andTmuuidEqualTo(uuid);
		tmZsdCriteria.andTypeEqualTo(2);
		tmZsdMapper.deleteByExample(tmZsdExample);
	}
	
	private void checkNull(DxxztCustom dxxztCustom) throws Exception{
		if(!MyUtil.isNotNullAndEmptyByTrim(dxxztCustom.getKcname()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"课程名称"}));
		if(dxxztCustom.getNdtype()==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"难度类型"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxxztCustom.getContent()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"内容"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxxztCustom.getOptiona()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项A"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxxztCustom.getOptionb()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项B"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxxztCustom.getOptionc()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项C"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxxztCustom.getOptiond()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项D"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxxztCustom.getOptione()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项E"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxxztCustom.getOptionf()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项F"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(dxxztCustom.getAnswer()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"答案"}));
	}

	@Override
	public void checkDxxzt(DxxztQueryVo dxxztQueryVo,Sysuser sysuser) throws Exception {
		
		//审核的题目必须存在
		List<DxxztCustom> dxxztList = dxxztMapperCustom.findDxxztList(dxxztQueryVo);
		if(dxxztList==null||dxxztList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 311,null));

		DxxztCustom dxxztCustom = dxxztList.get(0);

		//修改单选题的状态为审核通过
		dxxztCustom.setStatus(1);
		dxxztMapper.updateByPrimaryKey(dxxztCustom);
	}

	@Override
	public void outCheckDxxzt(DxxztQueryVo dxxztQueryVo,Sysuser sysuser) throws Exception {
		//审核的题目必须存在
		List<DxxztCustom> dxxztList = dxxztMapperCustom.findDxxztList(dxxztQueryVo);
		if(dxxztList==null||dxxztList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 311,null));
		
		//获取单选题题目信息
		DxxztCustom dxxztCustom = dxxztList.get(0);

		//如果该题目在试卷中存在，则不允许修改题目状态为未通过
		SjTmExample sjTmExample=new SjTmExample();
		SjTmExample.Criteria sjTmCriteria = sjTmExample.createCriteria();
		sjTmCriteria.andTuuidEqualTo(dxxztCustom.getUuid());
		List<SjTm> sjTmList = sjTmMapper.selectByExample(sjTmExample);
		if(sjTmList!=null&&sjTmList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 328, null));
		
		//修改单选题的状态为审核未通过
		dxxztCustom.setStatus(2);
		dxxztMapper.updateByPrimaryKey(dxxztCustom);
	}
}

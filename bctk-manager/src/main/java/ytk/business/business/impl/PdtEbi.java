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
import ytk.business.business.PdtEbo;
import ytk.business.dao.mapper.PdtMapper;
import ytk.business.dao.mapper.PdtMapperCustom;
import ytk.business.dao.mapper.SjTmMapper;
import ytk.business.dao.mapper.TmZsdMapper;
import ytk.business.pojo.po.Pdt;
import ytk.business.pojo.po.PdtExample;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.SjTmExample;
import ytk.business.pojo.po.TmZsd;
import ytk.business.pojo.po.TmZsdExample;
import ytk.business.pojo.vo.PdtCustom;
import ytk.business.pojo.vo.PdtQueryVo;
import ytk.util.MyUtil;
import ytk.util.UUIDBuild;

public class PdtEbi implements PdtEbo{

	@Autowired
	private PdtMapperCustom pdtMapperCustom;
	@Autowired 
	private KcMapper kcMapper;
	@Autowired
	private KcZyMapper kcZyMapper;
	@Autowired
	private PdtMapper pdtMapper;
	@Autowired
	private SysuserMapper sysuserMapper;
	@Autowired
	private SjTmMapper sjTmMapper;
	@Autowired
	private ZyMapper zyMapper;
	@Autowired
	private KcZyMapperCustom kcZyMapperCustom;
	@Autowired
	private ZsdMapper zsdMapper;
	@Autowired
	private TmZsdMapper tmZsdMapper;
	@Autowired
	private TeacherKcMapper teacherKcMapper;
	
	@Override
	public List<PdtCustom> findPdtList(PdtQueryVo pdtQueryVo) throws Exception {
		PdtCustom pdtCustom = pdtQueryVo.getPdtCustom();
		if(pdtCustom!=null){
			Long createtimeMax = pdtCustom.getCreatetimeMax();
			if(createtimeMax!=null&&!createtimeMax.equals(""))
				createtimeMax=createtimeMax+86400000-1;
			pdtCustom.setCreatetimeMax(createtimeMax);
		}
		List<PdtCustom> pdtList = pdtMapperCustom.findPdtList(pdtQueryVo);
		PageQuery pageQuery = pdtQueryVo.getPageQuery();
		
		if(pageQuery!=null){
			int lastIndex=pageQuery.getPageQuery_start()+pageQuery.getPageQuery_pageSize();
			if(pageQuery.getPageQuery_pageSize()>pdtList.size()-pageQuery.getPageQuery_start())
				lastIndex=pdtList.size();
			List<PdtCustom> subList = pdtList.subList(pageQuery.getPageQuery_start(), lastIndex);
			return subList;
		}
		return pdtList;
	}

	@Override
	public int findPdtListCount(PdtQueryVo pdtQueryVo) throws Exception {
		PdtCustom pdtCustom = pdtQueryVo.getPdtCustom();
		if(pdtCustom!=null){
			Long createtimeMax = pdtCustom.getCreatetimeMax();
			if(createtimeMax!=null&&!createtimeMax.equals(""))
				createtimeMax=createtimeMax+86400000-1;
			pdtCustom.setCreatetimeMax(createtimeMax);
		}
		return pdtMapperCustom.findPdtListCount(pdtQueryVo);
	}

	@Override
	public void addPdt(PdtCustom pdtCustom,Sysuser sysuser,String[] zsdList) throws Exception {
		//非空判断
		checkNull(pdtCustom);
		if(zsdList==null||zsdList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 326, null));
		
		//添加的课程名必须存在,先根据kcname查询对应的题目信息
		String kcname = pdtCustom.getKcname();
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
		PdtExample pdtExample=new PdtExample();
		PdtExample.Criteria pdtCriteria = pdtExample.createCriteria();
		String content = pdtCustom.getContent();
		pdtCriteria.andContentEqualTo(content);
		pdtCriteria.andKcuuidEqualTo(kcUuid);
		List<Pdt> pdtList = pdtMapper.selectByExample(pdtExample);
		if(pdtList!=null&&pdtList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 305, null));
		
		Pdt pdt=new Pdt();
		String pdtUuid=UUIDBuild.getUUID();
		pdt.setUuid(pdtUuid);
		pdt.setType(5);
		//添加对应的kcuuid
		pdt.setKcuuid(kcUuid);
		pdt.setContent(content);
		pdt.setAnswer(pdtCustom.getAnswer());
		pdt.setNdtype(pdtCustom.getNdtype());
		//保存添加题目用户信息，即登录人信息
		pdt.setTeacheruuid(sysuser.getUuid());
		//添加创建题目的时间
		Long createtime=System.currentTimeMillis();
		pdt.setCreatetime(createtime);
		//添加题目状态
		pdt.setStatus(2);
		pdtMapper.insert(pdt);

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
			tmZsd.setTmuuid(pdtUuid);
			tmZsd.setType(5);
			tmZsd.setZsduuid(zsdUuid);
			tmZsdMapper.insert(tmZsd);
		}
		
	}

	@Override
	public PdtCustom findPdtByUuid(String uuid) throws Exception {
		PdtQueryVo pdtQueryVo=new PdtQueryVo();
		PdtCustom pdtCustom=new PdtCustom();
		pdtCustom.setUuid(uuid);
		pdtQueryVo.setPdtCustom(pdtCustom);
		List<PdtCustom> pdtList = pdtMapperCustom.findPdtList(pdtQueryVo);
		if(pdtList!=null&&pdtList.size()>0)
			return pdtList.get(0);
		return null;
	}

	@Override
	public void updatePdt(String uuid, PdtCustom pdtCustom,Sysuser sysuser,String[] zsdList) throws Exception {
		//非空判断
		checkNull(pdtCustom);
		if(zsdList==null||zsdList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 326, null));
		
		//修改的用户必须是创建该题的人才具有修改权利
		
		//获取未修改前的题目信息
		PdtCustom pdtCustomDb = findPdtByUuid(uuid);
		
		//修改的简答题信息必须存在,不存在则抛异常
		if(pdtCustomDb==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 316,null));
		
		//若题目状态为审核未通过，则可修改
		if(pdtCustomDb.getStatus()==2){
			//将登陆者的uuid与该题创建者的uuid进行比较
			String userid = sysuser.getUuid();
			String createteacherUuid=pdtCustomDb.getTeacheruuid();
			
			//如果不相等，则无权限修改
			if(!userid.equals(createteacherUuid))
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 301, null));
			
			//修改的课程名必须存在,先根据kcname查询对应的题目信息
			String kcname = pdtCustom.getKcname();
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
			String content = pdtCustom.getContent();
			if(!pdtCustomDb.getContent().equals(content)||!pdtCustomDb.getKcuuid().equals(kcUuid)){
				
				PdtExample pdtExample=new PdtExample();
				PdtExample.Criteria pdtCriteria = pdtExample.createCriteria();
				pdtCriteria.andContentEqualTo(content);
				pdtCriteria.andKcuuidEqualTo(kcUuid);
				List<Pdt> pdtList =pdtMapper.selectByExample(pdtExample); 
				if(pdtList!=null&&pdtList.size()>0)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 305, null));
			}
			
			pdtCustomDb.setKcuuid(kcUuid);
			pdtCustomDb.setNdtype(pdtCustom.getNdtype());
			pdtCustomDb.setContent(pdtCustom.getContent());
			pdtCustomDb.setAnswer(pdtCustom.getAnswer());
			
			pdtMapper.updateByPrimaryKey(pdtCustomDb);

			//修改题目知识点
			//现将之前该题目对应知识点删除，添加新的知识点
			TmZsdExample tmZsdExample=new TmZsdExample();
			TmZsdExample.Criteria tmZsdCriteria = tmZsdExample.createCriteria();
			tmZsdCriteria.andTmuuidEqualTo(pdtCustomDb.getUuid());
			tmZsdCriteria.andTypeEqualTo(5);
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
				tmZsd.setTmuuid(pdtCustomDb.getUuid());
				tmZsd.setType(5);
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
	public void deletePdt(String uuid,String sysuseruuid) throws Exception {
		//获取要删除的题目信息
		PdtCustom pdtCustomDb = findPdtByUuid(uuid);
		
		//删除的简答题信息必须存在,不存在则抛异常
		if(pdtCustomDb==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 306,null));
		
		//必须是删除的题目的创建者，否则无权利删除
		String teacheruuid = pdtCustomDb.getTeacheruuid();
		if(!teacheruuid.equals(sysuseruuid))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 310,null));
		
		//删除的题目状态必须是未通过(未通过的题目不会出现在试卷中)
		if(pdtCustomDb.getStatus()!=2)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 313,null));
		
		pdtMapper.deleteByPrimaryKey(uuid);
		
		//删除题目知识点
		TmZsdExample tmZsdExample=new TmZsdExample();
		TmZsdExample.Criteria tmZsdCriteria = tmZsdExample.createCriteria();
		tmZsdCriteria.andTmuuidEqualTo(uuid);
		tmZsdCriteria.andTypeEqualTo(5);
		tmZsdMapper.deleteByExample(tmZsdExample);
	}
	private void checkNull(PdtCustom pdtCustom) throws Exception{
		if(!MyUtil.isNotNullAndEmptyByTrim(pdtCustom.getKcname()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"课程名称"}));
		if(pdtCustom.getNdtype()==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"难度类型"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(pdtCustom.getContent()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"内容"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(pdtCustom.getAnswer()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"答案"}));
	}
	
	@Override
	public void checkPdt(PdtQueryVo pdtQueryVo,Sysuser sysuser) throws Exception {
		
		//审核的题目必须存在
		List<PdtCustom> pdtList = pdtMapperCustom.findPdtList(pdtQueryVo);
		if(pdtList==null||pdtList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 311,null));
		
		PdtCustom pdtCustom = pdtList.get(0);

		//修改单选题的状态为审核通过
		pdtCustom.setStatus(1);
		pdtMapper.updateByPrimaryKey(pdtCustom);
	}

	@Override
	public void outCheckPdt(PdtQueryVo pdtQueryVo,Sysuser sysuser) throws Exception {
		//审核的题目必须存在
		List<PdtCustom> pdtList = pdtMapperCustom.findPdtList(pdtQueryVo);
		if(pdtList==null||pdtList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 311,null));
		
		//获取单选题题目信息
		PdtCustom pdtCustom = pdtList.get(0);

		//如果该题目在试卷中存在，则不允许修改题目状态为未通过
		SjTmExample sjTmExample=new SjTmExample();
		SjTmExample.Criteria sjTmCriteria = sjTmExample.createCriteria();
		sjTmCriteria.andTuuidEqualTo(pdtCustom.getUuid());
		List<SjTm> sjTmList = sjTmMapper.selectByExample(sjTmExample);
		if(sjTmList!=null&&sjTmList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 328, null));
		
		//修改单选题的状态为审核未通过
		pdtCustom.setStatus(2);
		pdtMapper.updateByPrimaryKey(pdtCustom);
		
	}
}

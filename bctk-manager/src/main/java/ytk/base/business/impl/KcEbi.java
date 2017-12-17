package ytk.base.business.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.KcEbo;
import ytk.base.dao.mapper.KcMapper;
import ytk.base.dao.mapper.KcMapperCustom;
import ytk.base.dao.mapper.KcZyMapper;
import ytk.base.dao.mapper.TeacherKcMapper;
import ytk.base.dao.mapper.ZyMapper;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.KcExample;
import ytk.base.pojo.po.KcZyExample;
import ytk.base.pojo.po.KcExample.Criteria;
import ytk.base.pojo.po.KcZy;
import ytk.base.pojo.po.TeacherKc;
import ytk.base.pojo.po.TeacherKcExample;
import ytk.base.pojo.po.Zy;
import ytk.base.pojo.vo.KcCustom;
import ytk.base.pojo.vo.KcQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.business.dao.mapper.DxtMapper;
import ytk.business.dao.mapper.DxxztMapper;
import ytk.business.dao.mapper.JdtMapper;
import ytk.business.dao.mapper.KsglMapper;
import ytk.business.dao.mapper.PdtMapper;
import ytk.business.dao.mapper.SjMapper;
import ytk.business.dao.mapper.TktMapper;
import ytk.business.pojo.po.Dxt;
import ytk.business.pojo.po.DxtExample;
import ytk.business.pojo.po.Dxxzt;
import ytk.business.pojo.po.DxxztExample;
import ytk.business.pojo.po.Jdt;
import ytk.business.pojo.po.JdtExample;
import ytk.business.pojo.po.Ksgl;
import ytk.business.pojo.po.KsglExample;
import ytk.business.pojo.po.Pdt;
import ytk.business.pojo.po.PdtExample;
import ytk.business.pojo.po.Sj;
import ytk.business.pojo.po.SjExample;
import ytk.business.pojo.po.Tkt;
import ytk.business.pojo.po.TktExample;
import ytk.util.MyUtil;
import ytk.util.PinYin4jUtils;
import ytk.util.UUIDBuild;

public class KcEbi implements KcEbo{
	
	@Autowired
	private KcMapper kcMapper;
	@Autowired
	private KcMapperCustom kcMapperCustom;
	@Autowired
	private KcZyMapper kcZyMapper;
	@Autowired
	private ZyMapper zyMapper;
	@Autowired
	private TeacherKcMapper teacherKcMapper;
	@Autowired
	private DxtMapper dxtMapper;
	@Autowired
	private DxxztMapper dxxztMapper;
	@Autowired
	private TktMapper tktMapper;
	@Autowired
	private JdtMapper jdtMapper;
	@Autowired
	private PdtMapper pdtMapper;
	@Autowired
	private SjMapper sjMapper;
	@Autowired
	private KsglMapper ksglMapper;
	
	@Override
	public List<KcCustom> findKcList(KcQueryVo kcQueryVo) throws Exception {
		return kcMapperCustom.findKcList(kcQueryVo);
	}

	@Override
	public int findKcListCount(KcQueryVo kcQueryVo) throws Exception {
		return kcMapperCustom.findKcListCount(kcQueryVo);
	}

	@Override
	public void addKc(KcQueryVo kcQueryVo,String[] teacherList) throws Exception {
		KcCustom kcCustom = kcQueryVo.getKcCustom();
		
		//非空判断
		checkNull(kcCustom);
		if(teacherList==null||teacherList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"任课教师"}));
		if(kcQueryVo.getZyList()==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 607, null));
		
		String name = kcCustom.getName();
		
		//添加的课程不能与已存在的重复
		KcExample kcExample=new KcExample();
		Criteria criteria = kcExample.createCriteria();
		criteria.andNameEqualTo(name);
		List<Kc> kcList = kcMapper.selectByExample(kcExample);
		if(kcList!=null&&kcList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 601, null));
		
		//为当前课程信息添加创建日期
		kcCustom.setCreatetime(System.currentTimeMillis());
		
		//补全简码
		String[] headString = PinYin4jUtils.getHeadByString(name);
		String shortcode = StringUtils.join(headString,"");
		kcCustom.setShortcode(shortcode.toLowerCase());
		
		//补全课程编码
		String kccode = PinYin4jUtils.hanziToPinyin(name, "");
		kcCustom.setKccode(kccode);
		
		//添加课程
		kcMapper.insert(kcCustom);
		
		//添加的课程信息主键返回
		Long kcuuid = kcCustom.getUuid();
		
		// 为当前课程添加专业信息
		Long[] zyList = kcQueryVo.getZyList();
		if(zyList!=null){
			for(Long zyuuid:zyList){
				KcZy kcZy=new KcZy();
				kcZy.setKcuuid(kcuuid);
				kcZy.setZyuuid(zyuuid);
				kcZyMapper.insert(kcZy);
			}
		}
		
		//添加任课教师
		for(String uuid:teacherList){
			TeacherKc teacherKc=new TeacherKc();
			String uuid2 = UUIDBuild.getUUID();
			teacherKc.setUuid(uuid2);
			teacherKc.setKcuuid(kcuuid);
			teacherKc.setTeacheruuid(uuid);
			teacherKcMapper.insert(teacherKc);
		}
	}

	@Override
	public void updateKc(Long uuid, KcCustom kcCustom,Long[] zyList,String[] teacherList) throws Exception {
		//非空判断
		checkNull(kcCustom);
		if(zyList==null||zyList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 607, null));
		if(teacherList==null||teacherList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"任课教师"}));
		
		//修改的课程必须存在
		Kc kc = kcMapper.selectByPrimaryKey(uuid);
		if(kc==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 602, null));
		
		//若对课程信息进行了修改，则不允许与已存在的课程信息重复
		String updateName = kcCustom.getName();
		String name=kc.getName();
		if(!name.equals(updateName)){
			KcExample kcExample=new KcExample();
			Criteria criteria = kcExample.createCriteria();
			criteria.andNameEqualTo(updateName);
			List<Kc> kcList = kcMapper.selectByExample(kcExample);
			if(kcList!=null&&kcList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 603, null));
			kc.setName(updateName);
		}
		
		//修改创建时间
		Long createtime = kcCustom.getCreatetime();
		if(createtime!=null)
			kc.setCreatetime(createtime);
		
		//更新课程
		kcMapper.updateByPrimaryKey(kc);
		
		//删除之前的课程专业信息
		KcZyExample kcZyExample=new KcZyExample();
		KcZyExample.Criteria criteria2 = kcZyExample.createCriteria();
		criteria2.andKcuuidEqualTo(uuid);
		kcZyMapper.deleteByExample(kcZyExample);
		
		//添加新的课程专业信息
		for(Long zyuuid:zyList){
			KcZy kcZy=new KcZy();
			kcZy.setKcuuid(uuid);
			kcZy.setZyuuid(zyuuid);
			kcZyMapper.insert(kcZy);
		}
		
		//删除以前的任课教师
		TeacherKcExample teacherKcExample=new TeacherKcExample();
		TeacherKcExample.Criteria createCriteria = teacherKcExample.createCriteria();
		createCriteria.andKcuuidEqualTo(uuid);
		teacherKcMapper.deleteByExample(teacherKcExample);
		
		//添加新的任课教师
		for(String teacheruuid:teacherList){
			TeacherKc teacherKc=new TeacherKc();
			String uuid2 = UUIDBuild.getUUID();
			teacherKc.setUuid(uuid2);
			teacherKc.setKcuuid(uuid);
			teacherKc.setTeacheruuid(teacheruuid);
			teacherKcMapper.insert(teacherKc);
		}
	}

	@Override
	public void deleteKc(Long uuid,Long zyuuid) throws Exception {
		//删除的课程信息必须存在
		Kc kc = kcMapper.selectByPrimaryKey(uuid);
		if(kc==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 604, null)); 
		
		Long kcuuid=kc.getUuid();
		
		//删除的专业信息必须存在
		Zy zy = zyMapper.selectByPrimaryKey(zyuuid);
		if(zy==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 605, null));
		
		//删除的专业课程信息必须存在
		KcZyExample kcZyExample=new KcZyExample();
		KcZyExample.Criteria criteria = kcZyExample.createCriteria();
		criteria.andKcuuidEqualTo(kcuuid);
		criteria.andZyuuidEqualTo(zyuuid);
		List<KcZy> kcZyList = kcZyMapper.selectByExample(kcZyExample);
		if(kcZyList==null||kcZyList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 606, null));
		
		KcZy kcZy = kcZyList.get(0);
		
		//如果移除该课程后，没有专业存在该课程，则如果存在该课程对应的题目，试卷，考试，提示用户必须先其删除
		KcZyExample kcZyExample3=new KcZyExample();
		KcZyExample.Criteria kcZyCriteria3 = kcZyExample3.createCriteria();
		kcZyCriteria3.andUuidNotEqualTo(kcZy.getUuid());
		kcZyCriteria3.andKcuuidEqualTo(uuid);
		List<KcZy> kcZyList3 = kcZyMapper.selectByExample(kcZyExample3);
		if(kcZyList3==null||kcZyList3.size()<1){
			//查询该课程所对应单选题题目
			DxtExample dxtExample=new DxtExample();
			DxtExample.Criteria dxtCriteria = dxtExample.createCriteria();
			dxtCriteria.andKcuuidEqualTo(kcuuid);
			List<Dxt> dxtList = dxtMapper.selectByExample(dxtExample);
			if(dxtList!=null&&dxtList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 608, null));
			
			//查询该课程所对应多项选择题题目
			DxxztExample dxxztExample=new DxxztExample();
			DxxztExample.Criteria dxxztCriteria = dxxztExample.createCriteria();
			dxxztCriteria.andKcuuidEqualTo(kcuuid);
			List<Dxxzt> dxxztList = dxxztMapper.selectByExample(dxxztExample);
			if(dxxztList!=null&&dxxztList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 608, null));
			
			//查询该课程所对应填空题题目
			TktExample tktExample=new TktExample();
			TktExample.Criteria tktCriteria = tktExample.createCriteria();
			tktCriteria.andKcuuidEqualTo(kcuuid);
			List<Tkt> tktList = tktMapper.selectByExample(tktExample);
			if(tktList!=null&&tktList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 608, null));
			
			//查询该课程所对应简答题题目
			JdtExample jdtExample=new JdtExample();
			JdtExample.Criteria jdtCriteria = jdtExample.createCriteria();
			jdtCriteria.andKcuuidEqualTo(kcuuid);
			List<Jdt> jdtList = jdtMapper.selectByExample(jdtExample);
			if(jdtList!=null&&jdtList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 608, null));
			
			//查询该课程所对应简答题题目
			PdtExample pdtExample=new PdtExample();
			PdtExample.Criteria pdtCriteria = pdtExample.createCriteria();
			pdtCriteria.andKcuuidEqualTo(kcuuid);
			List<Pdt> pdtList = pdtMapper.selectByExample(pdtExample);
			if(pdtList!=null&&pdtList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 608, null));
			
			SjExample sjExample=new SjExample();
			SjExample.Criteria sjCriteria = sjExample.createCriteria();
			sjCriteria.andKcidEqualTo(kcuuid);
			List<Sj> sjList = sjMapper.selectByExample(sjExample);
			if(sjList!=null&&sjList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 609, null));
			
			KsglExample ksglExample=new KsglExample();
			KsglExample.Criteria ksglCriteria = ksglExample.createCriteria();
			ksglCriteria.andKcuuidEqualTo(kcuuid);
			List<Ksgl> ksglList = ksglMapper.selectByExample(ksglExample);
			if(ksglList!=null&&ksglList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 610, null));
		}
		
		//移除相应专业课程信息
		kcZyMapper.deleteByPrimaryKey(kcZy.getUuid());
		
		//若移除完后，对应课程不在课程专业中，则将该课程删除
		KcZyExample kcZyExample2=new KcZyExample();
		KcZyExample.Criteria criteria2 = kcZyExample2.createCriteria();
		criteria2.andKcuuidEqualTo(kcuuid);
		List<KcZy> kcZyList2 = kcZyMapper.selectByExample(kcZyExample2);
		if(kcZyList2==null||kcZyList2.size()<1){
			//删除课程
			kcMapper.deleteByPrimaryKey(uuid);
			
			//删除相应的任课教师
			TeacherKcExample teacherKcExample=new TeacherKcExample();
			TeacherKcExample.Criteria createCriteria = teacherKcExample.createCriteria();
			createCriteria.andKcuuidEqualTo(uuid);
			teacherKcMapper.deleteByExample(teacherKcExample);
		}
	}

	@Override
	public KcCustom findKcByUuid(Long uuid) throws Exception {
		Kc kc = kcMapper.selectByPrimaryKey(uuid);
		KcZyExample kcZyExample=new KcZyExample();
		KcZyExample.Criteria criteria = kcZyExample.createCriteria();
		criteria.andKcuuidEqualTo(uuid);
		List<KcZy> zylist = kcZyMapper.selectByExample(kcZyExample);
		int i=0;
		Long[] zyuuids=new Long[zylist.size()];
		for(KcZy kcZy:zylist){
			zyuuids[i++]=kcZy.getZyuuid();
		}
		KcCustom kcCustom=new KcCustom();
		BeanUtils.copyProperties(kc, kcCustom);
		kcCustom.setZyuuids(zyuuids);
		return kcCustom;
	}

	@Override
	public List<Kc> findKcList() throws Exception {
		return kcMapper.selectByExample(new KcExample());
	}

	private void checkNull(KcCustom kcCustom) throws Exception{
		if(!MyUtil.isNotNullAndEmptyByTrim(kcCustom.getName())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"课程名称"}));
		}
	}

	@Override
	public List<KcCustom> findKcListByQ(KcQueryVo kcQueryVo,String q) throws Exception{
		if(kcQueryVo==null){
			kcQueryVo=new KcQueryVo();
			KcCustom kcCustom=new KcCustom();
			kcCustom.setQ(q);
			kcQueryVo.setKcCustom(kcCustom);
		}
		else{
			KcCustom kcCustom = kcQueryVo.getKcCustom();
			if(kcCustom==null){
				kcCustom=new KcCustom();
				kcQueryVo.setKcCustom(kcCustom);
			}
			kcCustom.setQ(q);
		}
		List<KcCustom> kcList = kcMapperCustom.findKcListByQ(kcQueryVo);
		return kcList;
	}

	@Override
	public List<KcCustom> findKcListNoZy(KcQueryVo kcQueryVo)
			throws Exception {
		return kcMapperCustom.findKcListNoZy(kcQueryVo);
	}

	@Override
	public List<KcCustom> findKcListNoZyByQ(KcQueryVo kcQueryVo, String q)
			throws Exception {
		if(kcQueryVo==null){
			kcQueryVo=new KcQueryVo();
			KcCustom kcCustom=new KcCustom();
			kcCustom.setQ(q);
			kcQueryVo.setKcCustom(kcCustom);
		}
		else{
			KcCustom kcCustom = kcQueryVo.getKcCustom();
			if(kcCustom==null){
				kcCustom=new KcCustom();
				kcQueryVo.setKcCustom(kcCustom);
			}
			kcCustom.setQ(q);
		}
		return kcMapperCustom.findKcListNoZyByQ(kcQueryVo);
	}
}

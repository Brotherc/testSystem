package ytk.base.business.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.ZsdEbo;
import ytk.base.dao.mapper.KcMapper;
import ytk.base.dao.mapper.ZsdMapper;
import ytk.base.dao.mapper.ZsdMapperCustom;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.KcExample;
import ytk.base.pojo.po.KcExample.Criteria;
import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.po.ZsdExample;
import ytk.base.pojo.vo.ZsdCustom;
import ytk.base.pojo.vo.ZsdQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.business.dao.mapper.TmZsdMapper;
import ytk.business.pojo.po.TmZsd;
import ytk.business.pojo.po.TmZsdExample;
import ytk.util.MyUtil;
import ytk.util.PinYin4jUtils;
import ytk.util.UUIDBuild;

public class ZsdEbi implements ZsdEbo{

	@Autowired
	private ZsdMapperCustom zsdMapperCustom;
	@Autowired
	private KcMapper kcMapper;
	@Autowired
	private ZsdMapper zsdMapper;
	@Autowired
	private TmZsdMapper tmZsdMapper;
	
	@Override
	public List<ZsdCustom> findZsdList(ZsdQueryVo zsdQueryVo) throws Exception {
		return zsdMapperCustom.findZsdList(zsdQueryVo);
	}

	@Override
	public int findZsdListCount(ZsdQueryVo zsdQueryVo) throws Exception {
		return zsdMapperCustom.findZsdListCount(zsdQueryVo);
	}

	@Override
	public void addZsd(ZsdQueryVo zsdQueryVo) throws Exception {
		ZsdCustom zsdCustom = zsdQueryVo.getZsdCustom();
		//非空判断
		checkNull(zsdCustom);
		
		//课程必须存在
		KcExample kcExample=new KcExample();
		Criteria kcCriteria = kcExample.createCriteria();
		kcCriteria.andNameEqualTo(zsdCustom.getKcname());
		List<Kc> kcList = kcMapper.selectByExample(kcExample);
		if(kcList==null||kcList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1401, null));
		
		Kc kc = kcList.get(0);
		String name = zsdCustom.getName();
		
		//该课程不能出现相同的知识点
		ZsdExample zsdExample=new ZsdExample();
		ZsdExample.Criteria zsdCriteria = zsdExample.createCriteria();
		zsdCriteria.andNameEqualTo(name);
		zsdCriteria.andKcuuidEqualTo(kc.getUuid());
		List<Zsd> zsdList = zsdMapper.selectByExample(zsdExample);
		if(zsdList!=null&&zsdList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1402, null));
		
		//补全简码
		String[] headString = PinYin4jUtils.getHeadByString(name);
		String shortcode = StringUtils.join(headString,"");
		zsdCustom.setShortcode(shortcode.toLowerCase());
		
		//补全知识点编码
		String zsdcode = PinYin4jUtils.hanziToPinyin(name, "");
		zsdCustom.setZsdcode(zsdcode);
		zsdCustom.setKcuuid(kc.getUuid());
		
		//添加uuid
		zsdCustom.setUuid(UUIDBuild.getUUID());
		
		//添加知识点信息
		zsdMapper.insert(zsdCustom);
	}

	private void checkNull(ZsdCustom zsdCustom) throws Exception{
		String name = zsdCustom.getName();
		if(!MyUtil.isNotNullAndEmptyByTrim(name))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"知识点名称"}));
		
		String kcname = zsdCustom.getKcname();
		if(!MyUtil.isNotNullAndEmptyByTrim(kcname))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"课程名称"}));
	}
	
	@Override
	public List<ZsdCustom> findZsdListByQ(ZsdQueryVo zsdQueryVo, String q)
			throws Exception {
		if(zsdQueryVo==null){
			zsdQueryVo=new ZsdQueryVo();
			ZsdCustom zsdCustom=new ZsdCustom();
			zsdCustom.setQ(q);
			zsdQueryVo.setZsdCustom(zsdCustom);
		}
		else{
			ZsdCustom zsdCustom = zsdQueryVo.getZsdCustom();
			if(zsdCustom==null){
				zsdCustom=new ZsdCustom();
				zsdQueryVo.setZsdCustom(zsdCustom);
			}
			zsdCustom.setQ(q);
		}
		List<ZsdCustom> zsdList = zsdMapperCustom.findZsdListByQ(zsdQueryVo);
		return zsdList;
	}

	@Override
	public void deleteZsd(String uuid) throws Exception {
		//删除的知识点信息必须存在
		Zsd zsd = zsdMapper.selectByPrimaryKey(uuid);
		if(zsd==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1403, null));
		
		//如果存在该知识点的题目，则不允许删除
		TmZsdExample tmZsdExample=new TmZsdExample();
		TmZsdExample.Criteria tmZsdCriteria = tmZsdExample.createCriteria();
		tmZsdCriteria.andZsduuidEqualTo(uuid);
		List<TmZsd> tmZsdList = tmZsdMapper.selectByExample(tmZsdExample);
		if(tmZsdList!=null&&tmZsdList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1406, null));
		
		//删除知识点
		zsdMapper.deleteByPrimaryKey(uuid);
	}

	@Override
	public ZsdCustom findZsdByUuid(String uuid) throws Exception {
		ZsdQueryVo zsdQueryVo=new ZsdQueryVo();
		ZsdCustom zsdCustom=new ZsdCustom();
		zsdQueryVo.setZsdCustom(zsdCustom);
		zsdCustom.setUuid(uuid);
		List<ZsdCustom> zsdList = zsdMapperCustom.findZsdList(zsdQueryVo);
		if(zsdList!=null&&zsdList.size()>0)
			return zsdList.get(0);
		return null;
	}

	@Override
	public void updateZsd(String uuid, ZsdCustom zsdCustom) throws Exception {
	checkNull(zsdCustom);

		//课程必须存在
		KcExample kcExample=new KcExample();
		Criteria kcCriteria = kcExample.createCriteria();
		kcCriteria.andNameEqualTo(zsdCustom.getKcname());
		List<Kc> kcList = kcMapper.selectByExample(kcExample);
		if(kcList==null||kcList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1401, null));
		Kc kc = kcList.get(0);
	
		//若对知识点信息进行了修改，则不允许与已存在的课程知识点重复
		ZsdCustom zsdCustomDb = findZsdByUuid(uuid);
		if(zsdCustomDb==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1404, null));
		String name = zsdCustomDb.getName();
		String kcname=zsdCustomDb.getKcname();
		String updateName = zsdCustom.getName();
		String updateKcname = zsdCustom.getKcname();
		if(!name.equals(updateName)||!kcname.equals(updateKcname)){
			ZsdExample zsdExample=new ZsdExample();
			ZsdExample.Criteria zsdCriteria = zsdExample.createCriteria();
			zsdCriteria.andNameEqualTo(updateName);
			zsdCriteria.andKcuuidEqualTo(kc.getUuid());
			List<Zsd> zsdList = zsdMapper.selectByExample(zsdExample);
			if(zsdList!=null&&zsdList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1402, null));
			zsdCustomDb.setName(updateName);
			zsdCustomDb.setKcuuid(kc.getUuid());
		}
		zsdMapper.updateByPrimaryKey(zsdCustomDb);	
	}

	@Override
	public List<Zsd> findZsdListByKcUuid(Long kcuuid) throws Exception {
		ZsdExample zsdExample=new ZsdExample();
		ZsdExample.Criteria zsdCriteria = zsdExample.createCriteria();
		zsdCriteria.andKcuuidEqualTo(kcuuid);
		return zsdMapper.selectByExample(zsdExample);
	}
}

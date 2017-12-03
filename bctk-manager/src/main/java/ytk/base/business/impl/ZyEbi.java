package ytk.base.business.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.ZyEbo;
import ytk.base.dao.mapper.KcZyMapper;
import ytk.base.dao.mapper.XiMapper;
import ytk.base.dao.mapper.ZyMapper;
import ytk.base.dao.mapper.ZyMapperCustom;
import ytk.base.pojo.po.KcZy;
import ytk.base.pojo.po.KcZyExample;
import ytk.base.pojo.po.Xi;
import ytk.base.pojo.po.Zy;
import ytk.base.pojo.po.ZyExample;
import ytk.base.pojo.po.ZyExample.Criteria;
import ytk.base.pojo.vo.ZyCustom;
import ytk.base.pojo.vo.ZyQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.util.MyUtil;
import ytk.util.PinYin4jUtils;

public class ZyEbi implements ZyEbo{

	@Autowired
	private ZyMapper zyMapper;
	@Autowired
	private XiMapper xiMapper;
	@Autowired
	private ZyMapperCustom zyMapperCustom;
	@Autowired
	private KcZyMapper kcZyMapper;
	
	@Override
	public List<Zy> findZyList() throws Exception {
		ZyExample zyExample=new ZyExample();
		return zyMapper.selectByExample(zyExample);
	}

	@Override
	public List<ZyCustom> findZyList(ZyQueryVo zyQueryVo) throws Exception {
		ZyCustom zyCustom = zyQueryVo.getZyCustom();
		if(zyCustom!=null){
			Long createimeMax = zyCustom.getCreateimeMax();
			if(createimeMax!=null&&!createimeMax.equals(""))
				createimeMax=createimeMax+86400000-1;
			zyCustom.setCreateimeMax(createimeMax);
		}
		return zyMapperCustom.findZyList(zyQueryVo);
	}

	@Override
	public int findZyListCount(ZyQueryVo zyQueryVo) throws Exception {
		ZyCustom zyCustom = zyQueryVo.getZyCustom();
		if(zyCustom!=null){
			Long createimeMax = zyCustom.getCreateimeMax();
			if(createimeMax!=null&&!createimeMax.equals(""))
				createimeMax=createimeMax+86400000-1;
			zyCustom.setCreateimeMax(createimeMax);
		}
		return zyMapperCustom.findZyListCount(zyQueryVo);
	}

	@Override
	public List<Zy> findZyByXiUuid(Long uuid) throws Exception {
		ZyExample zyExample=new ZyExample();
		Criteria criteria = zyExample.createCriteria();
		criteria.andXuuidEqualTo(uuid);
		return zyMapper.selectByExample(zyExample);
	}
	
	@Override
	public ZyCustom findZyByUuid(Long uuid) throws Exception {
		Zy zy = zyMapper.selectByPrimaryKey(uuid);
		ZyCustom zyCustom = new ZyCustom();
		BeanUtils.copyProperties(zy, zyCustom);
		return zyCustom;
	}

	@Override
	public List<ZyCustom> findZyListByQ(ZyQueryVo zyQueryVo,String q) throws Exception{
		if(zyQueryVo==null){
			zyQueryVo=new ZyQueryVo();
			ZyCustom zyCustom=new ZyCustom();
			zyCustom.setQ(q);
			zyQueryVo.setZyCustom(zyCustom);
		}
		else{
			ZyCustom zyCustom = zyQueryVo.getZyCustom();
			if(zyCustom==null){
				zyCustom=new ZyCustom();
				zyQueryVo.setZyCustom(zyCustom);
			}
			zyCustom.setQ(q);
		}
		List<ZyCustom> zyList = zyMapperCustom.findZyListByQ(zyQueryVo);
		return zyList;
	}
	
	@Override
	public void addZy(ZyQueryVo zyQueryVo) throws Exception {
		ZyCustom zyCustom = zyQueryVo.getZyCustom();
		
		//非空判断
		checkNull(zyCustom);
		
		//添加的专业所属的系信息必须存在
		Long xiUuid=zyCustom.getXuuid();
		Xi xi = xiMapper.selectByPrimaryKey(xiUuid);
		if(xi==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 901, null));
		
		//不允许添加专业`````、系信息已存在的专业
		String zyName=zyCustom.getName();
		ZyExample zyExample=new ZyExample();
		Criteria criteria = zyExample.createCriteria();
		criteria.andNameEqualTo(zyName);
		criteria.andXuuidEqualTo(xiUuid);
		List<Zy> zyList = zyMapper.selectByExample(zyExample);
		if(zyList!=null&&zyList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 902, null));
		
		//添加创建时间信息
		zyCustom.setCreateime(System.currentTimeMillis());
		
		//补全简码
		String[] headString = PinYin4jUtils.getHeadByString(zyName);
		String shortcode = StringUtils.join(headString,"");
		zyCustom.setShortcode(shortcode.toLowerCase());
		
		//补全专业编码
		String zycode = PinYin4jUtils.hanziToPinyin(zyName, "");
		zyCustom.setZycode(zycode);
		
		//添加专业信息
		zyMapper.insert(zyCustom);
	}

	@Override
	public void updateZy(Long uuid, ZyCustom zyCustom) throws Exception {
		//非空判断
		checkNull(zyCustom);
		
		//修改的专业信息必须存在
		Zy zy = zyMapper.selectByPrimaryKey(uuid);
		if(zy==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 903, null));
		
		//修改的专业所属的系信息必须存在
		Long xiUuid=zy.getXuuid();
		Xi xi = xiMapper.selectByPrimaryKey(xiUuid);
		if(xi==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 901, null));
		
		//若进行修改（即专业或系发生了修改），则不允许与存在的专业重复
		String updateZyName=zyCustom.getName();
		Long updateXiUuid=zyCustom.getXuuid();
		String zyName=zy.getName();
		if(!updateZyName.equals(zyName)||!updateXiUuid.equals(xiUuid)){
			ZyExample zyExample=new ZyExample();
			Criteria criteria = zyExample.createCriteria();
			criteria.andNameEqualTo(updateZyName);
			criteria.andXuuidEqualTo(updateXiUuid);
			List<Zy> zyList = zyMapper.selectByExample(zyExample);
			if(zyList!=null&&zyList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 904, null));
			
			//修改专业名称
			zy.setName(updateZyName);
			//修改系名称
			zy.setXuuid(updateXiUuid);

		}
		//修改创建时间
		Long updateCreatetime=zyCustom.getCreateime();
		if(updateCreatetime!=null)
			zy.setCreateime(updateCreatetime);
		
		//更新专业信息
		zyMapper.updateByPrimaryKey(zy);
	}

	@Override
	public void deleteZy(Long uuid) throws Exception {
		//删除的专业信息必须存在
		Zy zy = zyMapper.selectByPrimaryKey(uuid);
		if(zy==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 905, null));
		
		//如果该专业存在课程，则不允许删除
		KcZyExample kcZyExample=new KcZyExample();
		KcZyExample.Criteria kcZyCriteria = kcZyExample.createCriteria();
		kcZyCriteria.andZyuuidEqualTo(uuid);
		List<KcZy> kcZyList = kcZyMapper.selectByExample(kcZyExample);
		if(kcZyList!=null&&kcZyList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 913, null));
		
		//删除专业信息
		zyMapper.deleteByPrimaryKey(uuid);
	}
	
	private void checkNull(ZyCustom zyCustom) throws Exception{
		if(!MyUtil.isNotNullAndEmptyByTrim(zyCustom.getName())){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"专业名称"}));
		}
		if(zyCustom.getXuuid()==null){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"系名称"}));
		}
	}

}

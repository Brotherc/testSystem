package ytk.base.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.ClassEbo;
import ytk.base.dao.mapper.ClassMapper;
import ytk.base.dao.mapper.ClassMapperCustom;
import ytk.base.dao.mapper.SysuserMapper;
import ytk.base.dao.mapper.ZyMapper;
import ytk.base.pojo.po.Class;
import ytk.base.pojo.po.ClassExample;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.SysuserExample;
import ytk.base.pojo.po.Zy;
import ytk.base.pojo.po.ZyExample;
import ytk.base.pojo.po.ZyExample.Criteria;
import ytk.base.pojo.vo.ClassCustom;
import ytk.base.pojo.vo.ClassQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.util.MyUtil;
import ytk.util.UUIDBuild;

public class ClassEbi implements ClassEbo{

	@Autowired
	private ClassMapperCustom classMapperCustom;
	@Autowired
	private ClassMapper classMapper;
	@Autowired
	private ZyMapper zyMapper;
	@Autowired
	private SysuserMapper sysuserMapper;
	
	@Override
	public List<ClassCustom> findClassList(ClassQueryVo classQueryVo)
			throws Exception {
		return classMapperCustom.findClassList(classQueryVo);
	}

	@Override
	public int findClassListCount(ClassQueryVo classQueryVo) throws Exception {
		return classMapperCustom.findClassListCount(classQueryVo);
	}

	@Override
	public ClassCustom findClassByUuid(String uuid) throws Exception {
		ClassQueryVo classQueryVo=new ClassQueryVo();
		ClassCustom classCustom=new ClassCustom();
		classCustom.setUuid(uuid);
		classQueryVo.setClassCustom(classCustom);
		List<ClassCustom> classList = classMapperCustom.findClassList(classQueryVo);
		if(classList!=null&&classList.size()>0)
			return classList.get(0);
		return null;
	}

	@Override
	public void deleteClass(String uuid) throws Exception{
		//删除的班级信息必须存在
		Class classCustom = classMapper.selectByPrimaryKey(uuid);
		if(classCustom==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1501, null));
		
		//如果删除的班级存在学生，则不允许删除
		SysuserExample sysuserExample=new SysuserExample();
		SysuserExample.Criteria sysuserCriteria = sysuserExample.createCriteria();
		sysuserCriteria.andClassuuidEqualTo(uuid);
		List<Sysuser> sysuserList = sysuserMapper.selectByExample(sysuserExample);
		if(sysuserList!=null&&sysuserList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1504, null));
		
		//删除班级
		classMapper.deleteByPrimaryKey(uuid);		
	}

	@Override
	public void addClass(ClassCustom classCustom, int i) throws Exception {
		//非空校验
		if(classCustom.getNjuuid()==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"年级"}));
		if(!MyUtil.isNotNullAndEmptyByTrim(classCustom.getZyname()))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"专业名称"}));		
		
		//专业信息必须存在
		ZyExample zyExample=new ZyExample();
		Criteria zyCriteria = zyExample.createCriteria();
		zyCriteria.andNameEqualTo(classCustom.getZyname());
		List<Zy> zyList = zyMapper.selectByExample(zyExample);
		if(zyList==null||zyList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1502, null));
		Zy zy = zyList.get(0);
		
		//添加的班级不允许重复
		ClassExample classExample=new ClassExample();
		ClassExample.Criteria classCriteria = classExample.createCriteria();
		classCriteria.andClassnameEqualTo(i+"");
		classCriteria.andNjuuidEqualTo(classCustom.getNjuuid());
		classCriteria.andZyuuidEqualTo(zy.getUuid());
		List<Class> classList = classMapper.selectByExample(classExample);
		if(classList!=null&&classList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1503, null));
		
		//设置uuid
		String uuid = UUIDBuild.getUUID();
		classCustom.setUuid(uuid);
		classCustom.setClassname(i+"");
		classCustom.setZyuuid(zy.getUuid());
		
		//添加班级
		classMapper.insert(classCustom);
	}

}

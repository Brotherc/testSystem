package ytk.base.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;

import ytk.base.business.XiEbo;
import ytk.base.dao.mapper.SysuserMapper;
import ytk.base.dao.mapper.XiMapper;
import ytk.base.dao.mapper.ZyMapper;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.SysuserExample;
import ytk.base.pojo.po.Xi;
import ytk.base.pojo.po.XiExample;
import ytk.base.pojo.po.XiExample.Criteria;
import ytk.base.pojo.po.Zy;
import ytk.base.pojo.po.ZyExample;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.pojo.vo.XiCustom;
import ytk.base.pojo.vo.XiQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.util.MyUtil;

public class XiEbi implements XiEbo{
	
	@Autowired
	private XiMapper xiMapper;
	@Autowired
	private ZyMapper zyMapper;
	@Autowired
	private SysuserMapper sysuserMapper;
	
	@Override
	public List<Xi> findXiList() throws Exception {
		return xiMapper.selectByExample(new XiExample());
	}

	@Override
	public List<Xi> findXiList(XiQueryVo xiQueryVo) throws Exception {
		XiExample xiExample=new XiExample();
		Criteria criteria = xiExample.createCriteria();
		XiCustom xiCustom=xiQueryVo.getXiCustom();
		if(xiCustom!=null){
			Long uuid=xiCustom.getUuid();
			if(uuid!=null&&!uuid.equals("")){
				criteria.andUuidEqualTo(uuid);
			}
			String name=xiCustom.getName();
			if(name!=null&&!name.trim().equals("")){
				criteria.andNameEqualTo(name.trim());
				System.out.println(name);
			}
			Long createtimeMin=xiCustom.getCreatetimeMin();
			if(createtimeMin!=null&&!createtimeMin.equals("")){
				criteria.andCreatetimeGreaterThanOrEqualTo(createtimeMin);
				System.out.println(createtimeMin);
			}
			Long createtimeMax=xiCustom.getCreatetimeMax();
			if(createtimeMax!=null&&!createtimeMax.equals("")){
				criteria.andCreatetimeLessThanOrEqualTo(createtimeMax+86400000-1);
				System.out.println(createtimeMax);
			}
		}
		PageQuery pageQuery = xiQueryVo.getPageQuery();
		if(pageQuery!=null){
			int pageNum=pageQuery.getPageQuery_currPage();
			int pageSize=pageQuery.getPageQuery_pageSize();
			PageHelper.startPage(pageNum, pageSize);
		}
		return xiMapper.selectByExample(xiExample);
	}
	
	@Override
	public Xi findXiByUuid(Long uuid) throws Exception {
		return xiMapper.selectByPrimaryKey(uuid);
	}
	
	@Override
	public int findXiListCount(XiQueryVo xiQueryVo) throws Exception {
		XiExample xiExample=new XiExample();
		Criteria criteria = xiExample.createCriteria();
		XiCustom xiCustom=xiQueryVo.getXiCustom();
		if(xiCustom!=null){
			Long uuid=xiCustom.getUuid();
			if(uuid!=null&&!uuid.equals(""))
				criteria.andUuidEqualTo(uuid);
			String name=xiCustom.getName();
			if(name!=null&&!name.trim().equals(""))
				criteria.andNameEqualTo(name.trim());
			Long createtimeMin=xiCustom.getCreatetimeMin();
			if(createtimeMin!=null&&!createtimeMin.equals(""))
				criteria.andCreatetimeGreaterThanOrEqualTo(createtimeMin);
			Long createtimeMax=xiCustom.getCreatetimeMax();
			if(createtimeMax!=null&&!createtimeMax.equals(""))
				criteria.andCreatetimeLessThanOrEqualTo(createtimeMax+86400000-1);
		}
		return xiMapper.countByExample(xiExample);
	}

	@Override
	public void addXi(XiQueryVo xiQueryVo) throws Exception {
		
		XiCustom xiCustom = xiQueryVo.getXiCustom();
		
		//非空判断
		checkNull(xiCustom);
	
		//添加的系不能与已存在的重复
		XiExample xiExample=new XiExample();
		Criteria criteria = xiExample.createCriteria();
		criteria.andNameEqualTo(xiCustom.getName());
		List<Xi> xiList = xiMapper.selectByExample(xiExample);
		if(xiList!=null&&xiList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 801, null));
		
		//为当前系信息添加创建日期
		xiCustom.setCreatetime(System.currentTimeMillis());
		
		xiMapper.insert(xiCustom);
	}

	@Override
	public void updateXi(Long uuid, XiCustom xiCustom) throws Exception {
		checkNull(xiCustom);
		
		//修改的系信息必须存在
		Xi xi = findXiByUuid(uuid);
		if(xi==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 803, null));
		
		//若对系信息进行了修改，则不允许与已存在的系信息重复
		String updateName = xiCustom.getName();
		Long updateCreatetime = xiCustom.getCreatetime();
		String name=xi.getName();
		Long createtime = xi.getCreatetime();
		if(!name.equals(updateName)){
			XiExample xiExample=new XiExample();
			Criteria criteria = xiExample.createCriteria();
			criteria.andNameEqualTo(updateName);
			List<Xi> xiList = xiMapper.selectByExample(xiExample);
			if(xiList!=null&&xiList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 802, null));
			xi.setName(updateName);
		}
		if(updateCreatetime!=null&&!updateCreatetime.equals(createtime))
			xi.setCreatetime(updateCreatetime);
		
		//更新系信息
		xiMapper.updateByPrimaryKey(xi);
	}

	@Override
	public void deleteXi(Long uuid) throws Exception {
		//删除的系信息必须存在
		Xi xi = xiMapper.selectByPrimaryKey(uuid);
		if(xi==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 804, null));
		
		//如果该系存在专业，则不允许删除
		ZyExample zyExample=new ZyExample();
		ZyExample.Criteria zyCriteria = zyExample.createCriteria();
		zyCriteria.andXuuidEqualTo(uuid);
		List<Zy> zyList = zyMapper.selectByExample(zyExample);
		if(zyList!=null&&zyList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 805, null));
		
		//如果存在单位为该系的教师或学生，则不允许删除
		SysuserExample sysuserExample=new SysuserExample();
		SysuserExample.Criteria sysuserCriteria = sysuserExample.createCriteria();
		sysuserCriteria.andXuuidEqualTo(uuid);
		List<Sysuser> sysuserList = sysuserMapper.selectByExample(sysuserExample);
		if(sysuserList!=null&&sysuserList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 806, null));
		
		//删除系信息
		xiMapper.deleteByPrimaryKey(uuid);
	}

	private void checkNull(XiCustom xiCustom) throws Exception{
		String name = xiCustom.getName();
		if(!MyUtil.isNotNullAndEmptyByTrim(name))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"系名称"}));
	}
}

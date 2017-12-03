package ytk.base.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;

import ytk.base.business.NjEbo;
import ytk.base.dao.mapper.NjMapper;
import ytk.base.pojo.po.Nj;
import ytk.base.pojo.po.NjExample;
import ytk.base.pojo.po.NjExample.Criteria;
import ytk.base.pojo.vo.NjQueryVo;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.util.MyUtil;

public class NjEbi implements NjEbo{

	@Autowired
	private NjMapper njMapper;
	
	@Override
	public List<Nj> findNjListByStatus(int status) throws Exception {
		NjExample njExample=new NjExample();
		Criteria criteria = njExample.createCriteria();
		criteria.andStatusEqualTo(status);
		return njMapper.selectByExample(njExample);
	}

	@Override
	public int findNjListCount(NjQueryVo njQueryVo) throws Exception {
		NjExample njExample=new NjExample();
		Criteria criteria = njExample.createCriteria();
		Nj nj = njQueryVo.getNj();
		if(nj!=null){
			Long uuid=nj.getUuid();
			if(uuid!=null&&!uuid.equals(""))
				criteria.andUuidEqualTo(uuid);
			String name=nj.getNjnane();
			if(name!=null&&!name.trim().equals(""))
				criteria.andNjnaneEqualTo(name);
			Integer status = nj.getStatus();
			if(status!=null&&!status.equals(""))
				criteria.andStatusEqualTo(status);
		}
		return njMapper.countByExample(njExample);
	}

	@Override
	public List<Nj> findNjList(NjQueryVo njQueryVo) throws Exception {
		NjExample njExample=new NjExample();
		Criteria criteria = njExample.createCriteria();
		Nj nj = njQueryVo.getNj();
		if(nj!=null){
			Long uuid=nj.getUuid();
			if(uuid!=null&&!uuid.equals(""))
				criteria.andUuidEqualTo(uuid);
			String name=nj.getNjnane();
			if(name!=null&&!name.trim().equals(""))
				criteria.andNjnaneEqualTo(name);
			Integer status = nj.getStatus();
			if(status!=null&&!status.equals(""))
				criteria.andStatusEqualTo(status);
		}
		PageQuery pageQuery = njQueryVo.getPageQuery();
		if(pageQuery!=null){
			int pageNum=pageQuery.getPageQuery_currPage();
			int pageSize=pageQuery.getPageQuery_pageSize();
			PageHelper.startPage(pageNum, pageSize);
		}
		return njMapper.selectByExample(njExample);
	}

	@Override
	public void addNj(Nj nj) throws Exception {
		//非空判断
		String name = nj.getNjnane();
		if(!MyUtil.isNotNullAndEmptyByTrim(name))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"年级名称"}));
		
		//判断是否年级重复
		NjExample njExample=new NjExample();
		Criteria njCriteria = njExample.createCriteria();
		njCriteria.andNjnaneEqualTo(name);
		List<Nj> njList = njMapper.selectByExample(njExample);
		if(njList!=null&&njList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1601, null));
		
		//设置状态为正常
		nj.setStatus(1);
		//添加年级
		njMapper.insert(nj);
	}

	@Override
	public void updateNjStatus(Long uuid,Integer status) throws Exception {
		//修改的年级信息必须存在
		Nj nj = njMapper.selectByPrimaryKey(uuid);
		if(nj==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1602, null));
		
		//修改年级状态
		nj.setStatus(status);
		//更新年级信息
		njMapper.updateByPrimaryKey(nj);
	}

}

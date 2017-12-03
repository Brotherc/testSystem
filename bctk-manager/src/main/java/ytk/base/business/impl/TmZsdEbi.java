package ytk.base.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.TmZsdEbo;
import ytk.business.dao.mapper.TmZsdMapper;
import ytk.business.pojo.po.TmZsd;
import ytk.business.pojo.po.TmZsdExample;

public class TmZsdEbi implements TmZsdEbo{
	
	@Autowired
	private TmZsdMapper tmZsdMapper;
	
	@Override
	public List<TmZsd> findTmZsdListByTuuid(String tuuid)
			throws Exception {
			TmZsdExample tmZsdExample=new TmZsdExample();
			TmZsdExample.Criteria tmZsdCriteria = tmZsdExample.createCriteria();
			tmZsdCriteria.andTmuuidEqualTo(tuuid);
			return tmZsdMapper.selectByExample(tmZsdExample); 
	}
}

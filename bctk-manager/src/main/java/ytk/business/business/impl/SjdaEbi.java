package ytk.business.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.business.business.SjdaEbo;
import ytk.business.dao.mapper.SjdaMapper;
import ytk.business.pojo.po.Sjda;
import ytk.business.pojo.po.SjdaExample;
import ytk.business.pojo.po.SjdaExample.Criteria;

public class SjdaEbi implements SjdaEbo{

	@Autowired
	private SjdaMapper sjdaMapper;
	
	@Override
	public Sjda findSjdaBySjXitmid(String sjXitmId) throws Exception {
		SjdaExample sjdaExample=new SjdaExample();
		Criteria criteria = sjdaExample.createCriteria();
		criteria.andSjxitmidEqualTo(sjXitmId);
		List<Sjda> sjdaList = sjdaMapper.selectByExample(sjdaExample);
		if(sjdaList==null||sjdaList.size()<1)
			return null;
		return sjdaList.get(0);
	}

}

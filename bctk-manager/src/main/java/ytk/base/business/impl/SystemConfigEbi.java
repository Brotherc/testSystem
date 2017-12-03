package ytk.base.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.SystemConfigEbo;
import ytk.base.dao.mapper.DictinfoMapper;
import ytk.base.pojo.po.Dictinfo;
import ytk.base.pojo.po.DictinfoExample;
import ytk.base.pojo.po.DictinfoExample.Criteria;

public class SystemConfigEbi implements SystemConfigEbo{

	@Autowired
	private DictinfoMapper dictinfoMapper;

	@Override
	public List<Dictinfo> findSysuserTypeDictinfo() throws Exception {
		return findDictinfoByTypeCode("s01");
	}

	@Override
	public List<Dictinfo> findDictinfoByTypeCode(String typeCode)
			throws Exception {
		DictinfoExample dictinfoExample=new DictinfoExample();
		Criteria criteria = dictinfoExample.createCriteria();
		criteria.andTypecodeEqualTo(typeCode);
		return dictinfoMapper.selectByExample(dictinfoExample);
	}

	@Override
	public List<Dictinfo> findNdTypeDictinfo() throws Exception {
		return findDictinfoByTypeCode("002");
	}
	
	@Override
	public Dictinfo  findDictinfoByDictcode(String typecode,String dictcode) throws Exception{
		DictinfoExample dictinfoExample = new DictinfoExample();
		DictinfoExample.Criteria criteria = dictinfoExample.createCriteria();
		criteria.andDictcodeEqualTo(dictcode);
		criteria.andTypecodeEqualTo(typecode);
		List<Dictinfo> list = dictinfoMapper.selectByExample(dictinfoExample);
		if(list!=null && list.size()==1){
			return list.get(0);
		}
		return null;
		
	}
}

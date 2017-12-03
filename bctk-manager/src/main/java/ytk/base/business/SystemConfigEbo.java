package ytk.base.business;

import java.util.List;

import ytk.base.pojo.po.Dictinfo;

public interface SystemConfigEbo {
	
	//根据数据字典类型查询对应详情
	public List<Dictinfo>findDictinfoByTypeCode(String typeCode) throws Exception;
	
	//根据数据字典类型(用户类别)查询对应详情
	public List<Dictinfo>findSysuserTypeDictinfo() throws Exception;
	
	//根据数据字典类型(题目难度类别)查询对应详情
	public List<Dictinfo>findNdTypeDictinfo() throws Exception;
	
	//根据typeocde和dictcode获取单个字典明细
	public Dictinfo  findDictinfoByDictcode(String typecode,String dictcode) throws Exception;
}

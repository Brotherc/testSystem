package ytk.base.business;

import java.util.List;

import ytk.business.pojo.po.TmZsd;

public interface TmZsdEbo {
	//根据题目uuid查询知识点信息
	public List<TmZsd> findTmZsdListByTuuid(String tuuid) throws Exception;
}

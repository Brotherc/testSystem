package ytk.business.business;

import ytk.business.pojo.po.Sjda;

public interface SjdaEbo {
	//根据试卷系题目id查询试卷答案
	public Sjda findSjdaBySjXitmid(String sjXitmId) throws Exception;
}

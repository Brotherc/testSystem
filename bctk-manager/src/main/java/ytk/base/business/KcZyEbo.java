package ytk.base.business;

import java.util.List;

import ytk.base.pojo.vo.KcZyCustom;
import ytk.base.pojo.vo.KcZyQueryVo;

public interface KcZyEbo {
	//根据条件查询课程专业信息
	public List<KcZyCustom> findKcZyList(KcZyQueryVo kcZyQueryVo) throws Exception;
}

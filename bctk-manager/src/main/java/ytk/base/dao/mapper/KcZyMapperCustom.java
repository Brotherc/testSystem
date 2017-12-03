package ytk.base.dao.mapper;

import java.util.List;


import ytk.base.pojo.vo.KcZyCustom;
import ytk.base.pojo.vo.KcZyQueryVo;

public interface KcZyMapperCustom {
	public List<KcZyCustom> findKcZyList(KcZyQueryVo kcZyQueryVo) throws Exception;
}
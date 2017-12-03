package ytk.base.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.KcZyEbo;
import ytk.base.dao.mapper.KcZyMapperCustom;
import ytk.base.pojo.vo.KcZyCustom;
import ytk.base.pojo.vo.KcZyQueryVo;

public class KcZyEbi implements KcZyEbo{

	@Autowired
	private KcZyMapperCustom kcZyMapperCustom;
	
	@Override
	public List<KcZyCustom> findKcZyList(KcZyQueryVo kcZyQueryVo)
			throws Exception {
		return kcZyMapperCustom.findKcZyList(kcZyQueryVo);
	}
}

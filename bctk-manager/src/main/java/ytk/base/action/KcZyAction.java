package ytk.base.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.KcZyEbo;
import ytk.base.pojo.vo.KcZyCustom;
import ytk.base.pojo.vo.KcZyQueryVo;

@Controller
public class KcZyAction {
	@Autowired
	private KcZyEbo kcZyEbo;
	
	@RequestMapping("/kczy/jsonList")
	public @ResponseBody List<KcZyCustom> getKcZyJsonList(KcZyQueryVo kcZyQueryVo) throws Exception{
		return kcZyEbo.findKcZyList(kcZyQueryVo);
	}
	
}

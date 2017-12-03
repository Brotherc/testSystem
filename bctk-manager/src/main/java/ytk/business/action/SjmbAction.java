package ytk.business.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.business.business.SjmbEbo;
import ytk.business.pojo.po.Sjmb;

@Controller
public class SjmbAction {
	
	@Autowired
	private SjmbEbo sjmbEbo;
	
	@RequestMapping("/sjmb/jsonList")
	public @ResponseBody List<Sjmb> findSjmbJsonList() throws Exception{
		return sjmbEbo.findSjmbList();
	}
}

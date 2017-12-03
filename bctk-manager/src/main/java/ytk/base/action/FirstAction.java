package ytk.base.action;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.pojo.vo.Menu;
import ytk.base.pojo.vo.SysuserCustom;
import ytk.base.process.context.Config;

@Controller
public class FirstAction {

	@RequestMapping("/first")
	public String toFirst(){
		return "/base/first";
	}
	
	@RequestMapping("/welcome")
	public String toWelcome(){
		return "/base/welcome";
	}
	
	/**
	 * 获取菜单菜单，并转成json
	 */
	@RequestMapping("/usermenu")
	public @ResponseBody Menu usermenu(HttpSession session)throws Exception{
		SysuserCustom sysuserCustom=(SysuserCustom) session.getAttribute(Config.LOGINUSER_KEY);
		return sysuserCustom.getMenu();
	}
}

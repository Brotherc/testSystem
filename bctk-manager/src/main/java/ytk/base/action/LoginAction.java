package ytk.base.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ytk.base.business.StudentEbo;
import ytk.base.business.SysuserEbo;
import ytk.base.pojo.vo.StudentCustom;
import ytk.base.pojo.vo.SysuserCustom;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;

@Controller
public class LoginAction {
	
	@Autowired
	private SysuserEbo sysuserEbo;
	@Autowired
	private StudentEbo studentEbo;
	
	//跳转登录页面
	@RequestMapping("/login")
	public String toLogin(){
		return "/base/login";
	}

	//登录，进行身份校验，跳转首页
	@RequestMapping("/loginsubmit")
	public @ResponseBody SubmitResultInfo loginSubmit(String userid,String pwd,String validatecode,HttpSession session) throws Exception{
		String loginValidateCode=(String) session.getAttribute("validateCode");
		if(!loginValidateCode.equals(validatecode))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 113, null));
		SysuserCustom sysuserCustom = sysuserEbo.loginCheck(userid, pwd);
		//将查询出来的用户信息保存到session
		session.setAttribute(Config.LOGINUSER_KEY, sysuserCustom);
		session.setAttribute(Config.LOGINTYPE_KEY, "1");
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 107, new Object[]{sysuserCustom.getUsername()}));
	}
	
	//学生登录，进行身份校验，跳转首页
	@RequestMapping("/studentLoginsubmit")
	public @ResponseBody SubmitResultInfo studentLoginSubmit(String studentId,String validatecode,HttpSession session,HttpServletRequest request) throws Exception{
		String loginValidateCode=(String) session.getAttribute("validateCode");
		if(!loginValidateCode.equals(validatecode))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 113, null));
		StudentCustom student=studentEbo.loginCheck(studentId,request.getRemoteAddr());
		//将查询出来的用户信息保存到session
		session.setAttribute(Config.LOGINSTUDENT_KEY, student);
		session.setAttribute(Config.LOGINTYPE_KEY, "3");
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 107, new Object[]{student.getStudentName()}));
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) throws Exception{
		//session过期
		session.invalidate();
		return toLogin();
	}
}

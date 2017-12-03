package ytk.base.filter;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ytk.base.pojo.vo.Operation;
import ytk.base.pojo.vo.SysuserCustom;
import ytk.base.process.context.Config;
import ytk.base.process.result.ExceptionResultInfo;
import ytk.base.process.result.ResultInfo;
import ytk.util.ResourcesUtil;

/**
 * 权限拦截器
 * @author BrotherChun
 *
 */
public class PermissionInterceptor implements HandlerInterceptor {

	/**
	 * 权限拦截方法
	 */
	// 将异常信息转json
	private HttpMessageConverter<ExceptionResultInfo> jsonMessageConverter;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		//校验用户请求的地址是否是公开地址
		//获取用户请求的url
		String url = request.getRequestURI();
		System.out.println(url);
		//例如：/ytkproject/first.action
		
		//判断url是否属于公开地址，如果是公开地址放行
		//获取公开地址
		List<String> openurl_list = ResourcesUtil.gekeyList(Config.ANONYMOUS_ACTIONS);
		
		//校验请求的url是否在公开地址内
		for(String open_url:openurl_list){
			if(url.indexOf(open_url)>=0){
				return true;//如果是公开地址则放行
			}
		}
		
		//校验是否是公共权限
		//获取公共权限 地址
		List<String> commonurl_list = ResourcesUtil.gekeyList(Config.COMMON_ACTIONS);
		//校验请求的url是否在公共权限地址内
		for(String common_url:commonurl_list){
			if(url.indexOf(common_url)>=0){
				return true;//如果是公共权限 地址则放行
			}
		}
		
		//是否是用户的操作权限
		//从session中拿到用户的操作权限
		//获取session
		HttpSession session = request.getSession();
		//用户身份信息
		SysuserCustom sysuserCustom = (SysuserCustom)session.getAttribute(Config.LOGINUSER_KEY);
		List<Operation> operations = sysuserCustom.getOperationList();
		
		//校验请求的url是否在用户操作权限地址内
		for(Operation operation_index:operations){
			String operation = operation_index.getActionUrl();
			if(url.indexOf(operation)>=0){
				return true;//如果是用户的操作权限 地址则放行
			}
		}
		
		//提示用户无此操作权限
		//跳转到无此操作权限操作页面
		
		// 转成springmvc底层对象（就是对action方法的封装对象，只有一个方法）
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 取出方法
		Method method = handlerMethod.getMethod();

		// 判断方法是否返回json
		// 只要方法上有responsebody注解表示返回json
		// 查询method是否有responsebody注解
		ResponseBody responseBody = AnnotationUtils.findAnnotation(method,
				ResponseBody.class);
		if (responseBody != null) {
			// 将异常信息转json输出
			ExceptionResultInfo exceptionResultInfo = createNoPowerException();
			HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
			jsonMessageConverter.write(exceptionResultInfo, MediaType.APPLICATION_JSON, outputMessage);
		}
		else 
			request.getRequestDispatcher("/WEB-INF/jsp/base/refuse.jsp").forward(request, response);
				
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
    private ExceptionResultInfo createNoPowerException(){
    	ResultInfo resultInfo = new ResultInfo();
		resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
		resultInfo.setMessage("您没有权限执行该操作");
		return new ExceptionResultInfo(resultInfo);
    }
    
	public HttpMessageConverter<ExceptionResultInfo> getJsonMessageConverter() {
		return jsonMessageConverter;
	}

	public void setJsonMessageConverter(
			HttpMessageConverter<ExceptionResultInfo> jsonMessageConverter) {
		this.jsonMessageConverter = jsonMessageConverter;
	}
}

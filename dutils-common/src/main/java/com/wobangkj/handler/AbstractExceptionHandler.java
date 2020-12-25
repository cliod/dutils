package com.wobangkj.handler;

import com.wobangkj.api.Response;
import com.wobangkj.bean.Res;
import com.wobangkj.enums.ResultEnum;
import com.wobangkj.exception.AccessException;
import com.wobangkj.exception.AuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 全局异常处理
 *
 * @author @cliod
 * @since 4/28/20 3:03 PM
 * package: com.wobangkj.jzlw.handler
 */
@Slf4j
@ResponseBody
public abstract class AbstractExceptionHandler implements com.wobangkj.handler.ExceptionHandler {

	protected int code = Response.FAIL_CODE;

	/**
	 * 自定义授权异常
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(AuthorizeException.class)
	public Object authorizeException(AuthorizeException e) {
		Res r = Res.empty();
		r.setStatus(code);
		r.setMsg(ResultEnum.NOT_AUTH.getMsg());
		r.setErr(e.getAuth());
		return r;
	}

	/**
	 * 自定义访问异常
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(AccessException.class)
	public Object accessException(AccessException e) {
		Res r = Res.empty();
		r.setStatus(code);
		r.setMsg(e.getMessage());
		r.setErr(e.getAccess());
		return r;
	}

	/**
	 * 接口未找到异常
	 * <p>课接收参数: HttpServletRequest request, HttpServletResponse response</p>
	 * <p>
	 * 这个异常的捕抓需要配置:
	 * <code>
	 * spring.mvc.throw-exception-if-no-handler-found=true
	 * spring.resources.add-mappings=false
	 * </code>
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public Object noHandlerFoundException(NoHandlerFoundException e) {
		Res r = Res.empty();
		r.setStatus(HttpStatus.NOT_FOUND.value());
		r.setMsg(HttpStatus.NOT_FOUND.getReasonPhrase());
		r.setErr(String.format("接口不存在(%s):" + e.getRequestURL(), e.getHttpMethod() + "方法"));
		return r;
	}

	/**
	 * 非法参数异常
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public Object illegalArgumentException(IllegalArgumentException e) {
		Res r = Res.empty();
		r.setStatus(code);
		r.setMsg(e.getMessage());
		return r;
	}

	/**
	 * 丢失Servlet请求参数异常
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Object missingServletRequestParameterException(MissingServletRequestParameterException e) {
		Res r = Res.empty();
		r.setStatus(HttpStatus.BAD_REQUEST.value());
		r.setMsg(HttpStatus.BAD_REQUEST.getReasonPhrase());
		r.setErr(String.format("指定参数类型%s,名称%s的方式不存在", e.getParameterType(), e.getParameterName()));
		return r;
	}

	/**
	 * HTTP请求方法不支持异常
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Object httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		Res r = Res.empty();
		r.setStatus(code);
		r.setMsg(e.getMessage());
		r.setErr(e.getMethod());
		return r;
	}

	/**
	 * 方法参数类型不匹配异常
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Object methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		Res r = Res.empty();
		r.setStatus(code);
		r.setMsg(e.getMessage());
		r.setErr(e.getLocalizedMessage());
		return r;
	}

	/**
	 * 方法参数无效异常
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object methodArgumentNotValidException(MethodArgumentNotValidException e) {
		Res r = Res.empty();
		r.setStatus(code);
		r.setMsg(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
		return r;
	}

	/**
	 * 参数绑定异常
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(BindException.class)
	public Object bindException(BindException e) {
		Res r = Res.empty();
		r.setStatus(code);
		r.setMsg(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
		return r;
	}

	/**
	 * 空指针异常
	 *
	 * @return 结果消息
	 */
	@ExceptionHandler(NullPointerException.class)
	public Object nullPointerException(NullPointerException e) {
		Res r = Res.empty();
		r.setStatus(code);
		r.setMsg("空指针异常");
		r.setErr(e.getMessage());
		return r;
	}

	/**
	 * 其他异常
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(Exception.class)
	public Object exceptionHandler(@NotNull Exception e) {
		Res r = Res.empty();
		String msg = e.getMessage();
		if (StringUtils.isEmpty(msg)) {
			msg = ResultEnum.ERROR.getMsg();
		}
		r.setStatus(500);
		r.setMsg(msg);
		return r;
	}

	/**
	 * 异常处理
	 *
	 * @param e        异常
	 * @param request  请求
	 * @param response 响应
	 * @return 结果消息
	 */
	@Override
	@ExceptionHandler(Throwable.class)
	public abstract Object handler(Throwable e, HttpServletRequest request, HttpServletResponse response);
}

package com.wobangkj.handler;

import com.wobangkj.api.Response;
import com.wobangkj.bean.Res;
import com.wobangkj.exception.AppException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

	@Getter
	protected int code = 217;

	/**
	 * 自定义访问异常
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(AppException.class)
	public Object appException(AppException e) {
		Res r = Res.empty();
		r.setStatus(e.getCode());
		r.setMsg(e.getMessage());
		log.warn(e.getMessage());
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
		r.setErr(String.format("接口不存在(%s方法: %s)", e.getHttpMethod(), e.getRequestURL()));
		// 这里不需要警告 o.s.web.servlet.PageNotFound 会进行警告
		// log.warn(e.getMessage());
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
		r.setStatus(this.getCode());
		r.setMsg(e.getMessage());
		log.error(e.getMessage());
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
		log.warn(e.getMessage());
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
		r.setStatus(this.getCode());
		r.setMsg(e.getMessage());
		r.setErr(String.format("该请求不支持%s方式，仅支持: %s", e.getMethod(), e.getSupportedHttpMethods()));
		log.warn(e.getMessage());
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
		r.setStatus(this.getCode());
		r.setMsg(e.getMessage());
		MethodParameter mp = e.getParameter();
		r.setErr(String.format("方法参数类型不匹配, 方法名: %s, 参数：%s", e.getName(), mp.toString()));
		log.warn(e.getMessage());
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
		r.setStatus(this.getCode());
		MethodParameter mp = e.getParameter();
		BindingResult br = e.getBindingResult();
		FieldError fe = br.getFieldError();
		if (Objects.nonNull(fe)) {
			// bindingResult 报的错误
			r.setMsg(fe.getDefaultMessage());
			log.warn(fe.getDefaultMessage());
		} else {
			List<ObjectError> errs = br.getAllErrors();
			r.setMsg(errs.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.joining(",", "[", "]")));
			log.error(e.getMessage());
		}
		log.warn(mp.toString());
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
		r.setStatus(this.getCode());
		FieldError fe = e.getFieldError();
		if (Objects.nonNull(fe)) {
			// bindingResult 报的错误
			r.setMsg(fe.getDefaultMessage());
			log.warn(fe.getDefaultMessage());
		} else {
			r.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
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
		r.setStatus(this.getCode());
		r.setMsg("空指针异常");
		log.error(e.getMessage(), e);
		return r;
	}

	/**
	 * 其他运行时异常
	 *
	 * @param e 异常
	 * @return 结果消息
	 */
	@ExceptionHandler(RuntimeException.class)
	public Object runtimeExceptionHandler(@NotNull RuntimeException e) {
		Res r = Res.empty();
		r.setStatus(500);
		r.setMsg(e.getMessage());
		log.error(r.getMsg());
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
		Res r;
		String msg = e.getMessage();
		if (StringUtils.isEmpty(msg)) {
			r = Response.ERR;
		} else {
			r = Res.empty();
			r.setStatus(500);
			r.setMsg(msg);
		}
		log.error(r.getMsg());
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

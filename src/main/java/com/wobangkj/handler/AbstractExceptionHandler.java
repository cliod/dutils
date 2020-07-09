package com.wobangkj.handler;

import com.wobangkj.bean.Res;
import com.wobangkj.enums.ResultEnum;
import com.wobangkj.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口统一异常捕获
 *
 * @author cliod
 * @since 7/8/20 4:44 PM
 */
@Slf4j
public abstract class AbstractExceptionHandler implements ExceptionHandler {

    @Override
    public abstract Res handler(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable e);

    public Res handler(@NotNull AppException e) {
        String msg = e.getMessage();
        if (StringUtils.isEmpty(msg)) {
            msg = ResultEnum.ERROR.getMsg();
        }
        Res r = new Res();
        r.setMsg(msg);
        r.setCode(217);
        if (e instanceof AuthorizeException) {
            AuthorizeException e1 = (AuthorizeException) e;
            log.warn(ResultEnum.NOT_AUTH.getMsg(), e1.getAuth());
            r.setErr(e1.getAuth());
        } else if (e instanceof NotFoundException) {
            NotFoundException e1 = (NotFoundException) e;
            log.info("{}处理", e1.getAccess());
            r.setErr(e1.getAccess());
        } else if (e instanceof NullObjectException) {
            NullObjectException e1 = (NullObjectException) e;
            log.info("{}处理", e1.getAccess());
            r.setErr(e1.getAccess());
        } else if (e instanceof AccessException) {
            AccessException e1 = (AccessException) e;
            log.info("{}处理", e1.getAccess());
            r.setErr(e1.getAccess());
        } else {
            log.info(e.getMessage());
        }
        return r;
    }

    public Res handler(@NotNull IllegalArgumentException e) {
        Res res = Res.of(217, e.getMessage());
        process(res);
        return res;
    }

    public void process(Res res) {
    }

    public void process(Res res, HttpServletRequest request, HttpServletResponse response) {
    }
}
package com.luoben.glmall.order.interceptor;

import com.luoben.common.constant.AuthServerConstant;
import com.luoben.common.vo.MemberResponseVO;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberResponseVO> loginUser=new InheritableThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String uri  = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean statusMatch = antPathMatcher.match("/order/order/status/**", uri);//根据订单号查询订单
        boolean payedMatch = antPathMatcher.match("/payed/notify", uri);//支付宝异步回调
        boolean queryPayMatch = antPathMatcher.match("/queryPayStatus", uri); //支付宝查询支付状态接口

        if(statusMatch || payedMatch||queryPayMatch){
            return true;
        }

        MemberResponseVO attribute = (MemberResponseVO)request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        if(attribute!=null){
            loginUser.set(attribute);
            return true;
        }else {
            request.getSession().setAttribute("msg","请先登录！");
            response.sendRedirect("http://auth.glmall.com/login.html");

            //没登陆 去登录
            return false;
        }

    }
}

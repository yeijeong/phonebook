package com.nasa.phonebook.login;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/phonebook/*")
@Component
public class LoginCheckFilter implements Filter {
	
	  private static final String[] whiteList = {"/phonebook/login", "/phonebook/loginrequest", "/logout", "/phonebook/signup", "/phonebook/signrequest", "/phonebook/idcheck", "/phonebook/checkph", "/css/*"};
	 
	  private final Logger logger = LoggerFactory.getLogger(this.getClass());
	  
	  @Override
	  public void init(FilterConfig filterConfig) throws ServletException {
	    logger.info("log filter init");
	  }
	
	  @Override 
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	      throws IOException, ServletException {
	
	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    String requestURI = httpRequest.getRequestURI();
	
	    HttpServletResponse httpResponse = (HttpServletResponse) response;
	
	    try {
	    	 logger.info("인증 체크 필터 시작 {}", requestURI);
	      if (isLoginCheckPath(requestURI)) {
	    	  logger.info("인증 체크 필터 시작 {}", requestURI);
	    	  HttpSession session = httpRequest.getSession(false);
	    	  if ((session == null || session.getAttribute("loginid") == null)) {
	    		  logger.info("인증 체크 필터 시작 {}", requestURI);
//	          httpResponse.sendRedirect("phonebook/login?redirectURL=" + requestURI);
	    		  httpResponse.sendRedirect("redirect:/phonebook/login");
	    		  return; // 여기가 중요, 미인증 사용자는 다음으로 진행하지 않고 끝!
	    	  }
	      }
	      chain.doFilter(request, response);
	    } catch (Exception e) {
	      throw e; // 예외 로깅 가능하지만, 톰캣까지 예외를 보내야 함
	    }finally {
	    	logger.info("인증 체크 필터 종료 {} ", requestURI);
	    }
	
	  }//doFilter()end
	
	  private boolean isLoginCheckPath(String requestURI) {
	    return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
	  }
	  
	  @Override
	  public void destroy() {
	    logger.info("log filter destroy");
	  }

}
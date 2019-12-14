/******************************************************************************
 
 *  Purpose: To create filter for API gateway of note service and user service
 *         
 *          
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   09-12-2019
 *
 ******************************************************************************/
package com.birdgelabz.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;


@Component
public class ZuulFilter  extends com.netflix.zuul.ZuulFilter{
	
	private static Logger log = LoggerFactory.getLogger(ZuulFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

		return null;
	}

}

package cn.aaron.ablog.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
*TODO
*@author Aaron
*@date 2017年2月23日
*/
@Aspect
@Component
public class LogAspect {
	
	private Logger log = Logger.getLogger(LogAspect.class);

	@Around("execution(* cn.aaron.ablog.dao.*.* (..))")
	public Object  log(ProceedingJoinPoint joinPoint) throws Throwable{
		long start = System.currentTimeMillis();
		String method = joinPoint.getSignature().getDeclaringTypeName();
		Object object = joinPoint.proceed(joinPoint.getArgs());
		long end = System.currentTimeMillis();
		log.info(method+" use time:"+(end-start));
		return object;
	}
	
}

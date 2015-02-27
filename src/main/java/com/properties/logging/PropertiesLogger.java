/**
 * 
 */
package com.properties.logging;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//import org.springframework.util.StopWatch;

/**
 * @author ryanmcdonald
 * 
 *         TODO: tweak for debug, etc.
 */
@Aspect
@Component
public class PropertiesLogger {

	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesLogger.class);

	@Pointcut("within(com.properties..*)")
	private void logAll() {
	}

	/**
	 * 
	 * @param point
	 */
	@Before("logAll()")
	public void logBefore(JoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		logger.debug(method.toGenericString());
	}

	/**
	 * 
	 * @param point
	 */
	@After("logAll()")
	public void logAfter(JoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		logger.debug(method.toGenericString());
	}

	/**
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	// @Around("execution(* com.properties.fileio.*.*(..))")
	// public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
	// final Logger logger = LoggerFactory.getLogger(joinPoint.getTarget()
	// .getClass().getName());
	// Object retVal = null;
	//
	// try {
	// StringBuffer startMessageStringBuffer = new StringBuffer();
	//
	// startMessageStringBuffer.append("Start method ");
	// startMessageStringBuffer.append(joinPoint.getSignature().getName());
	// startMessageStringBuffer.append("(");
	//
	// Object[] args = joinPoint.getArgs();
	// for (int i = 0; i < args.length; i++) {
	// startMessageStringBuffer.append(args[i]).append(",");
	// }
	// if (args.length > 0) {
	// startMessageStringBuffer.deleteCharAt(startMessageStringBuffer
	// .length() - 1);
	// }
	//
	// startMessageStringBuffer.append(")");
	//
	// logger.trace(startMessageStringBuffer.toString());
	//
	// StopWatch stopWatch = new StopWatch();
	// stopWatch.start();
	//
	// retVal = joinPoint.proceed();
	//
	// stopWatch.stop();
	//
	// StringBuffer endMessageStringBuffer = new StringBuffer();
	// endMessageStringBuffer.append("Finish method ");
	// endMessageStringBuffer.append(joinPoint.getSignature().getName());
	// endMessageStringBuffer.append("(..); execution time: ");
	// endMessageStringBuffer.append(stopWatch.getTotalTimeMillis());
	// endMessageStringBuffer.append(" ms;");
	//
	// logger.trace(endMessageStringBuffer.toString());
	// } catch (Throwable ex) {
	// StringBuffer errorMessageStringBuffer = new StringBuffer();
	//
	// // Create error message
	// logger.error(errorMessageStringBuffer.toString(), ex);
	//
	// throw ex;
	// }
	//
	// return retVal;
	// }

	/**
	 * 
	 */
	@Pointcut("within(com.properties..*)|| execution(* java.util.concurrent.Callable+.call())|| execution(* java.lang.Runnable+.run())")
	private void logErrors() {
	}

	/**
	 * 
	 * @param ex
	 */
	@AfterThrowing(pointcut = "logErrors()", throwing = "ex")
	public void logUncaughtException(Exception ex) {
		logger.error("@EXCEPTION: " + ex.getMessage().toUpperCase());

	}

}

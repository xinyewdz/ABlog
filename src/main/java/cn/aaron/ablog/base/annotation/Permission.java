package cn.aaron.ablog.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *TODO
 *@author Aaron
 *@date 2016年9月8日
 */

@Retention(RetentionPolicy.RUNTIME)   //解释如下
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Permission {
	
	boolean value() default false;

}

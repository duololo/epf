package com.zfer.kit.excel.importor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.FIELD })
public @interface ExcelVOAnnotation {

	/**
	 * export excel display name
	 */
	public abstract String name();

	/**
	 * filed excel column name,like A,B,C,D...
	 */
	public abstract String column();

	/**
	 * tip message
	 */
	public abstract String prompt() default "";

	/**
	 * only select drop down content
	 */
	public abstract String[] combo() default {};

	/**
	 * export template, has head name
	 */
	public abstract boolean isExport() default true;

}

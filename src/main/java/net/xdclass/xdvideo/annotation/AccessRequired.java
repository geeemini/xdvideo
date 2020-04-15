package net.xdclass.xdvideo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/14 12:03
 * 有内鬼，中止交易
 */
@Target(ElementType.METHOD)// 可用在方法名上
@Retention(RetentionPolicy.RUNTIME)// 运行时有效
public @interface AccessRequired {
}

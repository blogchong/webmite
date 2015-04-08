package com.blogchong.webmite.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:  blogchong
 * @Blog:    www.blogchong.com
 * @Mailbox: blogchong@163.com
 * @QQGroup: 191321336
 * @Weixin:  blogchong
 * @version: 2015年1月14日 下午8:46:02
 * @Des: 判断邮箱格式
 */

public class JudgeEmail {

	public static boolean judgeEmail(String email) {

		// 匹配该行模板
		String regex = "^([a-zA-Z0-9]|[._])+@([a-zA-Z0-9_-])+([a-zA-Z0-9]|[._])+\\.(com|cn|net|org|gov|edu|int|biz|info|name|tv|cc|pro|coop|aero|museum|CC|TV|Club)$";

		Pattern pattern = Pattern.compile(regex);
		Matcher line = pattern.matcher(email);

		if (line.find()) {
			return true;
		} else {
			return false;
		}

	}

	// 判断是否为qq邮箱
	public static boolean judgeEmail2(String email) {

		// 匹配该行模板
		String regex = "^([a-zA-Z0-9]|[._])+@qq\\.com$";

		Pattern pattern = Pattern.compile(regex);
		Matcher line = pattern.matcher(email);

		if (line.find()) {
			return true;
		} else {
			return false;
		}

	}

	public static void main(String[] args) {
		String email = "dkj_dkdl@126.dd.com";
		if (judgeEmail(email)) {
			System.out.println("OK");
		} else {
			System.out.println("NO");
		}
	}

}

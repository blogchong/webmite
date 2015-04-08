package com.blogchong.webmite.company;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @Author:  blogchong
 * @Blog:    www.blogchong.com
 * @Mailbox: blogchong@163.com
 * @QQGroup: 191321336
 * @Weixin:  blogchong
 * @CreateTime：2015年1月14日 下午3:00:02
 * @Description: 从网页源码中解析邮箱
 */

public class AnalyzeData {

	// 从源码中分析地址、电话、手机、电子邮箱
	public static String analyzeData(String url) {

		WebClient webClient = new WebClient();

		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);

		try {

			HtmlPage page = webClient.getPage(url);
			String content = page.asText();

			String regex_email = "\\s*(\\w+@\\S+\\.\\S*com|cn|net|org|gov|edu|int|biz|info|name|tv|cc|pro|coop|aero|museum|CC|TV|Club).*";
			Matcher mt_email = Pattern.compile(regex_email).matcher(content);

			if (mt_email.find()) {
				return mt_email.group(1);
			} else {
				return null;
			}

		} catch (FailingHttpStatusCodeException e) {
			// e.printStackTrace();
		} catch (MalformedURLException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			webClient.closeAllWindows();
		}

		return null;
	}

	public static void main(String[] args) {
		String url = "http://shanghai.myjob.com/company/11-2378094572.html";
		System.out.println(analyzeData(url));
	}

}

package com.blogchong.webmite.company;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.blogchong.webmite.util.MacroDef;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @Author:  blogchong
 * @Blog:    www.blogchong.com
 * @Mailbox: blogchong@163.com
 * @QQGroup: 191321336
 * @Weixin:  blogchong
 * @CreateTime：2015年1月14日 下午1:43:28
 * @Description: 从智联招聘中解析出公司名
 */

public class GetCompanyFromZhilian {

	// 从智联解析公司
	public static Set<String> getCompanyFromZl(String urls) {

		Set<String> set = new HashSet<String>();

		WebClient webClient = new WebClient();

		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);

		String content = null;

		int count = 0;

		String url = null;

		while (true) {

			// 构造url
			if (count == 0) {
				url = urls;
			} else {
				// 解析后面页面的url
				url = packUrl(urls, count);
			}

			// 若是url不符合跳出
			if (url == null) {
				break;
			}

			try {

				HtmlPage page = webClient.getPage(url);

				content = page.asText();

				// 判断前一页跟后一页是否相同，若相同，说明已经到最后，跳出
				if (content.equals(MacroDef.ZL_SRC)) {
					break;
				} else {
					MacroDef.ZL_SRC = content;
					count++;
				}

				String[] lines = content.split("\n");

				for (String line : lines) {

					// 匹配该行模板
					String regex = "^\\s+\\S+\\s+\\S+\\s+\\S+\\s+.*(\\d{1,2}-\\d{1,2})$";

					Pattern pattern = Pattern.compile(regex);
					Matcher mt = pattern.matcher(line);

					// 首先将不符合基本格式的过滤
					if (mt.find()) {

						String note = mt.group();

						String[] fields = note.split("\t");
						if (fields.length >= 2) {
							set.add(fields[1]);
							// System.out.println(fields[1]);
						}
					}

				}

			} catch (FailingHttpStatusCodeException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				webClient.closeAllWindows();
			}
		}

		// 最后将标识置为原先默认值
		MacroDef.ZL_SRC = MacroDef.ZL_FLAG;

		return set;
	}

	// url拆分
	public static String packUrl(String url_tmp, int count) {

		String url = null;

		String[] url_tmps = url_tmp.split("sm=0&");

		if (url_tmps.length >= 2) {

			String[] url_tmps2 = url_tmps[0].split("jl=");

			if (url_tmps2.length == 2) {
				url = url_tmps2[0].toLowerCase()
						+ "jl="
						+ url_tmps2[1]
						+ "sm=0&"
						+ "sf=0&st=99999&cs=1&isadv=1&sg=a021204b5ef145448301208cd5b38c68&p="
						+ (count + 1);
			}
		}

		return url;
	}

	public static void main(String[] args) {
		String url = "http://sou.zhaopin.com/jobs/searchresult.ashx?in=140000%3B140100%3B140200%3B201400%3B120200%3B170000%3B120800%3B121100%3B210600%3B120700&jl=%E5%8C%97%E4%BA%AC&sm=0&p=1&sf=0&st=99999&cs=1&isadv=1";
		Set<String> set = GetCompanyFromZhilian.getCompanyFromZl(url);
		for (String str : set) {
			System.out.println(str);
		}
	}

}

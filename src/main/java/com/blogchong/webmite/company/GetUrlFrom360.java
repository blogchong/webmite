package com.blogchong.webmite.company;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.blogchong.webmite.util.MacroDef;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @Author:  blogchong
 * @Blog:    www.blogchong.com
 * @Mailbox: blogchong@163.com
 * @QQGroup: 191321336
 * @Weixin:  blogchong
 * @CreateTime：2015年1月14日 下午12:50:17
 * @Description:从360搜索解析url
 */

public class GetUrlFrom360 {

	/*
	 * 入参为公司名，返回一个json
	 */
	@SuppressWarnings("deprecation")
	public JSONObject getUrlFrom360(String companyName) {

		// JSON格式数据解析对象
		JSONObject obj = new JSONObject();

		String str = java.net.URLEncoder.encode(companyName);

		String url = "http://www.haosou.com/s?ie=utf-8&shb=1&src=360sou_newhome&q="
				+ str;

		WebClient webClient = new WebClient();

		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);

		// JSON格式数据解析对象
		JSONArray jar_is = new JSONArray();
		JSONArray jar_not = new JSONArray();

		try {

			HtmlPage page = webClient.getPage(url);

			List<HtmlAnchor> list = page.getAnchors();

			for (HtmlAnchor htmlAnchor : list) {

				String url_tmp = htmlAnchor.getHrefAttribute();

				// 匹配该行模板
				String regex = "http://.*";

				Pattern pattern = Pattern.compile(regex);
				Matcher line = pattern.matcher(url_tmp);

				// 首先将不符合基本格式的过滤
				if (line.find()) {

					// 匹配该行模板
					String regex2 = "http://.*haoso|360|baidu|google|bing|leidian.*";

					Pattern pattern2 = Pattern.compile(regex2);
					Matcher line2 = pattern2.matcher(line.group());

					// 将带搜索引擎专有页面过滤
					if (!line2.find()) {

						// 匹配该行模板
						String regex3 = "http://www\\..*\\.((cn|com|net|org)/)$";

						Pattern pattern3 = Pattern.compile(regex3);
						Matcher line3 = pattern3.matcher(line.group());

						// 判断是否为首页
						if (line3.find()) {
							jar_is.put(line.group());
						} else {
							jar_not.put(line.group());
						}

					}
				}
			}

			page.cleanUp();

		} catch (FailingHttpStatusCodeException e) {
			// e.printStackTrace();
		} catch (MalformedURLException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			webClient.closeAllWindows();
		}

		obj.put(MacroDef.INDEX_FLAG, jar_is);
		obj.put(MacroDef.OTHER_FLAG, jar_not);

		return obj;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		GetUrlFrom360 getUrlFrom360 = new GetUrlFrom360();

		JSONObject obj = getUrlFrom360.getUrlFrom360("北京场道市政工程有限公司");

		Set<String> keys = obj.keySet();

		for (String key : keys) {

			JSONArray jar = obj.getJSONArray(key);

			for (int i = 0; i < jar.length(); i++) {

				System.out.println(key + ":" + jar.getString(i));

			}
		}
	}

}

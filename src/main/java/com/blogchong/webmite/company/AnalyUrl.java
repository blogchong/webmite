package com.blogchong.webmite.company;

import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import com.blogchong.webmite.util.MacroDef;

/**
 * @Author:  blogchong
 * @Blog:    www.blogchong.com
 * @Mailbox: blogchong@163.com
 * @QQGroup: 191321336
 * @Weixin:  blogchong
 * @CreateTime：2015年1月14日 下午3:09:01
 * @Description: 过滤出无企业网站的公司
 */

public class AnalyUrl {

	@SuppressWarnings("unchecked")
	public static JSONObject analyUrl(String url) {

		JSONObject obj_ret = new JSONObject();

		Set<String> set = GetCompanyFromZhilian.getCompanyFromZl(url);

		for (String str : set) {

			GetUrlFrom360 getUrlFrom360 = new GetUrlFrom360();

			JSONObject obj = getUrlFrom360.getUrlFrom360(str);

			Set<String> keys = obj.keySet();

			// 是否有首页的标识
			boolean index_flag = false;
			// 其他是否为空标识
			boolean other_flag = false;

			// 存储非首页网址
			JSONArray jar_not = new JSONArray();

			for (String key : keys) {

				JSONArray jar = obj.getJSONArray(key);

				if (key.equals(MacroDef.INDEX_FLAG) && jar.length() == 0) {
					index_flag = true;
				} else if (key.equals(MacroDef.OTHER_FLAG) && jar.length() == 0) {
					other_flag = true;
				}

				for (int i = 0; i < jar.length(); i++) {

					if (key.equals(MacroDef.OTHER_FLAG)) {
						jar_not.put(jar.getString(i));
					}

				}
			}

			if (index_flag && !other_flag) {
				obj_ret.put(str, jar_not);
			}

		}

		return obj_ret;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String url = "http://sou.zhaopin.com/jobs/searchresult.ashx?in=210500%3B160400%3B160000%3B160500%3B160200%3B300100%3B160100%3B160600&jl=%E5%8C%97%E4%BA%AC&sm=0&p=1&sf=0&st=99999&cs=1&isadv=1";
		JSONObject obj = analyUrl(url);

		Set<String> keys = obj.keySet();

		for (String key : keys) {
			System.out.println(key + ":(" + obj.get(key) + ")");
		}
	}

}

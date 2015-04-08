package com.blogchong.webmite.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.blogchong.webmite.util.JudgeEmail;
import com.blogchong.webmite.util.OptFile;

/**
 * @Author:  blogchong
 * @Blog:    www.blogchong.com
 * @Mailbox: blogchong@163.com
 * @QQGroup: 191321336
 * @Weixin:  blogchong
 * @CreateTime：2015年1月14日 下午5:40:20
 * @Description: 起始类
 */

public class CompanyStart {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		List<String> listUrl = OptFile
				.readFile("D:\\projects\\webmite\\file\\read.txt");

		// 统计
		int count = 1;

		for (String url : listUrl) {

			List<String> listEmail = new ArrayList<String>();
			List<String> listQqEmail = new ArrayList<String>();
			List<String> listQqOtherEmail = new ArrayList<String>();

			JSONObject obj = AnalyUrl.analyUrl(url);

			Set<String> keys = obj.keySet();

			for (String key : keys) {

				String companyName = key;
				try {

					JSONArray jar_url = obj.getJSONArray(key);

					JSONArray jarOhter = new JSONArray();
					JSONArray jarQq = new JSONArray();

					for (int i = 0; i < jar_url.length(); i++) {

						String email_tmp = AnalyzeData.analyzeData(jar_url
								.getString(i));

						if (email_tmp != null
								&& JudgeEmail.judgeEmail(email_tmp)) {
							if (JudgeEmail.judgeEmail2(email_tmp)) {
								jarQq.put(email_tmp);
							} else {
								jarOhter.put(email_tmp);
							}
						}

					}

					if (jarOhter.length() != 0 && jarQq.length() == 0) {
						listEmail
								.add(companyName + "==>" + jarOhter.toString());
					} else if (jarOhter.length() != 0 && jarQq.length() != 0) {
						listQqEmail.add(companyName + "==>" + jarQq.toString());
						listQqOtherEmail.add(companyName + "==>"
								+ jarOhter.toString());
					} else if (jarOhter.length() == 0 && jarQq.length() != 0) {
						listQqEmail.add(companyName + "==>" + jarQq.toString());
					}

				} catch (Exception e) {
				}

			}

			// 写入
			OptFile.writeFile("D:\\projects\\webmite\\file\\ot\\ot_email_"
					+ count + ".log", listEmail);
			// 写入
			OptFile.writeFile("D:\\projects\\webmite\\file\\qq\\qq_email_"
					+ count + ".log", listQqEmail);

			// 写入
			OptFile.writeFile("D:\\projects\\webmite\\file\\qo\\qo_email_"
					+ count + ".log", listQqOtherEmail);

			count++;
		}

	}

}

package com.blogchong.webmite.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/** 
 * @Author:  blogchong
 * @Blog:    www.blogchong.com
 * @Mailbox: blogchong@163.com
 * @QQGroup: 191321336
 * @Weixin:  blogchong
 * @CreateTime：2015年1月14日 下午1:00:57 
 * @Description: 生成的文件处理
 */

public class OptFile {

	// 读文件
	public static List<String> readFile(String path) {
		List<String> list = new ArrayList<String>();

		File fd = new File(path);
		String line = "";
		try {

			FileReader reader = new FileReader(fd);
			BufferedReader br = new BufferedReader(reader);
			while ((line = br.readLine()) != null) {
				list.add(line);
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 写文件
	public static void writeFile(String path, List<String> list) {
		File fd = new File(path);
		try {

			FileWriter writer = new FileWriter(fd);
			BufferedWriter bw = new BufferedWriter(writer);

			for (String line : list) {
				bw.write(line);
				bw.newLine();
			}

			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		List<String> list = readFile("E:\\Java_Web\\projects\\webmite\\file\\read.txt");
		writeFile("E:\\Java_Web\\projects\\webmite\\file\\email.txt", list);
	}

}

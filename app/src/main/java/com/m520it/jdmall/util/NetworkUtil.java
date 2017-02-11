package com.m520it.jdmall.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class NetworkUtil {
	
	public static String doGet(String urlPath){
		String result="";
		try {
			URL url=new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			InputStream inputStream = conn.getInputStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
			String line=null;
			while ((line=reader.readLine())!=null) {
				result+=line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String doPost(String urlPath,Map<String, String> paramsMap){
		String result="";
		try {
			URL url=new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			String params="";
			for (Map.Entry<String, String> entry:paramsMap.entrySet()) {
				params+=("&"+entry.getKey()+"="+entry.getValue());
			}
			params=params.substring(1);
			conn.getOutputStream().write(params.getBytes());
			if (conn.getResponseCode()==200) {
				InputStream is = conn.getInputStream();
				BufferedReader reader=new BufferedReader(new InputStreamReader(is));
				String line=null;
				while ((line=reader.readLine())!=null) {
					result+=line;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}

package com.ziqun.crawleritem.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class JsoupUtil {

	/**
	 * 支持所有站点Https请求
	 * https://stackoverflow.com/questions/2793150/using-java-net-urlconnection-
	 * to-fire-and-handle-http-requests/2793153#2793153
	 */
	static {
		TrustManager[] trustAllCertificates = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null; // Not relevant.
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
				// Do nothing. Just allow them all.
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
				// Do nothing. Just allow them all.
			}
		} };

		HostnameVerifier trustAllHostnames = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true; // Just allow them all.
			}
		};

		try {
			System.setProperty("jsse.enableSNIExtension", "false");
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCertificates, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);
		} catch (GeneralSecurityException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Document connect(String url, Map<String, String> headers, Map<String, String> params, boolean useProxy) {
		int timestamp = (int) (new Date().getTime() / 1000);
		String authHeader = authHeader("ZF201712242879fAzefS", "3ecde279cc8c45b585a9f3270bc53d0b", timestamp);
		Map<String, String> headerMap = new HashMap<>();
		if (headers != null) {
			headerMap.putAll(headers);
		}
		try {
			Thread.sleep(200);
			if (useProxy) {
//				headerMap.put("Proxy-Authorization", authHeader);
				if (params != null) {
					return Jsoup.connect(url).data(params).proxy("forward.xdaili.cn", 80).validateTLSCertificates(false)
							.header("Proxy-Authorization", authHeader).ignoreContentType(true).post();
				}
				return Jsoup.connect(url).proxy("forward.xdaili.cn", 80).validateTLSCertificates(false)
						.header("Proxy-Authorization", authHeader).ignoreContentType(true).get();
			} else {
				if (params != null) {
					Connection connection = Jsoup.connect(url);
					for (Entry<String, String> entry : params.entrySet()) {
						connection.data(entry.getKey(), entry.getValue());
					}
					return connection.headers(headerMap).ignoreContentType(true).post();
				}
				return Jsoup.connect(url).headers(headerMap).ignoreContentType(true).get();
			}

		} catch (Exception e) {
			System.out.println(url);
			e.printStackTrace();
			return null;
		}
	}

	public static String requestJson(String url, Map<String, String> headers, boolean useProxy) {
		int timestamp = (int) (new Date().getTime() / 1000);
		String authHeader = authHeader("ZF201712242879fAzefS", "3ecde279cc8c45b585a9f3270bc53d0b", timestamp);
		Map<String, String> headerMap = new HashMap<>();
		if (headers != null) {
			headerMap.putAll(headers);
		}

		try {
			Thread.sleep(200);
			if (useProxy) {
				return Jsoup.connect(url).proxy("forward.xdaili.cn", 80).validateTLSCertificates(false)
						.header("Proxy-Authorization", authHeader).headers(headerMap).ignoreContentType(true).execute()
						.body();
			} else {

				return Jsoup.connect(url).headers(headerMap).ignoreContentType(true).execute().body();
			}

		} catch (Exception e) {
			System.out.println(url);
			e.printStackTrace();
			return null;
		}
	}

	public static String authHeader(String orderno, String secret, int timestamp) {
		// 拼装签名字符串
		String planText = String.format("orderno=%s,secret=%s,timestamp=%d", orderno, secret, timestamp);

		// 计算签名
		String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(planText).toUpperCase();

		// 拼装请求头Proxy-Authorization的值
		String authHeader = String.format("sign=%s&orderno=%s&timestamp=%d", sign, orderno, timestamp);
		return authHeader;
	}

	public static void main(String[] args) {
		String planText = "";
		String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(planText).toUpperCase();
	}

}

package cn.wenhaha.spider.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Collar {

	
	/**
	 * 返回源码
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getSource(String url,String charset) throws IOException {
		CloseableHttpClient createDefault = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent",BrowserUA.getComputerUA());
		CloseableHttpResponse response = createDefault.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String code = EntityUtils.toString(entity, charset);
		createDefault.close();
		return code;
	}
	
	

	
	
	/*
	*//**
	 * 获取代理浏览器对象
	 * 
	 * @return
	 *//*
	private static CloseableHttpClient getHttpClients() {
		List<String> proxyList = getConfig("proxy");
		Random random = new Random();
		String[] ip = proxyList.get(random.nextInt(proxyList.size() - 1))
				.split(":");
		int port = Integer.parseInt(ip[1]);
		HttpHost proxy = new HttpHost(ip[0], port);
		System.out.println("代理ip:" + ip[0] + ":" + port);
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(
				proxy);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setRoutePlanner(routePlanner).build();

		return HttpClients.createDefault();
	}
	*/

	
}

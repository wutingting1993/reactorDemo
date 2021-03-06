package hotel;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleHttpClientDemo {
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString) {

			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString) {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] {trustManager}, null);
		return sc;
	}

	public static void main(String[] args) throws Exception {
		//		tujia();
		xiecheng();

	}

	private static void xiecheng() throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException {
		ObjectMapper objectMapper = new ObjectMapper();

		String s = "__VIEWSTATEGENERATOR:DB1FBB6D\n"
			+ "page:1\n"
			+ "cityId:28\n"
			+ "keyword:成都莱恩度假套房公寓\n"
			+ "k1:成都莱恩度假套房公寓\n"
			+ "txtkeyword:成都莱恩度假套房公寓\n"
			+ "cityName:成都\n"
			+ "StartTime:2019-10-10\n"
			+ "DepTime:2019-10-11\n"
			+ "operationtype:NEWHOTELORDER\n"
			+ "IsOnlyAirHotel:F\n"
			+ "OrderBy:99\n"
			+ "IsCanReserve:F\n"
			+ "prepay:F\n"
			+ "promotion:F\n"
			+ "priceRange:-2\n"
			+ "htlPageView:0\n"
			+ "hotelType:F\n"
			+ "hasPKGHotel:F\n"
			+ "requestTravelMoney:F\n"
			+ "isusergiftcard:F\n"
			+ "useFG:F\n"
			+ "htlFrom:hotellist\n"
			+ "isHuaZhu:False\n"
			+ "HideIsNoneLogin:T\n"
			+ "isfromlist:T\n"
			+ "ubt_price_key:htl_search_result_promotion";

		for (int i = 1; i < 2; i++) {
			Map<String, String> map = Arrays.stream(s.split("\n")).collect(Collectors.toMap(x -> x.split(":")[0], x -> x.split(":")[1]));
			map.put("page", "15");
			String result = sendHttps("https://hotels.ctrip.com/Domestic/Tool/AjaxHotelList.aspx", map, "utf-8");//请求地址，参数，编码集
			System.out.println(result);
			Thread.sleep(3000);//文明爬取，要睡几秒
		}
	}

	private static void tujia() throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException {
		ObjectMapper objectMapper = new ObjectMapper();

		//https://ct.ctrip.com/hoteldomestic/hoteldetail?hotelid=3992855&sdate=2019-10-09&edate=2019-10-10&districtId=270&city=28&country=1&RoomNum=1
		for (int i = 1; i < 2; i++) {
			Map<String, String> map = new HashMap();
			map.put("cityid", "28");//城市id
			map.put("districtId", "270");//区域id
			map.put("pindex", i + "");//动态循环的分页
			map.put("pSize", "15");//每页记录数
			map.put("zoneId", "189");//热门商圈187,189
			map.put("checkIn", "2019-12-10");
			map.put("checkOut", "2019-12-11");
			map.put("vendorPid", "12666851");
			map.put("cid", "09031036411932847970");
			//cid=09031036411932847970

			//			map.put("pid", "1199589095");//热门商圈
			String result = sendHttps("https://m.ctrip.com/restapi/soa2/12455/prod/json/searchProduct?_fxpcqlniredt=09031133110201642129", map, "utf-8");//请求地址，参数，编码集
			Map resultMap = objectMapper.readValue(result, Map.class);
			System.out.println(objectMapper.writeValueAsString(resultMap));//debug分析返回json的格式
			List<Map> list = (List<Map>)resultMap.get("product");//获取需要的节点

			if (CollectionUtils.isEmpty(list)) {
				break;
			}
			for (Map map1 : list) {
				Hotel hotel = objectMapper.convertValue(map1, Hotel.class);

				System.out.println(objectMapper.writeValueAsString(hotel));
				if ("1199589095".equals(hotel.getPid())) {

				}
			}
			Thread.sleep(3000);//文明爬取，要睡几秒
		}
	}

	/**
	 * 模拟请求https
	 *
	 * @param url        资源地址
	 * @param map    参数列表
	 * @param encoding    编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String sendHttps(String url, Map<String, String> map, String encoding) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
		String body = "";
		//采用绕过验证的方式处理https请求
		SSLContext sslcontext = createIgnoreVerifySSL();

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			.register("http", PlainConnectionSocketFactory.INSTANCE)
			.register("https", new SSLConnectionSocketFactory(sslcontext))
			.build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);

		//创建自定义的httpclient对象
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
		//        CloseableHttpClient client = HttpClients.createDefault();

		//创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);

		//装填参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		//设置参数到请求对象中
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		System.out.println("请求地址：" + url);
		System.out.println("请求参数：" + nvps.toString());

		//设置header信息
		//指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		//===================

		//执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		//获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			//按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		//释放链接
		response.close();
		return body;
	}

	/**
	 * 模拟请求http
	 *
	 * @param url        资源地址
	 * @param map    参数列表
	 * @param encoding    编码
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String sendHttp(String url, Map<String, String> map, String encoding) throws ParseException, IOException {
		String body = "";

		//创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		//创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);

		//装填参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		//设置参数到请求对象中
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		System.out.println("请求地址：" + url);
		System.out.println("请求参数：" + nvps.toString());

		//设置header信息
		//指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		//执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		//获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			//按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		//释放链接
		response.close();
		return body;
	}
}

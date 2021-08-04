package cn.bintools.cloud.openapi.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * HttpCommonUtils
 *
 * @author xiaohuang
 * @time 2020/10/15 14:13
 * <p>
 * http调用工具类
 */
public class HttpCommonUtils {


    private static final String URL_PARAM_CONNECT_FLAG = "&";

    private static final String EMPTY = "";

    private static MultiThreadedHttpConnectionManager connectionManager = null;

    private static int connectionTimeOut = 25000;

    private static int socketTimeOut = 25000;

    private static int maxConnectionPerHost = 20;

    private static int maxTotalConnections = 20;

    private static HttpClient client;

    static {
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
        connectionManager.getParams().setSoTimeout(socketTimeOut);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        client = new HttpClient(connectionManager);
    }


    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * 请求第三方请求参数
     *
     * @param url    第三方地址
     * @param params 请求参数
     * @return 返回状态信息
     */
    public static String postRequest(String url,String params) {
        //通过接口获取token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if (response.getStatusCode().value() == HttpStatus.SC_OK){
            //请求正常
            return response.getBody();
        }else{
            //第三方请求网络异常
            throw new IllegalArgumentException("请求["+url+"]地址网络异常");
        }
    }

    /**
     * GET请求第三方请求参数
     *
     * @param url    第三方地址
     * @return 返回状态信息
     */
    public static String getRequest(String url){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        if (responseEntity.getStatusCode().value() == HttpStatus.SC_OK){
            //请求正常
            return responseEntity.getBody();
        }else{
            //第三方请求网络异常
            throw new IllegalArgumentException("请求["+url+"]地址网络异常");
        }
    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                System.out.println(baos.toString());
                System.out.println(new String(baos.toByteArray(), "UTF-8"));
                return new String(baos.toByteArray(), "UTF-8");
//                return baos.toString();//注：在本地调用生产搜索引擎获取数据时乱码     生产服务器没有问题
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url1  发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doGetJson(String url1, String param) {
        String line = "";
        try {
            URL url = new URL(url1 + "?" + param);
            URLConnection connect = url.openConnection();
            //强制造型HttpUrlConnection
            HttpURLConnection httpConnect = (HttpURLConnection) connect;
            httpConnect.setRequestMethod("GET");
            //设置连接超时的时间
            httpConnect.setConnectTimeout(3000);
            //设置速去数据超时时间
            httpConnect.setReadTimeout(3000);
            //设置接收的数据类型
            httpConnect.setRequestProperty("Accept-Charset", "utf-8");
            //设置可以接受序列化的java对象
            httpConnect.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpConnect.setDoInput(true);//默认为true，可以不设置

            httpConnect.setDoOutput(true);//设置客户端可以给服务器提交数据，默认为false，post方法必须设置为true
            //post方法不允许使用缓存,必须设置为false
            httpConnect.setUseCaches(false);
            httpConnect.getOutputStream().write(param.getBytes());
            int code = httpConnect.getResponseCode();
            if (code == HttpsURLConnection.HTTP_OK) {
                InputStream is = httpConnect.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                line = br.readLine();
                if (line != null) {
                    line = br.readLine();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            line = "";
        } catch (IOException e) {
            e.printStackTrace();
            line = "";
        }
        return line;
    }

}

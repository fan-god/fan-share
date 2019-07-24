package com.fan.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * httpCilent工具类
 *
 * @author Achievo
 */
@Slf4j
public class HttpCilentUtil {
    private static final String charset = ConstantFan.CHARSET;

    private static HttpClient httpClient;

    static {

        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        // 将最大连接数增加到200
        httpClientConnectionManager.setMaxTotal(400);
        // 将每个路由基础的连接增加到20
        httpClientConnectionManager.setDefaultMaxPerRoute(40);

        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder = requestBuilder.setConnectTimeout(10000);// 10秒
        requestBuilder = requestBuilder.setConnectionRequestTimeout(60000);// 60秒
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setConnectionManager(httpClientConnectionManager);
        builder.setDefaultRequestConfig(requestBuilder.build());
        httpClient = builder.build();
    }

    /**
     * 获取HttpClient
     *
     * @return
     */
    private static HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * get请求工具
     *
     * @param url
     * @return
     */
    public static String doGet(String url, String params) {
        log.info(String.format("%s?%s", url, params));
        try {
            HttpClient client = getHttpClient();
            HttpGet get = new HttpGet(url.concat("?").concat(params));

            HttpResponse response = client.execute(get);
            String result = EntityUtils.toString(response.getEntity(), charset);
            log.info("response:" + result);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return result;
            }
        } catch (Exception e) {
            log.error("HttpClientUtil doGet error{}:", e);
            return null;
        }
        return null;
    }

    /**
     * get请求工具
     *
     * @param url
     * @return
     */
    public static String doGet(String url, Map<String, String> params, Map<String, String> headers) {
        try {
            HttpClient client = getHttpClient();
            StringBuffer sb = new StringBuffer();
            String param = "";
            // 设置参数
            if (null != params && !params.isEmpty()) {
                for (String key : params.keySet()) {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
                param = sb.toString().replaceFirst("&", "");
            }
            log.info(String.format("%s?%s", url, param));
            HttpGet get;
            if (param.length() > 0) {
                get = new HttpGet(url.concat("?").concat(param));
            } else {
                get = new HttpGet(url);
            }
            //设置头部
            if (null != headers && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    get.setHeader(key, headers.get(key));
                }
            }
            HttpResponse response = client.execute(get);
            String result = EntityUtils.toString(response.getEntity(), charset);
            log.info("response:" + result);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return result;
            }
        } catch (Exception e) {
            log.error("HttpClientUtil doGet error{}:", e);
            return null;
        }
        return null;
    }

    /**
     * post请求工具
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static InputStream doPost(String url, byte[] bs, String serviceName, String host) {
        try {
            HttpClient client = getHttpClient();
            HttpPost post = new HttpPost(url);
            // 请求头部信息
            post.setHeader("Host", host);
            post.setHeader("Locations", serviceName);
            post.setHeader("Format", ConstantFan.PROTOBUF);
            post.setHeader("Content-Type", ConstantFan.CONTENT_TYPE);
            post.setEntity(new ByteArrayEntity(bs));
            HttpResponse response = client.execute(post);
            InputStream content = response.getEntity().getContent();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return content;
            }
        } catch (Exception e) {
            log.error("HttpClientUtil doPost error{}:", e);
            return null;
        }
        return null;
    }

    /**
     * post请求(用于key-value格式的参数)
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, "");
    }

    /**
     * post请求(用于key-value格式的参数)
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String, String> params, String logInfo) {
        String result = null;
        BufferedReader in = null;
        try {
            // 定义HttpClient
            HttpClient client = getHttpClient();
            // 实例化HTTP方法
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));

            // 设置参数
            List<NameValuePair> nvps = Lists.newArrayList();
            for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext(); ) {
                String name = iter.next();
                String value = String.valueOf(params.get(name));
                nvps.add(new BasicNameValuePair(name, value));
                //log.log.info("param:" + name + "-" + value);
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, charset));

            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), charset));
            StringBuffer sb = new StringBuffer();
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            result = sb.toString();
            log.info(logInfo.concat("response:").concat(result));
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 请求成功
                return result;
            }
        } catch (Exception e) {
            log.error("{}", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return result;
    }

    /**
     * 获取客户端ip
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * post请求工具
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String doPost(String url, String input, String serviceName, String host) {
        try {
            HttpClient client = getHttpClient();
            if (null == input) {
                input = "";
            }
            HttpPost post = new HttpPost(url);
            log.info("request==>" + url + "?" + input, false);
            // 请求头部信息
            post.setHeader("Host", host);
            post.setHeader("Locations", serviceName);
            post.setHeader("Format", ConstantFan.JSON);
            post.setHeader("Addition", ConstantFan.JSON);
            HttpEntity entity = new StringEntity(input, charset);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            String result = EntityUtils.toString(response.getEntity(), charset);
            log.info("response==>" + result, true);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED) {
                return result;
            }
        } catch (Exception e) {
            log.error("HttpClientUtil doPost error{}", e);
            return null;
        }
        return null;
    }

    /**
     * post请求工具
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, String> params, String body, Map<String, String> headers) {
        try {
            HttpClient client = getHttpClient();
            StringBuffer sb = new StringBuffer();
            for (String key : params.keySet()) {
                sb.append("&").append(key).append("=").append(params.get(key));
            }
            String param = sb.toString().replaceFirst("&", "");
            HttpPost post = new HttpPost(url.concat("?").concat(param));
            if (!headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    post.setHeader(key, headers.get(key));
                }
            }
            HttpEntity entity = new StringEntity(body, charset);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            String result = EntityUtils.toString(response.getEntity(), charset);
            log.info("response==>" + result, true);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED) {
                return result;
            }
        } catch (Exception e) {
            log.error("HttpClientUtil doPost error{}", e);
            return null;
        }
        return null;
    }

    /**
     * 当前请求获取数据体
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String getContent(HttpServletRequest request) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = request.getInputStream();
            isr = new InputStreamReader(is, ConstantFan.CHARSET);
            br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            if (StringUtils.isNotBlank(sb)) {
                return sb.toString();
            }
            return null;
        } catch (Exception e) {
            log.error("HttpClientUtil getContent error{}", e);
            return null;
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * 获取每次访问的请求
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return request;
        } catch (Exception e) {
            log.error("HttpClientUtil getRequest error{}:", e);
            return null;
        }
    }

    /**
     * 向页面输出
     * @param response
     * @param str
     */
    public static void printOut(HttpServletResponse response, String str) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(str);
        } catch (IOException e) {
            log.error("printOut error:{}", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}

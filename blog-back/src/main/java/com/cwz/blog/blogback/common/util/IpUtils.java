package com.cwz.blog.blogback.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/*
* 获取Ip
*/
public class IpUtils {
    // 获取该类对象的日志信息,slf4j日志记录器
    private static Logger LOG = LoggerFactory.getLogger(IpUtils.class);

    /*
     * 获取IP地址
     *
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
     * X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址.
     *
     * 详情可参考：https://blog.csdn.net/wangkai_123456/article/details/71055913
     *
     * X-Forwarded-For（XFF）是用来识别通过HTTP代理或负载均衡方式连接到Web服
     * 务器的客户端最原始的IP地址的HTTP请求头字段。
     *
     * Proxy-Client-IP/WL- Proxy-Client-IP : 这个一般是经过apache http服务器的请求才会有，用apache http
     * 做代理时一般会加上Proxy-Client-IP请求头，而WL-Proxy-Client-IP是他的weblogic插件加上的头。
     *
     * HTTP_CLIENT_IP : 有些代理服务器会加上此请求头。
     *
     * X-Real-IP : nginx代理一般会加上此请求头。
     *
     */
    public static String getIpAddr(HttpServletRequest request){
        String ip = null, unknown = "unknown", seperator = ",";
        int maxLength = 15;

        try {
            ip = request.getHeader("x-forwarded-for");

            /*
            * equalsIgnoreCase将此String与另一个String进行比较，不考虑大小写。如果两个字符串的长度相等，并且两个字
            * 符串中的相应字符都相等（忽略大小写），则认为这两个字符串是相等的。
            */
            if(StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)){
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 ||unknown.equalsIgnoreCase(ip)){
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)){
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)){
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)){
                ip = request.getRemoteAddr();
            }

        }catch (Exception e){
            // 日志记录，error级别的输出
            LOG.error("IpUtils ERROR : ", e);
        }

        // 使用代理，则获取第一个IP地址
        if(StringUtils.isEmpty(ip) && ip.length() > maxLength){
            //  indexOf()方法可返回某个指定的字符串值在字符串中首次出现的位置
            int idx = ip.indexOf(seperator);

            // substring()法用于提取字符串中介于两个指定下标之间的字符
            if(idx > 0){
                ip = ip.substring(0, idx);
            }
        }

        return ip;
    }


    /*
    * 获取ip地址
    */
    public static String getIpAddr(){
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        return getIpAddr(request);
    }
}

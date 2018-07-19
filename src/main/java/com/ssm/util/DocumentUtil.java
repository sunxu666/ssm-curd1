package com.ssm.util;


import com.ssm.entity.ProxyIp;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 用于获取Document对象的工具类
 */
public class DocumentUtil {

    private static int i;

    private static final Logger logger = LoggerFactory.getLogger(DocumentUtil.class);
    public static Document getDocument(String url){

        //建立连接
        Connection connection = Jsoup.connect(url);
        //设置请求的超时时间，避免由于部分请求的时间过长带来的爬取效率降低
        //用当前IP爬取超过两秒还没有爬取到，就断开当前请求。换个IP继续请求
        connection.timeout(2000);
        //设置合法的userAgent
        connection.userAgent(Constants.UserAgent_Firefox);
        //获取Document对象
        Document document = setProxyIp(connection);
        return document;
    }

    public static Document setProxyIp(Connection connection){

        Document document = null;
        ProxyIp proxyIp = new ProxyIp();
        //需要捕获所有可能出现的异常，避免出现异常导致系统停止爬取数据
        try {
            proxyIp = ProxyIpUtils.getProxyIp(i);
            System.out.println(proxyIp.getIp()+"================="+proxyIp.getPort());
            //修改ip和端口号
            connection.proxy(proxyIp.getIp(), proxyIp.getPort());
            System.out.println(connection.toString());
            //可能出现异常：虽然是付费的IP，但是也有可能你在用这个IP的时候，他已经失效了。
            //所以可能会获取不到document
            document = connection.get();
            System.out.println(document.text());
        } catch (IOException e) {
            try {
                //出现异常时，休眠5秒重新设置IP来获取document，这个时间可以通过测试取一个合理的值，避免导致爬取数据缓慢
                Thread.sleep(5000);
                i++;
                logger.info(">>>>>>>>>>>>>>出现异常，稍等继续爬取i"+i);

                return setProxyIp(connection);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return document;
    }
}

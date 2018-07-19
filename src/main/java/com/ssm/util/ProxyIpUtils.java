package com.ssm.util;


import com.ssm.entity.ProxyIp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * 设置动态代理IP
 * @author
 */
public class ProxyIpUtils {

    private static final Logger logger = LoggerFactory.getLogger(ProxyIpUtils.class);
    public static ProxyIp getProxyIp(int i){
        ControllerZl1 controllerZl1 = new ControllerZl1();
       List<String> lists = controllerZl1.crawlingZl();
        //IP地址
        String ipStr = null;
        //代理IP实体类
        ProxyIp proxyIp = null;
        if(i>=lists.size()){
           i=0;
        }

        for (; i < lists.size() ;i++ ) {
            ipStr = lists.get(i);
            lists.toString();
            //截取IP
            String ip = ipStr.split(":")[0];
            //截取端口号
            String port = ipStr.split(":")[1];
            logger.info("获取到的ip地址>>>>>>>>>>>>>>>>>"+ip);
            logger.info("获取到的端口号>>>>>>>>>>>>>>>>>"+port);
            proxyIp = new ProxyIp();
            //设置端口号
            proxyIp.setPort(Integer.parseInt(port));
            //设置IP
            proxyIp.setIp(ip);

            logger.info("获取到的i>>>>>>>>>>>>>>>>>"+i);
            return  proxyIp;
        }

        return  proxyIp;
    }
}

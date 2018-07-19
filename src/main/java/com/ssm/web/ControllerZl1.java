package com.ssm.web;


import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* * 分页、使用多线程爬取智联招聘前50页数据。
 * 爬取内容为：招聘标题、公司名称、职位月薪、工作描述
 * 因为这几个部分处于页面中不同的位置，练习如何使用选择器选择页面中不同的部分
 **/

@Controller
@RequestMapping(value = "/homework")
public class ControllerZl1 {

    List<String> lists = new ArrayList<>();


    static Logger logger = Logger.getLogger(ControllerZl1.class);

@RequestMapping(value = "/getString")
    public List<String> crawlingZl() {
        //获取当前系统时间的毫秒数,目的查看通过多线程爬取数据所花费的时间
        Long startTime = System.currentTimeMillis();
        //获取智联招聘URL
        String url = "http://www.89ip.cn/tqdl.html?num=30&address=&kill_address=&port=&kill_port=&isp=";
        //建立连接
        Connection connection = Jsoup.connect(url);
        //创建线程
        //发送get请求获取Document对象
        Document document = null;
        try {
            document = connection.get();
            //编写选择器.定位到招聘详情页对应的a标签
            //选取的是第一个a标签
            String selector = "div[class=layui-col-md8]>div[class=fly-panel]>div";
            //根据选择器获取对应的Elements对象
            String ipStr = document.select(selector).text();
            System.out.println("=============" + ipStr);
            for (int i = 0; i < 30; i++) {
                String ipStr1 = ipStr.split(" ")[i];
                lists.add(ipStr1);
                System.out.println(ipStr1);
            }
            System.out.println(lists.size());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return lists;
    }


}

package com.ssm.web;


import com.ssm.entity.Recruit;
import com.ssm.service.RecruitService;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* * 分页、使用多线程爬取智联招聘前50页数据。
 * 爬取内容为：招聘标题、公司名称、职位月薪、工作描述
 * 因为这几个部分处于页面中不同的位置，练习如何使用选择器选择页面中不同的部分
 **/

@Controller
@RequestMapping(value = "/homework")
public class ControllerZYH {

    //注入业务层组件
    @Resource
    private RecruitService recruitService;

    static Logger logger = Logger.getLogger(ControllerZYH.class);

    @RequestMapping(value = "/zl11")
    @ResponseBody
    public String crawlingZl() {
        //获取当前系统时间的毫秒数,目的查看通过多线程爬取数据所花费的时间
        Long startTime = System.currentTimeMillis();
        //获取智联招聘URL
        String url = "http://192.168.1.175:8080/news?pageNo=";
        //通过Executors工厂类创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i <= 300; i++) {
            //通过拼接字符串的形式，在url后面动态拼接上页码
            String listUrl = url + (i + 1);
            //建立连接
            Connection connection = Jsoup.connect(listUrl);
            //创建线程
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //发送get请求获取Document对象
                        Document document = connection.get();
                        //编写选择器.定位到招聘详情页对应的a标签



                            //招聘标题
                            String title = "";
                            //公司名称
                            String companyName = "";
                            //职位月薪
                            String monthlySalary = "";
                            //工作描述
                            String jobDescription = "";
                        for (int j = 0; j < 15 ; j++) {
                            //定义标题选择器
                            String selectorTitle = "tbody>tr:eq(1)>td:eq(1)";
                            //获取标题对应的Elements对象
                            Elements elementsTitle = document.select(selectorTitle);
                            //获取Elements对象中的文字
                            title = elementsTitle.text();
                            logger.info("招聘标题>>>>>>>>>>>" + title);
                            //定义公司名称选择器
                            String selectorCompanyName = "tbody>tr:eq(1)>td:eq(2)";
                            Elements elementsCompanyName = document.select(selectorCompanyName);
                            companyName = elementsCompanyName.text();
                            logger.info("招聘企业名称>>>>>>>>>>>" + companyName);

                            //获取招聘详情
                            String companySelector = "tbody>tr:eq(1)>td:eq(3)";
                            Elements companyElement = document.select(companySelector);
                            jobDescription = companyElement.text();
                            logger.info("招聘详情>>>>>>>>>>>" + jobDescription);
                            Recruit recruit = new Recruit();
                            recruit.setTitle(title);
                            recruit.setCompanyName(companyName);
                            recruit.setMonthlySalary(monthlySalary);
                            recruit.setJobDescription(jobDescription);
                            try {
                                recruitService.AddRecruit(recruit);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println("title==companyName==monthlySalary==jobDescription"+title+companyName+jobDescription);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
        //当所有线程的所有任务都完成之后，将所有线程终止
        executorService.shutdown();
        while (true) {
            //判断所有线程是否都已经停止
            if (executorService.isTerminated()) {
                //获取当前系统时间
                Long endTime = System.currentTimeMillis();
                //计算出总共耗时
                Float totalTime = (endTime - startTime) / 1000F;
                logger.info("爬取总耗时为>>>>>>>>>>>" + totalTime);
                break;
            }
        }
        return "success";
    }


}

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
public class ControllerZl {

    //注入业务层组件
    @Resource
    private RecruitService recruitService;

    static Logger logger = Logger.getLogger(ControllerZl.class);

    @RequestMapping(value = "/zl")
    @ResponseBody
    public String crawlingZl() {
        //获取当前系统时间的毫秒数,目的查看通过多线程爬取数据所花费的时间
        Long startTime = System.currentTimeMillis();
        //获取智联招聘URL
        String url = "http://sou.zhaopin.com/jobs/searchresult.ashx?jl=%E5%85%A8%E5%9B%BD&kw=java%E5%BC%80%E5%8F%91%E5%B7%A5%E7%A8%8B%E5%B8%88&sm=0&sg=374efcee1b6a405faa789635af5e95d7&p=";
        //通过Executors工厂类创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i <= 30; i++) {
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
                        //选取的是第一个a标签
                        String selector = "td[class=zwmc]>div>a:eq(0)";
                        //根据选择器获取对应的Elements对象
                        Elements elements = document.select(selector);
                        for (int j = 0; j < elements.size(); j++) {
                            //验证一下数量是否匹配
                            logger.info("本页共" + elements.size() + "条招聘信息");
                            //获取href属性的值
                            String detailUrl = elements.get(j).absUrl("href");
                            //连接详情页
                            Connection detailConnection = Jsoup.connect(detailUrl);
                            //招聘标题
                            String title = "";
                            //公司名称
                            String companyName = "";
                            //职位月薪
                            String monthlySalary = "";
                            //工作描述
                            String jobDescription = "";
                            try {
                                //获取详情页的Document对象
                                Document documentDetail = detailConnection.get();
                                //定义标题选择器
                                String selectorTitle = "div[class=fixed-inner-box]>div>h1";
                                //获取标题对应的Elements对象
                                Elements elementsTitle = documentDetail.select(selectorTitle);
                                //获取Elements对象中的文字
                                title = elementsTitle.text();
                                logger.info("招聘标题>>>>>>>>>>>" + title);

                                //定义公司名称选择器
                                String selectorCompanyName = "div[class=fixed-inner-box]>div>h1+h2>a";
                                Elements elementsCompanyName = documentDetail.select(selectorCompanyName);
                                companyName = elementsCompanyName.text();
                                logger.info("招聘企业名称>>>>>>>>>>>" + companyName);

                                //定义月薪选择器
                                String selectorMonthlySalary = "div[class=terminalpage-left]>ul>li:eq(0)>strong";
                                Elements elementsMonthlySalary = documentDetail.select(selectorMonthlySalary);
                                monthlySalary = elementsMonthlySalary.text();
                                logger.info("招聘企业月薪>>>>>>>>>>>" + monthlySalary);

                                //获取招聘详情
                                String companySelector = "div[class=tab-inner-cont]:eq(0)";
                                Elements companyElement = documentDetail.select(companySelector);
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
                                System.out.println("title==companyName==monthlySalary==jobDescription"+title+companyName+monthlySalary+jobDescription);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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

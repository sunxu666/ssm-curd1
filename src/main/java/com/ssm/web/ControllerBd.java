package com.ssm.web;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用爬虫对百度进行信息查询
 * @author
 */
@Controller
@RequestMapping(value = "/home")
public class ControllerBd {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ControllerBd.class);
    //static Logger logger = Logger.getLogger(ControllerBd.class);
    @RequestMapping(value = "/baidu")
    @ResponseBody
    public String  crawlingBd(){
        //获取前程无忧URL
        final String[] titleOut = {""};
        String url1 = "https://search.51job.com/list/010000%252C020000%252C040000%252C080200%252C070200,000000,0000,00,9,99,Java%25E9%25AB%2598%25E7%25BA%25A7%25E5%25BC%2580%25E5%258F%2591%25E5%25B7%25A5%25E7%25A8%258B%25E5%25B8%2588,2,";
        String url2 = ".html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
        //通过Executors工厂类创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i <1 ; i++) {
            //通过拼接字符串拼接页码
            String listUrl = url1 +(i+1)+url2;
            //建立连接
            Connection connection = Jsoup.connect(listUrl);
            //创建线程
            executorService.execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        //发送get请求获取Document对象
                        Document document = connection.get();
                        //编写选择器，定位到招聘详情页对应的a标签
                        //选择的是第一个标签
                        String selector = "div[class=el]>p[class=t1 ]>span>a:eq(0)";
                        //根据选择器获取对应的Elements对象
                        Elements elements = document.select(selector);
                        for (int j = 0; j <elements.size() ; j++) {
                            logger.info("=====有多少====="+elements.size());
                            //获取href
                            String detailUrl = elements.get(j).absUrl("href");
                            logger.debug("=========================="+detailUrl);
                            Connection detailConnection = Jsoup.connect(detailUrl);
                            //招聘标题
                            String title = "";
                            //公司名称
                            String companyName = "";
                            //职位月薪
                            String monthlySalary = "";
                            //工作描述
                            String jobDescription = "";
                            //获取详情页的Document对象
                            Document documentDetail = detailConnection.get();
                            //定义标题选择器
                           String selectorTitle = "div[class=cn]>h1";
                           //获取标题对应的Elements对象
                            Elements elementsTitle = documentDetail.select(selectorTitle);
                            //获取Elements对象中的文字
                            title = elementsTitle.text();
                            titleOut[0] = title;
                            logger.info("=================="+title+"AAAA===");

                            //定义公司名称选择器
                            String selectorCompanyName = "p[class=cname]>a";
                            Elements elementsCompanyName = documentDetail.select(selectorCompanyName);
                            companyName = elementsCompanyName.text();
                            logger.info("招聘企业名称>>>>>>>>>>>" + companyName);

                            //定义月薪选择器
                            String selectorMonthlySalary = "div[class=cn]>strong";
                            Elements elementsMonthlySalary = documentDetail.select(selectorMonthlySalary);
                            monthlySalary = elementsMonthlySalary.text();
                            logger.info("招聘企业月薪>>>>>>>>>>>" + monthlySalary);

                            //获取招聘详情
                            String companySelector = "div[class=tBorderTop_box]>div[class=bmsg job_msg inbox]";
                            Elements companyElement = documentDetail.select(companySelector);
                            jobDescription = companyElement.text();
                            logger.info("招聘详情>>>>>>>>>>>" + jobDescription);
                            System.out.println("title==companyName==monthlySalary==jobDescription"+title+companyName+monthlySalary+jobDescription);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        return "success"+ titleOut[0];
    }

}

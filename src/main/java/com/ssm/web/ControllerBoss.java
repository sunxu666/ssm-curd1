package com.ssm.web;


import com.ssm.entity.Recruit;
import com.ssm.service.RecruitService;
import com.ssm.util.DocumentUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 采用代理爬虫实现对BOSS直聘网站的数据进行抓取
 */
@Controller
@RequestMapping(value = "/homework")
public class ControllerBoss {
    @Resource
    private RecruitService recruitService;

    private static final Logger logger = LoggerFactory.getLogger(ControllerBoss.class);

    /**
     * 使用反反爬策略爬取boss直聘的数据
     */
    @RequestMapping("/boss")
    @ResponseBody
    public String crawlingBoss() {
        //定义爬取的目标网站的url地址
        String url = "https://www.zhipin.com/c101010100/h_101010100/?query=java%E5%BC%80%E5%8F%91%E5%B7%A5%E7%A8%8B%E5%B8%88&page=pageNo&ka=page-pageNo";
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        //循环爬取50页数据
        for (int i = 0; i < 1; i++) {
            final String listUrl = url.replaceAll("pageNo", (i + 1) + "");
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Document document = DocumentUtil.getDocument(listUrl);
                    //获取列表页中招聘信息的a标签
                    String selector = "div[class=info-primary]>h3>a";
                    Elements elements = document.select(selector);
                    //遍历Elements对象
                    for (int j = 0; j < elements.size(); j++) {
                        logger.info("本页共>>>>>>>>>>>" +elements.size()+"条招聘数据。");
                        //获取详情页url地址
                        String detailUrl = elements.get(j).absUrl("href");
                        //招聘标题
                        String title = "";
                        //企业名称
                        String companyName = "";
                        //职位描述
                        String jobDescript = "";
                        Document documentDetail = DocumentUtil.getDocument(detailUrl);
                        //定义标题选择器
                        String selectorTitle = "div[class=name]>h1";
                        Elements elementsTitle = documentDetail.select(selectorTitle);
                        title = elementsTitle.text();
                        logger.info("招聘标题>>>>>>>>>>>" + title);
                        //获取公司名称
                        String companySelector = "div[class=info-company]>h3>a";
                        Elements companyElement = documentDetail.select(companySelector);
                        companyName = companyElement.text();
                        logger.info("公司名称>>>>>>>>>>>" + companyName);
                        //定义职位描述选择器
                        String jobDescriptSelector = "div[class=detail-content]>div[class=job-sec]>div[class=text]";
                        Elements jobDescriptElement = documentDetail.select(jobDescriptSelector);
                        jobDescript = jobDescriptElement.text();
                        logger.info("职位描述>>>>>>>>>>>" + jobDescript);
                        Recruit recruit = new Recruit();
                        recruit.setTitle(title);
                        recruit.setCompanyName(companyName);
                        recruit.setJobDescription(jobDescript);
                        try {
                            //将爬取的数据入库
                            recruitService.AddRecruit(recruit);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        //当所有线程的所有任务都完成之后，将所有线程终止
        executorService.shutdown();
        return "success";
    }

}

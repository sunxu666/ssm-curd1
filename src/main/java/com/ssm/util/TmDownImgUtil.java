package com.ssm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 运用爬虫爬照片，下载
 * @author
 */
public class TmDownImgUtil {
    /**
     * 依据网址获取网页源代码
     * @param link  目标网址
     * @param encoding 网站的编码
     * @return
     */
    public static String htmlSource(String link,String encoding){
        //保存
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取网络对象
            URL url = new URL(link);
            //建立网络链接
            URLConnection uc = url.openConnection();
            //伪装
            uc.setRequestProperty("User-Agent","java");
            //下载源代码 流：获取文件输入流
            InputStream inputStream = uc.getInputStream();
            //建立io流的缓冲,提升效率
            InputStreamReader in = new InputStreamReader(inputStream,encoding);
            //下载源代码的缓冲区
            BufferedReader reader = new BufferedReader(in);

            //开始下载源代码
            //首先创建一个临时文件
            String line = null;
            while((line = reader.readLine())!=null){
                stringBuilder.append(line+"\n");
            }
            //关闭流
            reader.close();
            in.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /**
     * java的主函数程序的入口
     * @param args
     */
    public static void main(String[] args) {
        String html = htmlSource("http://www.qq.com","gbk");
        System.out.println("输入输出！");
    }

}

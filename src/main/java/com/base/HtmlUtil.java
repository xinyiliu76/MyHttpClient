package com.base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HtmlUtil {
    public String htmltoString(File file){
        StringBuffer sb=new StringBuffer();
        try {
            FileInputStream ins=new FileInputStream(file);
            byte[] bytes=new byte[1024];
            while (ins.read(bytes)!=-1)
                sb.append(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public Document htmlToDoc(String html){
        Document doc=Jsoup.parse(html);
        Elements es=doc.getElementsByTag("link");
        for(Element e:es)
            System.out.println(e.attr("href"));
        return Jsoup.parse(html);
    }

    public static void main(String[] args){
        File f=new File("C:\\Users\\yixin.liu.RAGENTEKXIAN\\Desktop\\新建文本文档.html");
        HtmlUtil h=new HtmlUtil();
        String s=h.htmltoString(f);
        h.htmlToDoc(s);
    }
}

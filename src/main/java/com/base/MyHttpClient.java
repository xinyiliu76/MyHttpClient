package com.base;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;

public class MyHttpClient {
    public InputStream doGet(String urlAddress,Map<String,List<String>> requestMap,String cookie){
        URLConnection urlConnection=null;
        InputStream ins=null;
        try {
            URL url = new URL(urlAddress);
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Cookie",cookie);
            urlConnection.connect();
            ins=urlConnection.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ins;
    }
    public InputStream doPost(String urlAddress,String param,Map<String,List<String>> requestMap){
        URLConnection urlConnection=null;
        InputStream ins=null;
        try {
        URL url=new URL(urlAddress);
        urlConnection=(HttpURLConnection)url.openConnection();
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(5000);
        urlConnection.setDoOutput(true);
        Set<String> set=requestMap.keySet();
        for(String temp:set){
            urlConnection.setRequestProperty(temp,requestMap.get(temp).get(0));
        }
        OutputStream os=urlConnection.getOutputStream();
        PrintWriter pw=new PrintWriter(new OutputStreamWriter(os,"utf-8"));
        pw.print(param);
        pw.flush();
        ins=urlConnection.getInputStream();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ins;
    }
    public Map<String,List<String>> getRequestHeader(String filepath){
        Map<String,List<String>> map=new HashMap<String, List<String>>();
        File file=new File(filepath);
        FileInputStream fins=null;
        BufferedReader bf=null;
        try {
            fins=new FileInputStream(file);
            bf=new BufferedReader(new InputStreamReader(fins));
            String data=bf.readLine();
            String[] temp=null;
            while (data!=null){
                //System.out.println(data);
                temp=data.split(": ");
                ArrayList<String>tempList=new ArrayList<String>();
                //System.out.println(data);
                tempList.add(temp[1]);
                map.put(temp[0],tempList);
                data=bf.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                fins.close();
                bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
    public void savePic(InputStream ins,String name){
        File file=new File("C:\\Users\\yixin.liu.RAGENTEKXIAN\\Desktop\\"+name+".jpg");
        ByteArrayOutputStream bao=new ByteArrayOutputStream();
        int len=0;
        byte[] bytes=new byte[1024];
        try {
            while ((len=ins.read(bytes))!=-1)
                bao.write(bytes,0,len);
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(bao.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public File saveHtml(InputStream ins){
        File file=new File("C:\\Users\\yixin.liu.RAGENTEKXIAN\\Desktop\\1.html");
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            BufferedReader bf=new BufferedReader(new InputStreamReader(ins,"utf-8"));
            String line=bf.readLine();
            while (line!=null){
                System.out.println(line);
                fileOutputStream.write(line.getBytes());
                line=bf.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    public static void main(String[] args){
        MyHttpClient httpClient=new MyHttpClient();
        Map<String,List<String>> map=httpClient.getRequestHeader("C:\\Users\\yixin.liu.RAGENTEKXIAN\\Desktop\\buff.txt");
        //InputStream inputStream= httpClient.doGet("https://buff.163.com/",null,"_ntes_nuid=fe8a6bedf46b82f340ebbb3d77afbe6c; _ga=GA1.2.1557826289.1560761030; _gid=GA1.2.1585070330.1560761030; csrf_token=d4b6120e26d8d07e44137fd69f2cc33f0899bcae; game=csgo; NTES_YD_SESS=63AHHJYw6hX.kcVFbR2U4WItsxF8g2P7iEDu07XPzzqNVQXFV_orx51Dqm8cNaEw6tvW0O9ZciATNlgrHHY2.chXjYd_ceLMJa.LVwBI_FWaYEWSQwEVx_3KvkezfUHr6l.Wf5qCD1t15307m7OGc3M4kcsk9l5NBWpYTtyxCxxA245C3B9H2F1ydIs7tgp3TeS28G8eHrv7rv0dOkf0ttfKtBpS.II6rcJCdXxAseANM; S_INFO=1560828161|0|3&80##|15111819208; P_INFO=15111819208|1560828161|0|netease_buff|00&99|sxi&1560580686&netease_buff#bej&null#10#0#0|&0|null|15111819208; session=1-zFE3wGY6rkaO_V6RPVwlJlLpQlOEb4dAKju4gjI2kXzH2045778840; _gat_gtag_UA_109989484_1=1");
        InputStream inputStream=httpClient.doGet("https://buff.163.com/",map,"");
        HtmlUtil htmlUtil=new HtmlUtil();
        httpClient.saveHtml(inputStream);
       // System.out.println(htmlUtil.htmltoString(httpClient.saveHtml(inputStream)));
    }

}

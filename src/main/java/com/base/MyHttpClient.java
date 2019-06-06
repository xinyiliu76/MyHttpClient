package com.base;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;

public class MyHttpClient {
    public void doGet(String urlAddress,String cookie,Map<String,List<String>> requestMap){
        URLConnection urlConnection=null;
        try {
            URL url = new URL(urlAddress);
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setUseCaches(false);
            //urlConnection.setRequestProperty("Cookie",cookie);
            Set<String> set=requestMap.keySet();
            for(String temp:set){
                urlConnection.setRequestProperty(temp,requestMap.get(temp).get(0));
            }
            urlConnection.connect();
            InputStream ins=urlConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(ins,"utf-8"));
            String data=bufferedReader.readLine();
            while (data!=null){
                System.out.println(data);
                data=bufferedReader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void doPost(String urlAddress,String param,String cookie,Map<String,List<String>> requestMap){
        URLConnection urlConnection=null;
        try {
        URL url=new URL(urlAddress);
        urlConnection=(HttpURLConnection)url.openConnection();
        urlConnection.setReadTimeout(5000);
        urlConnection.setConnectTimeout(5000);
        urlConnection.setDoOutput(true);
        //urlConnection.setRequestProperty("Cookie",cookie);
        Set<String> set=requestMap.keySet();
        for(String temp:set){
            urlConnection.setRequestProperty(temp,requestMap.get(temp).get(0));
        }
        OutputStream os=urlConnection.getOutputStream();
        PrintWriter pw=new PrintWriter(new OutputStreamWriter(os,"utf-8"));
        pw.print(param);
        pw.flush();
        //urlConnection.connect();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String data=bufferedReader.readLine();
            while (data!=null){
                System.out.println(data);
                data=bufferedReader.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
                temp=data.split(": ");
                ArrayList<String>tempList=new ArrayList<String>();
                System.out.println(data);
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
    public static void main(String[] args){
        MyHttpClient httpClient=new MyHttpClient();
        Map<String,List<String>> map=httpClient.getRequestHeader("C:\\Users\\yixin.liu.RAGENTEKXIAN\\Desktop\\test.txt");
        //httpClient.doPost("http://127.0.0.1:8080/postdemo","param=1","");
        httpClient.doGet("http://sgs2.pocyun.com:48000/SG_sns/poc?corpCode=","",map);
        //httpClient.doPost("http://sgs2.pocyun.com:48000/SG_sns/poc/dologin?corpCode=","userName=18792982369&password=6b4aac89f5f1a60bdb3fa8209f37abc0&verify=t6cxz","PHPSESSID=872tdlm1o4j2o0r5j7t8d0l9b1; SG_CK_lang=zh-cn");
    }

}

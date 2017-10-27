package com.example.myapplication.util;

/**
 * Created by 雪无痕 on 2017/6/28.
 */

public class URLManger {

    // 支持的一些新闻类别类别id如下：
    public final String[] channelId = new String[] {
            "T1348647909107",   // 头条
            "T1348648037603",   // 社会
            "T1348649580692",   // 科技
            "T1348648756099",   // 财经
            "T1348649079062",   // 体育
            "T1348654060988",   // 汽车
    };

    /**
     * 获取一页新闻数据
     * @param channelId 新闻类别id
     * @return
     */
    public static String getUrl(String channelId) {
        // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
        return "http://c.m.163.com/nc/article/headline/" + channelId + "/0-20.html";
    }

    /**
     *
     * @param channelId 新闻id
     * @param pagerNo  第几页数据
     * @param pageSize  多少条
     * @return
     */
    public static String getUrl(String channelId,int pagerNo,int pageSize) {
        // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
        //偏移量

        int offset=(pagerNo-1)*pageSize;
        return "http://c.m.163.com/nc/article/headline/" + channelId + "/"+offset+"-"+pageSize+".html";
    }

    /**
     *
     * @param channelId
     * @param pagerNo
     * @return
     */
    public static String getUrl(String channelId,int pagerNo) {
        // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
        //偏移量
        int pageSize=10;
        int offset=(pagerNo-1)*pageSize;
        return "http://c.m.163.com/nc/article/headline/" + channelId + "/"+offset+"-"+pageSize+".html";
    }
   /* // 视频url路径
    public static final String VideoURL = //
            "http://c.m.163.com/nc/video/list/V9LG4B3A0/y/0-20.html";*/


    public static String getVideoURL(int pagerNo) {
        // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
        //偏移量
        int pageSize=10;
        int offset=(pagerNo-1)*pageSize;
        return "http://c.m.163.com/nc/video/list/V9LG4B3A0/y/"+offset+"-"+pageSize+".html";
    }
}




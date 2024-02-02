package com.ruomm.springcloud.authserver.dal.bingwall;

import com.ruomm.javax.encryptx.DigestUtil;
import com.ruomm.javax.jsonx.XJSON;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/9/21 6:06
 */
public class BindImageItemJson {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");

    public static void main(String[] args) {
        out();
    }

    private static void out01() {
        String dateStartStr = "20220930";
        String fileName = "NationalDay2022_ZH-CN3861603311";
        String copyright = "山上的日出，河北蔚县 (© zhao zhenhao/Getty Images)";
        String copyrightLink = "河北蔚县";
        String title = "山上的日出";
        String hsh = DigestUtil.encodingMD5(fileName).toLowerCase();
        BingImageItem imageItem = new BingImageItem();
        imageItem.setStartdate(dateStartStr);
        imageItem.setFullstartdate(dateStartStr + "1600");
        imageItem.setEnddate(parseEndDate(dateStartStr));
        imageItem.setUrl("/th?id=OHR." + fileName + "_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp");
        imageItem.setUrlbase("/th?id=OHR." + fileName + "");
        imageItem.setCopyright(copyright);
        imageItem.setCopyrightlink("https://www.bing.com/search?q=" +
                URLEncoder.encode(copyrightLink) +
                "&form=hpcapt&mkt=zh-cn");
        imageItem.setTitle(title);
        imageItem.setQuiz("/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_" +
                dateStartStr +
                "_" +
                fileName.split("_")[0] +
                "%22&FORM=HPQUIZ");
        imageItem.setWp(true);
        imageItem.setDrk(1);
        imageItem.setTop(1);
        imageItem.setBot(1);
        imageItem.setHsh(hsh);
        System.out.println(XJSON.toJSONString(imageItem));

    }

    private static void out02() {
        String dateStartStr = "20221001";
        String fileName = "LacChesserys_ZH-CN4136691056";
        String copyright = "倒映在湖中的勃朗峰山脉，法国霞慕尼市 (© Stefan Huwiler/Alamy)";
        String copyrightLink = "勃朗峰山脉";
        String title = "倒映在湖中的勃朗峰山脉";
        String hsh = DigestUtil.encodingMD5(fileName).toLowerCase();
        BingImageItem imageItem = new BingImageItem();
        imageItem.setStartdate(dateStartStr);
        imageItem.setFullstartdate(dateStartStr + "1600");
        imageItem.setEnddate(parseEndDate(dateStartStr));
        imageItem.setUrl("/th?id=OHR." + fileName + "_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp");
        imageItem.setUrlbase("/th?id=OHR." + fileName + "");
        imageItem.setCopyright(copyright);
        imageItem.setCopyrightlink("https://www.bing.com/search?q=" +
                URLEncoder.encode(copyrightLink) +
                "&form=hpcapt&mkt=zh-cn");
        imageItem.setTitle(title);
        imageItem.setQuiz("/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_" +
                dateStartStr +
                "_" +
                fileName.split("_")[0] +
                "%22&FORM=HPQUIZ");
        imageItem.setWp(true);
        imageItem.setDrk(1);
        imageItem.setTop(1);
        imageItem.setBot(1);
        imageItem.setHsh(hsh);
        System.out.println(XJSON.toJSONString(imageItem));

    }

    private static void out() {
        String dateStartStr = "20221002";
        String fileName = "FairyGlen_ZH-CN4521633106";
        String copyright = "仙女谷，苏格兰斯凯岛 (© e55evu/Getty Images)";
        String copyrightLink = "苏格兰斯凯岛";
        String title = "仙女谷";
        String hsh = DigestUtil.encodingMD5(fileName).toLowerCase();
        BingImageItem imageItem = new BingImageItem();
        imageItem.setStartdate(dateStartStr);
        imageItem.setFullstartdate(dateStartStr + "1600");
        imageItem.setEnddate(parseEndDate(dateStartStr));
        imageItem.setUrl("/th?id=OHR." + fileName + "_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp");
        imageItem.setUrlbase("/th?id=OHR." + fileName + "");
        imageItem.setCopyright(copyright);
        imageItem.setCopyrightlink("https://www.bing.com/search?q=" +
                URLEncoder.encode(copyrightLink) +
                "&form=hpcapt&mkt=zh-cn");
        imageItem.setTitle(title);
        imageItem.setQuiz("/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_" +
                dateStartStr +
                "_" +
                fileName.split("_")[0] +
                "%22&FORM=HPQUIZ");
        imageItem.setWp(true);
        imageItem.setDrk(1);
        imageItem.setTop(1);
        imageItem.setBot(1);
        imageItem.setHsh(hsh);
        System.out.println(XJSON.toJSONString(imageItem));

    }

    private static String parseEndDate(String startDateStr) {
        try {
            Date date = SDF.parse(startDateStr);
            date.setDate(date.getDate() + 1);
            return SDF.format(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

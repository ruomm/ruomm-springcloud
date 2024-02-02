package com.ruomm.springcloud.authserver.dal.bingwall;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/10/18 10:47
 */
@Getter
@Setter
@ToString
public class BingImageItem {
    /**
     * "startdate": "20221010",
     * "fullstartdate": "202210101600",
     * "enddate": "20221011",
     * "url": "/th?id=OHR.TortulaMoss_ZH-CN8695265186_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp",
     * "urlbase": "/th?id=OHR.TortulaMoss_ZH-CN8695265186",
     * "copyright": "带着闪闪发光水滴的泛生墙藓, 荷兰 (© Arjan Troost/Minden Pictures)",
     * "copyrightlink": "https://www.bing.com/search?q=%E6%B3%9B%E7%94%9F%E5%A2%99%E8%97%93&form=hpcapt&mkt=zh-cn",
     * "title": "放大镜下的墙藓",
     * "quiz": "/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20221010_TortulaMoss%22&FORM=HPQUIZ",
     * "wp": true,
     * "hsh": "cc77fa1dc99ef3fb8959234f09689dcc",
     * "drk": 1,
     * "top": 1,
     * "bot": 1,
     * "hs": []
     */
    private String startdate;
    private String fullstartdate;
    private String enddate;
    private String url;
    private String urlbase;
    private String copyright;
    private String copyrightlink;
    private String title;
    private String quiz;
    // 非必要
    private boolean wp;
    private String hsh;
    // 非必要
    private int drk;
    // 非必要
    private int top;
    // 非必要
    private int bot;

}

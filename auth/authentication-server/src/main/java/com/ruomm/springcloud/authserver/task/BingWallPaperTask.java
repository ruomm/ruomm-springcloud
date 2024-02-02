package com.ruomm.springcloud.authserver.task;

import com.ruomm.springcloud.authserver.dal.bingwall.BingImageData;
import com.ruomm.springcloud.authserver.dal.bingwall.BingImageItem;
import com.ruomm.javax.corex.FileUtils;
import com.ruomm.javax.corex.ListUtils;
import com.ruomm.javax.corex.RemoteUrlUtils;
import com.ruomm.javax.corex.StringUtils;
import com.ruomm.javax.httpx.HttpConfig;
import com.ruomm.javax.httpx.dal.ResponseFile;
import com.ruomm.javax.httpx.dal.ResponseText;
import com.ruomm.javax.httpx.httpurlconnect.FileUrlConnect;
import com.ruomm.javax.httpx.httpurlconnect.TextUrlConnect;
import com.ruomm.javax.jsonx.XJSON;
import com.ruomm.javax.jsonx.XUrlEncodedParse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/10/18 10:39
 */
@Component
@Slf4j
public class BingWallPaperTask {
    //    private final static String TAG = BingWallPaperTask.class.getSimpleName();
    private final static SimpleDateFormat SDF_BING = new SimpleDateFormat("yyyyMMdd");
    private final static int PER_COUNT = 8;

    // 必应壁纸API路径
    @Value(value = "${ruomm.bing.apiUrl}")
    private String apiUrl;
    // 必应壁纸下载路径
    @Value(value = "${ruomm.bing.downloadDir}")
    private String bingDownloadDir;

    // 命名方式按照开始日期还是结束日期 0.开始日期 1.开始日期下一天 2.结束日期 其他.开始日期
    @Value(value = "${ruomm.bing.renameMode}")
    private int renameMode;

    @Scheduled(cron = "0 0 0,6,12 * * ?")
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void downloadBingWallPaper() {
        List<BingImageItem> bingImageItems = doHttpGetBingImages();
        if (ListUtils.isEmpty(bingImageItems)) {
            return;
        }
        for (BingImageItem bingImageItem : bingImageItems) {
            try {
                doHttpDownloadImage(bingImageItem);
            } catch (Exception e) {
                log.error("必应壁纸下载发生异常", e);
            }

        }

    }

    private List<BingImageItem> doHttpGetBingImages() {
        String url = RemoteUrlUtils.parseRemoteUrl(apiUrl,
                "HPImageArchive.aspx?format=js&idx=0&n=" + PER_COUNT + "&mkt=zh-CN");
        ResponseText responseText = new TextUrlConnect().setUrl(url).setPost(false).doHttpSync(BingImageData.class);
        if (null == responseText || !HttpConfig.isSuccessful(responseText.getStatus()) || null == responseText.getResultObject()) {
            log.error("必应壁纸获取图片列表失败");
            return null;
        }
        BingImageData bingImageData = (BingImageData) responseText.getResultObject();
        if (null == bingImageData || ListUtils.isEmpty(bingImageData.getImages())) {
            log.error("必应壁纸获取图片列表为空");
            return null;
        }
        return bingImageData.getImages();
    }

    private void doHttpDownloadImage(BingImageItem imageItem) {
        int indexWh = imageItem.getUrl().indexOf("?");
        if (indexWh <= 0) {
            throw new RuntimeException("必应壁纸下载路径非法");
        }
        String bingDataStr = parseFileDate(imageItem);
        Map<String, String> imageParams = XUrlEncodedParse.parseToMap(imageItem.getUrl().substring(indexWh + 1), "UTF-8");
        String imageTypeTemp = FileUtils.getFileExtension(imageParams.get("id"));
        String imageType;
        if (StringUtils.isEmpty(imageTypeTemp) || imageTypeTemp.length() > 6) {
            imageType = "jpg";
        } else {
            imageType = imageTypeTemp.toLowerCase();
        }
        String year = bingDataStr.substring(0, 4);
        String month = bingDataStr.substring(4, 6);
        String day = bingDataStr.substring(6, 8);
        String relativeJsonPath = "json" + FileUtils.parseFileSeparator(bingDownloadDir) + year + "-" + month + FileUtils.parseFileSeparator(bingDownloadDir) + year + "-" + month + "_" + day + ".json";
        String jsonPath = FileUtils.concatFilePath(bingDownloadDir, relativeJsonPath);
        File bingJsonOutFile = FileUtils.createFile(jsonPath);
        String relativePath = year + "-" + month + FileUtils.parseFileSeparator(bingDownloadDir) + year + "-" + month + "_" + day + "." + imageType;
        String imagePath = FileUtils.concatFilePath(bingDownloadDir, relativePath);
        File bingImageOutFile = FileUtils.createFile(imagePath);
        if (null == bingJsonOutFile) {
            throw new RuntimeException("必应壁纸JSON信息目标文件创建失败");
        }
        if (null == bingImageOutFile) {
            throw new RuntimeException("必应壁纸图片下载目标文件创建失败");
        }
        if (bingJsonOutFile.exists() && bingJsonOutFile.length() > 0) {
            log.info("必应壁纸JSON信息已经存储过了，文件名称为：" + relativeJsonPath);
        } else {
            boolean jsonSaveResult = FileUtils.writeFile(bingJsonOutFile, XJSON.toJSONString(imageItem), false);
            if (jsonSaveResult) {
                log.info("必应壁纸JSON信息存储成功，文件名称为：" + relativeJsonPath);
            } else {
                log.info("必应壁纸JSON信息存储失败，文件名称为：" + relativeJsonPath);
            }
        }
        if (bingImageOutFile.exists() && bingImageOutFile.length() > 0) {
            log.info("必应壁纸图片已经下载过了，文件名称为：" + relativePath);
            return;
        }
        String imageUrl = RemoteUrlUtils.parseRemoteUrl(apiUrl, imageItem.getUrl());
        ResponseFile responseFile = new FileUrlConnect().setUrl(imageUrl).setPost(false).setOverWrite(true).doHttpSync(bingImageOutFile);
        if (null == responseFile || !HttpConfig.isSuccessful(responseFile.getStatus())) {
            log.error("必应壁纸图片下载失败，文件名称为：" + relativePath);
        } else {
            log.info("必应壁纸图片下载成功，文件名称为：" + relativePath);
        }
    }

    private String parseFileDate(BingImageItem imageItem) {
        String dataStr = null;
        try {
            Date dateFile;
            if (renameMode == 0) {
                dateFile = SDF_BING.parse(imageItem.getStartdate());
            } else if (renameMode == 1) {
                dateFile = SDF_BING.parse(imageItem.getStartdate());
                dateFile.setDate(dateFile.getDate() + 1);
            } else if (renameMode == 2) {
                dateFile = SDF_BING.parse(imageItem.getEnddate());
            } else {
                dateFile = SDF_BING.parse(imageItem.getStartdate());
            }
            dataStr = SDF_BING.format(dateFile);
        } catch (Exception e) {
            throw new RuntimeException("必应壁纸日期解析异常", e);
        }
        if (StringUtils.getLength(dataStr) != 8) {
            throw new RuntimeException("必应壁纸日期无法解析");
        } else {
            return dataStr;
        }
    }
}

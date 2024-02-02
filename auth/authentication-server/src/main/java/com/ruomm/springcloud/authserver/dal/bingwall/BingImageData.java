package com.ruomm.springcloud.authserver.dal.bingwall;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/10/18 10:50
 */
@Getter
@Setter
@ToString
public class BingImageData {
    private List<BingImageItem> images;
}

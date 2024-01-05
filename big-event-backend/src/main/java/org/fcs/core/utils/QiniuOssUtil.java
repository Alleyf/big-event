package org.fcs.core.utils;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 七牛云文件上传配置类
 *
 * @Author alleyf
 * @Date 2023/12/30 21:46
 * @Version 1.0
 */
@Component
@Slf4j
public class QiniuOssUtil {


    /**
     * 公钥
     */
    @Value("${qiniu.accessKey}")
    private String accessKey;
    /**
     * 私钥
     */
    @Value("${qiniu.secretKey}")
    private String accessSecretKey;
    /**
     * 空间名
     */
    @Value("${qiniu.bucket}")
    private String bucket;
    /**
     * 域名
     */
    @Value("${qiniu.domain}")
    private String url;

    /**
     * 处理多文件
     *
     * @param multipartFiles 文件数组
     * @return map
     */
    public List<Map<String, String>> uploadImages(MultipartFile[] multipartFiles) throws IOException {
//        log.info(multipartFiles.length + "张图片上传中...");
        List<Map<String, String>> imageUrls = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String originalFilename = file.getOriginalFilename();
            String fileUrl = uploadImageQiniu(file);
            Map<String, String> fileMap = new HashMap<>() {
                {
                    put(originalFilename, fileUrl);
                }
            };
            imageUrls.add(fileMap);
        }
        log.info(imageUrls.size() + "张图片上传成功");
        return imageUrls;
    }

    /**
     * 上传文件到七牛云
     *
     * @param multipartFile 文件
     * @return 文件访问地址
     */
    private String uploadImageQiniu(MultipartFile multipartFile) throws IOException {
        //1、获取文件上传的流
        byte[] fileBytes = multipartFile.getBytes();
        //2、创建日期目录分隔
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//            String datePath = dateFormat.format(new Date());
        String dirPath = "bigEvent/";
        //3、获取文件名
        String originalFilename = multipartFile.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        //定义一个文件唯一标识码,保证文件名唯一防止被覆盖
        String uuid = IdUtil.fastSimpleUUID();
        String filename = dirPath + uuid + StrUtil.DOT + type;
        //4.构造一个带指定 Region 对象的配置类
        //(根据自己的对象空间的地址选)
        Configuration cfg = new Configuration(Region.regionAs0());
        /*未绑定个人域名，需要关闭https的配置
         域名不支持https访问会报错ssl验证error
         cfh.useHttpsDomains=false 关闭实列即可，默认是开启的
         */
        UploadManager uploadManager = new UploadManager(cfg);
        //5.获取七牛云提供的 token
        Auth auth = Auth.create(accessKey, accessSecretKey);
        String upToken = auth.uploadToken(bucket);
        uploadManager.put(fileBytes, filename, upToken);
        return url + filename;
    }
}

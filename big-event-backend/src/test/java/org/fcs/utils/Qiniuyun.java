package org.fcs.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * @Author alleyf
 * @Date 2023/12/30 21:14
 * @Version 1.0
 */
public class Qiniuyun {


    //    @Test
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.regionAs0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本

//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "zlsDfteiKb2EoQCgcdFTRAqDzjhKxvr5L4Qyqig9";
        String secretKey = "HeDk3u0C-TKdUbZsuQZtNJ2P29C77tJNJSzNXQ9k";
        String bucket = "abroadmap";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "E:\\IDEAProjects\\big_event\\src\\main\\resources\\static\\files\\e2cd0d0506ab44aca7a43493401dda19.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            ex.printStackTrace();
            if (ex.response != null) {
                System.err.println(ex.response);

                try {
                    String body = ex.response.toString();
                    System.err.println(body);
                } catch (Exception ignored) {
                }
            }
        }

    }
}

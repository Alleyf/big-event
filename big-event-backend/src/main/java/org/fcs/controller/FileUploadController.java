package org.fcs.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.fcs.core.utils.QiniuOssUtil;
import org.fcs.model.pojo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author alleyf
 * @Date 2023/12/30 19:42
 * @Version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/api/file")
public class FileUploadController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private QiniuOssUtil qiniuOssUtil;

    /**
     * 上传文件到本地
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadToLocal")
    public Result<String> uploadToLocal(@RequestParam MultipartFile file) throws IOException {
//        将文件存储到本地磁盘上
        //获取文件名和后缀
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        //引入yml文件配置的文件存储路径，判断目录是否存在
        File uploadFileDirectory = new File(fileUploadPath);
//        判断文件目录是否存在 如果不存在，就创建一个文件夹
        if (!uploadFileDirectory.exists()) {
            Assert.isTrue(uploadFileDirectory.mkdirs(), "创建文件目录失败");
        }
        //定义一个文件唯一标识码,保证文件名唯一防止被覆盖
        String uuid = IdUtil.fastSimpleUUID();
        //使用文件IO获取到文件
        File uploadFile = new File(fileUploadPath + uuid + StrUtil.DOT + type);
        //把文件存储到磁盘目录，抛出IO异常
        file.transferTo(uploadFile);
//        file.transferTo(new File(fileUploadPath + originalFilename));
        return Result.success("success");
    }

    /**
     * 上传文件到七牛云
     *
     * @param file 文件
     * @return 地址结果
     * @throws IOException 异常
     */
    @PostMapping("/uploadToQiniu")
    public Result<List<Map<String, String>>> uploadToQiniu(@RequestParam(required = false, value = "file") MultipartFile[] file) throws IOException {
        List<Map<String, String>> uploadedUrls = qiniuOssUtil.uploadImages(file);
        return Result.success(uploadedUrls);
    }
}

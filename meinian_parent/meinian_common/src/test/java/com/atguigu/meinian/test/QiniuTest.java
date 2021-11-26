package com.atguigu.meinian.test;

import com.atguigu.meinian.utils.QiniuUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date:2021/9/29
 * Author:ybc
 * Description:
 */
public class QiniuTest {

    public void testQiniuUtils() throws IOException {
        //QiniuUtils.upload2Qiniu("E:\\尚硅谷学习资料\\第六阶段美年\\上传图片\\1.jpg", "abc.jpg");
        /*InputStream is = new FileInputStream("E:\\尚硅谷学习资料\\第六阶段美年\\上传图片\\1.jpg");
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        QiniuUtils.upload2Qiniu(bytes, "xyz.jpg");*/
        QiniuUtils.deleteFileFromQiniu("abc.jpg");
    }

    public void testUploadFile(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());//华南
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "_02f1DD5A2yDOZzHFhJ46nIYVq9rZ8edWNwpZEyC";
        String secretKey = "SHjVDQe5CNGSaC_BEwzFKOwSC8ztvBZIci4ORh2l";
        String bucket = "lamosi";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png，可支持中文
        String localFilePath = "E:\\尚硅谷学习资料\\第六阶段美年\\上传图片\\1.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "a.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);// FspfyEyKfuHZ0kcbXRIc5T9YhCax
            System.out.println(putRet.hash);// FspfyEyKfuHZ0kcbXRIc5T9YhCax
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    public void deleteFile(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        String accessKey = "_02f1DD5A2yDOZzHFhJ46nIYVq9rZ8edWNwpZEyC";
        String secretKey = "SHjVDQe5CNGSaC_BEwzFKOwSC8ztvBZIci4ORh2l";
        String bucket = "lamosi";
        String key = "a.jpg";//文件名称
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    public void testRegex(){
        String reg_username = "^\\w$";
        "123".matches(reg_username);
        Pattern.matches(reg_username, "123");
        Pattern pattern = Pattern.compile(reg_username);
        Matcher matcher = pattern.matcher("123");
        matcher.matches();
    }

}

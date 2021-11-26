package com.atguigu.meinian.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.constant.MessageConstant;
import com.atguigu.meinian.constant.RedisConstant;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.pojo.Setmeal;
import com.atguigu.meinian.pojo.TravelGroup;
import com.atguigu.meinian.service.SetmealService;
import com.atguigu.meinian.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        try {
            //获取上传的文件名
            String fileName = imgFile.getOriginalFilename();
            //获取上传到的文件的后缀
            String hzName = fileName.substring(fileName.lastIndexOf("."));
            //将UUID作为新的文件名
            fileName = UUID.randomUUID().toString() + hzName;
            //文件上传
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //将上传到七牛云存储空间中的文件名保存到redis中
            Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            jedis.close();
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] travelGroupIds){
        try {
            setmealService.add(setmeal, travelGroupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPage(queryPageBean);
    }


    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            String img = setmealService.getByImg(id);
            QiniuUtils.deleteFileFromQiniu(img);
            setmealService.delete(id);
            //将上传到七牛云存储空间中的文件名删除到redis中
            Jedis jedis = jedisPool.getResource();
            jedis.srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);
            jedis.close();
            return new Result(true, MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
    }

    @RequestMapping("/findTravelItemIdsByTravelGroupId")
    public Result findTravelItemIdsByTravelGroupId(Integer id){
        List<Integer> travelItemIds = setmealService.findTravelItemIdsByTravelGroupId(id);
        return new Result(true, "查询套餐的跟团游id成功", travelItemIds);
    }


    @RequestMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] travelGroupIds){
        try {
            setmealService.update(setmeal, travelGroupIds);
            return new Result(true, "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"更新失败");
        }
    }





}

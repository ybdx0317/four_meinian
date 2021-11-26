package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.constant.RedisConstant;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.mapper.SetmealMapper;
import com.atguigu.meinian.pojo.Setmeal;
import com.atguigu.meinian.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] travelGroupIds) {
        //添加套餐游信息
        setmealMapper.addSetmeal(setmeal);
        //添加套餐游和跟团游到的中间表信息
        setmealMapper.addSetmealAndTravelGroup(setmeal.getId(), travelGroupIds);
        //将保存到数据库中的图片名称保存到redis中
        Jedis jedis = jedisPool.getResource();
        jedis.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        jedis.close();
    }


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> page = setmealMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }


    @Override
    public void delete(Integer id) {
        //先删除跟团游和自由行的中间表信息
        setmealMapper.deleteTravelGroupAndItem(id);
        //再删除跟团游信息
        setmealMapper.deleteTravelGroup(id);
    }


    @Override
    public String getByImg(Integer id) {
        return setmealMapper.getByImg(id);
    }


    @Override
    public List<Integer> findTravelItemIdsByTravelGroupId(Integer id) {
        return setmealMapper.findTravelItemIdsByTravelGroupId(id);
    }


    @Override
    public void update(Setmeal setmeal, Integer[] travelGroupIds) {
        Jedis jedis = jedisPool.getResource();
        String byImg = setmealMapper.getByImg(setmeal.getId());
        System.out.println("=======");
        System.out.println(byImg);
        //修改套餐信息
        setmealMapper.updateSetmeal(setmeal);

        System.out.println("++++++");
        System.out.println(setmeal.getImg());
        jedis.srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,byImg);
        jedis.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        //删除中间表信息
        setmealMapper.deleteTravelGroupAndItem(setmeal.getId());
        //添加中间表信息
        setmealMapper.add2s(setmeal.getId(),travelGroupIds);
        jedis.close();
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealMapper.findAll();
    }


    @Override
    public Setmeal findSetmealDetailById(Integer id) {
        return setmealMapper.findSetmealDetailById(id);
    }

    @Override
    public List<Map> getSetmealReport() {
        return setmealMapper.getSetmealReport();
    }
}

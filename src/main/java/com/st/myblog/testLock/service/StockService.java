package com.st.myblog.testLock.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.st.myblog.entity.Stock;
import com.st.myblog.mapper.StockMapper;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


//cglib动态代理,多例模式 jvm本地锁失效
//@Scope(value = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)

@Service
//@EnableAsync(proxyTargetClass=true)
@Scope(value = "singleton",proxyMode = ScopedProxyMode.TARGET_CLASS)
public  class StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ReentrantLock lock = new ReentrantLock();


    public  void  deduct(){
        //加锁setnx
        Boolean look = this.redisTemplate.opsForValue().setIfAbsent("look", "111");

        //1.查询库存信息
        //2.判断库存是否充足
        //3.扣减库存
    }


    public  void  deduct5(){

        this.redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations operations) throws DataAccessException {
                //watch
                operations.watch("stock");
                //1.查询库存信息
                String stock = (String) operations.opsForValue().get("stock");
                //2.判断库存是否充足

                if(stock !=null && stock.length() !=0){
                    Integer st = Integer.valueOf(stock);
                    if(st>0){
                        //multi
                        operations.multi();
                        //3.扣减库存
                        operations.opsForValue().set("stock",String.valueOf(--st));
                        //exec
                        List exec = operations.exec();
                        //如果执行事务的返回结果集为空，则代表减库存失败，重调方法
                        if(exec ==null || exec.size() ==0){
                            deduct();
                        }
                        return exec;
                    }
                }
                return null;
            }


        });


    }



    /*
     * 事务 Mysql默认隔离级别，可重复读  Repeatable Read 导致jvm本地锁失效
     * Isolation.READ_UNCOMMITTED 隔离级别读未提交，订单不能使用
     */

    //乐观锁
    //@Transactional 开启收到事务会加锁，但你不会自动释放锁
    //导致后面线程请求阻塞，Mysql默认超过30秒，就会连接超时
     //@Transactional

     public  void  deduct4(){

        //1.查询库存信息
        List<Stock> stocks = this.stockMapper.selectList(new QueryWrapper<Stock>().eq("product_code","1001"));
        //2.判断库存是否充足
         Stock stock = stocks.get(0);
         if(stock !=null && stock.getCount()>0){
             //3.扣减库存
             stock.setCount(stock.getCount()-1);
             Integer version = stock.getVersion();
             stock.setVersion(version + 1);

             //判断是否是之前查询的版本
             if(this.stockMapper.update(stock,new UpdateWrapper<Stock>().eq("id",stock.getId()).eq("version",version))==0){
                 //如果更新失败则重新更新
                 //java.lang.StackOverflowError 递归会导致栈内存溢出，方法在栈中
                 try {
                     Thread.sleep(20);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 deduct();
             }

         }

      }



    @Transactional
    public  void  deduct3(){

        //1.查询库存信息并锁定库存信息
        List<Stock> stocks = this.stockMapper.queryStock("1001");
        //2.判断库存是否充足
        Stock stock = stocks.get(0);
        if(stock !=null && stock.getCount()>0){
            //3.扣减库存
            stock.setCount(stock.getCount()-1);
            this.stockMapper.updateById(stock);
        }

    }

    //@Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public synchronized void  deduct2(){

        ///lock.lock();
        try{
            //数据库 update insert delete 写操作本身会加锁（悲观锁）
            //update db_stock t set t.count=count-1 where t.product_code ='1001' and t.count>=1;

            this.stockMapper.UpdateStock("1001",1);

             /* Stock stock = this.stockMapper.selectOne(new QueryWrapper<Stock>().eq("product_code", "1001"));
              if (stock !=null && stock.getCount()>0){
                 stock.setCount(stock.getCount()-1);
                 this.stockMapper.updateById(stock); }
            */
        }catch (Exception e){

        }finally {

            // lock.unlock();
        }


    }
}

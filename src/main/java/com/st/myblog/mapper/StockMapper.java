package com.st.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.st.myblog.entity.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface StockMapper extends BaseMapper<Stock> {

    @Update("update db_stock t set t.count=count- #{count} where t.product_code = #{productCode} and t.count>=#{count}")
    int UpdateStock(@Param("productCode") String productCode,@Param("count") Integer count);

    @Select("select * from db_stock where product_code = #{productCode} for update ")
    List<Stock> queryStock(String productCode);
}

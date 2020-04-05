package com.github.vspro.stock.persist.ext.mapper;

import com.github.vspro.stock.persist.orm.dao.StockDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface StockMapper extends StockDao {


     @Update("update t_stock set stock=stock-1 where product_id=#{productId} and deleted=2 and stock > 0")
    int deductStock(@Param("productId") Long productId);
}

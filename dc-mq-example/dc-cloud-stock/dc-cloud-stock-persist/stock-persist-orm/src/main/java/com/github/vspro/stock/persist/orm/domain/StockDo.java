package com.github.vspro.stock.persist.orm.domain;

import com.github.vspro.persist.base.domain.BaseDo;
import lombok.Data;

@Data
public class StockDo extends BaseDo {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 逻辑删除: 1、已删除、2、未删除
     */
    private String deleted;


}

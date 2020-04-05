package com.github.vspro.order.persist.orm.domain;

import com.github.vspro.persist.base.domain.BaseDo;
import lombok.Data;

@Data
public class OrderDo extends BaseDo {

    /** 订单编号 */
    private String orderNo;

    /** 订单名称 */
    private String orderName;

    /** 用户id */
    private Long userId;

    /** 金额 */
    private Integer amount;

    /** 商品id */
    private Long productId;

    /** 逻辑删除: 1、已删除、2、未删除 */
    private String deleted;


}

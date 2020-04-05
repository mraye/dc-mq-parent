package com.github.vspro.dmcpublic.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TransactionMessageVO implements Serializable {

    private Long id;

    /** 服务提供者 */
    private String producerServiceId;

    /** 消费者 */
    private String consumerServiceId;

    /** 消息内容 */
    private String message;

    /** 发送次数，超过该次数就不发送消息 */
    private Integer sendCount;

    /** 状态：1、待发送、2、已消费、3、已关闭、4、已发送 */
    private Integer status;

    /** 最近上次发送时间 */
    private LocalDateTime lastSendDate;

    /** 逻辑删除: 1未删除、2、已删除 */
    private String deleted;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

}

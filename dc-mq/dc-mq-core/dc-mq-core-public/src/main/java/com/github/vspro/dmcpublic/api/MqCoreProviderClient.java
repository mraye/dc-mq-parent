package com.github.vspro.dmcpublic.api;

import com.github.vspro.dmcpublic.vo.TransactionMessageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "mq-core-provider", path = "/core")
public interface MqCoreProviderClient {

    /**
     * 检索消息
     * @param messageId
     * @return
     */
    @PostMapping("/retrieveMessage")
    TransactionMessageVO retrieveMessage(@RequestParam("messageId") Long messageId);

    /**
     * 发送一条消息
     * @param messageVO
     * @return
     */
    @PostMapping("/sendMessage")
    boolean sendMessage(@RequestBody TransactionMessageVO messageVO);


    /**
     * 生产者，消息ack成功
     * @param messageId
     * @return true|false 成功|失败
     */
    @PostMapping("/confirmMessage")
    boolean confirmMessage(@RequestParam("messageId") Long messageId);

    /**
     * 关闭一条消息
     * @param messageId
     * @return
     */
    @PostMapping("/closeMessage")
    boolean closeMessage(@RequestParam("messageId") Long messageId);


    /**
     * 批量查询消息
     * @param limit
     * @return
     */
    @PostMapping("/loadUnAckMessageList")
    List<TransactionMessageVO> loadUnAckMessageList(@RequestParam("limit") int limit);

    /**
     * mq每发送一次消息，就需要更新当前消息
     * @param messageId
     * @param date
     * @return
     */
    @PostMapping("/refreshMessage")
    boolean refreshMessage(@RequestParam("messageId") Long messageId, @RequestParam("date") String date);

    @PostMapping("/messageDelivered")
    boolean messageDelivered(@RequestParam("messageId") Long messageId, @RequestParam("date") String date);
}

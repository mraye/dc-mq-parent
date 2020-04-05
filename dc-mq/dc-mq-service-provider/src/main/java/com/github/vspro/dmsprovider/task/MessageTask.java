package com.github.vspro.dmsprovider.task;


import com.github.vspro.dmcpublic.api.MqCoreProviderClient;
import com.github.vspro.dmcpublic.vo.TransactionMessageVO;
import com.github.vspro.dmsprovider.dto.MqMessage;
import com.github.vspro.dmsprovider.producer.MqClient;
import com.github.vspro.dmsprovider.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MessageTask {

    private static final int UNACK_LIMIT = 5000;
    private static final int MAX_SEND_TIMES = 6;
    //5 seconds
    private static final int PER_DELAY = 5;

    @Autowired
    private MqCoreProviderClient mqCoreProviderClient;

    @Resource(name = "mq-task-executor")
    private TaskExecutor executor;

    @Autowired
    private MqClient mqClient;

    private Semaphore semaphore = new Semaphore(20);


    public void start() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {

                    try {
                        int delay = process();
                        if (delay > 0) {
                            TimeUnit.SECONDS.sleep(delay);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        log.error("消息任务执行失败", e);
                    }
                }

            }
        });

        thread.start();
    }

    private int process() throws InterruptedException {
        int sleepTime = 10;
        List<TransactionMessageVO> unAckList = mqCoreProviderClient.loadUnAckMessageList(UNACK_LIMIT);
        if (unAckList != null && !unAckList.isEmpty()) {

            if (unAckList.size() == UNACK_LIMIT) {
                sleepTime = 0;
            }

            CountDownLatch latch = new CountDownLatch(unAckList.size());
            for (TransactionMessageVO transactionMessageVO : unAckList) {
                semaphore.acquire();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            doProcess(transactionMessageVO);
                        } finally {

                            latch.countDown();
                            semaphore.release();
                        }
                    }
                });
            }
            //等待所有消息发送成功
            latch.await();

        }else {
            System.out.println(">>>>>>未查询到待消费的消息，休眠 10 seconds");
        }
        return sleepTime;

    }

    private void doProcess(TransactionMessageVO transactionMessageVO) {


        if (transactionMessageVO == null) {
            return;
        }
        Long messageId = transactionMessageVO.getId();
        if (transactionMessageVO.getSendCount() >= MAX_SEND_TIMES) {
            mqCoreProviderClient.closeMessage(messageId);
            System.out.println(">>>>发送消息已达最大重试次数，关闭该消息...");
            return;
        }

        LocalDateTime lastSend = LocalDateTime.now();
        if (transactionMessageVO.getLastSendDate() != null) {
            lastSend = transactionMessageVO.getLastSendDate();
        }


        if (transactionMessageVO.getSendCount() > 0) {
            lastSend = lastSend.plus(transactionMessageVO.getSendCount() * PER_DELAY,
                    ChronoUnit.SECONDS);
        }

        if (LocalDateTime.now().compareTo(lastSend) >= 0) {

            System.out.println(String.format(">>>>发送消息 [ %s ]:  messageId: %s, content: %s, retry times: %s",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()),
                    messageId, transactionMessageVO.getMessage(), transactionMessageVO.getSendCount()));

            //发送给rabbitmq服务节点
            MqMessage mqMessage = new MqMessage();
            mqMessage.setMessageId(messageId);
            mqMessage.setBody(transactionMessageVO.getMessage());
            boolean success = mqClient.sendMessage(JacksonUtil.toJsonString(mqMessage));
            if (success){

                System.out.println(">>>>消息发送成功... messageId: "+ messageId);
                //记录该消息发送日志，状态改成已发送
                boolean sc = mqCoreProviderClient.messageDelivered(messageId, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .format(LocalDateTime.now()));
                if (sc){
                    System.out.println(">>>>>>messageDelivered success");
                }
            }else {

                System.out.println(">>>>消息发送成功,些后重新发送... messageId: "+ messageId);

                //记录该消息发送日志,还是待发送
                mqCoreProviderClient.refreshMessage(messageId, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .format(LocalDateTime.now()));
            }


        }


    }


}

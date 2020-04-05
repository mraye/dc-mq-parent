package com.github.vspro.core.svr.service.impl;

import com.github.vspro.core.svr.beantrans.TransactionMessageBeanTrans;
import com.github.vspro.core.svr.dto.TransactionMessageDTO;
import com.github.vspro.core.svr.service.TransactionMessageService;
import com.github.vspro.persist.ext.manager.TransactionMessageMgr;
import com.github.vspro.persist.orm.domain.TransactionMessageDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionMessageServiceImpl implements TransactionMessageService {

    @Autowired
    private TransactionMessageMgr transactionMessageMgr;


    @Override
    public boolean sendMessage(TransactionMessageDTO message) {
        TransactionMessageDo messageDo = TransactionMessageBeanTrans.dtoToDo.apply(message);
        return transactionMessageMgr.insert(messageDo) > 0;
    }

    @Override
    public boolean confirmMessage(Long messageId) {
        return transactionMessageMgr.confirmMessage(messageId);
    }

    @Override
    public boolean closeMessage(Long messageId) {
        return transactionMessageMgr.closeMessage(messageId);
    }

    @Override
    public List<TransactionMessageDTO> loadUnAckMessageList(int limit) {
        List<TransactionMessageDo> list = transactionMessageMgr.loadUnAckMessageList(limit);
        if (list != null && !list.isEmpty()) {
            List<TransactionMessageDTO> results = list.stream()
                    .map(TransactionMessageBeanTrans.doToDto)
                    .collect(Collectors.toList());
            return results;
        }
        return null;
    }

    @Override
    public boolean refreshMessage(Long messageId, String date) {
        return transactionMessageMgr.refreshMessage(messageId, date);
    }

    @Override
    public TransactionMessageDTO retrieveMessage(Long messageId) {
        TransactionMessageDo transactionMessageDo = transactionMessageMgr.selectByPrimaryKey(messageId);
        return TransactionMessageBeanTrans.doToDto.apply(transactionMessageDo);
    }

    @Override
    public boolean messageDelivered(Long messageId, String date) {
        return transactionMessageMgr.messageDelivered(messageId, date);
    }
}

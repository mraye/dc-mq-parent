package com.github.vspro.persist.ext.manager;

import com.github.vspro.persist.base.dao.BaseDao;
import com.github.vspro.persist.base.manager.BaseManager;
import com.github.vspro.persist.ext.constants.CoreConstants;
import com.github.vspro.persist.ext.mapper.TransactionMessageMapper;
import com.github.vspro.persist.orm.domain.TransactionMessageDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("transactionMessageMgr")
public class TransactionMessageMgr extends BaseManager<TransactionMessageDo> {

    @Autowired
    private TransactionMessageMapper transactionMessageMapper;

    @Override
    public BaseDao<TransactionMessageDo> getDao() {
        return this.transactionMessageMapper;
    }


    public boolean confirmMessage(Long messageId) {
        return transactionMessageMapper.confirmMessage(messageId);
    }

    public boolean closeMessage(Long messageId) {
        return transactionMessageMapper.closeMessage(messageId);
    }

    public List<TransactionMessageDo> loadUnAckMessageList(int limit) {
        return transactionMessageMapper.loadUnAckMessageList(limit);
    }

    public boolean refreshMessage(Long messageId, String now) {
        return transactionMessageMapper.refreshMessage(messageId, now);
    }

    public boolean messageDelivered(Long messageId, String date) {
        return transactionMessageMapper.messageDelivered(messageId, date);
    }
}

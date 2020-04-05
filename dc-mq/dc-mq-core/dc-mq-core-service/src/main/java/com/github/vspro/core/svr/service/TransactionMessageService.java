package com.github.vspro.core.svr.service;

import com.github.vspro.core.svr.dto.TransactionMessageDTO;

import java.util.List;

public interface TransactionMessageService {

    boolean sendMessage(TransactionMessageDTO message);


    boolean confirmMessage(Long messageId);

    boolean closeMessage(Long messageId);

    List<TransactionMessageDTO> loadUnAckMessageList(int limit);

    boolean refreshMessage(Long messageId, String date);

    TransactionMessageDTO retrieveMessage(Long messageId);

    boolean messageDelivered(Long messageId, String date);
}

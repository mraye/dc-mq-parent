package com.github.vspro.core.svr.beantrans;

import com.github.vspro.core.svr.dto.TransactionMessageDTO;
import com.github.vspro.persist.orm.domain.TransactionMessageDo;
import org.springframework.beans.BeanUtils;

import java.util.function.Function;

public class TransactionMessageBeanTrans {


    public static final Function<TransactionMessageDTO, TransactionMessageDo> dtoToDo = (transactionMessageDTO -> {
        TransactionMessageDo transactionMessageDo = new TransactionMessageDo();
        BeanUtils.copyProperties(transactionMessageDTO, transactionMessageDo);
        return transactionMessageDo;
    });


    public static final Function<TransactionMessageDo, TransactionMessageDTO> doToDto = (transactionMessageDo -> {
        TransactionMessageDTO transactionMessageDTO = new TransactionMessageDTO();
        BeanUtils.copyProperties(transactionMessageDo, transactionMessageDTO);
        return transactionMessageDTO;
    });
}

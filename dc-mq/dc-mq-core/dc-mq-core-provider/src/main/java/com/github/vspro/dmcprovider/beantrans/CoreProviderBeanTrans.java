package com.github.vspro.dmcprovider.beantrans;

import com.github.vspro.core.svr.dto.TransactionMessageDTO;
import com.github.vspro.dmcprovider.vo.TransactionMessageVO;
import org.springframework.beans.BeanUtils;

import java.util.function.Function;

public class CoreProviderBeanTrans {

    public static Function<TransactionMessageVO, TransactionMessageDTO> voToDto = (transactionMessageVO -> {
        TransactionMessageDTO transactionMessageDTO = new TransactionMessageDTO();
        BeanUtils.copyProperties(transactionMessageVO, transactionMessageDTO);
        return transactionMessageDTO;
    });


    public static Function<TransactionMessageDTO, TransactionMessageVO> dtoToVo = (transactionMessageDTO -> {
        TransactionMessageVO transactionMessageVO = new TransactionMessageVO();
        BeanUtils.copyProperties(transactionMessageDTO, transactionMessageVO);
        return transactionMessageVO;
    });

}

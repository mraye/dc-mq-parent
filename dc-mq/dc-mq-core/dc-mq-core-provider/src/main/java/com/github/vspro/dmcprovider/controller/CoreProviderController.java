package com.github.vspro.dmcprovider.controller;

import com.github.vspro.core.svr.dto.TransactionMessageDTO;
import com.github.vspro.core.svr.service.TransactionMessageService;
import com.github.vspro.dmcprovider.beantrans.CoreProviderBeanTrans;
import com.github.vspro.dmcprovider.vo.TransactionMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/core")
public class CoreProviderController {


    @Autowired
    private TransactionMessageService transactionMessageService;


    @PostMapping("/retrieveMessage")
    public TransactionMessageVO retrieveMessage(@RequestParam("messageId") Long messageId){
        TransactionMessageDTO messageDTO = transactionMessageService.retrieveMessage(messageId);
        return CoreProviderBeanTrans.dtoToVo.apply(messageDTO);
    }


    @PostMapping("/sendMessage")
    public boolean sendMessage(@RequestBody TransactionMessageVO messageVO) {
        TransactionMessageDTO messageDTO = CoreProviderBeanTrans.voToDto.apply(messageVO);
        return transactionMessageService.sendMessage(messageDTO);
    }


    @PostMapping("/confirmMessage")
    public boolean confirmMessage(@RequestParam("messageId") Long messageId) {
        return transactionMessageService.confirmMessage(messageId);
    }


    @PostMapping("/closeMessage")
    public boolean closeMessage(@RequestParam("messageId") Long messageId) {
        return transactionMessageService.closeMessage(messageId);
    }


    @PostMapping("/loadUnAckMessageList")
    @Nullable
    public List<TransactionMessageVO> loadUnAckMessageList(@RequestParam("limit") int limit) {
        List<TransactionMessageDTO> list = transactionMessageService.loadUnAckMessageList(limit);

        if (list != null && !list.isEmpty()) {
            List<TransactionMessageVO> results = list.stream()
                    .map(CoreProviderBeanTrans.dtoToVo).collect(Collectors.toList());
            return results;
        }
        return null;
    }

    @PostMapping("/refreshMessage")
    boolean refreshMessage(@RequestParam("messageId") Long messageId, @RequestParam("date") String date){
        return transactionMessageService.refreshMessage(messageId, date);
    }

    @PostMapping("/messageDelivered")
    boolean messageDelivered(@RequestParam("messageId") Long messageId, @RequestParam("date") String date){
        return transactionMessageService.messageDelivered(messageId, date);
    }

}

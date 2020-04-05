package com.github.vspro.dmsprovider.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MqMessage implements Serializable {

    private Long messageId;

    private String body;
}

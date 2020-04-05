package com.github.vspro.stock.pd.dccloudstockprovider.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MqMessage implements Serializable {

    private Long messageId;

    private String body;
}

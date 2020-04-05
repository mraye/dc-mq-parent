package com.github.vspro.dmsprovider.producer;

public interface MqClient {

    public boolean sendMessage(String msg);

}

package com.estudos.demo.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class I18nService {
    
    @Autowired
    private MessageSource message;

    public String getMsg(String chave, Locale locale) {
        String msg = message.getMessage(chave, null, locale);
        return msg;
    }

}

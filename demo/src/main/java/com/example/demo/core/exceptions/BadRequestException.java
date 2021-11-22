package com.example.demo.core.exceptions;

import java.util.Map;
import java.util.HashMap;

public class BadRequestException extends HttpException {

    private final Map<String, String> map = new HashMap<String, String>();

    public BadRequestException(){
        this("Bad Request");
    }

    public BadRequestException(String message){
        super(400, message);
    }

    public Map<String, String> getExceptions(){
        return this.map;
    }

    public void addException(String key, String message){
        this.map.put(key, message);
    }
    
}

package com.example.demo.core;

import com.example.demo.core.functionalinterfaces.FindById;
import com.example.demo.core.exceptions.NotFoundException;

public abstract class ApplicationBase<T, ID> {
    protected FindById<T,ID> findById;
    protected T findById(ID id){
        T t = this.findById.findById(id).orElseThrow(()->{
            throw new NotFoundException();
        });
        return t;
    }

    protected ApplicationBase(FindById<T, ID> findById){
        this.findById = findById;
    }

    protected String serializeObject(T entity, String message){
        return String.format("%s %s succesfully.", entity.toString(), message);
    }
}

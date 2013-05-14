package com.vijayrc.supportguy.repository;

import com.google.gson.Gson;
import com.vijayrc.supportguy.meta.Config;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Scope("singleton")
public class AllConfigs implements BeanPostProcessor{



    public void init() {


    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }




}

package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.web.Renderer;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController{
    protected Renderer renderer = new Renderer();
}

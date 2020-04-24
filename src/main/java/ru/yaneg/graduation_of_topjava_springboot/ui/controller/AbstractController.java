package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


public abstract class AbstractController {

    protected Logger logger = LoggerFactory.getLogger(AbstractController.class);

    protected ModelMapper modelMapper = new ModelMapper();


}

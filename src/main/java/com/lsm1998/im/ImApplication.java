package com.lsm1998.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImApplication
{
    public static void main(String[] args)
    {
        // VM option: -Djava.awt.headless=false
        SpringApplication.run(ImApplication.class, args);
    }
}

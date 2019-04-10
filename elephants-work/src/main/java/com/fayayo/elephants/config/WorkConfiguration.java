package com.fayayo.elephants.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author dalizu on 2019/2/27.
 * @version v1.0
 * @desc
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "work")
@Configuration
public class WorkConfiguration {


    private Integer connectPort;

    private Integer heartBeat;


}

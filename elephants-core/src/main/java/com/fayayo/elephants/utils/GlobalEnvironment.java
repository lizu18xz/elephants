package com.fayayo.elephants.utils;

import com.fayayo.elephants.enums.OperatorSystemEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dalizu on 2019/2/25.
 * @version v1.0
 * @desc
 */
public class GlobalEnvironment {

    /**
     * 判断是否是linux 环境，有些命令不一样
     */
    private static boolean linuxSystem = false;


    @Getter
    private static OperatorSystemEnum systemEnum;

    /**
     * 用户环境变量
     */
    public static Map<String, String> userEnvMap = new HashMap<>();

    static {
        String os = System.getProperties().getProperty("os.name");
        if (os != null) {
            if (os.toLowerCase().startsWith("win")) {
                systemEnum = OperatorSystemEnum.WIN;
            } else if (os.toLowerCase().startsWith("mac")) {
                systemEnum = OperatorSystemEnum.MAC;
            } else {
                systemEnum = OperatorSystemEnum.LINUX;
                linuxSystem = true;
            }
        }

        // 全局配置，支持中文不乱
        userEnvMap.putAll(System.getenv());
        userEnvMap.put("LANG", "zh_CN.UTF-8");
    }

    public static boolean isLinuxSystem() {
        return linuxSystem;
    }


}

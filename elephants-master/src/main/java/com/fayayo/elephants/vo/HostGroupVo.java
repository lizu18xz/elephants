package com.fayayo.elephants.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc 保存 work组  和 里面 的机器信息
 */
@Data
@Builder
public class HostGroupVo {

    private String id;

    private String name;

    private String description;

    private List<String> hosts;

}

package com.fayayo.elephants.queue;

import lombok.Data;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc
 */
@Data
public class JobElement {

    private String jobId;

    private Long hostGroupId;

}

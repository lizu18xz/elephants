package com.fayayo.elephants.cluster;

import com.fayayo.elephants.work.Referer;

import java.util.List;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc
 */
public interface LoadBalance {

    void onRefresh(List<Referer> referers);

    Referer select(String key);

    void selectToHolder(String key, List<Referer> refersHolder);

}

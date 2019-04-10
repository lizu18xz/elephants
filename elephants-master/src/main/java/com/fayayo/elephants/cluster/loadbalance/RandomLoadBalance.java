package com.fayayo.elephants.cluster.loadbalance;

import com.fayayo.elephants.work.Referer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author dalizu on 2019/3/1.
 * @version v1.0
 * @desc 随机
 */
public class RandomLoadBalance extends AbstractLoadBalance{


    @Override
    protected Referer doSelect(String key) {
        List<Referer> referers = getReferers();
        int idx = (int) (ThreadLocalRandom.current().nextDouble() * referers.size());
        for (int i = 0; i < referers.size(); i++) {
            Referer ref = referers.get((i + idx) % referers.size());
            if (ref.isAvailable()) {
                return ref;
            }
        }
        return null;
    }

    @Override
    protected void doSelectToHolder(String key, List<Referer> refersHolder) {

    }

}

package com.fayayo.elephants.cluster.loadbalance;

import com.fayayo.elephants.work.Referer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dalizu on 2019/3/1.
 * @version v1.0
 * @desc 轮训
 */
public class RoundRobinLoadBalance extends AbstractLoadBalance {

    private AtomicInteger idx = new AtomicInteger(0);

    @Override
    protected Referer doSelect(String key) {
        List<Referer> referers = getReferers();

        int index = getNextNonNegative();
        for (int i = 0; i < referers.size(); i++) {
            Referer ref = referers.get((i + index) % referers.size());
            if (ref.isAvailable()) {
                return ref;
            }
        }
        return null;
    }

    @Override
    protected void doSelectToHolder(String key, List<Referer> refersHolder) {

    }

    // get non-negative int
    private int getNextNonNegative() {
        return getNonNegative(idx.incrementAndGet());
    }
}

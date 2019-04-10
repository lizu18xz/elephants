package com.fayayo.elephants.cluster.loadbalance;

import com.fayayo.elephants.cluster.LoadBalance;
import com.fayayo.elephants.exception.ElephantsServiceException;
import com.fayayo.elephants.work.Referer;
import lombok.Getter;

import java.util.List;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc 路由
 */
@Getter
public abstract class AbstractLoadBalance implements LoadBalance {

    /**
     * 刷新Referer集合信息
     * 提供 负载均衡的  选择方法,这里实现三种简单的
     * 常见的有:
     * 轮训
     * 随机
     * 权重
     * 一致性hash
     * 低并发度
     */
    private List<Referer> referers;


    @Override
    public void onRefresh(List<Referer> referers) {
        this.referers = referers;
    }

    @Override
    public Referer select(String key) {
        List<Referer> referers = this.referers;
        if (referers == null) {
            throw new ElephantsServiceException(this.getClass().getSimpleName() + " No available referers list for key:" + key);
        }
        Referer ref = null;
        if (referers.size() > 1) {
            ref = doSelect(key);

        } else if (referers.size() == 1) {
            ref = referers.get(0).isAvailable() ? referers.get(0) : null;
        }
        if (ref != null) {
            return ref;
        }
        throw new ElephantsServiceException(this.getClass().getSimpleName() + " No available referers for key:" + key);
    }

    @Override
    public void selectToHolder(String key, List<Referer> refersHolder) {
        List<Referer> referers = this.referers;

        if (referers == null) {
            throw new ElephantsServiceException(this.getClass().getSimpleName() + " No available referers list for call : referers_size= 0 "
                    + key);
        }

        if (referers.size() > 1) {
            doSelectToHolder(key, refersHolder);

        } else if (referers.size() == 1 && referers.get(0).isAvailable()) {
            refersHolder.add(referers.get(0));
        }

        if (refersHolder.isEmpty()) {
            throw new ElephantsServiceException(this.getClass().getSimpleName() + " No available referers for call : referers_size="
                    + referers.size() + " " + key);
        }
    }


    protected abstract Referer doSelect(String key);

    protected abstract void doSelectToHolder(String key, List<Referer> refersHolder);

    /**
     * 通过二进制位操作将originValue转化为非负数:
     *      0和正数返回本身
     *      负数通过二进制首位取反转化为正数或0（Integer.MIN_VALUE将转换为0）
     * return non-negative int value of originValue
     * @param originValue
     * @return positive int
     */
    protected  int getNonNegative(int originValue){
        return 0x7fffffff & originValue;
    }

}

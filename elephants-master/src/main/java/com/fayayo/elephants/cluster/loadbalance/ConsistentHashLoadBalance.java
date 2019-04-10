package com.fayayo.elephants.cluster.loadbalance;

import com.fayayo.elephants.work.Referer;

import java.util.*;

/**
 * @author dalizu on 2019/3/1.
 * @version v1.0
 * @desc 一致性hash算法 可以保证在上线、下线服务器的情况下尽量有多的请求命中原来路由到的服务器。
 */
public class ConsistentHashLoadBalance extends AbstractLoadBalance{

    /**
     * 一致性hash算法
     * 构建一个哈希池
     * 模一个素数
     * */
    private List<Referer> consistentHashReferers;

    @Override
    public void onRefresh(List<Referer> referers) {
        super.onRefresh(referers);

        List<Referer> copyReferers = new ArrayList<Referer>(referers);
        List<Referer> tempRefers = new ArrayList<Referer>();
        for (int i = 0; i < 53; i++) {
            Collections.shuffle(copyReferers);
            for (Referer ref : copyReferers) {
                tempRefers.add(ref);
            }
        }
        consistentHashReferers = tempRefers;
    }


    @Override
    protected Referer doSelect(String key) {

        int hash = getHash(key);
        Referer ref;
        for (int i = 0; i < getReferers().size(); i++) {
            ref = consistentHashReferers.get((hash + i) % consistentHashReferers.size());
            if (ref.isAvailable()) {
                return ref;
            }
        }
        return null;
    }

    @Override
    protected void doSelectToHolder(String key, List<Referer> refersHolder) {


    }

    private int getHash(String key) {
        int hashcode;
        hashcode = key.hashCode();
        return getNonNegative(hashcode);
    }



    public static void main(String[] args) {

        AbstractLoadBalance abstractLoadBalance=new ConsistentHashLoadBalance();

        List<Referer>list=new LinkedList<>();
        list.add(new Referer("10.10.10.1"));
        list.add(new Referer("10.10.10.2"));
        list.add(new Referer("10.10.10.3"));
        list.add(new Referer("10.10.10.4"));
        list.add(new Referer("10.10.10.5"));

        abstractLoadBalance.onRefresh(list);

        for (int i=0;i<2;i++){
            System.out.println(abstractLoadBalance.doSelect("1").toString());
            System.out.println(abstractLoadBalance.doSelect("2").toString());
            System.out.println(abstractLoadBalance.doSelect("3").toString());
            System.out.println(abstractLoadBalance.doSelect("4").toString());
            System.out.println(abstractLoadBalance.doSelect("5").toString());
            System.out.println(abstractLoadBalance.doSelect("6").toString());
        }

    }

}

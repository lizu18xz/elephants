package com.fayayo.elephants.transport.serialize;

import com.fayayo.elephants.transport.serialize.impl.HessianSerializer;

/**
 * @author dalizu on 2019/1/10.
 * @version v1.0
 * @desc 序列化接口
 */
public interface Serializer {

    Serializer DEFAULT=new HessianSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlogrithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);


}

package com.fayayo.elephants.transport.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public abstract class Packet implements Serializable {

    @JSONField(deserialize = false, serialize = false)
    private Byte version=1;

    @JSONField(serialize = false)
    protected abstract Byte getCommand();

}

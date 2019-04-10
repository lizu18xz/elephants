package com.fayayo.elephants.transport.protocol.request;

import com.fayayo.elephants.transport.protocol.Packet;
import lombok.Data;
import static com.fayayo.elephants.transport.protocol.command.Command.HEART_BEAT_REQUEST;

/**
 * @author dalizu on 2019/2/27.
 * @version v1.0
 * @desc
 */
@Data
public class HeartBeatRequestPacket extends Packet {

    private long timestamp;

    /**
     *内存占用比例
     */
    private float memRate;

    /**
     *发送心跳的主机
     *
     */
    private String host;

    /**
     *每个核心的cpu负载
     *
     */
    private float cpuLoadPerCore;

    /**
     *总内存大小
     */
    private float memTotal;

    /**
     * cpu核数
     */
    private int cores;

    @Override
    protected Byte getCommand() {
        return HEART_BEAT_REQUEST;
    }

    @Override
    public String toString() {
        return "HeartBeatRequestPacket{" +
                "timestamp=" + timestamp +
                ", memRate=" + memRate +
                ", host='" + host + '\'' +
                ", cpuLoadPerCore=" + cpuLoadPerCore +
                ", memTotal=" + memTotal +
                ", cores=" + cores +
                '}';
    }
}

package com.fayayo.elephants.transport.protocol.response;

import com.fayayo.elephants.transport.protocol.Packet;
import lombok.Data;

import static com.fayayo.elephants.transport.protocol.command.Command.HEART_BEAT_RESPONSE;

/**
 * @author dalizu on 2019/2/27.
 * @version v1.0
 * @desc
 */
@Data
public class HeartBeatResponsePacket extends Packet {

    private boolean success;

    @Override
    protected Byte getCommand() {
        return HEART_BEAT_RESPONSE;
    }
}

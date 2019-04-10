package com.fayayo.elephants.transport.protocol.response;

import com.fayayo.elephants.transport.protocol.Packet;
import lombok.Data;

import static com.fayayo.elephants.transport.protocol.command.Command.SCHEDULE_RESPONSE;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc
 */
@Data
public class ScheduleResponsePacket extends Packet {

    private boolean success;

    @Override
    protected Byte getCommand() {
        return SCHEDULE_RESPONSE;
    }
}

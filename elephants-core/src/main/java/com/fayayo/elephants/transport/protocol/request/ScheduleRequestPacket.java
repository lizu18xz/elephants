package com.fayayo.elephants.transport.protocol.request;

import com.fayayo.elephants.transport.protocol.Packet;
import lombok.Data;

import static com.fayayo.elephants.transport.protocol.command.Command.SCHEDULE_REQUEST;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc 请求work启动job
 */
@Data
public class ScheduleRequestPacket extends Packet {

    private long requestId;

    private String jobId;

    @Override
    protected Byte getCommand() {
        return SCHEDULE_REQUEST;
    }
}

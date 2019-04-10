package com.fayayo.elephants.transport.channel;

import com.fayayo.elephants.transport.protocol.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.TimeUnit;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc 包装真正的channel,处理一些异常情况
 */
public class NettyChannel {

    private Channel channel;

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    public void writeAndFlush(Packet packet) {

        long timeout = 1000L;
        ChannelFuture writeFuture = this.channel.writeAndFlush(packet);
        boolean result = writeFuture.awaitUninterruptibly(timeout, TimeUnit.MILLISECONDS);

        if (result && writeFuture.isSuccess()) {

            return;
        }

        /*throw new RemotingException(this, getLocalAddress() + "Failed to send message to " + getRemoteAddress()
                + "in timeout(" + timeout + "ms) limit");*/
    }

}

package com.fan.service.netty.client;

import com.fan.entity.NettyModel;
import com.fan.service.netty.Middleware;
import com.fan.util.ConstantFan;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author fan
 * @title: NettyServerHandler
 * @projectName fan-share
 * @description: 服务端控制器
 * @date 2019/7/17/001714:37
 */
public class NettyClientHandler extends Middleware {
    private NettyClient client;

    public NettyClientHandler(NettyClient client) {
        super("客户端");
        this.client = client;
    }

    @Override
    protected void handlerData(ChannelHandlerContext ctx, Object msg) {
        NettyModel model = (NettyModel) msg;
        System.out.println("client  收到数据： " + model.toString());
    }

    @Override
    protected void handlerAllIdle(ChannelHandlerContext ctx) {
        super.handlerAllIdle(ctx);
        sendPingMsg(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        client.doConnect();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println(name + "exception :" + cause.toString());
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
    }
}


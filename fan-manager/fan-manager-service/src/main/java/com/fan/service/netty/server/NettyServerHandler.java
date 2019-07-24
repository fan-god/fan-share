package com.fan.service.netty.server;

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
public class NettyServerHandler extends Middleware {
    public NettyServerHandler() {
        super("服务端");
    }

    @Override
    protected void handlerData(ChannelHandlerContext ctx, Object msg) {
        NettyModel model = (NettyModel) msg;
        System.out.println("server 接收数据 ： " + model.toString());
        model.setType(ConstantFan.CUSTOMER);
        model.setBody("---------------");
        ctx.channel().writeAndFlush(model);
        System.out.println("server 发送数据： " + model.toString());
    }

    @Override
    protected void handlerReaderIdle(ChannelHandlerContext ctx) {
        super.handlerReaderIdle(ctx);
        System.err.println(" ---- client " + ctx.channel().remoteAddress().toString() + " reader timeOut, --- close it");
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println(name + "exception:" + cause.toString());
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }
}

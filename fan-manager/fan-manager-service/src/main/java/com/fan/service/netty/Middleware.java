package com.fan.service.netty;

import com.fan.entity.NettyModel;
import com.fan.util.ConstantFan;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fan
 * @title: Middleware
 * @projectName fan-share
 * @description: 处理中枢
 * @date 2019/7/17/001714:42
 */
@Slf4j
public abstract class Middleware extends SimpleChannelInboundHandler {
    protected String name;
    //记录次数
    private int heartbeatCount = 0;

    //获取server and client 传入的值
    public Middleware(String name) {
        this.name = name;
    }

    /**
     * 继承ChannelInboundHandlerAdapter实现了channelRead就会监听到通道里面的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyModel m = (NettyModel) msg;
        int type = m.getType();
        switch (type) {
            case ConstantFan.PING:
                sendPongMsg(ctx);
                break;
            case ConstantFan.PONG:
                sendPingMsg(ctx);
                log.info(name + " get  pong  msg  from" + ctx.channel().remoteAddress());
                break;
            case ConstantFan.CUSTOMER:
                handlerData(ctx, msg);
                break;
            default:
                break;
        }
    }

    /**
     * 处理数据
     * @param ctx
     * @param msg
     */
    protected abstract void handlerData(ChannelHandlerContext ctx, Object msg);

    /**
     * 发送ping数据
     * @param ctx
     */
    protected void sendPingMsg(ChannelHandlerContext ctx) {
        NettyModel model = new NettyModel();
        model.setType(ConstantFan.PING);
        ctx.channel().writeAndFlush(model);
        heartbeatCount++;
        log.info(name + " send ping msg to " + ctx.channel().remoteAddress() + "count :" + heartbeatCount);
    }

    /**
     * 发送pong数据
     * @param ctx
     */
    private void sendPongMsg(ChannelHandlerContext ctx) {
        NettyModel model = new NettyModel();
        model.setType(ConstantFan.PONG);
        ctx.channel().writeAndFlush(model);
        heartbeatCount++;
        log.info(name + " send pong msg to " + ctx.channel().remoteAddress() + " , count :" + heartbeatCount);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent stateEvent = (IdleStateEvent) evt;
        switch (stateEvent.state()) {
            case READER_IDLE:
                handlerReaderIdle(ctx);
                break;
            case WRITER_IDLE:
                handlerWriterIdle(ctx);
                break;
            case ALL_IDLE:
                handlerAllIdle(ctx);
                break;
            default:
                break;
        }
    }

    protected void handlerAllIdle(ChannelHandlerContext ctx) {
        System.err.println("---ALL_IDLE---");
    }

    protected void handlerWriterIdle(ChannelHandlerContext ctx) {
        System.err.println("---WRITER_IDLE---");
    }


    protected void handlerReaderIdle(ChannelHandlerContext ctx) {
        System.err.println("---READER_IDLE---");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(" ---@@@@@@@@@@@@@@----" + ctx.channel().remoteAddress() + "-----@@@@@@@@@@@@@@@@@@@@@---- is  Action");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(" ---@@@@@@@@@@@@@@----" + ctx.channel().remoteAddress() + "-----@@@@@@@@@@@@@@@@@@@@@---- is  inAction");
    }

}

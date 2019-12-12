package com.fan.service.netty;

import com.fan.entity.NettyModel;
import com.fan.util.ConstantFan;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author fan
 * @title: Middleware
 * @projectName fan-share
 * @description: 处理中枢
 * @date 2019/7/17/001714:42
 */
@Slf4j
public abstract class Middleware extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker handshaker;
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
     *
     * @param ctx
     * @param msg
     */
    protected abstract void handlerData(ChannelHandlerContext ctx, Object msg);

    /**
     * 发送ping数据
     *
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
     *
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

    //超时
    protected void handlerAllIdle(ChannelHandlerContext ctx) {
        System.err.println("---ALL_IDLE---");
    }

    //超时
    protected void handlerWriterIdle(ChannelHandlerContext ctx) {
        System.err.println("---WRITER_IDLE---");
    }

    //超时
    protected void handlerReaderIdle(ChannelHandlerContext ctx) {
        System.err.println("---READER_IDLE---");
    }

    //webSocket激活
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(" ---@@@@@@@@@@@@@@----" + ctx.channel().remoteAddress() + "-----@@@@@@@@@@@@@@@@@@@@@---- is  Action");
    }

    //webSocket关闭
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(" ---@@@@@@@@@@@@@@----" + ctx.channel().remoteAddress() + "-----@@@@@@@@@@@@@@@@@@@@@---- is  inAction");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        //传统的HTTP接入
        if (msg instanceof FullHttpRequest) {
//            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }//WebSocket接入
        else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

//    protected void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
//        //如果HTTP解码失败，返回HTTP异常
//        if (!req.getDecoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
//            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
//            return;
//        }
//        //构造握手响应返回，本机测试
//        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
//        handshaker = wsFactory.newHandshaker(req);
//        if (handshaker == null) {
//            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
//        } else {
//            //把握手消息返回给客户端
//            handshaker.handshake(ctx.channel(), req);
//        }
//    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //判断是否关闭链路指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        //判断是否是Ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content()).retain());
            return;
        }
        //本例程仅支持文本信息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }
        //返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        log.info(String.format("%s received %s", ctx.channel(), request));
        ctx.channel().write(new TextWebSocketFrame(request + "欢迎使用Netty WebSocket服务，现在时刻:" + new Date().toString()));
    }
}

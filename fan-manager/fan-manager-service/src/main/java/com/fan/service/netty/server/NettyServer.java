package com.fan.service.netty.server;

import com.fan.util.MsgPckDecode;
import com.fan.util.MsgPckEncode;
import com.fan.util.PropertiesUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author fan
 * @title: NettyServer
 * @projectName fan-share
 * @description: netty服务端
 * @date 2019/7/17/001714:28
 */
@Slf4j
@Component
public class NettyServer extends HttpServlet {
    private static final String NETTY_CONFIG_FILE_NAME = "config/netty.properties";
    private static final String NETTY_PORT_KEY_NAME = "netty.server.port";
    private PropertiesUtil propertiesUtil = new PropertiesUtil(NETTY_CONFIG_FILE_NAME);

    public void nettyServerStart() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            int port = Integer.parseInt(propertiesUtil.readProperty(NETTY_PORT_KEY_NAME));
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2));
                            pipeline.addLast(new IdleStateHandler(10, 0, 0));
                            pipeline.addLast("msgpack decoder", new MsgPckDecode());
                            pipeline.addLast("msgpack encoder", new MsgPckEncode());
                            //用于Http请求的编码或者解码
                            pipeline.addLast("http-codec", new HttpServerCodec());
                            //把Http消息组成完整地HTTP消息
                            pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                            //向客户端发送HTML5文件
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                            //实际处理的Handler
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            log.info("start netty server {} --", port);
            Channel channel = serverBootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("NettyServer NettyServer error:{}", e);
        } finally {
            //优雅的关闭资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void init() throws ServletException {
        FutureTask<String> task = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                // 使用另一个线程来执行该方法，会避免占用Tomcat的启动时间
                startAfterTomcat();
                return "Collection Completed";
            }
        });
        new Thread(task).start();
    }

    // 希望Tomcat启动结束后执行的方法
    private void startAfterTomcat() {
        this.nettyServerStart();
        log.info("********************tomcat和netty服务端启动**********************");
    }
}


package org.mage.network.handlers;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class ExceptionHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(ExceptionHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Communications error", cause);
        ctx.close();
    }
    
}

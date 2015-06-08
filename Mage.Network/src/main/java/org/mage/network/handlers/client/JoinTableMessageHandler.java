package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import mage.cards.decks.DeckCardLists;
import mage.game.match.MatchOptions;
import mage.view.TableView;
import org.mage.network.model.CreateTableMessage;
import org.mage.network.model.CreateTableRequest;
import org.mage.network.model.JoinTableMessage;
import org.mage.network.model.JoinTableRequest;


/**
 *
 * @author BetaSteward
 */
public class JoinTableMessageHandler extends SimpleChannelInboundHandler<JoinTableMessage> {

    private ChannelHandlerContext ctx;
    private final BlockingQueue<Boolean> queue = new LinkedBlockingQueue<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }    

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, JoinTableMessage msg) throws Exception {
        queue.offer(msg.getSuccess());
    }
    
    public boolean joinTable(UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password) throws Exception {
        ctx.writeAndFlush(new JoinTableRequest(roomId, tableId, name, playerType, skill, deckList, password));
        return queue.take();
    }
    
}

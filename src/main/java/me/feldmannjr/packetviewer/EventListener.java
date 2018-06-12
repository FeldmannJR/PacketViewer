package me.feldmannjr.packetviewer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.logging.Logger;

public class EventListener {

    @SubscribeEvent
    public void onPlayerJoinedServer(final FMLNetworkEvent.ClientConnectedToServerEvent event)
    {
        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
            @Override
            public void run()
            {

                PacketViewer.LOGGER.info("Joined a server " + getServerIP());
                Channel channel = event.manager.channel();

                channel.pipeline().addBefore("fml:packet_handler", "packetviewer_handler", new ChannelHandler());
                for (String nome : channel.pipeline().names()) {
                    PacketViewer.LOGGER.info(nome + ", ");
                }
                LogUtils.lastServer = getServerIP();

            }
        });
    }


    public String getServerIP()
    {
        Minecraft mc = FMLClientHandler.instance().getClient();
        if (mc.isSingleplayer()) {
            return "localhost";
        }
        return mc.getCurrentServerData().serverIP;

    }

    public void handlePacket(PacketHandler p, boolean isSent)
    {


        if (p.getPacket() instanceof S34PacketMaps) {
            p.log(o("mapId", 0), o("mapScale", 1), o("mapMinX", 3), o("mapMinY", 4), o("mapMaxX", 5), o("mapMaxY", 6), o("mapVisiblePlayersVec4b", 2), o("mapDataBytes", 7));

        }

    }

    public PacketHandler.ObsField o(String nome, int index)
    {
        return new PacketHandler.ObsField(nome, index);
    }

    class ChannelHandler extends ChannelDuplexHandler {


        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
        {
            try {
                handlePacket(new PacketHandler((Packet) msg), true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            super.write(ctx, msg, promise);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
        {
            try {
                handlePacket(new PacketHandler((Packet) msg), false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            super.channelRead(ctx, msg);
        }


    }
}

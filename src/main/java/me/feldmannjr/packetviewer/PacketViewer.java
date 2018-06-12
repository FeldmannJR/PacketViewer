package me.feldmannjr.packetviewer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mod(modid = PacketViewer.MODID, version = PacketViewer.VERSION)
public class PacketViewer {

    public static final String MODID = "PacketViewer";
    public static final String VERSION = "1.0";

    static final String KEY_HANDLER = "packet_handler";
    static final String KEY_PLAYER = "packet_listener_player";


    @EventHandler
    public void init(FMLInitializationEvent event)
    {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dia = format.format(new Date(System.currentTimeMillis()));
        File pasta = new File("./packetlogs/");
        if (!pasta.exists()) {
            pasta.mkdir();
        }
        File f;
        int id = 0;
        do {
            f = new File(pasta, "log-" + id + "-" + dia + ".log");
            id++;
        } while (f.exists());
        LogUtils.saida = f;
    }


    @EventHandler
    public void inject(FMLNetworkEvent.ClientConnectedToServerEvent ev)
    {
        Channel channel = ev.manager.channel();
        channel.pipeline().addBefore(KEY_HANDLER, KEY_PLAYER, new ChannelHandler());
        LogUtils.lastServer = getServerIP();

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
            p.log("mapId", "mapMinX", "mapMinY", "mapMaxX", "mapMaxY", "mapVisiblePlayersVec4b", "mapDataBytes");

        }

    }

    class ChannelHandler extends ChannelDuplexHandler {


        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
        {

            handlePacket(new PacketHandler((Packet) msg), true);
            super.write(ctx, msg, promise);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
        {

            handlePacket(new PacketHandler((Packet) msg), false);
            super.channelRead(ctx, msg);
        }


    }


}

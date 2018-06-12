package me.feldmannjr.packetviewer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mod(modid = PacketViewer.MODID, version = PacketViewer.VERSION)
public class PacketViewer {

    public static final String MODID = "PacketViewer";
    public static final String VERSION = "1.0";


    public static final Logger LOGGER = LogManager.getLogger(MODID);

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
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.saida = f;
        MinecraftForge.EVENT_BUS.register(new EventListener());

    }


}

package me.feldmannjr.packetviewer;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import sun.rmi.runtime.Log;

import java.lang.reflect.Array;

public class PacketHandler {

    private boolean isCancelled;
    private Packet packet;


    public PacketHandler(Packet p)
    {
        this.packet = p;
    }

    public boolean isCancelled()
    {
        return isCancelled;
    }

    public Packet getPacket()
    {
        return packet;
    }


    public void log(ObsField... fields)
    {
        String str = "PacketName: " + getPacket().getClass().getSimpleName() + "[";
        for (int x = 0; x < fields.length; x++) {
            ObsField o = fields[x];
            if (x != 0) {
                str += ", ";
            }
            Object value = ReflectionUtils.getField(o.index, getPacket());
            if (value == null) {
                value = "null";
            }
            String valor;
            if (value.getClass().isArray()) {
                valor = value.getClass().getSimpleName() + "{length=" + Array.getLength(value) + "}";
            } else {
                valor = value.toString();
            }
            str += o.nome + "= '" + valor + "'";

        }
        str += "]";
        LogUtils.writeLine(str);
        //PacketViewer.LOGGER.info(str);

    }

    public void setCancelled(boolean cancelled)
    {
        isCancelled = cancelled;
    }

    public static class ObsField {
        String nome;
        int index;

        public ObsField(String nome, int index)
        {
            this.nome = nome;
            this.index = index;
        }

    }

}

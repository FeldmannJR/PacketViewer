package me.feldmannjr.packetviewer;

import net.minecraft.network.Packet;
import sun.rmi.runtime.Log;

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

    public Object getField(String field)
    {
        return ReflectionUtils.getField(field, packet);
    }

    public void log(String... fields)
    {
        String str = "PacketName: " + getPacket().getClass().getSimpleName() + "[";
        for (int x = 0; x < fields.length; x++) {
            String f = fields[x];
            if (x != 0) {
                f += ", ";
            }
            Object value = getField(f);
            String valor;
            if (value.getClass().isArray()) {
                Object[] ar = (Object[]) value;
                valor = value.getClass().getSimpleName() + "{length=" + ar.length + "}";
            } else {
                valor = value.toString();
            }
            str += f + "= '" + valor + "'";

        }
        str += "]";
        LogUtils.writeLine(str);

    }

    public void setCancelled(boolean cancelled)
    {
        isCancelled = cancelled;
    }

}

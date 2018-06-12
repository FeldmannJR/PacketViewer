package me.feldmannjr.packetviewer;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

public class ReflectionUtils {


    public static Object getField(int field, Object ob)
    {
        int x = 0;
        for (Field f : ob.getClass().getDeclaredFields()) {
            {
                if (x == field) {
                    f.setAccessible(true);
                    try {
                        return f.get(ob);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                x++;
            }
        }
        return "null";
    }
}

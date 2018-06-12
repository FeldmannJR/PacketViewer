package me.feldmannjr.packetviewer;

import java.lang.reflect.Field;

public class ReflectionUtils {


    public static Object getField(String field, Object ob)
    {
        try {
            Field f = ob.getClass().getDeclaredField(field);
            f.setAccessible(true);
            return f.get(ob);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}

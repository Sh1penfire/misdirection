package misdirection.content;

import arc.func.Prov;
import arc.struct.ObjectIntMap;
import arc.struct.ObjectMap.Entry;
import arc.util.Structs;
import mindustry.content.UnitTypes;
import mindustry.gen.EntityMapping;
import mindustry.gen.Entityc;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import misdirection.stealth.units.AreaCloakerUnitType;
import misdirection.stealth.units.SmokeSprayerUnit;

public class ExampleUnits {

    private static Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] types = new Entry[]{
            prov(SmokeSprayerUnit.class, SmokeSprayerUnit::new)
    };

    public static ObjectIntMap<Class<? extends Entityc>> idMap = new ObjectIntMap<>();

    private static <T extends Entityc> Entry<Class<T>, Prov<T>> prov(Class<T> type, Prov<T> prov){
        Entry<Class<T>, Prov<T>> entry = new Entry<>();
        entry.key = type;
        entry.value = prov;
        return entry;
    }

    private static void setupID(){
        int start = 33;

        int[] free = new int[types.length];
        for (int i = start, j = 0; i < EntityMapping.idMap.length; i++) {
            if(EntityMapping.idMap[i] == null) free[j++] = i;
            if(j > free.length - 1) break;
        }

        for (int i = 0; i < free.length; i++) {
            idMap.put(types[i].key, free[i]);
            EntityMapping.idMap[free[i]] = types[i].value;
        }
    }

    public static <T extends Entityc> int classID(Class<T> type){
        return idMap.get(type, -1);
    }

    public static void load(){
        setupID();

        new AreaCloakerUnitType("smoke-sprayer"){{
            health = 694201337;
            flying = true;
            range = 0;
            speed = 6;
            accel = 0.015f;
            drag = 0.0005f;
            maxRange = 0;
            mineRange = 0;
            hitSize = 11;
            constructor = SmokeSprayerUnit::new;
            outlineColor = Pal.darkOutline;
        }};
    }
}

package misdirection.stealth.states;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Structs;
import misdirection.stealth.StealthCondition;
import misdirection.stealth.units.DelegateUnit;
import mindustry.gen.Unit;

/**
 * An interface used to determine if the unit should be invisible or not, supplied by the source of cloaking.
 * "Determines" the behaviour of the stealth.
 */
public abstract class StealthState {
    public Unit unit;
    public DelegateUnit delegate;

    public abstract StealthType type();
    public Seq<StealthCondition> hooks = new Seq<>();
    private static boolean valid = false;

    public boolean valid(){
        if(unit.dead()) return false;
        valid = false;
        Seq<StealthCondition> hooksClone = hooks.copy();
        hooks.each(hook -> {
            if(hook.valid(unit)) {
                valid = true;
                return;
            }
            hooksClone.remove(hook);
            hook.invalid(unit);
        });
        hooks.set(hooksClone);
        return valid;
    }

}

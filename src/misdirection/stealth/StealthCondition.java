package misdirection.stealth;

/**
 * A class used to determine if the current unit should be cloaked. Supplied to StealthState upon instantiation.
 */

import arc.func.Boolf;
import mindustry.gen.Unit;

public interface StealthCondition {
    boolean valid(Unit unit);

    /**
     * Called once the condition's valid method returns false
     * @param unit
     */
    void invalid(Unit unit);
}

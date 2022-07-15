package main.stealth;

import mindustry.gen.Groups;

/**
 * A class used to determine if the current unit should be cloaked. Supplied to StealthState upon instantiation.
 */
public abstract class StealthCondition {

    public abstract boolean valid();

    Groups.unit
}

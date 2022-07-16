package misdirection.stealth.states;

import misdirection.stealth.StealthCondition;
import misdirection.stealth.units.DelegateUnit;
import mindustry.gen.Unit;

/**
 * An interface used to determine if the unit should be invisible or not, supplied by the source of cloaking.
 * "Determines" the behaviour of the stealth.
 */
public abstract class StealthState {
    public Unit cloaked;
    public DelegateUnit delegate;

    public abstract StealthType type();
    public abstract StealthCondition owner();

}

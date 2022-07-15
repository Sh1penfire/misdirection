package main.stealth.states;

import main.stealth.StealthCondition;
import main.stealth.units.DelegateUnit;
import mindustry.gen.MechUnit;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;

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

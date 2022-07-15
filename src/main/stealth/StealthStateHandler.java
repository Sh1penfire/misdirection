package main.stealth;

import arc.struct.Seq;
import main.stealth.states.StealthState;
import main.stealth.states.StealthType;
import main.stealth.units.DelegateUnit;
import mindustry.gen.Groups;
import mindustry.gen.Unit;

/**
 * A singleton class that handles all stealth states with their corresponding units
 */
public class StealthStateHandler {
    public Seq<StealthState> states;
    private static StealthStateHandler instance;

    public static StealthStateHandler getInstance() {
        if(instance == null) instance = new StealthStateHandler();
        return instance;
    }

    /**
     * Handles cloaking units depending on the stealth type. Use only if the specified StealthType is unknown.
     * Ex: Units which change StealthType mid-battle.
     *
     * @param unit The unit being cloaked
     * @param type The supplied stealth type used in cloaking the unit appropriately.
     * @param type The supplied stealth condition used to determine if the unit should drop their stealth
     */
    public void cloakUnit(Unit unit, StealthType type, StealthCondition condition){
        //don't cloak already-cloaked units, nullpointers or unadded units (Cloaked units by default are removed from the entity group)
        if(unit == null || unit.isAdded() == false || unit instanceof DelegateUnit) return;

        states.add(
                new StealthState() {
                    public StealthType type(){
                        return type;
                    }
                    public StealthCondition owner(){
                        return condition;
                    }
                    {
                        cloaked = unit;
                        if(type == StealthType.INVISIBILITY) delegate = assignDelegate(unit);
                        else handleCustomDelegate(unit);
                    }
            }
        );
    }

    /**
     * Assigns a delegate unit to the supplied unit. Only used for handling cases of StealthType.INVISIBILITy
     * @param cloaked The unit which is being assigned a Delegate unit.
     * @return Returns the assigned DelegateUnit instance
     */
    public DelegateUnit assignDelegate(Unit cloaked){
        Groups.unit.removeByID(cloaked.id);
        return new DelegateUnit() {
        };
    }

    public DelegateUnit handleCustomDelegate(Unit cloaked){
        Groups.unit.removeByID(cloaked.id);
        return new DelegateUnit() {
        };
    }

    public void handleState(Unit unit, StealthState state){

    }

    public void handleStealthEnd(Unit unit, StealthState state){

    }
}

package misdirection.stealth;

import arc.struct.Seq;
import misdirection.stealth.states.StealthState;
import misdirection.stealth.states.StealthType;
import misdirection.stealth.units.DelegateUnit;
import mindustry.gen.Groups;
import mindustry.gen.Unit;

/**
 * A singleton class that handles all stealth states with their corresponding units
 */
public class StealthStateHandler {
    public Seq<StealthState> states = new Seq<>();
    private static StealthStateHandler instance;
    private static boolean cloaked = false;

    public static StealthStateHandler getInstance() {
        if(instance == null) instance = new StealthStateHandler();
        return instance;
    }

    /**
     * Handles cloaking units depending on the stealth type. Use only if the specified StealthType is unknown.
     * Ex: Units which change StealthType dynamically.
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
                        this.unit = unit;
                        if(type == StealthType.INVISIBLE) delegate = assignDelegate(unit);
                        else handleCustomDelegate(unit);
                    }
            }
        );
    }

    /**
     * Assigns a delegate unit to the supplied unit. Only used for handling cases of StealthType.INVISIBILITY
     * @param cloaked The unit which is being assigned a Delegate unit.
     * @return Returns the assigned DelegateUnit instance
     */
    public DelegateUnit assignDelegate(Unit cloaked){
        /*
        Groups.unit.removeByID(cloaked.id);
        Groups.all.remove(cloaked);
        Groups.draw.remove(cloaked);
         */
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
        states.remove(state);
    }

    public boolean isCloaked(Unit unit){
        states.each(s -> {
            if(s.unit == unit && s.owner().valid()) cloaked = true;
        });
        return cloaked;
    }

    public void update(){
        states.each(state -> {
            if(!state.owner().valid()) handleStealthEnd(state.unit, state);
        });
    }
}

package misdirection.stealth;

import arc.math.geom.Rect;
import arc.struct.Seq;
import mindustry.gen.*;
import misdirection.stealth.states.StealthState;
import misdirection.stealth.states.StealthType;
import misdirection.stealth.units.DelegateUnit;

/**
 * A singleton class that handles all stealth states with their corresponding units
 */
public class StealthStateHandler {
    public Seq<StealthState> states = new Seq<>();
    private static StealthStateHandler instance;
    private static boolean cloaked = false;
    private static StealthState tmpState;

    public static StealthStateHandler getInstance() {
        if(instance == null) instance = new StealthStateHandler();
        return instance;
    }

    /**
     * Handles cloaking units depending on the stealth type. Use only if the specified StealthType is unknown.
     * Ex: Units which change StealthType dynamically.
     *
     * @param target The unit being cloaked
     * @param type The supplied stealth type used in cloaking the unit appropriately.
     * @param condition The supplied stealth condition used to determine if the unit should drop their stealth
     */
    public void cloakUnit(Unit target, StealthType type, StealthCondition condition){
        //don't cloak already-cloaked units, nullpointers or unadded units (Cloaked units by default are removed from the entity group)
        if(target == null || target.isAdded() == false || target instanceof DelegateUnit) return;

        tmpState = getState(target);
        if(tmpState == null) {
            states.add(
                    new StealthState() {
                        public StealthType type() {
                            return type;
                        }

                        {
                            this.unit = target;
                            hooks.add(condition);
                            if (type == StealthType.INVISIBLE) delegate = assignDelegate(unit);
                            else handleCustomDelegate(unit);
                        }
                    }
            );
            return;
        }
        if(!tmpState.hooks.contains(condition)) tmpState.hooks.add(condition);
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
        cloaked.hitSize(Float.NaN);
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
        /*
        Groups.unit.add(unit);
        Groups.all.add(unit);
        Groups.draw.add(unit);
         */
        unit.hitSize(unit.type.hitSize);
    }

    public boolean isCloaked(Unit unit){
        tmpState = states.find(s -> s.unit == unit);
        return tmpState != null && tmpState.valid();
    }

    public StealthState getState(Unit unit){
        return states.find(s -> s.unit == unit);
    }

    public void update(){
        states.each(state -> {
            if(!state.valid()) handleStealthEnd(state.unit, state);
        });
    }
}

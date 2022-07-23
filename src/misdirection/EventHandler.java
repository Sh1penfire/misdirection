package misdirection;

import arc.Events;
import mindustry.core.GameState.State;
import mindustry.game.EventType.StateChangeEvent;
import mindustry.game.EventType.Trigger;
import misdirection.stealth.units.AreaCloakerUnitType;

import static misdirection.Misdirection.stealth;

public class EventHandler {
    public static void run(){
        Events.run(Trigger.update, () -> stealth.update());
        Events.on(StateChangeEvent.class, e -> {
            if(e.from == State.playing && e.to == State.menu){
                reset();
            }
            if(e.from == State.menu && e.to == State.playing){
                setup();
            }
        });
    }

    public static void reset(){
        AreaCloakerUnitType.areaCloakers.clear();
    }

    public static void setup(){

    }
}

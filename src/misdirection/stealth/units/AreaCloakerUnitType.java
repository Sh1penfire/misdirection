package misdirection.stealth.units;

import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import misdirection.stealth.StealthCondition;

public class AreaCloakerUnitType extends UnitType {
    public static Seq<Unit> areaCloakers = new Seq<>();
    public static StealthCondition areaCloakerCond = new StealthCondition() {
        @Override
        public boolean valid(Unit unit) {
            return areaCloakers.find(u2 -> u2.dst(unit) < u2.range()/2) != null;
        }

        @Override
        public void invalid(Unit unit) {
            Fx.smokeCloud.at(unit.x, unit.y, unit.physicSize());
        }
    };

    public float cloakRange = 50;

    public float area(Unit unit){
        return ((SmokeSprayerUnit) unit).warmup * cloakRange;
    }

    public AreaCloakerUnitType(String name) {
        super(name);
    }

    @Override
    public Unit create(Team team) {
        Unit unit = super.create(team);
        areaCloakers.add(unit);
        return unit;
    }
}

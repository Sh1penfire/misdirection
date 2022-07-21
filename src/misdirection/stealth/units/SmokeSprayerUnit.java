package misdirection.stealth.units;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.EntityCollisions;
import mindustry.gen.*;
import misdirection.Misdirection;
import misdirection.stealth.StealthCondition;
import misdirection.stealth.states.StealthType;

import static misdirection.Misdirection.stealth;
import static misdirection.content.ExampleUnits.classID;
import static misdirection.content.ExampleUnits.idMap;

public class SmokeSprayerUnit extends UnitEntity implements ElevationMovec {

    public float warmup = 0, spinspeed = 2;
    @Override
    public String toString() {
        return "SmokeSprayerUnit#" + id;
    }

    @Override
    public void update() {
        super.update();
        Unit owner = this;
        Groups.unit.intersect(x, y, range(), range(), u -> {
            if(!stealth.isCloaked(u))  stealth.cloakUnit(u, StealthType.INVISIBLE, new StealthCondition() {
                @Override
                public boolean valid() {
                    return !owner.isNull() || u.dst2(owner) < range();
                }
            });
        });
        rotation += (warmup + spinspeed) * Time.delta;
        warmup = Mathf.clamp(warmup + Time.delta * 0.01f, 0, 1);
    }

    @Override
    public int classId() {
        return classID(this.getClass());
    }

    @Override
    public float range() {
        return 50;
    }
}

package misdirection.stealth.units;

import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.EntityCollisions;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.type.UnitType;
import misdirection.Misdirection;
import misdirection.stealth.StealthCondition;
import misdirection.stealth.states.StealthType;

import static misdirection.Misdirection.stealth;
import static misdirection.content.ExampleUnits.classID;
import static misdirection.content.ExampleUnits.idMap;
import static misdirection.stealth.units.AreaCloakerUnitType.areaCloakerCond;

public class SmokeSprayerUnit extends UnitEntity implements ElevationMovec {

    public AreaCloakerUnitType type1 = null;
    public float warmup = 0, spinspeed = 5;

    @Override
    public String toString() {
        return "SmokeSprayerUnit#" + id;
    }

    @Override
    public boolean targetable(Team targeter) {
        return super.targetable(targeter);
    }

    @Override
    public void setType(UnitType type) {
        super.setType(type);
        if(type instanceof AreaCloakerUnitType) type1 = (AreaCloakerUnitType) type;
    }

    @Override
    public void update() {
        super.update();
        dead();
        if(warmup == 1) {
            Unit owner = this;
            Groups.unit.intersect(x - range()/2, y - range()/2, range(), range(), u -> {
                if (u.isFlying() && u.dst(owner) < range()/2 && !(u.type instanceof AreaCloakerUnitType)) {
                    if(!stealth.isCloaked(u)) Fx.smokeCloud.at(u.x, u.y, u.physicSize());
                    stealth.cloakUnit(u, StealthType.INVISIBLE, areaCloakerCond);
                }
            });
        }
        if(Mathf.chance(warmup/15 * Time.delta)) Fx.smoke.at(x + Mathf.range(range()/2), y + Mathf.range(range()/2));
        rotation += (Interp.smooth.apply(warmup) * spinspeed) * Time.delta;
        warmup = Mathf.clamp(warmup + Time.delta * 0.0015f, 0, 1);
    }

    @Override
    public int classId() {
        return classID(this.getClass());
    }

    @Override
    public float range() {
        return type1.area(this);
    }

    @Override
    public void write(Writes w) {
        super.write(w);
        w.f(warmup);
    }

    @Override
    public void read(Reads r) {
        super.read(r);
        warmup = r.f();
    }

    @Override
    public void afterRead() {
        super.afterRead();
        AreaCloakerUnitType.areaCloakers.add(this);
    }
}

package misdirection;

import arc.Core;
import arc.struct.Seq;
import arc.util.*;
import mindustry.gen.UnitEntity;
import misdirection.content.ExampleUnits;
import misdirection.stealth.StealthStateHandler;
import mindustry.Vars;
import mindustry.mod.*;
import rhino.ImporterTopLevel;
import rhino.NativeJavaPackage;

public class Misdirection extends Mod{

    public static NativeJavaPackage p = null;
    public static StealthStateHandler stealth;

    public Misdirection(){
        stealth = StealthStateHandler.getInstance();
    }

    @Override
    public void init() {
        super.init();

        ImporterTopLevel scope = (ImporterTopLevel) Vars.mods.getScripts().scope;

        Seq<String> packages = Seq.with(
                "misdirection",
                "misdirection.stealth",
                "misdirection.stealth.units",
                "misdirection.stealth.states",
                "misdirection.content"
        );

        packages.each(name -> {
            p = new NativeJavaPackage(name, Vars.mods.mainLoader());

            p.setParentScope(scope);

            scope.importPackage(p);
        });

        Vars.mods.getScripts().runConsole("const stealthcf = method => new StealthCondition(){valid: method}");

        Core.settings.put("console", true);
    }

    @Override
    public void loadContent(){
        ExampleUnits.load();
    }

}

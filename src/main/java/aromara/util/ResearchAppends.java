package aromara.util;

import java.util.HashMap;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ResearchAppends {

    static HashMap<String, AspectList> appendPairs = new HashMap<String, AspectList>();

    public static void initAppends() {

        appendPairs.put("TCA_SCENTBURNING", new AspectList()
                .add(Aspect.MAN, 15)
                .add(Aspect.PLANT, 10)
                .add(Aspect.VOID, 5)
                .add(Aspect.FIRE, 20)
                .add(Aspect.UNDEAD, 10)
                .add(Aspect.AURA, 5)
                .add(Aspect.AIR, 10)
                .add(Aspect.SENSES, 20)
                .add(Aspect.FLUX, 15));

        appendPairs.put("TCA_SCENTBOILING", new AspectList()
                .add(Aspect.BEAST, 20)
                .add(Aspect.MAGIC, 10)
                .add(Aspect.PROTECT, 30)
                .add(Aspect.AVERSION, 5)
                .add(Aspect.SOUL, 5)
                .add(Aspect.MOTION, 20)
                .add(Aspect.FIRE, 20)
                .add(Aspect.FLIGHT, 5));
    }



    public static AspectList getList(String name) {
        return appendPairs.get(name);
    }
}

package aromara.util;

import java.util.HashMap;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ResearchAppends {

    static HashMap<String, AspectList> appendPairs = new HashMap<String, AspectList>();

    public static void initAppends() {

        appendPairs.put("TCA_SCENTBURNING", new AspectList()
                .add(Aspect.MAN, 15)
                .add(Aspect.DESIRE, 5)
                .add(Aspect.PLANT, 10)
                .add(Aspect.FIRE, 20)
                .add(Aspect.VOID, 5)
                .add(Aspect.UNDEAD, 10)
                .add(Aspect.AURA, 5)
                .add(Aspect.AIR, 10)
                .add(Aspect.FLUX, 15));
    }

    public static AspectList getList(String name) {
        return appendPairs.get(name);
    }
}

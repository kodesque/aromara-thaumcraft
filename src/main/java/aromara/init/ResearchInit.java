package aromara.init;

import aromara.root.Main;
import aromara.util.ResearchAppends;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.research.ResearchCategories;

public class ResearchInit {

    public static void initResearch() {

        ResearchCategories.registerCategory(
                "SCENTMIXING",
                "FIRSTSTEPS",
                new AspectList().add(Aspect.ALCHEMY, 5),
                new ResourceLocation(Main.MODID + ":textures/research/" + "r_basescentmixing" + ".png"),
                new ResourceLocation(Main.MODID + ":textures/research/" + "background.png"),
                new ResourceLocation(Main.MODID + ":textures/research/" + "background_overlay.png")
                );

        registerResearchLocation(new ResourceLocation("aromara:research/scentmixing"));

        ResearchAppends.initAppends();

    }

    public static void registerResearchLocation(ResourceLocation loc) {
        if (!CommonInternals.jsonLocs.containsKey(loc.toString())) {
            CommonInternals.jsonLocs.put(loc.toString(), loc);
        }
    }


}

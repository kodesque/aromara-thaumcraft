package aromara.init;

import aromara.util.Researches;
import aromara.util.TCAResearchBuilder;
import aromara.util.TCAResearchManager;
import aromara.util.Researches.TCAkey;
import aromara.util.Researches.TCkey;
import aromara.common.research.TCAResearch;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.research.ResearchEntry.EnumResearchMeta;

public class TCAResearchInit {

    public static void initResearch() {

        TCAResearchBuilder.start(TCAkey.BASESCENTMIXING.get(), TCAResearch.BASESCENTMIXING)
        .useCategory(Researches.CAT_TCA)
        .onPosition(0, 0)
        .useShape(EnumResearchMeta.ROUND)
        .addParents(TCkey.HEDGEALCHEMY.get())
        .addStage(stage -> stage
                .assignText(TCAkey.BASESCENTMIXING.get(), 1)
                .giveWarp(0)
                )
        .addStage(stage -> stage
                .assignText(TCAkey.BASESCENTMIXING.get(), 2)
                )
        .finish();
    }

}

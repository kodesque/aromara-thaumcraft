package aromara.util;

import java.lang.reflect.Method;

import thaumcraft.api.research.ResearchEntry;
import thaumcraft.common.lib.research.ResearchManager;

public class TCAResearchManager {

    public static void addResearchDirect(ResearchEntry entry) {
        try {
            Method m = ResearchManager.class
                    .getDeclaredMethod("addResearchToCategory", ResearchEntry.class);

            m.setAccessible(true);
            m.invoke(null, entry);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

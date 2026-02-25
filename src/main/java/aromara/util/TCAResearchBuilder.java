package aromara.util;

import java.util.Arrays;
import java.util.function.Consumer;

import aromara.common.research.TCAResearch;
import aromara.root.Main;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.research.ResearchAddendum;
import thaumcraft.api.research.ResearchEntry;
import thaumcraft.api.research.ResearchStage;
import thaumcraft.api.research.ResearchStage.Knowledge;

public class TCAResearchBuilder {

    private ResearchEntry entry;
    private String key;

    private TCAResearchBuilder(String key, ResearchEntry entry) {
        entry = new ResearchEntry();
        this.entry = entry;
        this.key = key;
        this.entry.setKey(key);
        this.entry.setName("research." + key + ".title");
        this.entry.setAddenda(new ResearchAddendum[0]);
        this.entry.setIcons(new Object[0]);
        this.entry.setCategory(Researches.CAT_TCA);
        this.entry.setStages(new ResearchStage[0]);
        this.entry.setMeta(new ResearchEntry.EnumResearchMeta[0]);
    }

    public static TCAResearchBuilder start(String key, ResearchEntry entry) {
        return new TCAResearchBuilder(key, entry);
    }

    public TCAResearchBuilder useCategory(String category) {
        this.entry.setCategory(category);
        return this;
    }

    public TCAResearchBuilder useIcon(Item... icons) {

        if (icons.length == 0) {
            this.entry.setIcons(new Object[] {Main.MODID + ":textures/research/" + this.key.toLowerCase() + ".png"});
        }

        ItemStack[] actual = new ItemStack[icons.length];

        for (int i = 0; i < icons.length; i++) {
            actual[i] = new ItemStack(icons[i]);
        }

        this.entry.setIcons(actual);
        return this;
    }

    public TCAResearchBuilder onPosition(int column, int row) {
        this.entry.setDisplayColumn(column);
        this.entry.setDisplayRow(row);
        return this;
    }

    public TCAResearchBuilder useShape(ResearchEntry.EnumResearchMeta... shape) {
        this.entry.setMeta(shape);
        return this;
    }


    public TCAResearchBuilder addParents(String... parents) {
        this.entry.setParents(parents);
        return this;
    }

    public TCAResearchBuilder addSiblings(String... siblings) {
        this.entry.setSiblings(siblings);
        return this;
    }

    public TCAResearchBuilder giveItem(ItemStack... items) {
        this.entry.setRewardItem(items);
        return this;
    }

    public TCAResearchBuilder giveKnowledge(Knowledge... know) {
        this.entry.setRewardKnow(know);
        return this;
    }

    public TCAResearchBuilder addStage(Consumer<StageBuilder> consumer) {
        StageBuilder builder = new StageBuilder();
        consumer.accept(builder);

        ResearchStage[] old = this.entry.getStages();
        ResearchStage[] newStages = Arrays.copyOf(old, old.length + 1);
        newStages[old.length] = builder.finish();
        this.entry.setStages(newStages);

        return this;
    }

    public void finish() {
        TCAResearchManager.addResearchDirect(this.entry);
    }

    //    public void register() {
    //        ResearchManager.addResearch(entry);
    //    }

    public static class StageBuilder {
        private ResearchStage stage;

        public StageBuilder() {
            this.stage = new ResearchStage();
            this.stage.setResearch(new String[0]);
            this.stage.setRecipes(new ResourceLocation[0]);
            this.stage.setCraft(new Object[0]);
            this.stage.setCraftReference(new int[0]);
            this.stage.setKnow(new Knowledge[0]);
        }

        public StageBuilder assignText(String codeName, int stageNum) {
            this.stage.setText("research." + codeName + ".stage." + stageNum);
            return this;
        }

        public StageBuilder showRecipes(ResourceLocation... recipes) {
            this.stage.setRecipes(recipes);
            return this;
        }

        public StageBuilder requireCrafts(Object... craft) {
            this.stage.setCraft(craft);
            return this;
        }

        public StageBuilder requireCraftsById(int... craftRef) {
            this.stage.setCraftReference(craftRef);
            return this;
        }

        public StageBuilder giveKnowledge(Knowledge... know) {
            this.stage.setKnow(know);
            return this;
        }

        public StageBuilder requireResearch(String... research) {
            this.stage.setResearch(research);
            return this;
        }

        public StageBuilder useIcon(ItemStack... icons) {

            String[] actual = new String[icons.length];

            for (int i = 0; i < icons.length; i++) {
                actual[i] = icons[i].toString();
            }

            this.stage.setResearchIcon(actual);
            return this;
        }

        public StageBuilder giveWarp(int warp) {
            this.stage.setWarp(warp);
            return this;
        }

        public ResearchStage finish() {
            return this.stage;
        }
    }

}

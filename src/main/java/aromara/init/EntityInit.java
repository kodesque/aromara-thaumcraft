package aromara.init;

import aromara.common.entities.EntityItemComponent;
import aromara.root.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit {

    public static void preInitEntities() {

        int id = 0;

        EntityRegistry.registerModEntity(new ResourceLocation(Main.MODID, EntityItemComponent.id),
                EntityItemComponent.class,
                EntityItemComponent.id,
                id++,
                Main.instance,
                64,
                20,
                true
                );
    }

}

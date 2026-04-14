package aromara.util;

import java.util.function.Supplier;

import aromara.init.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;

public class NBTUtil {

    public enum EnumSubtype {
        AUGMENT("augment"),
        INTERNAL("id");

        private final String value;

        EnumSubtype(String value) {
            this.value = value;
        }

        public String get() {
            return this.value;
        }
    }

}

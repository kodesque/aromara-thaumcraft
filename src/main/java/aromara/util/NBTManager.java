package aromara.util;

import java.util.UUID;

import javax.annotation.Nullable;

import aromara.root.Main;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTManager {

    public enum EnumGroups {
        AUGMENT("augment",
                new NameTypePair(EnumValueNames.MAIN.getName(), String.class)),
        MEMORY("memory",
                new NameTypePair(EnumValueNames.MAIN.getName(), Integer.class),
                new NameTypePair(EnumValueNames.SUB.getName(), Integer.class)),
        SCENT("scent",
                new NameTypePair(EnumValueNames.TOP_NOTES, String.class),
                new NameTypePair(EnumValueNames.HEART_NOTES, String.class),
                new NameTypePair(EnumValueNames.BASE_NOTES, String.class));

        private final String groupName;
        private final NameTypePair[] pair;
        private final Class<?>[] clazz;

        EnumGroups(EnumValueNames groupName, NameTypePair... pair) {
            this.groupName = groupName;
            this.pair = pair;

            this.clazz = null;
        }

        EnumGroups(EnumValueNames groupName, Class<?>... clazz) {
            this.groupName = groupName.getName();
            this.clazz = clazz;

            this.pair = null;
        }

        public String getName() {
            return this.groupName;
        }

        public NameTypePair[] getAllPairs() {
            return this.pair;
        }

        public NameTypePair getPair(String name) {
            if (this.pair != null) {
                for (NameTypePair element : this.pair) {
                    if (element.getName().equals(name))
                        return element;
                }
            }
            return null;
        }

        public NameTypePair getPair(int i) {
            if (this.pair != null)
                return this.pair[i];
            return null;
        }

        public Class getClazz(int index) {
            return this.clazz != null ? this.clazz[index] : Object.class;
        }
    }

    public enum EnumValueNames {
        MAIN("main"),
        SUB("sub"),
        TOP_NOTES("top_notes"),
        HEART_NOTES("heart_notes"),
        BASE_NOTES("base_notes");

        private final String value;

        EnumValueNames(String value) {
            this.value = value;
        }

        public String getName() {
            return this.value;
        }
    }

    public static class NameTypePair {

        private final String valueName;
        private final Class<?> clazz;

        NameTypePair(String valueName, Class<?> clazz) {
            this.valueName = valueName;
            this.clazz = clazz;
        }

        public String getName() {
            return this.valueName;
        }

        public Class<?> getClazz() {
            return this.clazz;
        }

        @Nullable
        NameTypePair getPair() {
            return this;
        }

    }

    public static class NameValuePair {

        private final String valueName;
        private final Object value;

        public NameValuePair(String valueName, Object value) {
            this.valueName = valueName;
            this.value = value;
        }

        public String getName() {
            return this.valueName;
        }

        public Object getValue() {
            return this.value;
        }
    }

    public static ItemStack apply(ItemStack stack, EnumGroups group, @Nullable NameValuePair... value) {

        ItemStack copy = stack.copy();
        NBTTagCompound copyTag = copy.getOrCreateSubCompound(Main.MODID);
        if (value != null) {

            copyTag = copyTag.getCompoundTag(group.getName());

            String[] names = new String[value.length];

            for (int i = 0; i < value.length; i++) {
                names[i] = value[i].getName();
            }
            if (!has(stack, group, names)) {
                for (NameValuePair element : value) {
                    if (group.getPair(element.getName()) != null) {
                        if (group.getPair(element.getName()).getClazz().equals(element.getValue().getClass())) {

                            if (element.getValue().getClass().equals(Integer.class)) {

                            } else if (element.getValue().getClass().isInstance(Double.class)) {
                                copyTag.setDouble(element.getName(), (Double)element.getValue());
                            } else if (element.getValue().getClass().isInstance(String.class)) {
                                copyTag.setString(element.getName(), (String)element.getValue());
                            } else if (element.getValue().getClass().isInstance(UUID.class)) {
                                copyTag.setUniqueId(element.getName(), (UUID)element.getValue());;
                            } else if (element.getValue().getClass().isInstance(Boolean.class)) {
                                copyTag.setBoolean(element.getName(), (Boolean)element.getValue());
                            }
                        } else
                            throw new IllegalArgumentException("NBTManager tried to use an incorrect data type");
                    }
                }
            }
        } else {
            if (!has(stack, group)) {
                copyTag.setTag(group.getName(), null);
            }
        }

        return copy;
    }

    public static ItemStack remove(ItemStack stack, @Nullable EnumGroups group, @Nullable String... value) {

        ItemStack copy = stack.copy();
        NBTTagCompound copyTag = copy.getOrCreateSubCompound(Main.MODID);

        if (value == null) {
            copyTag.removeTag(group.getName());
            return copy;
        }
        if (group == null) {
            copy.removeSubCompound(Main.MODID);
            return copy;
        }

        if (has(stack, group, value)) {
            for (String element : value) {
                copyTag.removeTag(element);
            }
        }

        return copy;
    }

    public static boolean has(ItemStack stack, EnumGroups group, @Nullable String... value) {

        int found = 0;
        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);
        if (nbt != null && value != null) {
            nbt = nbt.getCompoundTag(group.getName());
            if (nbt != null) {
                for (String element : value) {
                    if (nbt.hasKey(element)) {
                        found++;
                    }
                }
            }
        } else if (nbt != null) {
            if (nbt.getTag(group.getName()) != null) {
                found = -1;
            }
        }

        return value != null ? (found == value.length) : (found == -1);
    }

}

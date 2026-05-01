package aromara.util;

import java.util.UUID;

import aromara.root.Main;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class NBTManager {

    public interface INBTGroupValues {

        String getValueName();

        Class<?> getClazz();

        /* intended to use when "0" doesn't necessarily mean nothing, e.g translation means */

        boolean isSpecialCase();
    }

    public enum EnumGroups {
        SCENTCRUDE("scentcrude", ValuesCrude.class);

        private final String groupName;
        private final Class<? extends INBTGroupValues> clazz;

        EnumGroups(String groupName, Class<? extends INBTGroupValues> clazz) {
            this.groupName = groupName;
            this.clazz = clazz;
        }

        public String getName() {
            return this.groupName;
        }

        public Class<? extends INBTGroupValues> getClazz() {
            return this.clazz;
        }

        public enum ValuesCrude implements INBTGroupValues {
            MAIN(EnumGeneralNames.MAIN.getName(), String.class);

            private final String valueName;
            private final Class<?> clazz;

            ValuesCrude(String valueName, Class<?> clazz) {
                this.valueName = valueName;
                this.clazz = clazz;
            }

            @Override
            public String getValueName() {
                return this.valueName;
            }

            @Override
            public Class<?> getClazz() {
                return this.clazz;
            }

            @Override
            public boolean isSpecialCase() {
                return false;
            }
        }

    }

    public enum EnumGeneralNames {
        MAIN("main"),
        SUB("sub");

        private final String value;

        EnumGeneralNames(String value) {
            this.value = value;
        }

        public String getName() {
            return this.value;
        }
    }

    public static class ValuePair<T> {

        private final INBTGroupValues type;
        private final T value;
        private final EnumGroups group;

        public ValuePair(EnumGroups group, INBTGroupValues type, T value) {
            this.type = type;
            this.value = value;
            this.group = group;
        }

        public Object getValue() {
            return this.value;
        }

        public INBTGroupValues getType() {
            return this.type;
        }

        public EnumGroups getGroup() {
            return this.group;
        }
    }

    public static NBTTagCompound apply(ItemStack stack, ValuePair<?>... value) {

        NBTTagCompound nbt = stack.getOrCreateSubCompound(Main.MODID);

        for (ValuePair<?> element : value) {

            NBTTagCompound group;

            if (has(stack, element.getGroup())) {
                group = nbt.getCompoundTag(element.getGroup().getName());
            } else {
                group = new NBTTagCompound();
                nbt.setTag(element.getGroup().getName(), group);
            }

            if (element.getGroup().getClazz().equals(element.getType().getClass())) {
                if (element.getType().getClazz().isInstance(element.getValue())) {
                    if (Integer.class.isInstance(element.getValue())) {
                        group.setInteger(element.getType().getValueName(), (Integer)element.getValue());
                    } else if (Double.class.isInstance(element.getValue())) {
                        group.setDouble(element.getType().getValueName(), (Double)element.getValue());
                    } else if (String.class.isInstance(element.getValue())) {
                        group.setString(element.getType().getValueName(), (String)element.getValue());
                    } else if (UUID.class.isInstance(element.getValue())) {
                        group.setUniqueId(element.getType().getValueName(), (UUID)element.getValue());;
                    } else if (Boolean.class.isInstance(element.getValue())) {
                        group.setBoolean(element.getType().getValueName(), (Boolean)element.getValue());
                    }
                } else
                    throw new IllegalArgumentException("NBTManager: Data type mismatch caught!");
            } else
                throw new IllegalArgumentException("NBTManager: Class mismatch caught!");

        }

        collectGarbage(stack);

        return nbt;
    }

    /* intended for special cases */

    public static NBTTagCompound applySoft(ItemStack stack, ValuePair<?>... value) {

        NBTTagCompound nbt = stack.getOrCreateSubCompound(Main.MODID);

        for (ValuePair<?> element : value) {
            if (!has(stack, element.getGroup())) {
                apply(stack, value);
            }
        }

        return nbt;
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(ItemStack stack, EnumGroups group, INBTGroupValues type) {

        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);
        if (nbt == null) return null;

        if (!nbt.hasKey(group.getName())) return null;

        NBTTagCompound nbt_group = nbt.getCompoundTag(group.getName());

        if (!nbt_group.hasKey(type.getValueName())) return null;

        Object value;

        String key = type.getValueName();

        Class<?> clazz = type.getClazz();

        if (clazz == Integer.class) {
            value = nbt_group.getInteger(key);
        } else if (clazz == Double.class) {
            value = nbt_group.getDouble(key);
        } else if (clazz == String.class) {
            value = nbt_group.getString(key);
        } else if (clazz == Boolean.class) {
            value = nbt_group.getBoolean(key);
        } else if (clazz == UUID.class) {
            value = nbt_group.getUniqueId(key);
        } else
            return null;

        return (T) clazz.cast(value);
    }

    public static void remove(ItemStack stack, ValuePair<?>... value) {

        NBTTagCompound nbt = stack.getOrCreateSubCompound(Main.MODID);

        if (has(stack, value)) {
            for (ValuePair<?> element : value) {
                NBTTagCompound group = nbt.getCompoundTag(element.getGroup().getName());
                group.removeTag(element.getType().getValueName());
            }
        }

        collectGarbage(stack);
    }

    public static void remove(ItemStack stack, EnumGroups... group) {

        NBTTagCompound nbt = stack.getOrCreateSubCompound(Main.MODID);

        for (EnumGroups value : group) {
            nbt.removeTag(value.groupName);
        }

        collectGarbage(stack);
    }

    /*
     * use if you need to find fields with specific values
     * is intended to skid if no group is found
     * */

    public static boolean has(ItemStack stack, ValuePair<?>... value) {
        int found = 0;

        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);
        if (nbt != null) {
            for (ValuePair<?> element : value) {
                NBTTagCompound group = nbt.getCompoundTag(element.getGroup().getName());
                if (group != null) {

                    Object val = NBTManager.get(stack, element.getGroup(), element.getType());

                    if (val != null && val.equals(element.getValue())) {
                        if (group.hasKey(element.getType().getValueName())) {
                            found++;
                        }
                    }
                }
            }
        }

        return found == value.length;
    }

    public static boolean has(ItemStack stack, EnumGroups... groups) {
        int found = 0;

        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);
        for (EnumGroups element : groups) {
            if (nbt != null) {
                if (nbt.hasKey(element.getName())) {
                    found++;
                }
            }
        }
        return found == groups.length;
    }

    public static void collectGarbage(ItemStack stack) {

        NBTTagCompound root = stack.getSubCompound(Main.MODID);
        if (root == null) return;

        for (EnumGroups groupEnum : EnumGroups.values()) {

            String groupName = groupEnum.getName();

            if (!root.hasKey(groupName)) {
                continue;
            }

            NBTTagCompound group = root.getCompoundTag(groupName);

            boolean groupEmpty = true;

            Class<? extends NBTManager.INBTGroupValues> valuesClass = groupEnum.clazz;

            for (NBTManager.INBTGroupValues valueEnum :
                valuesClass.getEnumConstants()) {

                String key = valueEnum.getValueName();

                if (!group.hasKey(key)) {
                    continue;
                }

                boolean shouldRemove = false;

                Class<?> type = valueEnum.getClazz();

                if (type == Integer.class) {
                    if (group.getInteger(key) == 0) {
                        shouldRemove = true;
                    }
                }

                else if (type == Double.class) {
                    if (group.getDouble(key) == 0.0) {
                        shouldRemove = true;
                    }
                }

                else if (type == String.class) {
                    if (group.getString(key).isEmpty()) {
                        shouldRemove = true;
                    }
                }

                else if (type == Boolean.class) {
                    if (!group.getBoolean(key)) {
                        shouldRemove = true;
                    }
                }

                else if (type == UUID.class) {
                    UUID uuid = group.getUniqueId(key);
                    if (uuid.getMostSignificantBits() == 0L &&
                            uuid.getLeastSignificantBits() == 0L) {
                        shouldRemove = true;
                    }
                }

                if (shouldRemove && !valueEnum.isSpecialCase()) {
                    group.removeTag(key);
                } else {
                    groupEmpty = false;
                }
            }

            if (groupEmpty || group.getKeySet().isEmpty()) {
                root.removeTag(groupName);
            }

            if (root.getKeySet().isEmpty()) {
                stack.removeSubCompound(Main.MODID);
            }
        }
    }

    // crafting utils start

    public enum EnumFunc {
        APPLY,
        REMOVE
    }

    public static ItemStack mutatePairs(ItemStack stack, EnumFunc func, ValuePair<?>... pair) {
        ItemStack copy = stack.copy();

        if (func == EnumFunc.APPLY) {
            NBTManager.apply(copy, pair);
        } else if (func == EnumFunc.REMOVE) {
            NBTManager.remove(copy, pair);
        }

        return copy;
    }

    public static ItemStack mutateMeta(ItemStack stack, int metadata) {
        ItemStack copy = stack.copy();

        copy.setItemDamage(metadata);

        return copy;
    }

    public static ItemStack mutateGroup(ItemStack stack, EnumGroups... group) {
        ItemStack copy = stack.copy();

        if (group != null) {
            NBTManager.remove(copy, group);
        }

        return copy;
    }

}

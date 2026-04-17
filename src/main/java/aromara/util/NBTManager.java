package aromara.util;

import java.util.UUID;

import javax.annotation.Nullable;

import aromara.root.Main;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTManager {

    public interface INBTGroupType {

        String getGroupName();

        String getValueName();

        Class<?> getClazz();
    }

    public enum TypeAugment implements INBTGroupType {
        MAIN(EnumGeneralNames.MAIN, String.class);

        private static final String groupName = "augment";

        private final String valueName;
        private final Class<?> clazz;

        TypeAugment(EnumGeneralNames valueName, Class<?> clazz) {
            this.valueName = valueName.getName();
            this.clazz = clazz;
        }

        @Override
        public String getGroupName() {
            return groupName;
        }

        @Override
        public String getValueName() {
            return this.valueName;
        }

        @Override
        public Class<?> getClazz() {
            return this.clazz;
        }

    }

    public enum TypeMemory implements INBTGroupType {
        MAIN(EnumGeneralNames.MAIN, String.class),
        SUB(EnumGeneralNames.SUB, String.class);

        private static final String groupName = "memory";

        private final String valueName;
        private final Class<?> clazz;

        TypeMemory(EnumGeneralNames valueName, Class<?> clazz) {
            this.valueName = valueName.getName();
            this.clazz = clazz;
        }

        @Override
        public String getGroupName() {
            return groupName;
        }

        @Override
        public String getValueName() {
            return this.valueName;
        }

        @Override
        public Class<?> getClazz() {
            return this.clazz;
        }
    }

    public enum TypeScent implements INBTGroupType {
        TOP("top_note", String.class),
        HEART("heart_note", String.class),
        BASE("base_note", String.class);

        private static final String groupName = "scent";

        private final String valueName;
        private final Class<?> clazz;

        TypeScent(String valueName, Class<?> clazz) {
            this.valueName = valueName;
            this.clazz = clazz;
        }

        @Override
        public String getGroupName() {
            return groupName;
        }

        @Override
        public String getValueName() {
            return this.valueName;
        }

        @Override
        public Class<?> getClazz() {
            return this.clazz;
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

        private final INBTGroupType type;
        private final T value;

        public ValuePair(INBTGroupType type, T value) {
            this.type = type;
            this.value = value;
        }

        public Object getValue() {
            return this.value;
        }

        public INBTGroupType getType() {
            return this.type;
        }
    }

    public static ItemStack apply(ItemStack stack, ValuePair<?>... value) {

        ItemStack copy = stack.copy();
        NBTTagCompound copyTag = copy.getOrCreateSubCompound(Main.MODID);

        for (ValuePair<?> element : value) {
            if (!has(stack, value)) {

                NBTTagCompound group = copyTag.getCompoundTag(element.getType().getGroupName());

                if (!copyTag.hasKey(element.getType().getGroupName())) {
                    copyTag.setTag(element.getType().getGroupName(), group);
                }

                if (element.getType().getClazz().equals(element.getValue().getClass())) {

                    if (element.getValue().getClass().equals(Integer.class)) {

                    } else if (Double.class.isInstance(element.getValue().getClass())) {
                        copyTag.setDouble(element.getType().getValueName(), (Double)element.getValue());
                    } else if (String.class.isInstance(element.getValue().getClass())) {
                        copyTag.setString(element.getType().getValueName(), (String)element.getValue());
                    } else if (UUID.class.isInstance(element.getValue().getClass())) {
                        copyTag.setUniqueId(element.getType().getValueName(), (UUID)element.getValue());;
                    } else if (Boolean.class.isInstance(element.getValue().getClass())) {
                        copyTag.setBoolean(element.getType().getValueName(), (Boolean)element.getValue());
                    }
                } else
                    throw new IllegalArgumentException("NBTManager tried to use an incorrect data type");
            }
        }

        return copy;
    }

    public static ItemStack remove(ItemStack stack, ValuePair<?>... value) {

        ItemStack copy = stack.copy();
        NBTTagCompound copyTag = copy.getOrCreateSubCompound(Main.MODID);

        if (has(stack, value)) {
            for (ValuePair<?> element : value) {
                copyTag.removeTag(element.getType().getValueName());
            }
        }

        return copy;
    }

    public static ItemStack remove(ItemStack stack, INBTGroupType group) {

        ItemStack copy = stack.copy();
        NBTTagCompound copyTag = copy.getOrCreateSubCompound(Main.MODID);

        copyTag.removeTag(group.getGroupName());

        return copy;

    }

    public static boolean has(ItemStack stack, ValuePair<?>... value) {
        int found = 0;

        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);
        if (nbt != null) {
            for (ValuePair<?> element : value) {
                NBTTagCompound group = nbt.getCompoundTag(element.getType().getGroupName());
                if (group.hasKey(element.getType().getValueName())) {
                    if (nbt.hasKey(element.getType().getValueName())) {
                        found++;
                    }
                }
            }
        }


        return found == value.length;
    }

    public static boolean has(ItemStack stack, INBTGroupType... type) {
        int found = 0;

        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);
        for (INBTGroupType element : type) {
            if (nbt != null) {
                if (nbt.getTag(element.getGroupName()) != null) {
                    found++;
                }
            }
        }
        return found == type.length;
    }

}

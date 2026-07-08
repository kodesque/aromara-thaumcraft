package aromara.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class RiddleHandler {

    public static String process(TextComponentTranslation ttc, List<Integer> chosen, AspectList list) {

        String formatted = ttc.getFormattedText();

        StringBuilder actual = new StringBuilder();

        actual.append(TextFormatting.GRAY)
        .append(TextFormatting.ITALIC);

        boolean purple = false;

        int keyIn = 0;
        int chosenIn = 0;

        for (int i = 0; i < formatted.length(); i++) {

            char c = formatted.charAt(i);

            if (c == '@') {

                if (chosenIn < chosen.size() && keyIn == chosen.get(chosenIn)) {
                    purple = true;

                    actual.append(TextFormatting.DARK_PURPLE)
                    .append(TextFormatting.ITALIC);

                    chosenIn++;
                }

                keyIn++;
                continue;
            }

            if (purple && c == ' ') {

                purple = false;

                Aspect aspect = list.getAspects()[chosenIn - 1];

                actual.append("(")
                .append(list.getAmount(aspect))
                .append(")")
                .append(TextFormatting.RESET)
                .append(TextFormatting.GRAY)
                .append(TextFormatting.ITALIC);
            }

            actual.append(c);
        }

        if (purple) {
            Aspect aspect = list.getAspects()[chosenIn - 1];

            actual.append("(")
            .append(list.getAmount(aspect))
            .append(")");
        }

        return actual.toString();
    }

    public static List<Integer> roll(AspectList list, World world) {
        List<Integer> result = new ArrayList<Integer>();

        for (int i = 0; i < 3; i++) {
            int r = world.rand.nextInt(list.size());

            if (!result.contains(r)) {
                result.add(r);
            } else {
                result.add(Math.min(list.size(), r + 1));
            }
        }

        return result;
    }

}

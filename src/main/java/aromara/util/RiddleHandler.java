package aromara.util;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.api.aspects.AspectList;

public class RiddleHandler {

    public static TextComponentString process(TextComponentTranslation ttc) {

        String formatted = ttc.getFormattedText();

        TextComponentString root = new TextComponentString("");

        int count = 0;
        int last = 0;

        for (int i = 0; i < formatted.length(); i++) {

            if (formatted.charAt(i) == '@') {

                if (i > last) {
                    root.appendSibling(
                            new TextComponentString(formatted.substring(last, i))
                            );
                }

                TextFormatting color = TextFormatting.DARK_PURPLE;

                int nextStart = i + 1;

                int nextAt = formatted.indexOf('@', nextStart);
                int end = (nextAt == -1) ? formatted.length() : nextAt;

                TextComponentString colored =
                        new TextComponentString(formatted.substring(nextStart, end));

                colored.setStyle(new Style().setColor(color));

                root.appendSibling(colored);

                root.appendSibling(
                        new TextComponentString(TextFormatting.RESET.toString())
                        );

                count++;
                last = end;
                i = end - 1;
            }
        }

        if (last < formatted.length()) {
            root.appendSibling(
                    new TextComponentString(formatted.substring(last))
                    );
        }

        return root;
    }

}

package ovh.roro.libraries.componentutil.test;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ovh.roro.libraries.componentutil.ComponentUtil;

import java.util.Map;
import java.util.Objects;

class ComponentCentererTests {

    @BeforeAll
    static void setup() {
        ComponentUtil.loadCustom(Objects.requireNonNull(ComponentCentererTests.class.getResourceAsStream("/font_data"), "font_data not found"));
    }

    @Test
    @DisplayName("Width validation")
    void validateWidth() {
        Assertions.assertEquals(4.0F, ComponentUtil.widthVanilla(Component.literal(" ")));
        Assertions.assertEquals(5.0F, ComponentUtil.widthVanilla(Component.literal(" ").withStyle(ChatFormatting.BOLD)));

        Assertions.assertEquals(173.0F, ComponentUtil.widthVanilla(Component.literal("Medium text that takes more space")));
        Assertions.assertEquals(206.0F, ComponentUtil.widthVanilla(Component.literal("Medium text that takes more space").withStyle(ChatFormatting.BOLD)));
    }

    @Test
    @DisplayName("Space padding validation")
    void validateSpacePadding() {
        Map<String, String> normalValues = Map.of(
                "", "                                      ",
                "Short text", "                               Short text",
                "Medium text that takes more space", "                Medium text that takes more space",
                "Long text that should have no padding at all since it already covers more than chat width", "Long text that should have no padding at all since it already covers more than chat width"
        );

        Map<String, String> boldValues = Map.of(
                "", "                                      ",
                "Short text", "                              Short text",
                "Medium text that takes more space", "            Medium text that takes more space",
                "Long text that should have no padding at all since it already covers more than chat width", "Long text that should have no padding at all since it already covers more than chat width"
        );

        for (Map.Entry<String, String> entry : normalValues.entrySet()) {
            Assertions.assertEquals(entry.getValue(), ComponentUtil.centerVanilla(Component.literal(entry.getKey())).getString());
        }

        for (Map.Entry<String, String> entry : boldValues.entrySet()) {
            Assertions.assertEquals(entry.getValue(), ComponentUtil.centerVanilla(Component.literal(entry.getKey()).withStyle(ChatFormatting.BOLD)).getString());
        }
    }
}

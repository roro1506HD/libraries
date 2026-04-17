package ovh.roro.libraries.componentutil.test;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.support.ParameterDeclarations;
import ovh.roro.libraries.componentutil.ComponentUtil;

import java.util.Objects;
import java.util.stream.Stream;

class ComponentCentererTests {

    @BeforeAll
    static void setup() {
        ComponentUtil.loadCustom(Objects.requireNonNull(ComponentCentererTests.class.getResourceAsStream("/font_data"), "font_data not found"));
    }

    @ParameterizedTest(name = "Padding Validation (Normal): \"{0}\"", quoteTextArguments = false)
    @ArgumentsSource(NormalInputValues.class)
    void validateNormalSpacePadding(String input, String expectedResult) {
        Assertions.assertEquals(expectedResult, ComponentUtil.centerVanilla(Component.literal(input)).getString());
    }

    @ParameterizedTest(name = "Padding Validation (Bold): \"{0}\"", quoteTextArguments = false)
    @ArgumentsSource(BoldInputValues.class)
    void validateBoldSpacePadding(String input, String expectedResult) {
        Assertions.assertEquals(expectedResult, ComponentUtil.centerVanilla(Component.literal(input).withStyle(ChatFormatting.BOLD)).getString());
    }

    @Test
    void validateWidth() {
        Assertions.assertEquals(4.0F, ComponentUtil.widthVanilla(Component.literal(" ")));
        Assertions.assertEquals(5.0F, ComponentUtil.widthVanilla(Component.literal(" ").withStyle(ChatFormatting.BOLD)));

        Assertions.assertEquals(173.0F, ComponentUtil.widthVanilla(Component.literal("Medium text that takes more space")));
        Assertions.assertEquals(206.0F, ComponentUtil.widthVanilla(Component.literal("Medium text that takes more space").withStyle(ChatFormatting.BOLD)));
    }

    static class NormalInputValues implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.arguments("", "                                      "),
                    Arguments.arguments("Short text", "                               Short text"),
                    Arguments.arguments("Medium text that takes more space", "                Medium text that takes more space"),
                    Arguments.arguments("Long text that should have no padding at all since it already covers more than chat width", "Long text that should have no padding at all since it already covers more than chat width")
            );
        }
    }

    static class BoldInputValues implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.arguments("", "                                      "),
                    Arguments.arguments("Short text", "                              Short text"),
                    Arguments.arguments("Medium text that takes more space", "            Medium text that takes more space"),
                    Arguments.arguments("Long text that should have no padding at all since it already covers more than chat width", "Long text that should have no padding at all since it already covers more than chat width")
            );
        }
    }
}

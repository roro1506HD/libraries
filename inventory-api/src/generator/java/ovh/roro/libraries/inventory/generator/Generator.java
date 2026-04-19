package ovh.roro.libraries.inventory.generator;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeSpec;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.processing.Generated;
import javax.lang.model.element.Modifier;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Generator extends JavaPlugin {

    @Override
    public void onLoad() {
        try {
            Generator.generateDataComponents();
        } catch (Exception ex) {
            this.getSLF4JLogger().error("Couldn't generate DataComponents", ex);
        }
    }

    @Override
    public void onEnable() {
        this.getServer().shutdown();
    }

    private static void generateDataComponents() throws Exception {
        TypeSpec.Builder dataComponentBuilder = TypeSpec.interfaceBuilder("DataComponent")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(
                        AnnotationSpec.builder(Generated.class)
                                .addMember("value", "$S", Generator.class.getCanonicalName())
                                .addMember("date", "$S", new Date().toString())
                                .build()
                )
                .addAnnotation(ApiStatus.NonExtendable.class)
                .addSuperinterface(Keyed.class);

        ClassName dataComponentName = ClassName.get("ovh.roro.libraries.inventory.api.item.component", "DataComponent");
        ClassName dataComponentImplName = ClassName.get("ovh.roro.libraries.inventory.impl.item.component", "DataComponentImpl");
        ClassName keyName = ClassName.get(Key.class);

        List<Identifier> dataComponentTypes = new ArrayList<>(BuiltInRegistries.DATA_COMPONENT_TYPE.keySet());

        dataComponentTypes.sort(Comparator.comparing(Identifier::getNamespace).thenComparing(Identifier::getPath));

        for (Identifier identifier : dataComponentTypes) {
            dataComponentBuilder.addField(
                    FieldSpec.builder(dataComponentName, identifier.getPath().toUpperCase(Locale.ROOT).replace("/", "_"), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            .initializer(CodeBlock.builder().add("new $T($T.key($S, $S))", dataComponentImplName, keyName, identifier.getNamespace(), identifier.getPath()).build())
                            .build()
            );
        }

        JavaFile javaFile = JavaFile.builder("ovh.roro.libraries.inventory.api.item.component", dataComponentBuilder.build())
                .indent("    ")
                .skipJavaLangImports(true)
                .build();

        javaFile.writeTo(new File("../src/generated/java"));
    }
}

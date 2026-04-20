package ovh.roro.libraries.config.api;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.OverrideOnly
public interface ConfigWritable {

    void write(ConfigWriter writer);

}

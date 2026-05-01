package ovh.roro.libraries.reflect.impl.accessor;

import org.jetbrains.annotations.ApiStatus;

import java.lang.invoke.VarHandle;

@ApiStatus.Internal
public final class ConstantFieldAccessor extends AbstractFieldAccessor {

    private final VarHandle varHandle;

    public ConstantFieldAccessor(VarHandle varHandle) {
        this.varHandle = varHandle;
    }

    @Override
    protected VarHandle varHandle() {
        return this.varHandle;
    }
}

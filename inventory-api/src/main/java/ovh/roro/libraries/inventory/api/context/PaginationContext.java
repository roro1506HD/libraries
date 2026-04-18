package ovh.roro.libraries.inventory.api.context;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.PageableInventory;

import java.util.List;

@ApiStatus.NonExtendable
public interface PaginationContext<T, U, V extends InventoryPlayerHolder> {

    void update(List<U> elements);

    void nextPage();

    boolean hasNextPage();

    void previousPage();

    boolean hasPreviousPage();

    int currentPage();

    int maxPage();

    List<U> currentElements();

    @Nullable T inventoryValue();

    PageableInventory<T, U, V> inventory();

}

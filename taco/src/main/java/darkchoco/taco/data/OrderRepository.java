package darkchoco.taco.data;

import darkchoco.taco.Order;

public interface OrderRepository {
    Order save(Order order);
}

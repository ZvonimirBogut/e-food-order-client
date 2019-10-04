package efoodorder.model;

import java.io.Serializable;
import java.util.List;

public class OrderItem implements Serializable {
    private Integer id;

    private Double price;

    private String name;

    private List<Order> orders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}

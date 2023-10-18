package org.example.model.data.onetomany;

import jakarta.persistence.*;
import org.example.model.data.Product;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private BigDecimal price;

  @ManyToOne
  private Order order;

  @ManyToOne
  private Product product;

  public OrderItem() {
  }

  public OrderItem(Order order, Product product, Integer quantity) {
    this.setOrder(order);
    this.setProduct(product);
    this.setQuantity(quantity);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;

    if (product != null && this.price == null) {
      this.setPrice(product.getPrice());
    }
  }
}

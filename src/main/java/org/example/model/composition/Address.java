package org.example.model.composition;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

  private String street;
  private String complement;

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getComplement() {
    return complement;
  }

  public void setComplement(String complement) {
    this.complement = complement;
  }
}

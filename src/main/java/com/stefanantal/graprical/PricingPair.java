package com.stefanantal.graprical;

public class PricingPair {
  private double price;
  private int limit;

  public PricingPair() {
  }

  public PricingPair(double price, int limit) {
    this.price = price;
    this.limit = limit;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }
}

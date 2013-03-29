package org.servicebase.database;

import java.io.PrintStream;

public class Customer
  implements Comparable
{
  private int id;
  private CustomerData data;

  public Customer()
  {
    this.data = new CustomerData();
  }

  public Customer(CustomerData data)
  {
    this.data = data;
  }

  public void print() {
    this.data.print();
  }

  public String getInfoByFieldName(String fieldname) {
    return this.data.getInfoByFieldName(fieldname);
  }

  public String getInfoByFieldNumber(int num) {
    return this.data.getInfoByFieldNumber(num);
  }

  public int compareTo(Object obj)
  {
    if (!(obj instanceof Customer)) {
      System.err.println("Customer can only be compared with Customer");
      System.exit(1);
    }
    Customer otherCust = (Customer)obj;
    String myName = this.data.getInfoByFieldName("lastname");
    String otherName = otherCust.data.getInfoByFieldName("lastname");
    return myName.compareToIgnoreCase(otherName);
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public String toString()
  {
    return this.data.getInfoByFieldName("lastname");
  }
}
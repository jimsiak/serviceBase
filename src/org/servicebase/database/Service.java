package org.servicebase.database;

public class Service
{
  private int id;
  private int mid;
  private ServiceData data;

  public Service()
  {
    this.data = new ServiceData();
  }

  public Service(ServiceData data)
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

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public int getMid() {
    return this.mid;
  }

  public void setMid(int mid) {
    this.mid = mid;
  }
}
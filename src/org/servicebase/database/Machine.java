package org.servicebase.database;

import java.io.PrintStream;

public class Machine
  implements Comparable
{
  private int id;
  private int cid;
  private MachineData data;

  public Machine()
  {
    this.data = new MachineData();
  }

  public Machine(MachineData data)
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

  public String toString()
  {
    return this.data.getInfoByFieldName("model");
  }

  public int compareTo(Object obj)
  {
    if (!(obj instanceof Machine)) {
      System.err.println("Machine can only be compared with Machine");
      System.exit(1);
    }
    Machine otherMach = (Machine)obj;
    String myName = this.data.getInfoByFieldName("model");
    String otherName = otherMach.data.getInfoByFieldName("model");
    return myName.compareToIgnoreCase(otherName);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCid() {
    return this.cid;
  }

  public void setCid(int cid) {
    this.cid = cid;
  }
}
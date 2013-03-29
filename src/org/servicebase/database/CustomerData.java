package org.servicebase.database;

import java.awt.Component;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JTextField;

public class CustomerData
{
  private Map<String, String> data;
  public static final String[] fieldsLabels = { 
    "Κωδικός", "Επώμυμο", "Όνομα", "Τηλέφωνο1", 
    "Τηλέφωνο2", "Κινητό", "Διεύθυνση", "Πόλη", 
    "Τ.Κ.", "Επάγγελμα", "ΔΟΥ", "ΑΦΜ" };

  public static final String[] fieldsTypes = { 
    "VARCHAR(30)", "VARCHAR(50)", "VARCHAR(50)", "VARCHAR(15)", 
    "VARCHAR(15)", "VARCHAR(15)", "VARCHAR(30)", "VARCHAR(30)", 
    "VARCHAR(5)", "VARCHAR(30)", "VARCHAR(30)", "VARCHAR(20)" };

  public static final String[] fieldsName = { 
    "code", "lastname", "firstname", "phone1", 
    "phone2", "cellphone", "address", "town", 
    "postal_code", "job", "doy", "afm" };

  public static final Component[] fieldsInputType = { 
    new JTextField(10), new JTextField(10), 
    new JTextField(10), new JTextField(10), 
    new JTextField(10), new JTextField(10), 
    new JTextField(10), new JTextField(10), 
    new JTextField(10), new JTextField(10), 
    new JTextField(10), new JTextField(10) };

  public CustomerData()
  {
    this.data = new HashMap();
    for (int i = 0; i < fieldsName.length; i++)
      this.data.put(fieldsName[i], "");
  }

  public void updateByFieldName(String fieldname, String val)
  {
    if (!this.data.containsKey(fieldname))
      return;
    this.data.put(fieldname, val);
  }

  public void updateByFieldNum(int num, String val) {
    String fieldname = fieldsName[num];
    if (!this.data.containsKey(fieldname))
      return;
    this.data.put(fieldname, val);
  }

  public void print() {
    System.out.println("Customer Data Begin");
    Set s = this.data.entrySet();
    Iterator it = s.iterator();
    while (it.hasNext()) {
      Map.Entry m = (Map.Entry)it.next();
      String key = (String)m.getKey();
      String val = (String)m.getValue();
      System.out.println(key + ": " + val);
    }
    System.out.println("Customer Data End");
  }

  public String getInfoByFieldName(String fieldname) {
    return (String)this.data.get(fieldname);
  }

  public String getInfoByFieldNumber(int num) {
    return (String)this.data.get(fieldsName[num]);
  }
}
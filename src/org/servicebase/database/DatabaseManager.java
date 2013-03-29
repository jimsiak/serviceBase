package org.servicebase.database;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.servicebase.file.DatabaseReadFile;
import org.servicebase.file.DatabaseWriteFile;
import org.servicebase.gui.DbConnectionErrorDialog;

public class DatabaseManager
{
  private final String dbClass = "com.mysql.jdbc.Driver";
  private String dbUrl;
  private String dbAddress = "83.212.110.199";
  private String username = "mpampis";
  private String password = "mpampis";
  private Connection con;

  public String getDbAddress()
  {
    return this.dbAddress;
  }

  public void setDbAddress(String dbAddress) {
    this.dbAddress = dbAddress;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void createDatabase(String dbname) {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://" + this.dbAddress, this.username, this.password);
      Statement stmt = con.createStatement();
      stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbname + ";");
      this.dbUrl = ("jdbc:mysql://" + this.dbAddress + "/" + dbname + "?useUnicode=true&characterEncoding=utf-8");
      con.close();
    }
    catch (SQLException e) {
      new DbConnectionErrorDialog();
      createDatabase(dbname);
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void connect() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      this.con = DriverManager.getConnection(this.dbUrl, this.username, this.password);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void disconnect() {
    try {
      this.con.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void exportDatabase(String filename) {
    DatabaseWriteFile file = new DatabaseWriteFile();
    file.open(filename);
    file.writeCustomers(getCustomersList());
    file.writeMachines(getMachinesList());
    file.writeServices(getServicesList());
    file.writeSpares(getSparesList());
    file.close();
  }

  public void importDatabase(String filename) {
    DatabaseReadFile file = new DatabaseReadFile();
    file.open(filename);

    List customers = file.readCustomersList();
    Iterator it = customers.iterator();
    while (it.hasNext()) {
      Customer cust = (Customer)it.next();
      String query = "INSERT INTO customers SET id=" + cust.getId() + ", ";
      for (int i = 0; i < CustomerData.fieldsName.length; i++) {
        String fieldname = CustomerData.fieldsName[i];
        String val = cust.getInfoByFieldName(fieldname);
        query = query + fieldname + "= \"" + val + "\"";
        if (i != CustomerData.fieldsName.length - 1)
          query = query + ", ";
      }
      query = query + ";";
      executeUpdate(query);
    }

    List machines = file.readMachinesList();
    Iterator it2 = machines.iterator();
    while (it2.hasNext()) {
      Machine mach = (Machine)it2.next();
      String query = "INSERT INTO machines SET id=" + mach.getId() + 
        ", cid=" + mach.getCid() + ", ";
      for (int i = 0; i < MachineData.fieldsName.length; i++) {
        String fieldname = MachineData.fieldsName[i];
        String val = mach.getInfoByFieldName(fieldname);
        query = query + fieldname + "= \"" + val + "\"";
        if (i != MachineData.fieldsName.length - 1)
          query = query + ", ";
      }
      query = query + ";";
      executeUpdate(query);
    }

    List services = file.readServicesList();
    Iterator it3 = services.iterator();
    while (it3.hasNext()) {
      Service serv = (Service)it3.next();
      String query = "INSERT INTO services SET id=" + serv.getId() + 
        ", mid=" + serv.getMid() + ", ";
      for (int i = 0; i < ServiceData.fieldsName.length; i++) {
        String fieldname = ServiceData.fieldsName[i];
        String val = serv.getInfoByFieldName(fieldname);
        query = query + fieldname + "= \"" + val + "\"";
        if (i != ServiceData.fieldsName.length - 1)
          query = query + ", ";
      }
      query = query + ";";
      executeUpdate(query);
    }

    List spares = file.readSparesList();
    Iterator it4 = spares.iterator();
    while (it4.hasNext()) {
      Spare spare = (Spare)it4.next();
      String query = "INSERT INTO spares SET id=" + spare.getId() + 
        ", mid=" + spare.getMid() + ", sid=" + spare.getSid() + ", ";
      for (int i = 0; i < SpareData.fieldsName.length; i++) {
        String fieldname = SpareData.fieldsName[i];
        String val = spare.getInfoByFieldName(fieldname);
        query = query + fieldname + "= \"" + val + "\"";
        if (i != SpareData.fieldsName.length - 1)
          query = query + ", ";
      }
      query = query + ";";
      executeUpdate(query);
    }

    file.close();
  }

  public void checkCustomersTableColumns()
  {
    try {
      Statement stmt = this.con.createStatement();
      String query = "SELECT * FROM customers";
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData md = rs.getMetaData();
      for (int i = 1; i <= md.getColumnCount(); i++)
        System.out.println("Customers table column " + i + " name " + md.getColumnName(i));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void executeUpdate(String query) {
    try {
      Statement stmt = this.con.createStatement();
      System.out.println(query);
      stmt.executeUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private ResultSet executeQuery(String query) {
    try {
      Statement stmt = this.con.createStatement();
      System.out.println(query);
      return stmt.executeQuery(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void createCustomersTable(String tbname)
  {
    String[] custFieldName = CustomerData.fieldsName;
    String[] custFieldType = CustomerData.fieldsTypes;
    int custLen = custFieldName.length;
    String query = "CREATE TABLE IF NOT EXISTS " + tbname + "(";
    query = query + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY";
    for (int i = 0; i < custLen; i++)
      query = query + ", " + custFieldName[i] + " " + custFieldType[i];
    query = query + ") DEFAULT CHARACTER SET utf8;";

    executeUpdate(query);
  }

  public List<Customer> getCustomersList() {
    try {
      ResultSet rs = executeQuery("SELECT * FROM customers");

      List list = new ArrayList();
      while (rs.next()) {
        CustomerData custData = new CustomerData();
        String id = rs.getString(1);
        for (int i = 0; i < CustomerData.fieldsName.length; i++) {
          String fieldValue = rs.getString(CustomerData.fieldsName[i]);
          custData.updateByFieldName(CustomerData.fieldsName[i], fieldValue);
        }
        Customer cust = new Customer(custData);
        cust.setId(Integer.parseInt(id));
        list.add(cust);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public Customer[] getCustomersArray()
  {
    List list = getCustomersList();
    Customer[] customers = (Customer[])list.toArray(new Customer[list.size()]);
    return customers;
  }

  public String[] getCustomersFieldsByName(String fieldname)
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection(this.dbUrl, "root", "01071989");
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM customers");

      List list = new ArrayList();
      while (rs.next()) {
        String fieldValue = rs.getString(fieldname);
        list.add(fieldValue);
      }

      con.close();

      return (String[])list.toArray(new String[list.size()]);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private int getNextId(String tbname) {
    try {
      String query = "SELECT max(id) FROM " + tbname + ";";
      ResultSet rs = executeQuery(query);
      if (!rs.next())
        return 1;
      return rs.getInt(1) + 1;
    } catch (SQLException e) {
      e.printStackTrace();
    }return -1;
  }

  public void addCustomerToDatabase(Customer cust)
  {
    int id = getNextId("customers");
    String query = "INSERT INTO customers SET id=" + id + ", ";
    for (int i = 0; i < CustomerData.fieldsName.length; i++) {
      String fieldname = CustomerData.fieldsName[i];
      String val = cust.getInfoByFieldName(fieldname);
      query = query + fieldname + "= \"" + val + "\"";
      if (i != CustomerData.fieldsName.length - 1)
        query = query + ", ";
    }
    query = query + ";";

    executeUpdate(query);
    cust.setId(id);
  }

  public void removeCustomerFromDatabase(Customer cust) {
    String query = "DELETE FROM customers WHERE id=" + cust.getId();
    executeUpdate(query);
  }

  public void createMachinesTable(String tbname) {
    String[] machFieldName = MachineData.fieldsName;
    String[] machFieldType = MachineData.fieldsTypes;
    int machLen = machFieldName.length;
    String query = "CREATE TABLE IF NOT EXISTS " + tbname + "(";
    query = query + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, cid INT";
    for (int i = 0; i < machLen; i++)
      query = query + ", " + machFieldName[i] + " " + machFieldType[i];
    query = query + ", FOREIGN KEY (cid) REFERENCES customers(id) ON DELETE CASCADE ON UPDATE CASCADE) DEFAULT CHARACTER SET utf8;";

    executeUpdate(query);
  }

  public List<Machine> getCustomerMachinesList(Customer cust) {
    try {
      ResultSet rs = 
        executeQuery("SELECT machines.* FROM customers JOIN machines ON customers.id=cid AND cid=" + 
        cust.getId() + ";");

      List list = new ArrayList();
      while (rs.next()) {
        MachineData machData = new MachineData();
        int id = rs.getInt(1);
        for (int i = 0; i < MachineData.fieldsName.length; i++) {
          String fieldValue = rs.getString(MachineData.fieldsName[i]);
          machData.updateByFieldName(MachineData.fieldsName[i], fieldValue);
        }
        Machine mach = new Machine(machData);
        mach.setId(id);
        mach.setCid(cust.getId());
        list.add(mach);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public List<Machine> getMachinesList()
  {
    try {
      ResultSet rs = 
        executeQuery("SELECT * FROM machines;");

      List list = new ArrayList();
      while (rs.next()) {
        MachineData machData = new MachineData();
        int id = rs.getInt(1);
        int cid = rs.getInt(2);
        for (int i = 0; i < MachineData.fieldsName.length; i++) {
          String fieldValue = rs.getString(MachineData.fieldsName[i]);
          machData.updateByFieldName(MachineData.fieldsName[i], fieldValue);
        }
        Machine mach = new Machine(machData);
        mach.setId(id);
        mach.setCid(cid);
        list.add(mach);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public void addMachineToDatabase(Machine mach, Customer cust)
  {
    int id = getNextId("machines");
    String query = "INSERT INTO machines SET id=" + id + ", ";
    query = query + "cid=" + cust.getId() + ", ";
    for (int i = 0; i < MachineData.fieldsName.length; i++) {
      String fieldname = MachineData.fieldsName[i];
      String val = mach.getInfoByFieldName(fieldname);
      query = query + fieldname + "= \"" + val + "\"";
      if (i != MachineData.fieldsName.length - 1)
        query = query + ", ";
    }
    query = query + ";";

    executeUpdate(query);
    mach.setId(id);
    mach.setCid(cust.getId());
  }

  public void createServiceTable(String tbname) {
    String[] serviceFieldName = ServiceData.fieldsName;
    String[] serviceFieldType = ServiceData.fieldsTypes;
    int serviceLen = serviceFieldName.length;
    String query = "CREATE TABLE IF NOT EXISTS " + tbname + "(";
    query = query + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, mid INT";
    for (int i = 0; i < serviceLen; i++)
      query = query + ", " + serviceFieldName[i] + " " + serviceFieldType[i];
    query = query + ", FOREIGN KEY (mid) REFERENCES machines(id) ON DELETE CASCADE ON UPDATE CASCADE) DEFAULT CHARACTER SET utf8;";

    executeUpdate(query);
  }

  public List<Service> getCustomerServicesList(Customer cust) {
    try {
      ResultSet rs = 
        executeQuery("SELECT services.* FROM customers JOIN machines JOIN services ON customers.id=cid AND machines.id=mid AND cid=" + 
        cust.getId() + ";");

      List list = new ArrayList();
      while (rs.next()) {
        ServiceData servData = new ServiceData();
        int id = rs.getInt(1);
        int mid = rs.getInt(2);
        for (int i = 0; i < ServiceData.fieldsName.length; i++) {
          String fieldValue = rs.getString(ServiceData.fieldsName[i]);
          servData.updateByFieldName(ServiceData.fieldsName[i], fieldValue);
        }
        Service serv = new Service(servData);
        serv.setId(id);
        serv.setMid(mid);
        list.add(serv);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public List<Service> getMachineServicesList(Machine mach)
  {
    try {
      ResultSet rs = 
        executeQuery("SELECT services.* FROM machines JOIN services ON machines.id=mid AND mid=" + 
        mach.getId() + ";");

      List list = new ArrayList();
      while (rs.next()) {
        ServiceData servData = new ServiceData();
        int id = rs.getInt(1);
        int mid = rs.getInt(2);
        for (int i = 0; i < ServiceData.fieldsName.length; i++) {
          String fieldValue = rs.getString(ServiceData.fieldsName[i]);
          servData.updateByFieldName(ServiceData.fieldsName[i], fieldValue);
        }
        Service serv = new Service(servData);
        serv.setId(id);
        serv.setMid(mid);
        list.add(serv);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public List<Service> getServicesList()
  {
    try {
      ResultSet rs = 
        executeQuery("SELECT * FROM services;");

      List list = new ArrayList();
      while (rs.next()) {
        ServiceData servData = new ServiceData();
        int id = rs.getInt(1);
        int mid = rs.getInt(2);
        for (int i = 0; i < ServiceData.fieldsName.length; i++) {
          String fieldValue = rs.getString(ServiceData.fieldsName[i]);
          servData.updateByFieldName(ServiceData.fieldsName[i], fieldValue);
        }
        Service serv = new Service(servData);
        serv.setId(id);
        serv.setMid(mid);
        list.add(serv);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public void addServiceToDatabase(Service serv, Machine mach)
  {
    int id = getNextId("services");
    String query = "INSERT INTO services SET id=" + id + ", ";
    query = query + "mid=" + mach.getId() + ", ";
    for (int i = 0; i < ServiceData.fieldsName.length; i++) {
      String fieldname = ServiceData.fieldsName[i];
      String val = serv.getInfoByFieldName(fieldname);
      query = query + fieldname + "= \"" + val + "\"";
      if (i != ServiceData.fieldsName.length - 1)
        query = query + ", ";
    }
    query = query + ";";

    executeUpdate(query);
    serv.setId(id);
    serv.setMid(mach.getId());
  }

  public void createSpareTable(String tbname) {
    String[] spareFieldName = SpareData.fieldsName;
    String[] spareFieldType = SpareData.fieldsTypes;
    int spareLen = spareFieldName.length;
    String query = "CREATE TABLE IF NOT EXISTS " + tbname + "(";
    query = query + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, mid INT, sid INT";
    for (int i = 0; i < spareLen; i++)
      query = query + ", " + spareFieldName[i] + " " + spareFieldType[i];
    query = query + ", FOREIGN KEY (mid) REFERENCES machines(id) , FOREIGN KEY (sid) REFERENCES services(id) ON DELETE CASCADE ON UPDATE CASCADE) DEFAULT CHARACTER SET utf8;";

    executeUpdate(query);
  }

  public List<Spare> getSparesList() {
    try {
      ResultSet rs = 
        executeQuery("SELECT * FROM spares;");

      List list = new ArrayList();
      while (rs.next()) {
        SpareData spareData = new SpareData();
        int id = rs.getInt(1);
        int mid = rs.getInt(2);
        int sid = rs.getInt(3);
        for (int i = 0; i < SpareData.fieldsName.length; i++) {
          String fieldValue = rs.getString(SpareData.fieldsName[i]);
          spareData.updateByFieldName(SpareData.fieldsName[i], fieldValue);
        }
        Spare spare = new Spare(spareData);
        spare.setId(id);
        spare.setMid(mid);
        spare.setSid(sid);
        list.add(spare);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public void addSpareListToDatabase(List<Spare> spares, Service serv)
  {
    Iterator it = spares.iterator();
    while (it.hasNext()) {
      Spare spare = (Spare)it.next();

      int id = getNextId("spares");
      String query = "INSERT INTO spares SET id=" + id + ", ";
      query = query + "sid=" + serv.getId() + ", mid=" + serv.getMid() + ", ";
      for (int i = 0; i < SpareData.fieldsName.length; i++) {
        String fieldname = SpareData.fieldsName[i];
        String val = spare.getInfoByFieldName(fieldname);
        query = query + fieldname + "= \"" + val + "\"";
        if (i != SpareData.fieldsName.length - 1)
          query = query + ", ";
      }
      query = query + ";";

      executeUpdate(query);
      spare.setId(id);
      spare.setSid(serv.getId());
      spare.setMid(serv.getMid());
    }
  }
}
package org.servicebase.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import org.servicebase.database.Customer;
import org.servicebase.database.CustomerData;
import org.servicebase.database.Machine;
import org.servicebase.database.MachineData;
import org.servicebase.database.Service;
import org.servicebase.database.ServiceData;
import org.servicebase.database.Spare;
import org.servicebase.database.SpareData;

public class DatabaseWriteFile
{
  public static String customersBegin = "------------CUSTOMERS_BEGIN-------------";
  public static String customersEnd = "------------CUSTOMERS_END-------------";
  public static String machinesBegin = "------------MACHINES_BEGIN-------------";
  public static String machinesEnd = "------------MACHINES_END-------------";
  public static String servicesBegin = "------------SERVICES_BEGIN-------------";
  public static String servicesEnd = "------------SERVICES_END-------------";
  public static String sparesBegin = "------------SPARES_BEGIN-------------";
  public static String sparesEnd = "------------SPARES_END-------------";
  public static String separator = ", ";

  private FileWriter fstream = null;
  private BufferedWriter out = null;

  public void open(String filename) {
    try {
      this.fstream = new FileWriter(filename);
      this.out = new BufferedWriter(this.fstream);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      this.out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeCustomers(List<Customer> customers) {
    try {
      this.out.write(customersBegin + "\n");
      writeCustomersHeader();
      writeCustomersList(customers);
      this.out.write(customersEnd + "\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeCustomersHeader() {
    try {
      String[] headers = CustomerData.fieldsName;
      this.out.write("id");
      for (int i = 0; i < headers.length; i++)
        this.out.write(separator + headers[i]);
      this.out.write("\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeCustomersList(List<Customer> customers) {
    try {
      Iterator it = customers.iterator();

      while (it.hasNext()) {
        Customer cust = (Customer)it.next();
        this.out.write(Integer.toString(cust.getId()));
        for (int i = 0; i < CustomerData.fieldsLabels.length - 1; i++) {
          String val = cust.getInfoByFieldNumber(i);
          this.out.write(separator + val);
        }
        this.out.write("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeMachines(List<Machine> machines) {
    try {
      this.out.write(machinesBegin + "\n");
      writeMachinesHeader();
      writeMachinesList(machines);
      this.out.write(machinesEnd + "\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeMachinesHeader() {
    try {
      String[] headers = MachineData.fieldsName;
      this.out.write("id" + separator + "cid");
      for (int i = 0; i < headers.length; i++)
        this.out.write(separator + headers[i]);
      this.out.write("\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeMachinesList(List<Machine> machines) {
    try {
      Iterator it = machines.iterator();

      while (it.hasNext()) {
        Machine mach = (Machine)it.next();
        this.out.write(Integer.toString(mach.getId()) + separator);
        this.out.write(Integer.toString(mach.getCid()));
        for (int i = 0; i < MachineData.fieldsLabels.length - 1; i++) {
          String val = mach.getInfoByFieldNumber(i);
          this.out.write(separator + val);
        }
        this.out.write("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeServices(List<Service> services) {
    try {
      this.out.write(servicesBegin + "\n");
      writeServicesHeader();
      writeServicesList(services);
      this.out.write(servicesEnd + "\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeServicesHeader() {
    try {
      String[] headers = ServiceData.fieldsName;
      this.out.write("id" + separator + "mid");
      for (int i = 0; i < headers.length; i++)
        this.out.write(separator + headers[i]);
      this.out.write("\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeServicesList(List<Service> services) {
    try {
      Iterator it = services.iterator();

      while (it.hasNext()) {
        Service serv = (Service)it.next();
        this.out.write(Integer.toString(serv.getId()) + separator);
        this.out.write(Integer.toString(serv.getMid()));
        for (int i = 0; i < ServiceData.fieldsLabels.length - 1; i++) {
          String val = serv.getInfoByFieldNumber(i);
          this.out.write(separator + val);
        }
        this.out.write("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeSpares(List<Spare> spares) {
    try {
      this.out.write(sparesBegin + "\n");
      writeSparesHeader();
      writeSparesList(spares);
      this.out.write(sparesEnd + "\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeSparesHeader() {
    try {
      String[] headers = SpareData.fieldsName;
      this.out.write("id" + separator + "mid" + separator + "sid");
      for (int i = 0; i < headers.length; i++)
        this.out.write(separator + headers[i]);
      this.out.write("\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeSparesList(List<Spare> spares) {
    try {
      Iterator it = spares.iterator();

      while (it.hasNext()) {
        Spare spare = (Spare)it.next();
        this.out.write(Integer.toString(spare.getId()) + separator);
        this.out.write(Integer.toString(spare.getMid()) + separator);
        this.out.write(Integer.toString(spare.getSid()));
        for (int i = 0; i < SpareData.fieldsLabels.length - 1; i++) {
          String val = spare.getInfoByFieldNumber(i);
          this.out.write(separator + val);
        }
        this.out.write("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
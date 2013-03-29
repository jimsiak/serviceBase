package org.servicebase.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.servicebase.database.Customer;
import org.servicebase.database.CustomerData;
import org.servicebase.database.Machine;
import org.servicebase.database.MachineData;
import org.servicebase.database.Service;
import org.servicebase.database.ServiceData;
import org.servicebase.database.Spare;
import org.servicebase.database.SpareData;

public class DatabaseReadFile
{
  private static String customersBegin = DatabaseWriteFile.customersBegin;
  private static String customersEnd = DatabaseWriteFile.customersEnd;
  private static String machinesBegin = DatabaseWriteFile.machinesBegin;
  private static String machinesEnd = DatabaseWriteFile.machinesEnd;
  private static String servicesBegin = DatabaseWriteFile.servicesBegin;
  private static String servicesEnd = DatabaseWriteFile.servicesEnd;
  private static String sparesBegin = DatabaseWriteFile.sparesBegin;
  private static String sparesEnd = DatabaseWriteFile.sparesEnd;
  private static String separator = DatabaseWriteFile.separator;

  private FileInputStream fstream = null;
  private DataInputStream in = null;
  private BufferedReader br = null;

  public void open(String filename) {
    try {
      this.fstream = new FileInputStream(filename);
      this.in = new DataInputStream(this.fstream);
      this.br = new BufferedReader(new InputStreamReader(this.in));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      this.in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Customer> readCustomersList() {
    try {
      List customers = new ArrayList();
      String line = this.br.readLine();
      if (!line.equals(customersBegin))
        return null;
      line = this.br.readLine();
      line = this.br.readLine();
      while (!line.equals(customersEnd)) {
        CustomerData data = new CustomerData();
        String[] vals = line.split(separator);
        for (int i = 1; i < vals.length; i++)
          data.updateByFieldNum(i - 1, vals[i]);
        Customer cust = new Customer(data);
        cust.setId(Integer.parseInt(vals[0]));
        customers.add(cust);
        line = this.br.readLine();
      }

      return customers;
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public List<Machine> readMachinesList()
  {
    try {
      List machines = new ArrayList();
      String line = this.br.readLine();
      if (!line.equals(machinesBegin))
        return null;
      line = this.br.readLine();
      line = this.br.readLine();
      while (!line.equals(machinesEnd)) {
        MachineData data = new MachineData();
        String[] vals = line.split(separator);
        for (int i = 2; i < vals.length; i++)
          data.updateByFieldNum(i - 2, vals[i]);
        Machine mach = new Machine(data);
        mach.setId(Integer.parseInt(vals[0]));
        mach.setCid(Integer.parseInt(vals[1]));
        machines.add(mach);
        line = this.br.readLine();
      }

      return machines;
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public List<Service> readServicesList()
  {
    try {
      List services = new ArrayList();
      String line = this.br.readLine();
      if (!line.equals(servicesBegin))
        return null;
      line = this.br.readLine();
      line = this.br.readLine();
      while (!line.equals(servicesEnd)) {
        ServiceData data = new ServiceData();
        String[] vals = line.split(separator);
        for (int i = 2; i < vals.length; i++)
          data.updateByFieldNum(i - 2, vals[i]);
        Service serv = new Service(data);
        serv.setId(Integer.parseInt(vals[0]));
        serv.setMid(Integer.parseInt(vals[1]));
        services.add(serv);
        line = this.br.readLine();
      }

      return services;
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public List<Spare> readSparesList()
  {
    try {
      List spares = new ArrayList();
      String line = this.br.readLine();
      if (!line.equals(sparesBegin))
        return null;
      line = this.br.readLine();
      line = this.br.readLine();
      while (!line.equals(sparesEnd)) {
        SpareData data = new SpareData();
        String[] vals = line.split(separator);
        for (int i = 3; i < vals.length; i++)
          data.updateByFieldNum(i - 3, vals[i]);
        Spare spare = new Spare(data);
        spare.setId(Integer.parseInt(vals[0]));
        spare.setMid(Integer.parseInt(vals[1]));
        spare.setSid(Integer.parseInt(vals[2]));
        spares.add(spare);
        line = this.br.readLine();
      }

      return spares;
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }
}
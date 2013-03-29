package org.servicebase.gui;

import java.awt.Dimension;
import java.io.PrintStream;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.servicebase.Main;
import org.servicebase.database.Customer;
import org.servicebase.database.DatabaseManager;
import org.servicebase.database.Machine;
import org.servicebase.database.Service;
import org.servicebase.database.ServiceData;

public class ServiceListJTable extends JTable
{
  MyDefaultTableModel model;

  public ServiceListJTable()
  {
    this.model = new MyDefaultTableModel();
    setModel(this.model);
    setPreferredScrollableViewportSize(new Dimension(800, 100));
    setAutoCreateRowSorter(true);

    ListSelectionModel cellSelectionModel = getSelectionModel();
    cellSelectionModel.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        System.out.println("HEEEELLO");
      }
    });
  }

  public void writeServicesFromDatabase(Customer cust)
  {
    List servicesList = Main.dbmanager.getCustomerServicesList(cust);
    Service[] services = (Service[])servicesList.toArray(new Service[servicesList.size()]);
    int nrServices = services.length;

    String[] columnNames = ServiceData.fieldsLabels;
    int nrFields = columnNames.length;

    String[][] rowData = new String[nrServices][nrFields];

    for (int i = 0; i < nrServices; i++) {
      Service serv = services[i];
      for (int j = 0; j < nrFields; j++) {
        rowData[i][j] = serv.getInfoByFieldNumber(j);
      }
    }
    this.model.setDataVector(rowData, columnNames);
    this.model.fireTableDataChanged();
  }

  public void writeServicesFromDatabase(Machine mach)
  {
    List servicesList = Main.dbmanager.getMachineServicesList(mach);
    Service[] services = (Service[])servicesList.toArray(new Service[servicesList.size()]);
    int nrServices = services.length;

    String[] columnNames = ServiceData.fieldsLabels;
    int nrFields = columnNames.length;

    String[][] rowData = new String[nrServices][nrFields];

    for (int i = 0; i < nrServices; i++) {
      Service serv = services[i];
      for (int j = 0; j < nrFields; j++) {
        rowData[i][j] = serv.getInfoByFieldNumber(j);
      }
    }
    this.model.setDataVector(rowData, columnNames);
    this.model.fireTableDataChanged();
  }
}
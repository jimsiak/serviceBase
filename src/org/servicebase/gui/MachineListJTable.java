package org.servicebase.gui;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.servicebase.Main;
import org.servicebase.database.Customer;
import org.servicebase.database.DatabaseManager;
import org.servicebase.database.Machine;
import org.servicebase.database.MachineData;

public class MachineListJTable extends JTable
{
  private ServiceListJTable servicesList;
  MyDefaultTableModel model;
  private Machine[] machines;

  public MachineListJTable()
  {
    this.model = new MyDefaultTableModel();
    setModel(this.model);
    setPreferredScrollableViewportSize(new Dimension(800, 100));

    ListSelectionModel cellSelectionModel = getSelectionModel();
    cellSelectionModel.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        if (MachineListJTable.this.servicesList != null) {
          int row = MachineListJTable.this.getSelectedRow();
          if (row >= 0)
            MachineListJTable.this.servicesList.writeServicesFromDatabase(MachineListJTable.this.getSelectedMachine());
        }
      }
    });
  }

  public void writeMachinesFromDatabase(Customer cust)
  {
    List machinesList = Main.dbmanager.getCustomerMachinesList(cust);
    this.machines = ((Machine[])machinesList.toArray(new Machine[machinesList.size()]));
    Arrays.sort(this.machines);
    int nrMachines = this.machines.length;

    String[] columnNames = MachineData.fieldsLabels;
    int nrFields = columnNames.length;

    String[][] rowData = new String[nrMachines][nrFields];

    for (int i = 0; i < nrMachines; i++) {
      Machine mach = this.machines[i];
      for (int j = 0; j < nrFields; j++) {
        rowData[i][j] = mach.getInfoByFieldNumber(j);
      }
    }
    this.model.setDataVector(rowData, columnNames);
    this.model.fireTableDataChanged();
  }

  public Machine getSelectedMachine()
  {
    if (this.machines == null) {
      return null;
    }
    return this.machines[getSelectedRow()];
  }

  public void setServicesList(ServiceListJTable servicesList) {
    this.servicesList = servicesList;
  }
}
package org.servicebase.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import org.servicebase.Main;
import org.servicebase.database.Customer;
import org.servicebase.database.DatabaseManager;
import org.servicebase.database.Machine;
import org.servicebase.database.MachineData;

public class MachineListComboBox extends JComboBox<Machine>
{
  private MachineInputPanel parentPanel;
  private CustomerInputPanel customerInputPanel;
  Machine newMach;

  public MachineListComboBox(CustomerInputPanel customerInputPanel)
  {
    this.customerInputPanel = customerInputPanel;

    MachineData data = new MachineData();
    data.updateByFieldName("model", "Νέο Μηχάνημα");
    this.newMach = new Machine(data);
    addItem(this.newMach);

    setEditable(false);
    addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        MachineListComboBox comboBox = (MachineListComboBox)arg0.getSource();
        if (comboBox.getSelectedItem().equals(MachineListComboBox.this.newMach)) {
          MachineListComboBox.this.parentPanel.writeMachineToTextFields(new Machine());
          MachineListComboBox.this.parentPanel.setEditable(true);
        } else {
          MachineListComboBox.this.parentPanel.writeMachineToTextFields(comboBox.getSelectedMachine());
          MachineListComboBox.this.parentPanel.setEditable(false);
        }
      }
    });
    refresh();
  }

  public boolean newMachineSelected() {
    return getSelectedItem().equals(this.newMach);
  }

  public void refresh() {
    List machines = Main.dbmanager.getCustomerMachinesList(
      this.customerInputPanel.getComboBox().getSelectedCustomer());
    Machine[] machinesArray = (Machine[])machines.toArray(new Machine[machines.size()]);

    DefaultComboBoxModel model = new DefaultComboBoxModel(machinesArray);
    model.insertElementAt(this.newMach, 0);
    model.setSelectedItem(this.newMach);
    setModel(model);

    if (this.parentPanel != null) {
      this.parentPanel.setEditable(true);
      this.parentPanel.reset();
    }
  }

  public void refresh(Customer cust) {
    List machines = Main.dbmanager.getCustomerMachinesList(cust);
    Machine[] machinesArray = (Machine[])machines.toArray(new Machine[machines.size()]);
    Arrays.sort(machinesArray);

    DefaultComboBoxModel model = new DefaultComboBoxModel(machinesArray);
    model.insertElementAt(this.newMach, 0);
    model.setSelectedItem(this.newMach);
    setModel(model);

    if (this.parentPanel != null) {
      this.parentPanel.setEditable(true);
      this.parentPanel.reset();
    }
  }

  public void setParentPanel(MachineInputPanel parentPanel) {
    this.parentPanel = parentPanel;
  }

  public Machine getSelectedMachine() {
    return (Machine)getSelectedItem();
  }
}
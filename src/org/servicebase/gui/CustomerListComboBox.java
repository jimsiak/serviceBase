package org.servicebase.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import org.servicebase.Main;
import org.servicebase.database.Customer;
import org.servicebase.database.CustomerData;
import org.servicebase.database.DatabaseManager;

public class CustomerListComboBox extends JComboBox<Customer>
{
  private CustomerInputPanel parentPanel;
  private MachineListComboBox machineListComboBox;
  Customer newCust;

  public CustomerListComboBox()
  {
    CustomerData data = new CustomerData();
    data.updateByFieldName("lastname", "Νέος Πελάτης");
    this.newCust = new Customer(data);
    addItem(this.newCust);

    setEditable(false);
    addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        CustomerListComboBox comboBox = (CustomerListComboBox)arg0.getSource();
        if (comboBox.getSelectedItem().equals(CustomerListComboBox.this.newCust)) {
          CustomerListComboBox.this.parentPanel.reset();
          CustomerListComboBox.this.machineListComboBox.refresh();
        } else {
          CustomerListComboBox.this.parentPanel.writeCustomerToTextFields(comboBox.getSelectedCustomer());
          CustomerListComboBox.this.parentPanel.setEditable(false);
          CustomerListComboBox.this.machineListComboBox.refresh(comboBox.getSelectedCustomer());
        }
      }
    });
    refresh();
  }

  public void refresh() {
    Customer[] customers = Main.dbmanager.getCustomersArray();
    Arrays.sort(customers);

    DefaultComboBoxModel model = new DefaultComboBoxModel(customers);
    model.insertElementAt(this.newCust, 0);
    model.setSelectedItem(this.newCust);
    setModel(model);
    if (this.parentPanel != null)
      this.parentPanel.setEditable(true);
  }

  public void setParentPanel(CustomerInputPanel parentPanel) {
    this.parentPanel = parentPanel;
  }

  public Customer getSelectedCustomer() {
    return (Customer)getSelectedItem();
  }

  public void setMachineListComboBox(MachineListComboBox machineListComboBox) {
    this.machineListComboBox = machineListComboBox;
  }

  public boolean newCustomerSelected() {
    return getSelectedItem().equals(this.newCust);
  }
}
package org.servicebase.gui;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import org.servicebase.database.Customer;
import org.servicebase.database.CustomerData;

public class CustomerInputPanel extends JPanel
{
  public CustomerListComboBox comboBox;

  public CustomerInputPanel()
  {
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    setBorder(new TitledBorder(null, "Πελάτης", 
      4, 2, null, null));

    this.comboBox = new CustomerListComboBox();
    this.comboBox.setParentPanel(this);
    add(this.comboBox);
    add(new JLabel());
    add(new JLabel());
    add(new JLabel());

    String[] labels = CustomerData.fieldsLabels;
    int numPairs = labels.length;

    for (int i = 0; i < numPairs; i++) {
      JLabel l = new JLabel(labels[i], 11);
      add(l);
      l.setLabelFor(CustomerData.fieldsInputType[i]);
      add(CustomerData.fieldsInputType[i]);
    }

    if (numPairs % 2 != 0) {
      numPairs++;
    }

    numPairs += 2;

    setLayout(new GridLayout(numPairs, 2, 4, 4));
  }

  public Customer getSpecifiedCustomer()
  {
    if (this.comboBox.newCustomerSelected()) {
      return null;
    }
    return this.comboBox.getSelectedCustomer();
  }

  public void writeCustomerToTextFields(Customer cust)
  {
    if (cust == null)
      for (int i = 0; i < CustomerData.fieldsName.length; i++) {
        Component inputField = CustomerData.fieldsInputType[i];
        if ((inputField instanceof JTextField))
          ((JTextField)inputField).setText("");
      }
    else
      for (int i = 0; i < CustomerData.fieldsName.length; i++) {
        Component inputField = CustomerData.fieldsInputType[i];
        if ((inputField instanceof JTextField))
          ((JTextField)inputField).setText(cust.getInfoByFieldNumber(i));
      }
  }

  public CustomerData getCustomerDataFromTextFields()
  {
    CustomerData data = new CustomerData();
    for (int i = 0; i < CustomerData.fieldsName.length; i++) {
      String fieldname = CustomerData.fieldsName[i];
      Component inputField = CustomerData.fieldsInputType[i];
      if ((inputField instanceof JTextField)) {
        String val = ((JTextField)inputField).getText();
        data.updateByFieldName(fieldname, val);
      }
    }
    return data;
  }

  public void reset() {
    this.comboBox.refresh();
    for (int i = 0; i < CustomerData.fieldsName.length; i++) {
      Component inputField = CustomerData.fieldsInputType[i];
      if ((inputField instanceof JTextField))
        ((JTextField)inputField).setText("");
    }
  }

  public void setEditable(boolean b)
  {
    for (int i = 0; i < CustomerData.fieldsName.length; i++) {
      Component inputField = CustomerData.fieldsInputType[i];
      if ((inputField instanceof JTextField))
        ((JTextField)inputField).setEditable(b);
    }
  }

  public CustomerListComboBox getComboBox() {
    return this.comboBox;
  }
}
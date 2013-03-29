package org.servicebase.gui;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import org.servicebase.database.Machine;
import org.servicebase.database.MachineData;

public class MachineInputPanel extends JPanel
{
  public MachineListComboBox comboBox;

  public MachineInputPanel(CustomerInputPanel customerInputPanel)
  {
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    setBorder(new TitledBorder(null, "Μηχάνημα", 
      4, 2, null, null));

    this.comboBox = new MachineListComboBox(customerInputPanel);
    this.comboBox.setParentPanel(this);
    add(this.comboBox);
    add(new JLabel());
    add(new JLabel());
    add(new JLabel());

    String[] labels = MachineData.fieldsLabels;
    int numPairs = labels.length;

    for (int i = 0; i < numPairs; i++) {
      JLabel l = new JLabel(labels[i], 11);
      add(l);
      l.setLabelFor(MachineData.fieldsInputType[i]);
      add(MachineData.fieldsInputType[i]);
    }

    if (numPairs % 2 != 0) {
      numPairs++;
    }

    numPairs += 2;

    setLayout(new GridLayout(numPairs, 2, 4, 4));
  }

  public Machine getSpecifiedMachine()
  {
    if (this.comboBox.newMachineSelected()) {
      return null;
    }
    return this.comboBox.getSelectedMachine();
  }

  public void writeMachineToTextFields(Machine mach)
  {
    if (mach == null) {
      for (int i = 0; i < MachineData.fieldsName.length; i++) {
        Component inputField = MachineData.fieldsInputType[i];
        if ((inputField instanceof JTextField))
          ((JTextField)inputField).setText("");
      }
    }
    else
      for (int i = 0; i < MachineData.fieldsName.length; i++) {
        Component inputField = MachineData.fieldsInputType[i];
        if ((inputField instanceof JTextField))
          ((JTextField)inputField).setText(mach.getInfoByFieldNumber(i));
      }
  }

  public MachineData getMachineDataFromTextFields() {
    MachineData data = new MachineData();
    for (int i = 0; i < MachineData.fieldsName.length; i++) {
      String fieldname = MachineData.fieldsName[i];
      Component inputField = MachineData.fieldsInputType[i];
      if ((inputField instanceof JTextField)) {
        String val = ((JTextField)inputField).getText();
        data.updateByFieldName(fieldname, val);
      }
    }
    return data;
  }

  public void reset() {
    for (int i = 0; i < MachineData.fieldsName.length; i++) {
      Component inputField = MachineData.fieldsInputType[i];
      if ((inputField instanceof JTextField))
        ((JTextField)inputField).setText("");
    }
  }

  public void setEditable(boolean b)
  {
    for (int i = 0; i < MachineData.fieldsName.length; i++) {
      Component inputField = MachineData.fieldsInputType[i];
      if ((inputField instanceof JTextField))
        ((JTextField)inputField).setEditable(b);
    }
  }
}
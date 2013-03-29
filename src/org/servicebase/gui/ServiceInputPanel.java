package org.servicebase.gui;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import org.servicebase.database.Service;
import org.servicebase.database.ServiceData;

public class ServiceInputPanel extends JPanel
{
  public ServiceInputPanel()
  {
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    setBorder(new TitledBorder(null, "Επισκευή", 
      4, 2, null, null));
    setLayout(new GridBagLayout());

    String[] labels = ServiceData.fieldsLabels;
    Component[] inputFields = ServiceData.fieldsInputType;
    int numPairs = labels.length;

    JLabel l = new JLabel(labels[0], 11);
    l.setLabelFor(inputFields[0]);

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0; c.gridy = 0;
    c.gridheight = 1; c.gridwidth = 1;
    c.insets = new Insets(0, 0, 0, 3);
    add(l, c);

    c = new GridBagConstraints();
    c.gridx = 1; c.gridy = 0;
    c.gridheight = 1; c.gridwidth = 1;
    add(inputFields[0], c);

    l = new JLabel(labels[1], 11);
    JTextArea desc = (JTextArea)inputFields[1];
    desc.setColumns(8);
    desc.setRows(8);
    desc.setLineWrap(true);
    desc.setWrapStyleWord(true);
    JScrollPane scroll = new JScrollPane(desc);
    l.setLabelFor(scroll);

    c = new GridBagConstraints();
    c.gridx = 0; c.gridy = 1;
    c.gridheight = 1; c.gridwidth = 1;
    c.anchor = 23;
    add(l, c);

    c = new GridBagConstraints();
    c.gridx = 1; c.gridy = 1;
    c.gridheight = 1; c.gridwidth = 1;
    add(scroll, c);
  }

  public Service getSpecifiedService()
  {
    ServiceData data = getServiceDataFromTextFields();
    return new Service(data);
  }

  public void writeServiceToTextFields(Service serv)
  {
    if (serv == null) {
      for (int i = 0; i < ServiceData.fieldsName.length; i++) {
        Component inputField = ServiceData.fieldsInputType[i];
        if ((inputField instanceof JTextField))
          ((JTextField)inputField).setText("");
        else if ((inputField instanceof JTextArea)) {
          ((JTextArea)inputField).setText("");
        }
      }
    }
    else
      for (int i = 0; i < ServiceData.fieldsName.length; i++) {
        Component inputField = org.servicebase.database.MachineData.fieldsInputType[i];
        if ((inputField instanceof JTextField))
          ((JTextField)inputField).setText(serv.getInfoByFieldNumber(i));
        else if ((inputField instanceof JTextArea))
          ((JTextArea)inputField).setText(serv.getInfoByFieldNumber(i));
      }
  }

  public ServiceData getServiceDataFromTextFields() {
    ServiceData data = new ServiceData();
    for (int i = 0; i < ServiceData.fieldsName.length; i++) {
      String fieldname = ServiceData.fieldsName[i];
      Component inputField = ServiceData.fieldsInputType[i];
      if ((inputField instanceof JTextField)) {
        String val = ((JTextField)inputField).getText();
        data.updateByFieldName(fieldname, val);
      }
      else if ((inputField instanceof JTextArea)) {
        String val = ((JTextArea)inputField).getText();
        data.updateByFieldName(fieldname, val);
      }
    }
    return data;
  }

  public void setEditable(boolean b) {
    for (int i = 0; i < ServiceData.fieldsName.length; i++) {
      Component inputField = ServiceData.fieldsInputType[i];
      if ((inputField instanceof JTextField))
        ((JTextField)inputField).setEditable(b);
      else if ((inputField instanceof JTextArea))
        ((JTextArea)inputField).setEditable(b);
    }
  }

  public void reset() {
    for (int i = 0; i < ServiceData.fieldsName.length; i++) {
      Component inputField = ServiceData.fieldsInputType[i];
      if ((inputField instanceof JTextField)) {
        ((JTextField)inputField).setText("");
      }
      else if ((inputField instanceof JTextArea))
        ((JTextArea)inputField).setText("");
    }
  }
}
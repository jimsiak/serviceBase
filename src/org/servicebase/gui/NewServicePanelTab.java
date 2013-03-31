package org.servicebase.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.servicebase.Main;
import org.servicebase.database.Customer;
import org.servicebase.database.CustomerData;
import org.servicebase.database.DatabaseManager;
import org.servicebase.database.Machine;
import org.servicebase.database.MachineData;
import org.servicebase.database.Service;

public class NewServicePanelTab extends JPanel
implements ActionListener
{
	private CustomerInputPanel customerInputPanel;
	private MachineInputPanel machineInputPanel;
	private ServiceInputPanel serviceInputPanel;
	private SpareInputPanel spareInputPanel;

	public NewServicePanelTab()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints c;
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = c.weighty = 1.0;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		this.customerInputPanel = new CustomerInputPanel();
		add(this.customerInputPanel, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = c.weighty = 1.0;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		this.machineInputPanel = new MachineInputPanel(this.customerInputPanel);
		add(this.machineInputPanel, c);

		this.customerInputPanel.comboBox.setMachineListComboBox(this.machineInputPanel.comboBox);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = c.weighty = 1.0;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		this.serviceInputPanel = new ServiceInputPanel();
		add(this.serviceInputPanel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = c.weighty = 1.0;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		this.spareInputPanel = new SpareInputPanel();
		add(this.spareInputPanel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		JButton addService = new JButton("ΈΤΟΙΜΟ!");
		addService.addActionListener(this);
		add(addService, c);
	}

	public void actionPerformed(ActionEvent e)
	{
		Customer cust = this.customerInputPanel.getSpecifiedCustomer();
		if (cust == null)
		{
			CustomerData data = this.customerInputPanel.getCustomerDataFromTextFields();
			cust = new Customer(data);
			Main.dbmanager.addCustomerToDatabase(cust);
		}

		Machine mach = this.machineInputPanel.getSpecifiedMachine();
		if (mach == null)
		{
			MachineData data = this.machineInputPanel.getMachineDataFromTextFields();
			mach = new Machine(data);
			Main.dbmanager.addMachineToDatabase(mach, cust);
		}

		Service serv = this.serviceInputPanel.getSpecifiedService();
		Main.dbmanager.addServiceToDatabase(serv, mach);

		List spares = this.spareInputPanel.getSpares();
		Main.dbmanager.addSpareListToDatabase(spares, serv);

		this.customerInputPanel.reset();
		this.machineInputPanel.reset();
		this.serviceInputPanel.reset();
	}
}
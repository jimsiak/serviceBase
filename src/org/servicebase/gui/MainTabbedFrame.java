package org.servicebase.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class MainTabbedFrame extends JFrame
{
  private JTabbedPane contentPane;

  public MainTabbedFrame()
  {
    setTitle("ServiceBase");
    setDefaultCloseOperation(3);

    setSize(new Dimension(870, 600));
    this.contentPane = new JTabbedPane();
    this.contentPane.setSize(new Dimension(900, 300));
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(this.contentPane);

    NewServicePanelTab newServicePanelTab = new NewServicePanelTab();
    ViewDatabasePanelTab viewDatabasePanelTab = new ViewDatabasePanelTab();

    this.contentPane.addTab("Δελτίο Επισκευής", newServicePanelTab);
    this.contentPane.addTab("Διαχείριση Βάσης", viewDatabasePanelTab);
    this.contentPane.addTab("Αντίγραφα Ασφαλείας", new ImportExportDatabasePanelTab());
  }
}
package org.servicebase;

import java.awt.EventQueue;
import org.servicebase.database.DatabaseManager;
import org.servicebase.gui.MainTabbedFrame;

public class Main
{
  public static DatabaseManager dbmanager;

  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Main.dbmanager = new DatabaseManager();
          Main.dbmanager.createDatabase("serviceBaseNew");
          Main.dbmanager.connect();
          Main.dbmanager.createCustomersTable("customers");
          Main.dbmanager.createMachinesTable("machines");
          Main.dbmanager.createServiceTable("services");
          Main.dbmanager.createSpareTable("spares");

          MainTabbedFrame frame = new MainTabbedFrame();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}
package org.example;



import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

public class Main {
   public static void main(String[] args) throws Exception {
      Settings settings = new Settings();
      settings.loadProperties();
      Frame frame = new Frame("QC Creator");

      JPanel container = new JPanel();
      container.setLayout(new BorderLayout());

      Label tokenLabel = new Label("Smartsheet Authentication Token", Label.RIGHT);
      TextField tokenTextField = createSettingstextField(settings.getToken());
      JPanel tokenPanel = createSettingsPanel(tokenLabel, tokenTextField);

      Label sheetNameLabel = new Label("Smartsheet Name", Label.RIGHT);
      TextField sheetNameTextField = createSettingstextField(settings.getSmartsheetName());
      JPanel sheetPanel = createSettingsPanel(sheetNameLabel, sheetNameTextField);

      Label sheetColumn = new Label("Smartsheet Column", Label.RIGHT);
      TextField sheetColumnText = createSettingstextField(settings.getSmartsheetColumn());
      JPanel sheetColumnPanel = createSettingsPanel(sheetColumn, sheetColumnText);

      Label sheetColumnFilter = new Label("Smartsheet Column Filter", Label.RIGHT);
      TextField sheetColumnFilterText = createSettingstextField(settings.getSmartsheetColumnFilter());
      JPanel sheetColumnFilterPanel = createSettingsPanel(sheetColumnFilter, sheetColumnFilterText);

      Label groupByColumn= new Label("Group By Column", Label.RIGHT);
      TextField groupByColumnText = createSettingstextField(settings.getGroupByColumn());
      JPanel groupByColumnPanel = createSettingsPanel(groupByColumn, groupByColumnText);

      Label titleColumn= new Label("Title Column", Label.RIGHT);
      TextField titleColumnText = createSettingstextField(settings.getTitleColumns());
      JPanel titleColumnPanel = createSettingsPanel(titleColumn, titleColumnText);

      Label descriptionColumn= new Label("Description Column", Label.RIGHT);
      TextField descriptionColumnText = createSettingstextField(settings.getDecriptionColumn());
      JPanel descriptionColumnPanel = createSettingsPanel(descriptionColumn, descriptionColumnText);

      JPanel settingsPanel = new JPanel();
      settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.PAGE_AXIS));
      settingsPanel.add(tokenPanel);
      settingsPanel.add(sheetPanel);
      settingsPanel.add(sheetColumnPanel);
      settingsPanel.add(sheetColumnFilterPanel);
      settingsPanel.add(groupByColumnPanel);
      settingsPanel.add(titleColumnPanel);
      settingsPanel.add(descriptionColumnPanel);

      JTextArea markdownArea = new JTextArea("content will appear here");


      Button create = new Button("Create");

      JPanel actionPanel = new JPanel();
      actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      actionPanel.add(create);

      container.add(settingsPanel, BorderLayout.NORTH);
      container.add(markdownArea, BorderLayout.CENTER);
      container.add(actionPanel, BorderLayout.SOUTH);
      frame.add(container);

      create.addActionListener(e -> {

         try {
            settings.saveProperties(tokenTextField.getText(), sheetNameTextField.getText(), sheetColumnText.getText(), sheetColumnFilterText.getText(), groupByColumnText.getText(), titleColumnText.getText(), descriptionColumnText.getText());
            Creator creator = new Creator();
            Map<String, List<Goal>> goals = creator.create(tokenTextField.getText(), sheetNameTextField.getText(), sheetColumnText.getText(), sheetColumnFilterText.getText(), groupByColumnText.getText(), titleColumnText.getText(), descriptionColumnText.getText());
            GoalPrinter printer = new GoalPrinter();
            StringBuilder print = printer.print(goals);
            markdownArea.setText(print.toString());
         } catch (Exception ex) {
            throw new RuntimeException(ex);
         }

      });

      // Handle window close event using WindowAdapter

      frame.addWindowListener(new WindowAdapter() {

         public void windowClosing(WindowEvent e) {

            System.exit(0);

         }

      });

      // Set frame size and make it visible

      frame.pack();
      frame.setSize(900, 450);

      frame.setVisible(true);



   }

   private static TextField createSettingstextField(String text) {
      TextField tokenField = new TextField(text);
      tokenField.setColumns(30);
      return tokenField;
   }

   private static JPanel createSettingsPanel(Label tokenLabel, TextField tokenField) {
      JPanel tokenPanel = new JPanel();
      tokenPanel.setLayout(new GridLayout(1,2));
      tokenPanel.add(tokenLabel);
      tokenPanel.add(tokenField);
      return tokenPanel;
   }
}
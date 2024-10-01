package org.example;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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
      container.setLayout(new BorderLayout(5,5));
      container.setBorder(new EmptyBorder(5, 5, 5, 5));

      JLabel tokenLabel = new JLabel("Smartsheet Authentication Token ", JLabel.RIGHT);
      JTextField tokenTextField = createSettingsTextField(settings.getToken());
      tokenTextField.setToolTipText("Use the token from the API integration properties within Smartsheet");
      JPanel tokenPanel = createSettingsPanel(tokenLabel, tokenTextField);

      JLabel sheetNameLabel = new JLabel("Smartsheet Name ", JLabel.RIGHT);
      JTextField sheetNameTextField = createSettingsTextField(settings.getSmartsheetName());
      sheetNameTextField.setToolTipText("Which smartsheet do you want to extract from");
      JPanel sheetPanel = createSettingsPanel(sheetNameLabel, sheetNameTextField);

      JLabel sheetColumn = new JLabel("Smartsheet Column ", JLabel.RIGHT);
      JTextField sheetColumnText = createSettingsTextField(settings.getSmartsheetColumn());
      sheetColumnText.setToolTipText("Which smartsheet column do you want to filter data by and extract");
      JPanel sheetColumnPanel = createSettingsPanel(sheetColumn, sheetColumnText);

      JLabel sheetColumnFilter = new JLabel("Smartsheet Column Filter ", JLabel.RIGHT);
      JTextField sheetColumnFilterText = createSettingsTextField(settings.getSmartsheetColumnFilter());
      sheetColumnFilterText.setToolTipText("Which value from the above column do you want to filter the data by");
      JPanel sheetColumnFilterPanel = createSettingsPanel(sheetColumnFilter, sheetColumnFilterText);

      JLabel groupByColumn= new JLabel("Group By Column ", JLabel.RIGHT);
      JTextField groupByColumnText = createSettingsTextField(settings.getGroupByColumn());
      groupByColumnText.setToolTipText("Which smartsheet column has the values you want to group the output by");
      JPanel groupByColumnPanel = createSettingsPanel(groupByColumn, groupByColumnText);

      JLabel titleColumn= new JLabel("Title Column ", JLabel.RIGHT);
      JTextField titleColumnText = createSettingsTextField(settings.getTitleColumns());
      titleColumnText.setToolTipText("Which smartsheet column has the title of the task");
      JPanel titleColumnPanel = createSettingsPanel(titleColumn, titleColumnText);

      JLabel descriptionColumn= new JLabel("Description Column ", JLabel.RIGHT);
      JTextField descriptionColumnText = createSettingsTextField(settings.getDecriptionColumn());
      descriptionColumnText.setToolTipText("Which smartsheet column has the description in it");
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


      JButton create = new JButton("Create");

      JPanel actionPanel = new JPanel();
      actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      actionPanel.add(create);

      JButton copyText = new JButton("Copy Text");
      copyText.setEnabled(false);

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
            copyText.setEnabled(true);
         } catch (Exception ex) {
            throw new RuntimeException(ex);
         }

      });

      copyText.addActionListener(e -> {

         try {
            StringSelection stringSelection = new StringSelection(markdownArea.getText());
            Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
            clpbrd.setContents (stringSelection, null);
         } catch (Exception ex) {
            throw new RuntimeException(ex);
         }

      });
      actionPanel.add(copyText);

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

   private static JTextField createSettingsTextField(String text) {
      JTextField tokenField = new JTextField(text);
      tokenField.setColumns(30);
      return tokenField;
   }

   private static JPanel createSettingsPanel(JLabel tokenLabel, JTextField tokenField) {
      JPanel tokenPanel = new JPanel();
      tokenPanel.setLayout(new GridLayout(1,2));
      tokenPanel.add(tokenLabel);
      tokenPanel.add(tokenField);
      return tokenPanel;
   }
}
package org.example;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.Map;

import io.github.gitbucket.markedj.*;

public class Main {
   public static void main(String[] args) throws Exception {
      Settings settings = new Settings();
      settings.loadProperties();
      JFrame frame = new JFrame("QC Creator");

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


      // With default options
      JEditorPane formattedMarkdown = new JEditorPane();
      formattedMarkdown.setContentType("text/html");
      formattedMarkdown.setEditable(false);
      JScrollPane scrollableText = new JScrollPane(formattedMarkdown, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


      JButton create = new JButton("Create");

      JPanel actionPanel = new JPanel();
      actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      actionPanel.add(create);

      JButton copyText = new JButton("Copy Text");
      copyText.setEnabled(false);

      container.add(settingsPanel, BorderLayout.NORTH);
      container.add(scrollableText, BorderLayout.CENTER);
      container.add(actionPanel, BorderLayout.SOUTH);
      frame.add(container);

      create.addActionListener(e -> {

         try {
            settings.saveProperties(tokenTextField.getText(), sheetNameTextField.getText(), sheetColumnText.getText(), sheetColumnFilterText.getText(), groupByColumnText.getText(), titleColumnText.getText(), descriptionColumnText.getText());
            Creator creator = new Creator();
            Map<String, List<Goal>> goals = creator.create(tokenTextField.getText(), sheetNameTextField.getText(), sheetColumnText.getText(), sheetColumnFilterText.getText(), groupByColumnText.getText(), titleColumnText.getText(), descriptionColumnText.getText());
            GoalPrinter printer = new GoalPrinter();
            StringBuilder print = printer.print(goals);
            String formattedText = Marked.marked(print.toString());
            formattedMarkdown.setText(formattedText);
            copyText.setEnabled(true);
         } catch (Exception ex) {
            throw new RuntimeException(ex);
         }

      });

      copyText.addActionListener(e -> {

         try {
            HtmlStringSelection stringSelection = new HtmlStringSelection(formattedMarkdown.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit ().getSystemClipboard ();
            clipboard.setContents (stringSelection, null);
         } catch (Exception ex) {
            throw new RuntimeException(ex);
         }

      });
      actionPanel.add(copyText);

      // Set the frame to exit on close
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Set frame size and make it visible

      frame.setPreferredSize(new Dimension(900, 450));
      frame.pack();
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
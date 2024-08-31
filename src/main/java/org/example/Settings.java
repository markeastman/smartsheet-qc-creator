package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
   private static final String TOKEN = "TOKEN";

   private static final String SHEETNAME = "SHEETNAME";

   private static final String SHEETCOLUMN = "SHEETCOLUMN";

   private static final String SHEETCOLUMNFILTER = "SHEETCOLUMNFILTER";

   private static final String GROUPBYCOLUMN = "GROUPBYCOLUMN";

   private static final String TITLECOLUMN = "TITLECOLUMN";

   private static final String DESCRIPTIONCOLUMN = "DESCRIPTIONCOLUMN";

   private Properties properties = new Properties();

   public String getToken() {
      return properties.getProperty(TOKEN);
   }

   public String getSmartsheetName() {
      return properties.getProperty(SHEETNAME);
   }

   public String getSmartsheetColumn() {
      return properties.getProperty(SHEETCOLUMN);
   }

   public String getSmartsheetColumnFilter() {
      return properties.getProperty(SHEETCOLUMNFILTER);
   }

   public String getGroupByColumn() {
      return properties.getProperty(GROUPBYCOLUMN);
   }

   public String getTitleColumns() {
      return properties.getProperty(TITLECOLUMN);
   }

   public String getDecriptionColumn(){
      return properties.getProperty(DESCRIPTIONCOLUMN);
   }

   public void loadProperties() throws Exception {
      String homeDir = System.getProperty("user.home");
      File file = new File(homeDir + "/creator.properties");
      if(!file.exists()) {
         properties.put(TOKEN, "Add Token");
         properties.put(SHEETNAME, "Add Smart Sheet name");
         properties.put(SHEETCOLUMN, "Add Column Name");
         properties.put(SHEETCOLUMNFILTER, "Add Column Filter");
         properties.put(GROUPBYCOLUMN, "Add Group By Column ");
         properties.put(TITLECOLUMN, "Title Column ");
         properties.put(DESCRIPTIONCOLUMN, "Add Description Column ");
         file.createNewFile();
         FileOutputStream outputStream = new FileOutputStream(file);
         properties.store(outputStream, "SmartSheets Quarterly Connection Creator");
         return;
      }
      FileInputStream inputStream = new FileInputStream(file);
      properties.load(inputStream);
   }

   public void saveProperties(String token, String sheetName, String sheetColumn, String sheetColumnFilter, String groupByColumn, String titleColumn, String descriptionColumn) throws IOException {
      properties = new Properties();
      properties.put(TOKEN, token);
      properties.put(SHEETNAME, sheetName);
      properties.put(SHEETCOLUMN, sheetColumn);
      properties.put(SHEETCOLUMNFILTER, sheetColumnFilter);
      properties.put(GROUPBYCOLUMN, groupByColumn);
      properties.put(TITLECOLUMN, titleColumn);
      properties.put(DESCRIPTIONCOLUMN, descriptionColumn);
      String homeDir = System.getProperty("user.home");
      File file = new File(homeDir + "/creator.properties");
      FileOutputStream outputStream = new FileOutputStream(file);
      properties.store(outputStream, "SmartSheets Quarterly Connection Creator");
   };
}

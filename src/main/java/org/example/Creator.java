package org.example;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.SmartsheetException;
import com.smartsheet.api.SmartsheetFactory;
import com.smartsheet.api.models.Attachment;
import com.smartsheet.api.models.Cell;
import com.smartsheet.api.models.Column;
import com.smartsheet.api.models.Discussion;
import com.smartsheet.api.models.PagedResult;
import com.smartsheet.api.models.Row;
import com.smartsheet.api.models.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Creator {
   public Map<String, List<Goal>>create(String accessToken, String sheetName, String filterColumn, String filterColumnValue, String groupByColumn, String titleColumn, String descriptionColumn) throws SmartsheetException {
      // Initialize client
      Smartsheet smartsheet = SmartsheetFactory.createDefaultClient(accessToken);

// List all sheets
      PagedResult<Sheet> sheets = smartsheet.sheetResources().listSheets(null, null);

      System.out.println("Found " + sheets.getTotalCount() + " sheets");
      long sheetId = 0;
      for (Sheet sheet : sheets.getData()) {
         if (sheetName.equalsIgnoreCase(sheet.getName())) {
            sheetId = sheet.getId();
            break;
         }
      }


      System.out.println("Loading sheet id: " + sheetId);

      // Load the entire sheet
      Sheet sheet = smartsheet.sheetResources().getSheet(sheetId, null, null, null, null, null, null, null);
      System.out.println("Loaded " + sheet.getTotalRowCount() + " rows from sheet: " + sheet.getName());
      Column theFilterColumn = getColumn(filterColumn, sheet);
      Column theGroupByColumn = getColumn(groupByColumn, sheet);
      Column theTitleColumn = getColumn(titleColumn, sheet);
      Column theDescritionColumn = getColumn(descriptionColumn, sheet);
      Map<String, List<Goal>> goals = new HashMap<>();
      for (Row row : sheet.getRows()) {
         Cell cell = row.getCells().get(theFilterColumn.getIndex());
         if (filterColumnValue.equalsIgnoreCase(cell.getDisplayValue())) {
            Cell groupByCell = row.getCells().get(theGroupByColumn.getIndex());
            List<Goal> goalList = goals.computeIfAbsent(groupByCell.getDisplayValue(), k -> new ArrayList<>());
            Goal goal = new Goal();
            goal.setTitle(row.getCells().get(theTitleColumn.getIndex()).getDisplayValue());
            goal.setDescription(row.getCells().get(theDescritionColumn.getIndex()).getDisplayValue());
            PagedResult<Discussion> discussionPagedResult = smartsheet.sheetResources().rowResources().discussionResources().listDiscussions(sheetId, row.getId(), null, null);
            for (Discussion discussion : discussionPagedResult.getData()) {
               goal.addBullet(discussion.getTitle() + " " + discussion.getComment());
            }

            PagedResult<Attachment> attachments = smartsheet.sheetResources().rowResources().attachmentResources().getAttachments(sheetId, row.getId(), null);
            for (Attachment attachment : attachments.getData()) {
               goal.addBullet(attachment.getName() + " " + (attachment.getDescription() != null ? attachment.getDescription() + " ": "") + attachment.getUrl());
            }
            goalList.add(goal);
         }
      }
      return goals;
   }

   private static Column getColumn(String columnName, Sheet sheet) {
      for (Column column : sheet.getColumns()) {
         if (columnName.equalsIgnoreCase(column.getTitle())) {
            return column;
         }
      }
      return null;
   }
}

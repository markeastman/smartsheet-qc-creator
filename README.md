# Summary
This application extracts data from a smartsheet spreadsheet and formats the data selected into headings for tasks and
the action items associated with each in MarkDown syntax. When viewed in a formatter this data can then be cut and pasted into
our WorkDay quarterly connection process.

# Running the application 
The application is a simple Java main application and once started you will be presented with a dialog that will allow
you to select the smartsheet, and the data you want to extract. Once defined the "create" button will then run the extraction and 
format the data items into the MarkDown syntax.

The fields you will be asked to enter are:
| Field    | Explanation |
| -------- | ------- |
| Smartsheet Authentication Token  | The API token defined within Smartsheet settings which will allow API access to your files    |
| Smartsheet Name | The name of the specific Smartsheet you want to extract data from     |
| Smartsheet Column    | The heading of the column that contains the filtering of rows that you want to extract    |
| Smartsheet Column Filter    | The filter value of the above column that allows you to select the correct rows for the extract    |
| Group By Column    | The column heading name of the column that contains items that you need to be grouped together in the output    |
| Title Column    | Which column contains the title of the row extracted    |
| Description Column    | Which column contaisn the description of the row extracted    |

Once these have been entered you can then create the reportusing the "Create" button.

After the report has been run you can copy the text quickly via the "Copy Text" button.

# Viewing the report
Any MarkDown viewer will work but a simple one is if you are using IntelliJ you can look at the file and it will format the output
as well as being able to view the source. The formatted data can then be copied into WD.

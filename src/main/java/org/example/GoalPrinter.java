package org.example;

import java.util.List;
import java.util.Map;

public class GoalPrinter {
   public StringBuilder print(Map<String, List<Goal>> goals) {
      StringBuilder builder = new StringBuilder();
      String separator = System.lineSeparator();
      builder.append("# Quarterly Connection").append(separator).append(separator);
      for (Map.Entry<String, List<Goal>> entry : goals.entrySet()) {
         builder.append("## ").append(entry.getKey()).append(separator).append(separator);
         for (List<Goal> value : goals.values()) {
            for (Goal goal : value) {
               builder.append("### ").append(goal.getTitle()).append(separator).append(separator);
               builder.append(goal.getDescription()).append(separator);
               for (String bullet : goal.getBullets()) {
                  builder.append("- ").append(bullet).append(separator);
               }
               builder.append(separator);
            }
         }
      }
      return builder;
   }
}

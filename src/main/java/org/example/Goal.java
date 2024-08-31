package org.example;

import java.util.ArrayList;
import java.util.List;

public class Goal {
   private String title;

   private String description;
   private final List<String> bullets = new ArrayList<>();

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }


   public List<String> getBullets() {
      return bullets;
   }

   public void addBullet(String bullet) {
      bullets.add(bullet);
   }
}

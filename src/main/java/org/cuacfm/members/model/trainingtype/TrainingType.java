package org.cuacfm.members.model.trainingtype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class TrainingType. */
@SuppressWarnings("serial")
@Entity
public class TrainingType implements java.io.Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The name. */
   @Column(unique = true)
   private String name;

   /** The required. */
   private boolean required;

   /** The description. */
   private String description;

   /** The place. */
   private String place;

   /** The duration. */
   private int duration;

   /**
    * The hasTrainings, is more efficient save this parameter for display valor
    * in table.
    */
   private boolean hasTrainings;

   /** Instantiates a new training. */
   protected TrainingType() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new training.
    *
    * @param name
    *           String
    * @param required
    *           boolean
    * @param description
    *           String
    * @param place
    *           String
    * @param duration
    *           int
    */
   public TrainingType(String name, boolean required, String description, String place, int duration) {
      super();
      this.name = name;
      this.required = required;
      this.description = description;
      this.place = place;
      this.duration = duration;
      this.hasTrainings = false;
   }

   /**
    * Get the id.
    *
    * @return id
    */
   public Long getId() {
      return id;
   }

   /**
    * Sets the id.
    *
    * @param id
    *           the new id
    */
   // If necessary to probe test in Junit, because is necessary one object in
   // detach
   public void setId(Long id) {
      this.id = id;
   }

   /**
    * Get the name.
    *
    * @return name
    */
   public String getName() {
      return name;
   }

   /**
    * Set the name.
    *
    * @param name
    *           String, the new name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Get the required.
    *
    * @return the boolean isRequired
    */
   public boolean isRequired() {
      return required;
   }

   /**
    * Set the required.
    *
    * @param required
    *           boolean
    */
   public void setRequired(boolean required) {
      this.required = required;
   }

   /**
    * Get the description.
    *
    * @return the description
    */
   public String getDescription() {
      return description;
   }

   /**
    * Set the description.
    *
    * @param description
    *           String, the new description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Get the place.
    *
    * @return String
    */
   public String getPlace() {
      return place;
   }

   /**
    * Set the place.
    *
    * @param place
    *           String, the new place
    */
   public void setPlace(String place) {
      this.place = place;
   }

   /**
    * Get the duration.
    *
    * @return int
    */
   public int getDuration() {
      return duration;
   }

   /**
    * Set the duration.
    *
    * @param duration
    *           int, the new duration
    */
   public void setDuration(int duration) {
      this.duration = duration;
   }

   /**
    * Get the hasTrainings.
    *
    * @return the boolean isHasTrainings
    */
   public boolean isHasTrainings() {
      return hasTrainings;
   }

   /**
    * Set the hasTrainings.
    *
    * @param hasTrainings
    *           boolean
    */
   public void setHasTrainings(boolean hasTrainings) {
      this.hasTrainings = hasTrainings;
   }
}

package org.cuacfm.members.model.training;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cuacfm.members.model.trainingtype.TrainingType;

/** The Class Training. */
@SuppressWarnings("serial")
@Entity
public class Training implements java.io.Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The trainingType. */
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   @JoinColumn(name = "trainingTypeId")
   private TrainingType trainingType;

   /** The name. */
   @Column(unique = true)
   private String name;

   /** The dateLimit. */
   private Date dateLimit;

   /** The dateTrainig. */
   private Date dateTraining;

   /** The description. */
   private String description;

   /** The place. */
   private String place;

   /** The duration. */
   private int duration;

   /** The maxPlaces. */
   private int maxPlaces;

   /** The countPlaces. */
   private int countPlaces;

   /** The close. */
   private boolean close;

   /** Instantiates a new training. */
   protected Training() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new training.
    *
    * @param trainingType
    *           TrainingType
    * @param name
    *           String
    * @param limitTraining
    *           Date
    * @param dateTrainig
    *           Date
    * @param description
    *           String
    * @param place
    *           String
    * @param duration
    *           int
    * @param maxPlaces
    *           int
    */
   public Training(TrainingType trainingType, String name, Date dateTraining, Date dateLimit,
         String description, String place, int duration, int maxPlaces) {
      super();
      this.trainingType = trainingType;
      this.name = name;
      this.dateTraining = dateTraining;
      this.dateLimit = dateLimit;
      this.description = description;
      this.place = place;
      this.duration = duration;
      this.maxPlaces = maxPlaces;
   }

   /**
    * Get the id.
    *
    * @return the id
    */
   public Long getId() {
      return id;
   }

   /**
    * Get the name.
    *
    * @return the name
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
    * Get the close.
    *
    * @return boolean isClose
    */
   public boolean isClose() {
      return close;
   }

   /**
    * Set the close.
    *
    * @param close
    *           boolean
    */
   public void setClose(boolean close) {
      this.close = close;
   }

   /**
    * Get the dateTrinig.
    *
    * @return Date with dateTraining
    */
   public Date getDateTraining() {
      return dateTraining;
   }

   /**
    * Set the dateTraining.
    *
    * @param dateTraining
    *           Date
    */
   public void setDateTraining(Date dateTraining) {
      this.dateTraining = dateTraining;
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
    * Get the TrainingType.
    *
    * @return TrainingType
    */
   public TrainingType getTrainingType() {
      return trainingType;
   }

   /**
    * Get the dateLimit.
    *
    * @return Date
    */
   public Date getDateLimit() {
      return dateLimit;
   }

   /**
    * Set the dateLimit.
    *
    * @param dateLimit
    *           Date, the new dateLimit
    */
   public void setDateLimit(Date dateLimit) {
      this.dateLimit = dateLimit;
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
    *           int, the new duration.
    */
   public void setDuration(int duration) {
      this.duration = duration;
   }

   /**
    * Get the MaxPlaces.
    *
    * @return int
    */
   public int getMaxPlaces() {
      return maxPlaces;
   }

   /**
    * Set the maxPlaces.
    *
    * @param countPlaces
    *           int, the new maxPlaces.
    */
   public void setMaxPlaces(int maxPlaces) {
      this.maxPlaces = maxPlaces;
   }

   /**
    * Get the CountPlaces.
    *
    * @return int
    */
   public int getCountPlaces() {
      return countPlaces;
   }

   /**
    * Set the countPlaces.
    *
    * @param countPlaces
    *           int, the new countPlaces
    */
   public void setCountPlaces(int countPlaces) {
      this.countPlaces = countPlaces;
   }

}

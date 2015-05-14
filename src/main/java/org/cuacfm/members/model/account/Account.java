package org.cuacfm.members.model.account;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.program.Program;

/** The Class Account. */
@SuppressWarnings("serial")
@Entity
public class Account implements java.io.Serializable {

   /** The Enum roles. */
   public static enum roles {
      /** The role user. */
      ROLE_USER,
      /** The role admin. */
      ROLE_ADMIN,
      /** The role trainer. */
      ROLE_TRAINER,
      /** The role prescription. */
      ROLE_PREREGISTERED
   }

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The name. */
   private String name;

   /** The nickName. */
   private String nickName;

   /** The dni. */
   @Column(unique = true)
   private String dni;

   /** The address. */
   private String address;

   /** The login. */
   @Column(unique = true)
   private String login;

   /** The email. */
   @Column(unique = true)
   private String email;

   /** The phone. */
   private Integer phone;

   /** The mobile. */
   private int mobile;

   /** The password. */
   // @JsonIgnore
   private String password;

   /** The methodPayment. */
   @ManyToOne(optional = true, fetch = FetchType.EAGER)
   @JoinColumn(name = "methodPaymentId")
   private MethodPayment methodPayment;

   /** The accountType. */
   @ManyToOne(optional = true, fetch = FetchType.EAGER)
   @JoinColumn(name = "accountTypeId")
   private AccountType accountType;

   /** The programs. */
   @ManyToMany(fetch = FetchType.LAZY, mappedBy = "accounts")
   // @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   // @ManyToMany(cascade = CascadeType.ALL)
   // @ManyToMany
   // @JoinTable(name="UserPrograms",
   // joinColumns={@JoinColumn(name="accountId")},
   // inverseJoinColumns={@JoinColumn(name="programId")})
   private List<Program> programs;

   /** The installments, number of installments for pay inscription fee. */
   private int installments;

   /** The active is a check if account if active*. */
   private boolean active;

   /** The student. */
   private boolean student;

   /** The date birth. */
   private Date dateBirth;

   /** The observations. */
   private String observations;

   /** The programName. */
   private String programName;

   /** The role. */
   @Enumerated(EnumType.STRING)
   private roles role;

   /** Instantiates a new account. */
   protected Account() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new account.
    *
    * @param name
    *           the name
    * @param dni
    *           the dni
    * @param address
    *           the address
    * @param login
    *           the login
    * @param email
    *           the email
    * @param phone
    *           the phone
    * @param mobile
    *           the mobile
    * @param password
    *           the password
    * @param role
    *           the role
    */
   public Account(String name, String dni, String address, String login, String email,
         Integer phone, int mobile, String password, roles role) {
      super();
      this.name = name;
      this.dni = dni;
      this.address = address;
      this.login = login;
      this.email = email;
      this.phone = phone;
      this.mobile = mobile;
      this.password = password;
      this.installments = 1;
      this.active = true;
      this.role = role;
   }

   /**
    * Gets the id.
    *
    * @return the id
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
    * Gets the name.
    *
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the name.
    *
    * @param name
    *           the new name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Gets the nick name.
    *
    * @return the nick name
    */
   public String getNickName() {
      return nickName;
   }

   /**
    * Sets the nick name.
    *
    * @param nickName
    *           the new nick name
    */
   public void setNickName(String nickName) {
      this.nickName = nickName;
   }

   /**
    * Gets the address.
    *
    * @return the address
    */
   public String getAddress() {
      return address;
   }

   /**
    * Sets the address.
    *
    * @param address
    *           the new address
    */
   public void setAddress(String address) {
      this.address = address;
   }

   /**
    * Gets the login.
    *
    * @return the login
    */
   public String getLogin() {
      return login;
   }

   /**
    * Sets the login.
    *
    * @param login
    *           the new login
    */
   public void setLogin(String login) {
      this.login = login;
   }

   /**
    * Gets the email.
    *
    * @return the email
    */
   public String getEmail() {
      return email;
   }

   /**
    * Sets the email.
    *
    * @param email
    *           the new email
    */
   public void setEmail(String email) {
      this.email = email;
   }

   /**
    * Gets the phone.
    *
    * @return the phone
    */
   public Integer getPhone() {
      return phone;
   }

   /**
    * Sets the phone.
    *
    * @param phone
    *           the new phone
    */
   public void setPhone(Integer phone) {
      this.phone = phone;
   }

   /**
    * Gets the mobile.
    *
    * @return the mobile
    */
   public int getMobile() {
      return mobile;
   }

   /**
    * Sets the mobile.
    *
    * @param mobile
    *           the new mobile
    */
   public void setMobile(int mobile) {
      this.mobile = mobile;
   }

   /**
    * Gets the dni.
    *
    * @return the dni
    */
   public String getDni() {
      return dni;
   }

   /**
    * Sets the dni.
    *
    * @param dni
    *           the new dni
    */
   public void setDni(String dni) {
      this.dni = dni;
   }

   /**
    * Gets the password.
    *
    * @return the password
    */
   public String getPassword() {
      return password;
   }

   /**
    * Sets the password.
    *
    * @param password
    *           the new password
    */
   public void setPassword(String password) {
      this.password = password;
   }

   /**
    * Gets the method payment.
    *
    * @return the method payment
    */
   public MethodPayment getMethodPayment() {
      return methodPayment;
   }

   /**
    * Sets the method payment.
    *
    * @param methodPayment
    *           the new method payment
    */
   public void setMethodPayment(MethodPayment methodPayment) {
      this.methodPayment = methodPayment;
   }

   /**
    * Gets the account type.
    *
    * @return the account type
    */
   public AccountType getAccountType() {
      return accountType;
   }

   /**
    * Sets the account type.
    *
    * @param accountType
    *           the new account type
    */
   public void setAccountType(AccountType accountType) {
      this.accountType = accountType;
   }

   /**
    * Gets the installments.
    *
    * @return the installments
    */
   public int getInstallments() {
      return installments;
   }

   /**
    * Sets the installments.
    *
    * @param installments
    *           the new installments
    */
   public void setInstallments(int installments) {
      this.installments = installments;
   }

   /**
    * Checks if is active.
    *
    * @return true, if is active
    */
   public boolean isActive() {
      return active;
   }

   /**
    * Sets the active.
    *
    * @param active
    *           the new active
    */
   public void setActive(boolean active) {
      this.active = active;
   }

   /**
    * Checks if is student.
    *
    * @return true, if is student
    */
   public boolean isStudent() {
      return student;
   }

   /**
    * Sets the student.
    *
    * @param student
    *           the new student
    */
   public void setStudent(boolean student) {
      this.student = student;
   }

   /**
    * Gets the date birth.
    *
    * @return the date birth
    */
   public Date getDateBirth() {
      return dateBirth;
   }

   /**
    * Sets the date birth.
    *
    * @param dateBirth
    *           the new date birth
    */
   public void setDateBirth(Date dateBirth) {
      this.dateBirth = dateBirth;
   }

   /**
    * Gets the observations.
    *
    * @return the observations
    */
   public String getObservations() {
      return observations;
   }

   /**
    * Sets the observations.
    *
    * @param observations
    *           the new observations
    */
   public void setObservations(String observations) {
      this.observations = observations;
   }

   /**
    * Gets the program name.
    *
    * @return the program name
    */
   public String getProgramName() {
      return programName;
   }

   /**
    * Sets the program name.
    *
    * @param programName
    *           the new program name
    */
   public void setProgramName(String programName) {
      this.programName = programName;
   }

   /**
    * Gets the role.
    *
    * @return the role
    */
   public roles getRole() {
      return role;
   }

   /**
    * Sets the role.
    *
    * @param role
    *           the new role
    */
   public void setRole(roles role) {
      this.role = role;
   }

   /**
    * Gets the programs.
    *
    * @return the programs
    */
   public List<Program> getPrograms() {
      return programs;
   }

   /**
    * Sets the programs.
    *
    * @param programs
    *           the new programs
    */
   public void setPrograms(List<Program> programs) {
      this.programs = programs;
   }

}

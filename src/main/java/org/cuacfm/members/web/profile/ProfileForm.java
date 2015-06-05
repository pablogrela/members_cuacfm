package org.cuacfm.members.web.profile;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/** The Class ProfileForm. */
public class ProfileForm {

   /** The Constant EMAIL_MESSAGE. */
   private static final String EMAIL_MESSAGE = "{email.message}";

   /** The Constant INSUFFICIENT_CHARACTERS. */
   private static final String INSUFFICIENT_CHARACTERS = "{insuficient.characters}";

   /** The Constant MAX_CHARACTERS. */
   private static final String MAX_CHARACTERS = "{max.characters}";

   /** The Constant NOT_BLANK_MESSAGE. */
   private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
   
   /** The name. */
   @NotBlank(message = ProfileForm.NOT_BLANK_MESSAGE)
   @Size(max = 30, message = ProfileForm.MAX_CHARACTERS)
   private String name;

   /** The nickName. */
   @Size(max = 30, message = ProfileForm.MAX_CHARACTERS)
   private String nickName;

   /** The dni. */
   @NotBlank(message = ProfileForm.NOT_BLANK_MESSAGE)
   @Size(max = 30, message = ProfileForm.MAX_CHARACTERS)
   private String dni;

   /** The address. */
   @NotBlank(message = ProfileForm.NOT_BLANK_MESSAGE)
   @Size(max = 30, message = ProfileForm.MAX_CHARACTERS)
   private String address;

   /** The cp. */
   @NotBlank(message = ProfileForm.NOT_BLANK_MESSAGE)
   @Size(max = 30, message = ProfileForm.MAX_CHARACTERS)
   private String cp;

   /** The province. */
   @NotBlank(message = ProfileForm.NOT_BLANK_MESSAGE)
   @Size(max = 30, message = ProfileForm.MAX_CHARACTERS)
   private String province;

   /** The code country. */
   @NotBlank(message = ProfileForm.NOT_BLANK_MESSAGE)
   @Size(max = 2, message = ProfileForm.MAX_CHARACTERS)
   private String codeCountry;

   /** The login. */
   @Size(max = 30, message = ProfileForm.MAX_CHARACTERS)
   private String login;

   /** The email. */
   @Email(message = ProfileForm.EMAIL_MESSAGE)
   @Size(max = 30, message = ProfileForm.MAX_CHARACTERS)
   private String email;

   /** The phone. */
   private Integer phone;

   /** The mobile. */
   private int mobile;

   /** The program name. */
   @Size(max = 30, message = ProfileForm.MAX_CHARACTERS)
   private String programName;

   /** The student. */
   private boolean student;

   /** The date birth. */
   private String dateBirth;

   /** The password. */
   @Size(min = 4, max = 20, message = ProfileForm.INSUFFICIENT_CHARACTERS)
   private String password;

   /** The retry password. */
   @Size(min = 4, max = 20, message = ProfileForm.INSUFFICIENT_CHARACTERS)
   private String rePassword;

   /** The accountType List. */
   private List<AccountType> accountTypes;

   /** The Global variable accountTypeId. */
   private Long accountTypeId;

   /** The methodPayment List. */
   private List<MethodPayment> methodPayments;

   /** The Global variable methodPaymentId. */
   private Long methodPaymentId;

   /** The installments. */
   @Min(0)
   @Max(12)
   private int installments;

   /** The observations. */
   @Size(max = 500, message = ProfileForm.MAX_CHARACTERS)
   private String observations;

   /** The roles. */
   private List<roles> roles;

   /** The role. */
   private String role;

   /** The on login. */
   private boolean onLogin;

   /** The on email. */
   private boolean onEmail;

   /** The on password. */
   private boolean onPassword;

   /** The on role. */
   private boolean onRole;

   /**
    * Instantiates a new profile form.
    */
   public ProfileForm() {
      // Default empty constructor.
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
    * Gets the cp.
    *
    * @return the cp
    */
   public String getCp() {
      return cp;
   }

   /**
    * Sets the cp.
    *
    * @param cp
    *           the new cp
    */
   public void setCp(String cp) {
      this.cp = cp;
   }

   /**
    * Gets the province.
    *
    * @return the province
    */
   public String getProvince() {
      return province;
   }

   /**
    * Sets the province.
    *
    * @param province
    *           the new province
    */
   public void setProvince(String province) {
      this.province = province;
   }

   /**
    * Gets the code country.
    *
    * @return the code country
    */
   public String getCodeCountry() {
      return codeCountry;
   }

   /**
    * Sets the code country.
    *
    * @param codeCountry
    *           the new code country
    */
   public void setCodeCountry(String codeCountry) {
      this.codeCountry = codeCountry;
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
   public String getDateBirth() {
      return dateBirth;
   }

   /**
    * Sets the date birth.
    *
    * @param dateBirth
    *           the new date birth
    */
   public void setDateBirth(String dateBirth) {
      this.dateBirth = dateBirth;
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
    * Gets the re password.
    *
    * @return the re password
    */
   public String getRePassword() {
      return rePassword;
   }

   /**
    * Sets the re password.
    *
    * @param rePassword
    *           the new re password
    */
   public void setRePassword(String rePassword) {
      this.rePassword = rePassword;
   }

   /**
    * Gets the account types.
    *
    * @return the account types
    */
   public List<AccountType> getAccountTypes() {
      return accountTypes;
   }

   /**
    * Sets the account types.
    *
    * @param accountTypes
    *           the new account types
    */
   public void setAccountTypes(List<AccountType> accountTypes) {
      this.accountTypes = accountTypes;
   }

   /**
    * Gets the account type id.
    *
    * @return the account type id
    */
   public Long getAccountTypeId() {
      return accountTypeId;
   }

   /**
    * Sets the account type id.
    *
    * @param accountTypeId
    *           the new account type id
    */
   public void setAccountTypeId(Long accountTypeId) {
      this.accountTypeId = accountTypeId;
   }

   /**
    * Gets the method payments.
    *
    * @return the method payments
    */
   public List<MethodPayment> getMethodPayments() {
      return methodPayments;
   }

   /**
    * Sets the method payments.
    *
    * @param methodPayments
    *           the new method payments
    */
   public void setMethodPayments(List<MethodPayment> methodPayments) {
      this.methodPayments = methodPayments;
   }

   /**
    * Gets the method payment id.
    *
    * @return the method payment id
    */
   public Long getMethodPaymentId() {
      return methodPaymentId;
   }

   /**
    * Sets the method payment id.
    *
    * @param methodPaymentId
    *           the new method payment id
    */
   public void setMethodPaymentId(Long methodPaymentId) {
      this.methodPaymentId = methodPaymentId;
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
    * Gets the roles.
    *
    * @return the roles
    */
   public List<Account.roles> getRoles() {
      return roles;
   }

   /**
    * Sets the roles.
    *
    * @param roles
    *           the new roles
    */
   public void setRoles(List<Account.roles> roles) {
      this.roles = roles;
   }

   /**
    * Gets the role.
    *
    * @return the role
    */
   public String getRole() {
      return role;
   }

   /**
    * Sets the role.
    *
    * @param role
    *           the new role
    */
   public void setRole(String role) {
      this.role = role;
   }

   /**
    * Checks if is on login.
    *
    * @return true, if is on login
    */
   public boolean isOnLogin() {
      return onLogin;
   }

   /**
    * Sets the on login.
    *
    * @param onLogin
    *           the new on login
    */
   public void setOnLogin(boolean onLogin) {
      this.onLogin = onLogin;
   }

   /**
    * Checks if is on email.
    *
    * @return true, if is on email
    */
   public boolean isOnEmail() {
      return onEmail;
   }

   /**
    * Sets the on email.
    *
    * @param onEmail
    *           the new on email
    */
   public void setOnEmail(boolean onEmail) {
      this.onEmail = onEmail;
   }

   /**
    * Checks if is on password.
    *
    * @return true, if is on password
    */
   public boolean isOnPassword() {
      return onPassword;
   }

   /**
    * Sets the on password.
    *
    * @param onPassword
    *           the new on password
    */
   public void setOnPassword(boolean onPassword) {
      this.onPassword = onPassword;
   }

   /**
    * Checks if is on role.
    *
    * @return true, if is on role
    */
   public boolean isOnRole() {
      return onRole;
   }

   /**
    * Sets the on role.
    *
    * @param onRole
    *           the new on role
    */
   public void setOnRole(boolean onRole) {
      this.onRole = onRole;
   }

}

package java8;

class Person {
   String firstName;
   String lastName;

   public Person() {}

   public Person(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public void setFullName(String firstName, String lastName) {
      this.setFirstName(firstName);
      this.setLastName(lastName);
   }
}
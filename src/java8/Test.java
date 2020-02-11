package java8;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Test
{
   //Concrete classes only implement the abstract method calculate
   //the default method double can be used outside of the box
   interface Formula {
      /*abstract*/ double calculate(int a);

      default double sqrt(int a) {
         return Math.sqrt(a);
      }
   }

   @FunctionalInterface //annotation can be omitted
   interface Converter<F, T> {
      T convert(F from);
   }

   public class Something
   {
      String startsWith(String s) {
         return String.valueOf(s.charAt(0));
      }
   }

   interface PersonFactory<P extends Person> {
      P create(String firstName, String lastName);
   }

   //List all files in a directory that match a filename extension in Java
   Collection<File> getAllConfig(String dirName) {
      File dir = new File(dirName);
      return FileUtils.listFiles(dir, new WildcardFileFilter(".txt"), null);
   }

   //Best way to set a file path
   String filePath = "data" + "\\" + "file.txt";
   String bestFilePath = "data" + File.separator + "file.txt";


   public static void main(String[] args) {
      Formula formula = new Formula() {

         public double calculate(int a) {
            return sqrt(a * 100);
         }
      };

      System.out.println((int) formula.calculate(100));
      System.out.println((int) formula.sqrt(9));

      List<String> names = Arrays.asList("jake", "taylor", "bill", "justin");
      List<String> names2 = names;
      System.out.println(names + "\n");

      //sorting
      Collections.sort(names, new Comparator<String>() {
         public int compare(String a, String b) {
            return b.compareTo(a); 
         } 
      });
      System.out.println("Sorting -> " + names);

      //quicker way to sort
      Collections.sort(names, Comparator.comparing(String::length));
      System.out.println("Sorted by length -> " + names);

      //sorting with a lambda expression
      Collections.sort(names2, (a, b) -> b.compareTo(a));
      System.out.println("Sorting with a lambda expression -> " + names2);

      //Functional interface
      Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
      Integer converted = converter.convert("123");
      System.out.println(converted);

      Something something = new Test().new Something();
      Converter<String, String> converter2 = something::startsWith;
      String converted2 = converter2.convert("Java");
      System.out.println(converted2);

      //Predicate
      Predicate<String> predicate = (s) -> s.length() > 0;
      System.out.println("Negating string length predicate -> " + predicate.negate().test("length"));
      System.out.println("String length predicate -> " + predicate.test("long"));
      Predicate<Object> nonNull = Objects::nonNull;
      Predicate<String> isEmpty = String::isEmpty;

      //Objects class contains useful methods for objects
      if (Objects.nonNull(formula)) {
         System.out.println("Object is not null");
      } else {
         System.out.println("Object is null");
      }

      if (nonNull.test(formula)) {
         System.out.println("Object is not null");
      } else {
         System.out.println("Object is null");
      }

      if (isEmpty.test("")) {
         System.out.println("String is empty");
      } else {
         System.out.println("String is not empty");
      }

      Function<String, Integer> toInteger = Integer::valueOf;
      int testInt = toInteger.apply("65");
      System.out.println(testInt);
      Function<String, String> backToString = toInteger.andThen(String::valueOf);
      String numToString = backToString.apply("76");
      System.out.println(numToString);

      //Creating a factory for creating objects
      //using an interface
      PersonFactory<Person> personFactory = Person::new;
      Person testPerson = personFactory.create("Peter", "Parker");
      testPerson.setLastName("Jones");

      //Supplier & Consumer class
      Supplier<Person> personSupplier = Person::new;
      Person person = personSupplier.get();
      person.setFullName("Jack", "Mathews");
      System.out.println("Supplier class test -> " + " first name: "+ person.firstName + ", last name: " + person.lastName);

      Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
      greeter.accept(person);


      //Comparator
      Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
      Person p1 = new Person("John", "Doe");
      Person p2 = new Person("Alice", "Wonderland");
      System.out.println("Comparator result -> " + comparator.compare(p1, p2));
      System.out.println("Comparator reverse result -> " + comparator.reversed().compare(p1, p2));


      //Java Streams
      Predicate<String> streamPredicate = (s) -> s.startsWith("a");
      List<String> stringCollection = new ArrayList<>();
      stringCollection.add("ddd2");
      stringCollection.add("aaa2");
      stringCollection.add("bbb1");
      stringCollection.add("aaa1");
      stringCollection.add("bbb3");
      stringCollection.add("ccc");
      stringCollection.add("bbb2");
      stringCollection.add("ddd1");

      System.out.println("Stream test, elements starting with a");
      stringCollection.stream().filter(streamPredicate).forEach(s -> System.out.print(s + " "));
      Optional<String> formatted = stringCollection.stream().filter(streamPredicate).reduce((s1, s2) -> s1 + ", " + s2);
      System.out.println("\nPrinted, separated with commas, using the method \"reduce\"");
      formatted.ifPresent(System.out::println);

      System.out.println("Second way to print them out");
      stringCollection.stream().sorted().filter(streamPredicate).forEach(System.out::println);

      //using method map
      System.out.println("Elements are made uppercase, collections is sorted and printed out");
      stringCollection.stream().map(String::toUpperCase).sorted((a, b) -> a.compareTo(b)).forEach(System.out::println);

      //using method anyMatch
      boolean anyStartsWithA = stringCollection.stream().anyMatch( s -> s.startsWith("a"));
      System.out.println("Are there any elements starting with \"a\" -> " + anyStartsWithA);

      //using method allMatch
      boolean allStartWithA = stringCollection.stream().allMatch(s -> s.startsWith("a"));
      System.out.println("Are all elements starting with \"a\" -> " + allStartWithA);

      //using method noneMatch
      boolean noneStartsWithZ = stringCollection.stream().noneMatch(s -> s.startsWith("z"));
      System.out.println("There are no elements starting with \"z\" -> " + noneStartsWithZ);

      //using method count
      long startsWithB = stringCollection.stream().filter(s -> s.startsWith("b")).count();
      System.out.println("Elements starting with \"b\" ->  " + startsWithB);

      //using method reduce
      System.out.println("Testing reduce method");
      Optional<String> reduced = stringCollection.stream().sorted().reduce((s1, s2) -> s1 + " # " + s2);
      reduced.ifPresent(System.out::println);

      //sorting an integer array
      ArrayList<Integer> listToBeSorted = new ArrayList<>(Arrays.asList(45, 2, 6, 34, 60, 34, 64, 100, 49, 3, 452));
      Optional<String> sortedDesc = listToBeSorted.stream().sorted((a, b) -> b.compareTo(a)).map((a) -> Integer.toString(a)).reduce((a, b) -> a + ", " + b);
      System.out.println("Sorted in descending order");
      sortedDesc.ifPresent(System.out::print);
      Optional<String> sortedAsc = listToBeSorted.stream().sorted((a, b) -> a.compareTo(b)).map((a) -> Integer.toString(a)).reduce((a, b) -> a + ", " + b);
      System.out.println("\nSorted in ascending order");
      sortedAsc.ifPresent(System.out::println);
      System.out.println("Optional -> " + sortedAsc);

      //function for parsing to integers with lambda expression
      Function<String, Integer> parseInteger = p -> Integer.parseInt(p);
      List<String> numbers = Arrays.asList("1", "2", "3", "4", "5", "6");
      List<Integer> parsedNumbers = numbers.stream().map(parseInteger).collect(Collectors.toList());
      System.out.println("Parsed number list -> " + parsedNumbers);

      //function to generate a random int array(101 is upper bound(exclusive), 0 is lower bound(inclusive))
      int[] array = new int[10];
      Arrays.setAll(array, i -> (int) (Math.random() * 101 + 0));
      System.out.println(Arrays.toString(array));




      //Parallel Streams
      int max = 1000000;
      List<String> values = new ArrayList<>(max);
      for (int i = 0; i < values.size(); i++) {
         UUID uuid = UUID.randomUUID();
         values.add(uuid.toString());
      }

      //Sequential sort
      long t0 = System.nanoTime();
      long count = values.stream().sorted().count();
      long t1 = System.nanoTime();
      long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
      System.out.println(String.format("sequential sort took: %d ms", millis));
      System.out.println(count);

      //Parallel sort
      long t2 = System.nanoTime();
      long count2 = values.parallelStream().sorted().count();
      long t3 = System.nanoTime();
      long millis2 = TimeUnit.NANOSECONDS.toMillis(t3 - t2);
      System.out.println(String.format("parallel sort took: %d ms", millis2));
      System.out.println(count2);

      //Findint the most repeated number in a list
      Stream.of(1, 3, 4, 3, 4, 3, 2, 3, 3, 3, 3, 3).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
      .entrySet()
      .stream()
      .max(Comparator.comparing(Entry::getValue))
      .ifPresent(System.out::println);
      

      //Maps










      //Create a basic email message to "John Doe" and send it through your Google Mail (GMail) account.
      Email email = new SimpleEmail();
      email.setHostName("smtp.googlemail.com");
      email.setSmtpPort(465);
      email.setAuthenticator(new DefaultAuthenticator("username", "password"));
      email.setSSLOnConnect(true);
      try {
         email.setFrom("user@gmail.com");
         email.setSubject("TestMail");
         email.setMsg("This is a test mail ... :-)");
         email.addTo("foo@bar.com");
         //email.send();
      } catch (EmailException e) {
         e.printStackTrace();
      }
   }
}
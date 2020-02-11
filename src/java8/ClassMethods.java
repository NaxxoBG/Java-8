package java8;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassMethods
{
   public static void main(String[] args) {
      ArrayList<Method> classMethods = new ArrayList<>(Arrays.asList(ClassMethods.class.getMethods()));
      System.out.println(classMethods);
      List<Method> list = classMethods.stream().filter(e -> e.getName().contains("Method")).collect(Collectors.toList());
      System.out.println("List is " + list + " and the size is " + list.size());
   }

   public void testMethod() {
      System.out.println("Testing");
   }

   public void anotherMethod() {
      System.out.println("something else");
   }
   
   //static factory method
   public static <K, V> HashMap<K, V> newInstance() {
      return new HashMap<K, V>();
   }
   
   //type witness
   Map<String, List<String>> m = ClassMethods.<String, List<String>>newInstance();
}
package hello;

import org.joda.time.LocalTime;

public class HelloWorld {
  public static void main(String[] args) {
    LocalTime currentTime = new LocalTime();
    System.out.println("The current local time is: " + currentTime);

    Greeter greeter = new Greeter();
    System.out.println(greeter.sayHello());

//    System.out.println(greeter.example01_RDF4J());
//    System.out.println(greeter.example02_RDF4J());
//    System.out.println(greeter.example03_RDF4J());
//    System.out.println(greeter.example04_RDF4J());
//    System.out.println(greeter.example05_RDF4J());
//    System.out.println(greeter.example06_RDF4J());
//    System.out.println(greeter.example07_RDF4J());
    System.out.println(greeter.example08_RDF4J());
  }
}
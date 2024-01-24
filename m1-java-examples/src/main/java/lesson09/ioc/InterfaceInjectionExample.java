//package lesson09.ioc;
//
//import lesson04.exceptions.Car;
//import lesson09.ioc.model.ElectricEngine;
//import lesson09.ioc.model.Engine;
//
//public class InterfaceInjectionExample {
//
//    public static void main(String[] args) {
//        // First, we need to create an instance of the dependency
//        Engine engine = new ElectricEngine();
//
//        // Then we inject the dependency via constructor
//        Car car = new Car(engine);
//        car.start();
//        car.stop();
//    }
//
//}

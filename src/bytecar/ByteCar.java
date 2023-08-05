 // @author fernandoorozco
package bytecar;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.util.Scanner;  

public class ByteCar {
  static Scanner keyboardInput = new Scanner(System.in);
  int rows = 20;
  int cols = 4;
  String[][] carInvetory = new String[rows][cols];

  
  public static void main(String[] args) {
    while (true) {
      main_menu();
    }
  }  
  
  public static void main_menu(){
    System.out.println("Who are you? \n1)Admin\n2)Client");
    String typeOfUser =  keyboardInput.nextLine();

    switch(typeOfUser){                                                                                         // Desplegamos el menu correspondiente al usuario
      case "1" -> login_admin();
      case "2" -> login_client();
      default -> System.out.println("\n❌ Invalid option, try again ❌\n");
    }
  }
  
  
  public static void login_admin(){
    System.out.println("\n--> Enter username: ");
    String adminUser = keyboardInput.nextLine();                                                                   
    
    System.out.println("\n--> Enter password: ");
    String password = keyboardInput.nextLine();                                                               
    
    if(adminUser.equals("a") && password.equals("a")){                      
      admin_menu();                                                                                             // Abrimos menu principal
    }else{
      System.out.println("\n❌ CREDENCIALES INCORRECTAS, INTENTE OTRA VEZ ❌\n");
    } 
  }
  
  
  public static void admin_menu(){
    System.out.println("\nWelcome to the Admin Menu 👩🏽‍💼\n"
                        + "\n1. Add new vehicles to inventory.\n"
                        + "2. Add special discounts.\n"
                        + "3. Make a report.\n"
                        + "4. Show users.\n"
                        + "5. Back to Main Menu.\n");
    while(true){
      String adminOption = keyboardInput.nextLine();
      switch(adminOption){                                                                                         // Desplegamos el menu correspondiente al usuario
        case "1" -> add_new_vehicle();
        case "2" -> add_special_discount();
        case "3" -> make_reports();
        case "4" -> show_users();
        case "5" -> main_menu();
        default -> System.out.println("\n❌ Invalid option, try again ❌\n");
      }
    }
  }
  
  
  public static void client_menu(){
    System.out.println("\nWelcome to ByteCar 🚘\n"
                        + "\n1. Register new client.\n"
                        + "2. Log-in as client.\n"
                        + "3. Make a reservation.\n"
                        + "4. Back to Main Menu.\n");
    while(true){
      String clientOption = keyboardInput.nextLine();
      switch(clientOption){                                                                                         
        case "1" -> register_new_client();
        case "2" -> login_client();
        case "3" -> make_reservation();
        case "4" -> main_menu();
        default -> System.out.println("\n❌ Invalid option, try again ❌\n");
      }
    }
  }
  
  
  // METODOS DEL ADMIN
  public static void add_new_vehicle(){
    System.out.println("Enter your car brand");
    String carBrand = keyboardInput.nextLine();
    System.out.println("Enter your car model");
    String carModel = keyboardInput.nextLine();
    System.out.println("Enter your car year");
    int carYear = parseInt(keyboardInput.nextLine());
    System.out.println("Enter your car license plate");
    String carLicensePlate = keyboardInput.nextLine();
    double dailyPrice = validate_price();
    System.out.println("Precio validado: " + dailyPrice);
  }
  
    
  public static void add_special_discount(){
    System.out.println("Special discount");
  }
  
    
  public static void make_reports(){
    System.out.println("Make reports");
  }
  
    
  public static void show_users(){
    System.out.println("Show users");
  }
  
  
  public static double validate_price(){
    double dailyPrice = 0.0;
    while (true) {
      System.out.println("Enter your daily rent price");
      try {
        dailyPrice = parseDouble(keyboardInput.nextLine());
        if (dailyPrice > 0) {
          break; // Exit the loop when a positive price is entered
        } else {
          System.out.println("Invalid price. Please enter a positive value.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid integer.");
      }
    }
   return dailyPrice;
  }
  
    
  
  
  
  // METODOS DEL CLIENTE
  public static void register_new_client(){
    System.out.println("register");
  }
  
  
  public static void login_client(){
    System.out.println("\n--> Enter NIT: ");
    String nitUser = keyboardInput.nextLine();                                                                   
    
    if(nitUser.equals("a")){                      
      client_menu();                                                                                             
    }else{
      System.out.println("\n❌ CREDENCIALES INCORRECTAS, INTENTE OTRA VEZ ❌\n");
    } 
  }
  
  
  public static void make_reservation(){
    System.out.println("reservation");
  }
  
}

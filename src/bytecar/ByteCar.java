 // @author fernandoorozco
package bytecar;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;  

public class ByteCar {
  static Scanner keyboardInput = new Scanner(System.in);
  static String[][] carInventory = new String[20][6];
  static String[][] usersDatabase = new String[20][3];
  static int[][] specialDiscounts = new int[20][2];
  static int carRow = 0;
  static int discountRow = 0;
  static int userRow = 0;
  static String nitUser;

  
  public static void main(String[] args) {
    while (true) {
      main_menu();
    }
  }  
  
  
  public static void main_menu() {
    System.out.println("Who are you? \n1)Admin\n2)Client");
    String typeOfUser =  keyboardInput.nextLine();

    switch(typeOfUser){                                                                                         // Desplegamos el menu correspondiente al usuario
      case "1" -> login_admin();
      case "2" -> login_client();
      default -> System.out.println("\n❌ Invalid option, try again ❌\n");
    }
  }
  
  
  public static void login_admin() {
    System.out.println("\n--> Enter username: ");
    String adminUser = keyboardInput.nextLine();                                                                   
    
    System.out.println("\n--> Enter password: ");
    String password = keyboardInput.nextLine();                                                               
    
    if(adminUser.equals("a") && password.equals("a")){                      
      admin_menu();                                                                                             // Abrimos menu principal
    }else{
      System.out.println("\n❌ WRONG CREDENTIALS, TRY AGAIN ❌\n");
    } 
  }
  
  
  public static void admin_menu() {
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
  
  
  public static void login_client() {
    boolean haveExistingAccount;
    boolean accountExists;
    haveExistingAccount = is_confirming("\nDo you have an account?"); 
    
    if(haveExistingAccount){
      System.out.println("\n--> Enter NIT: ");
      nitUser = keyboardInput.nextLine();                                                                   
      accountExists = contains_value(usersDatabase, nitUser);
      
      if(accountExists) {                      
        client_menu();                                                                                             
      }else{ 
        System.out.println("\n❌ CREDENCIALES INCORRECTAS, INTENTE OTRA VEZ ❌\n");
      } 
    }else{
      register_new_client();
      login_client();
    }
  }
  
  
  public static void client_menu() {
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
  public static void add_new_vehicle() {
    boolean addNewCar;
    
    do {
      System.out.println("Enter your car brand");
      carInventory[carRow][0] = keyboardInput.nextLine();
      
      System.out.println("Enter your car model");
      carInventory[carRow][1] = keyboardInput.nextLine();
      
      System.out.println("Enter your car year");
      carInventory[carRow][2] = keyboardInput.nextLine();
      
      validate_license_plate();
      
      double dailyPrice = validate_price();
      System.out.println("\nPrecio validado: " + dailyPrice);
      carInventory[carRow][4] = Double.toString(dailyPrice);
      
      boolean changeSomeValue = is_confirming("\nDo you want to change any value? (except license plate)"); 
   
      if(changeSomeValue) {
        change_specific_value();
      }else{
        System.out.println("\nRecording entered values...\n");
      }
      
      addNewCar = is_confirming("\nDo you want to add new register?"); 
      
      if(addNewCar) {
        carRow++;
      }else{
        carRow++;
        System.out.println("\nCars on inventory");
        System.out.println("\n Brand, Model, Year, License, Price/Day");
        for (int i = 0; i < carRow; i++) {
          System.out.print((i + 1) + ") ");
          for (int j = 0; j < 5; j++) {
            System.out.print(carInventory[i][j] + ", ");
          }
          System.out.println();
        }
      }
    } while (addNewCar);  
    
    admin_menu();
  }
  
  
  public static boolean contains_value(String[][] matrix, String target) {
    for (String[] row : matrix) {
      for (String element : row) {
        try {
          if (element.equals(target)) {
            return true;
          }
        } catch (NullPointerException e){
        }
      }
    }
    return false;
  }
   
   
  public static void change_specific_value() {
    System.out.println("\nWhat value of the car do you want to modify\n"
                        + "1) Brand.\n"
                        + "2) Model.\n"
                        + "3) Year.\n"
                        + "4) Daily rent price.\n"
                        + "5) Don't change anything.\n");
    try {
      int modifiedColumn = parseInt(keyboardInput.nextLine());
      switch(modifiedColumn){                                                                                         
        case 1 -> {
          System.out.println("Enter new Brand value: ");
          carInventory[carRow][0] = keyboardInput.nextLine();
        }
        case 2 -> {
          System.out.println("Enter new Model value: ");
          carInventory[carRow][1] = keyboardInput.nextLine();
        }
        case 3 -> {
          System.out.println("Enter new Year value: ");
          carInventory[carRow][2] = keyboardInput.nextLine();
        }
        case 4 -> {
          double dailyPrice = validate_price();
          System.out.println("Precio validado: " + dailyPrice);
          carInventory[carRow][4] = Double.toString(dailyPrice);
        }
        case 5 -> System.out.println("Returning...");
        default -> System.out.println("\n❌ Invalid option, try again ❌\n");
      }
    } catch (NumberFormatException e){
      System.out.println("Enter a valid option");
      change_specific_value();
    }
    
    System.out.println("NEW VALUES\n");
    for (int i = carRow; i < (carRow + 1); i++) {                           // Solo imprime el registro actualizado
      System.out.print(i+") ");
      for (int j = 0; j < 4; j++) {
        System.out.print(carInventory[i][j] + ", ");
      }
      System.out.println();
    }
  }
  
  
  public static void add_special_discount() {
    int minimunDays;
    int percentageDiscount;
    boolean addNewDiscount;
    
    System.out.println("Special discount");
    while(true) {
      System.out.println("Add minimun days to quailified for a discount: ");
      try {
        minimunDays = parseInt(keyboardInput.nextLine()); 
      } catch (NumberFormatException e) {
        System.out.println("❌ Enter a number not a String ❌");
        continue;
      }
      
      if(minimunDays > 0) {
        specialDiscounts[discountRow][0] = minimunDays;
        break;
      }else{
        System.out.println("❌ Invalid price. Please enter a positive value. ❌");
      }
    } 
    
    while(true) {
      System.out.println("Add percentage of discount (between 1 and 99)");
      try {
        percentageDiscount = parseInt(keyboardInput.nextLine()); 
      } catch (NumberFormatException e) {
        System.out.println("❌ Enter a number not a String ❌");
        continue;
      }
      
      if(percentageDiscount > 0 && percentageDiscount < 100) {
        specialDiscounts[discountRow][1] = percentageDiscount; 
        break;
      } else {
        System.out.println("❌ Invalid input. Please enter a value bewtween 1 and 99. ❌");
      }
    }
    
    addNewDiscount = is_confirming("\nDo you want to add new register?"); 
    
    if(addNewDiscount) {
        discountRow++;
        add_special_discount();
    } else {
      discountRow++;
      System.out.println("\nCurrent Discounts");
      System.out.println("\n Minimun Days, Discount percentage");
      for (int i = 0; i < discountRow; i++) {
        System.out.print((i + 1) + ") ");
        for (int j = 0; j < 2; j++) {
          System.out.print(specialDiscounts[i][j] + " ");
        }
        System.out.println("%");
      }
    }
    admin_menu();
  }
  
    
  public static void make_reports() {
    System.out.println("Make reports");
  }
  
    
  public static void show_users() {
    System.out.println("Lista de Usuarios registrados");
    System.out.println("Nit | First name | Last name");
    for (int i = 0; i < userRow; i++) {                           
      System.out.print((i + 1) +") ");
      for (int j = 0; j < 3; j++) {
        System.out.print(usersDatabase[i][j] + ", ");
      }
      System.out.println();
    }
    admin_menu();
  }
  
  
  public static double validate_price() {
    double dailyPrice = 0.0;
    while (true) {
      System.out.println("Enter your daily rent price");
      try {
        dailyPrice = parseDouble(keyboardInput.nextLine());
        if (dailyPrice > 0) {
          break; // Exit the loop when a positive price is entered
        }else{
          System.out.println("Invalid price. Please enter a positive value.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid integer.");
      }
    }
   return dailyPrice;
  }
  
  
  public static void validate_license_plate() {
    boolean isLicenseFound;
    do {
      System.out.println("Enter your car license plate");
      String licenseTarget = keyboardInput.nextLine(); 
      isLicenseFound = contains_value(carInventory, licenseTarget);

      if (isLicenseFound) {
        System.out.println("\n❌ The license plate '" + licenseTarget + "' is already registered on inventory. ❌\n");
      }else{
        System.out.println("\n✅ The license plate '" + licenseTarget + "' is not registered on inventory, you're good to go. ✅\n");
        carInventory[carRow][3] = licenseTarget;  
      }
    } while(isLicenseFound);
  }
  
  
  
  
  // METODOS DEL CLIENTE
  public static void register_new_client() {
    System.out.println("Register your new Account");
    validate_nit();
    
    System.out.println("Enter your first name");
    usersDatabase[userRow][1] = keyboardInput.nextLine();
    
    System.out.println("Enter your last name");
    usersDatabase[userRow][2] = keyboardInput.nextLine();
    
    System.out.println("This is your user Data\n");
    for (int i = userRow; i < (userRow + 1); i++) {                           
      System.out.println("Nit | First name | Last name");
      for (int j = 0; j < 3; j++) {
        System.out.print(usersDatabase[i][j] + ", ");
      }
    }
    userRow++;
  }
  
  
  public static void validate_nit() {
    boolean isNitFound = true;
    String nitTarget;
    int inputNIT;
    do {
      try {
        System.out.println("Enter your NIT");
        inputNIT = parseInt(keyboardInput.nextLine()); 
        nitTarget = Integer.toString(inputNIT);
        isNitFound = contains_value(usersDatabase, nitTarget);
      } catch (NumberFormatException e) {
        System.out.println("❌ Enter a number not a String ❌");
        continue;
      }
      if (isNitFound) {
        System.out.println("\n❌ This NIT '" + nitTarget + "' is already registered on inventory. ❌\n");
      }else{
        System.out.println("\n✅ This NIT '" + nitTarget + "' is not registered, you're good to go. ✅\n");
        usersDatabase[userRow][0] = nitTarget;  
      }
    } while(isNitFound);
  }
  
  
  public static void make_reservation() {
    System.out.println("\n+++ 🚖 Available cars 🚖 +++");
    if(carRow > 0) {
      for (int i = 0; i < carRow ; i++) {                           
        System.out.print((i+1)+") " + carInventory[i][0] + ", ");
        System.out.print(carInventory[i][1] + ", ");
        System.out.print(carInventory[i][4] + ", ");
        System.out.print(carInventory[i][3] + " \n");
      }
    }else{
      System.out.println("There are NO cars on inventory");
    }
    
    System.out.println("\n+++ 🏷️ Discount availables depending of number of rented days 🏷️ ++++");
    if(discountRow > 0) {
      for (int i = 0; i < discountRow; i++) {
        System.out.print((i + 1) + ") " + specialDiscounts[i][0] + " days to apply for a " + specialDiscounts[i][1] + "% discount\n");
      }
    }else{
      System.out.println("There are currently NO discounts available");
    }
    
    System.out.println("\n Options Available \n1)Show current cars your renting\n2)Reserve a new car\n3)End renting process");
    String rentingOption =  keyboardInput.nextLine();

    switch(rentingOption){                                                                                         // Desplegamos el menu correspondiente al usuario
      case "1" -> showRentedCars();
      case "2" -> reserve_new_car();
      case "3" -> show_bill();
      default -> System.out.println("\n❌ Invalid option, try again ❌\n");
    }
    client_menu();
  }

  
  private static void show_current_rented_cars() {
    
  }
  
  
  private static void reserve_new_car() {
    int carIndex = exists_license_plate();
    
  }
  
  
  private static void show_bill() {
    int userIndex = find_user_by_index(parseInt(nitUser));
    String currentDateTime = print_current_date_time();
    String userNit= usersDatabase[userIndex][0];
    String loggedInUserName = usersDatabase[userIndex][1];
    String loggedInUserLastName = usersDatabase[userIndex][2];
    
    System.out.println("****** 🚘 ByteCar 🚘 ******");
    System.out.println("Client name: " + loggedInUserName + ' ' + loggedInUserLastName);
    System.out.println("Nit: " + userNit);
    System.out.println("Current date and time: " + currentDateTime);
    System.out.println("List of cars to rent:\n");
    
    System.out.println("Subtotal:");
    System.out.println("Amount of discount: ");
    System.out.println("Total to pay: ");
  }
  
  
  public static int exists_license_plate() {
    boolean isLicenseFound;
    boolean validLicense = false;
    int carIndex = -1;
    
    do {
      System.out.println("Enter the license plate of the car you want to rent");
      String licenseTarget = keyboardInput.nextLine(); 
      isLicenseFound = contains_value(carInventory, licenseTarget);

      if (isLicenseFound) {
        System.out.println("\n✅ The license plate '" + licenseTarget + "' exists on inventory. ✅\n");
        carIndex = find_car_by_liscense(licenseTarget);
        carInventory[carIndex][5] = "";
        if (carInventory[carIndex][5].equals("Rented")){
          System.out.println("❌❌❌ But this car is already rented ❌❌❌");
          continue;
        } else {
          System.out.println("\n✅ The license plate '" + licenseTarget + "' is for rent. ✅\n");
          carInventory[carIndex][5] = "Rented";
        }
        
        System.out.println("Enter the number of days you want to rent the car '" + licenseTarget + "'");
        int daysOfRent = parseInt(keyboardInput.nextLine());
        
        String pricePerDayString = carInventory[carIndex][4];
        int pricePerDay = Double.valueOf(pricePerDayString).intValue();
        System.out.println("Value of Car as INT: " + pricePerDay);
        int carTotalCost = (daysOfRent * pricePerDay);
        System.out.println("Total of this car per X days: " + pricePerDay);
        
        System.out.println(carTotalCost);
        validLicense = true;
      }else{
        System.out.println("\n❌ The license plate '" + licenseTarget + "' is not registered on inventory, try entering another ❌\n");
      }
    } while(!validLicense);
    
    return carIndex;
  }
  
  
  public static int find_user_by_index(int nit) {
    for (int i = 0; i < usersDatabase.length; i++) {
      if (nit == Integer.parseInt(usersDatabase[i][0])) {
        return i;
      }
    }
    return -1; // User not found
  }
  
  
  public static int find_car_by_liscense(String license) {
    for (int i = 0; i < carInventory.length; i++) {
      if (license.equals(carInventory[i][3])) {
        return i;
      }
    }
    return -1; 
  }
  
  
  
  public static void showRentedCars() {
    System.out.println("Rented Cars:");
    for (String[] car : carInventory) {
      if (car[6] != null && car[6].equals("Rented")) {
        System.out.println("Brand: " + car[0] + ", Model: " + car[1] + ", Year: " + car[2] +
                           ", License: " + car[3] + ", Price: " + car[4]);
      }
    }
  }
  
  
  // Utils
  
  public static boolean is_confirming(String phraseToShow) {
      boolean isContinued = true;
      boolean breakLoop = true;
      do {
      System.out.println(phraseToShow);
      System.out.println("\n1. Yes\n"                                                                    
                          + "2. No");
      String continueInput = keyboardInput.nextLine();                                                         
        switch (continueInput) {
          case "1" -> {
            isContinued = true; 
            breakLoop = false;
          }
          case "2" -> {
            isContinued = false;
            breakLoop = false;
          }
          default -> {
            System.out.println("\n❌ Invalid option, try again  ❌\n");  
          }
        }
      } while(breakLoop);
      
      return isContinued;
  }
  
  
  public static String print_current_date_time() {
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatter);
    return formattedDateTime;
  }
}

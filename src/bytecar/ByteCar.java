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
  static String[][] currentCarOrder = new String[20][6];
  static String[][] mostRentedCarsByBrand = new String[20][2];
  static String[][] mostRentedCarsByYear = new String[20][2];
  static int[][] specialDiscounts = new int[20][2];
  static int[] carCostSubTotal = new int[20];
  static int[] appliedPercentages = new int[20];
  static double[] carTotalAfterDiscount = new double[20];
  static int carRow = 0;
  static int discountRow = 0;
  static int userRow = 0;
  static int carOrderRow = 0;
  static int mostRentedCarsByBrandRow = 0;
  static int mostRentedCarsByBrandRowNew = 1;
  static int mostRentedCarsByYearRowNew = 1;
  static int subTotalIndex = 0;
  static int appliedPercentagesIndex = 0;
  static int carTotalAfterDiscountIndex = 0;
  static String nitUser;

  
  public static void main(String[] args) {
    while (true) {
      main_menu();
    }
  }  
  
  
  public static void main_menu() {
    System.out.println("\nWho are you? \n1)Admin\n2)Client");
    String typeOfUser =  keyboardInput.nextLine();

    switch(typeOfUser){                                                                                         // Desplegamos el menu correspondiente al usuario
      case "1" -> admin_login();
      case "2" -> client_login();
      default -> System.out.println("\n❌ Invalid option, try again ❌\n");
    }
  }
  
  
  // METODOS DEL ADMIN
  public static void admin_login() {
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
    System.out.println("\n‍💼👨🏽 Welcome to the Admin Menu 👩🏽‍💼\n"
                        + "\n1. Add new vehicles to inventory.\n"
                        + "2. Add special discounts.\n"
                        + "3. Make a report.\n"
                        + "4. Show users.\n"
                        + "5. Back to Main Menu.");
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
  
  
  public static void add_new_vehicle() {
    boolean addNewCar;
    do {
      System.out.println("\nEnter your car brand");
      carInventory[carRow][0] = keyboardInput.nextLine();
      
      System.out.println("Enter your car model");
      carInventory[carRow][1] = keyboardInput.nextLine();
      
      System.out.println("Enter your car year");
      carInventory[carRow][2] = keyboardInput.nextLine();
      
      validate_license_plate();
      
      double dailyPrice = validate_price();
      System.out.println("\nPrice per day set: " + dailyPrice);
      carInventory[carRow][4] = Double.toString(dailyPrice);
      
      boolean changeSomeValue = is_confirming("\nDo you want to change any value? (except license plate)"); 
   
      if(changeSomeValue) {
        change_specific_value();
      }else{
        System.out.println("\n 💾💾 Recording entered values... 💾💾");
      }
      
      addNewCar = is_confirming("\nDo you want to add new register?"); 
      if(addNewCar) {
        carRow++;
      }else{
        carRow++;
        System.out.println("\n🚘🚘 Cars on inventory 🚘🚘");
        System.out.println("\n   Brand | Model | Year | License | Price/Day");
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
  
   
  public static void change_specific_value() {
    System.out.println("\nWhat value of the car do you want to modify\n"
                        + "1) Brand.\n"
                        + "2) Model.\n"
                        + "3) Year.\n"
                        + "4) Daily rent price.\n"
                        + "5) Don't change anything.");
    try {
      int modifiedColumn = parseInt(keyboardInput.nextLine());
      switch(modifiedColumn){                                                                                         
        case 1 -> {
          System.out.println("\nEnter new Brand value: ");
          carInventory[carRow][0] = keyboardInput.nextLine();
        }
        case 2 -> {
          System.out.println("\nEnter new Model value: ");
          carInventory[carRow][1] = keyboardInput.nextLine();
        }
        case 3 -> {
          System.out.println("\nEnter new Year value: ");
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
    
    System.out.println("\nValue updated 💾\n");
    for (int i = carRow; i < (carRow + 1); i++) {                                               // Solo imprime el registro actualizado
      System.out.println(carInventory[i][0] + ", " + carInventory[i][1] + ", " 
                          + carInventory[i][2] + ", " + carInventory[i][4] + ", ");
    }
  }
  
  
  public static void add_special_discount() {
    int minimunDays;
    int percentageDiscount;
    boolean addNewDiscount;
    
    System.out.println("\n٪ Special discounts ٪\n");
    while(true) {
      System.out.println("Add minimun days to quailified for a discount: ");
      try {
        minimunDays = parseInt(keyboardInput.nextLine()); 
      } catch (NumberFormatException e) {
        System.out.println("\n❌ Enter a number not a String ❌\n");
        continue;
      }
      
      if(minimunDays > 0) {
        specialDiscounts[discountRow][0] = minimunDays;
        break;
      }else{
        System.out.println("\n❌ Invalid price. Please enter a positive value. ❌\n");
      }
    } 
    
    while(true) {
      System.out.println("\nAdd percentage of discount (between 1 and 99)");
      try {
        percentageDiscount = parseInt(keyboardInput.nextLine()); 
      } catch (NumberFormatException e) {
        System.out.println("\n❌ Enter a number not a String ❌\n");
        continue;
      }
      
      if(percentageDiscount > 0 && percentageDiscount < 100) {
        specialDiscounts[discountRow][1] = percentageDiscount; 
        break;
      } else {
        System.out.println("\n❌ Invalid input. Please enter a value bewtween 1 and 99. ❌\n");
      }
    }
    
    addNewDiscount = is_confirming("\nDo you want to add new register?"); 
    if(addNewDiscount) {
      discountRow++;
      add_special_discount();
    } else {
      discountRow++;
      System.out.println("\n٪٪٪ Current Discounts ٪٪٪");
      System.out.println("\n Minimun Days | Discount percentage");
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
    System.out.println("\n\n🚛🚛🚛 Most Rented Cars by Brand 🚛🚛🚛");
    System.out.println("Brand    |    Total of rented days");
    System.out.println("----------------------------------");
    
    for (int i = 0; i < mostRentedCarsByBrandRowNew-1; i++) {                           
      System.out.print((i + 1) +") ");
      for (int j = 0; j < 2; j++) {
        System.out.print(mostRentedCarsByBrand[i][j] + ", ");
      }
      System.out.println();
    }
    
    System.out.println("\n\n🚙🚙🚙 Most Rented Cars by Year 🚙🚙🚙");
    System.out.println("Model    |    Total of rented days");
    System.out.println("----------------------------------");
    
    for (int i = 0; i < mostRentedCarsByYearRowNew-1; i++) {                           
      System.out.print((i + 1) +") ");
      for (int j = 0; j < 2; j++) {
        System.out.print(mostRentedCarsByYear[i][j] + ", ");
      }
      System.out.println();
    }
  }
  
    
  public static void show_users() {
    System.out.println("\nList of registered users");
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
  
  
  public static void report_most_rented_car(String brand, int daysRented){
    boolean brandFound = false;
    for (int i = 0; i < mostRentedCarsByBrandRowNew; i++) {
      if (mostRentedCarsByBrand[i][0] == null) {
        mostRentedCarsByBrand[i][0] = brand;
        mostRentedCarsByBrand[i][1] = Integer.toString(daysRented);
        brandFound = true;
        break;
      } else if (mostRentedCarsByBrand[i][0].equals(brand)) {
        int totalDays = Integer.parseInt(mostRentedCarsByBrand[i][1]) + daysRented;
        mostRentedCarsByBrand[i][1] = Integer.toString(totalDays);
        break;
      } 
    }
    
    if (brandFound) {
      System.out.println("Nueva marca registrada");
      mostRentedCarsByBrandRowNew++;
    }
  }
  
  
  public static void report_most_rented_year(String carYear, int daysRented){
    boolean yearFound = false;
    for (int i = 0; i < mostRentedCarsByYearRowNew; i++) {
      if (mostRentedCarsByYear[i][0] == null) {
        mostRentedCarsByYear[i][0] = carYear;
        mostRentedCarsByYear[i][1] = Integer.toString(daysRented);
        yearFound = true;
        break;
      } else if (mostRentedCarsByYear[i][0].equals(carYear)) {
        int totalDays = Integer.parseInt(mostRentedCarsByYear[i][1]) + daysRented;
        mostRentedCarsByYear[i][1] = Integer.toString(totalDays);
        break;
      } 
    }
    
    if (yearFound) {
      System.out.println("Nuevo AÑO registrado");
      mostRentedCarsByYearRowNew++;
    }
  }
  
  
  // METODOS DEL CLIENTE
  public static void client_login() {
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
        System.out.println("\n❌ WRONG CREDENTIALS, TRY AGAIN ❌");
      } 
    }else{
      register_new_client();
      client_login();
    }
  }
  
  
  public static void client_menu() {
    System.out.println("\nWelcome to ByteCar 🚘"
                        + "\n1. Go to reservation menu.\n"
                        + "2. Log-out.");
    while(true){
      String clientOption = keyboardInput.nextLine();
      switch(clientOption){                                                                                         
        case "1" -> make_reservation();
        case "2" -> main_menu();
        default -> System.out.println("\n❌ Invalid option, try again ❌\n");
      }
    }
  }
  
  
  public static void register_new_client() {
    System.out.println("\n+++ Register your new Account +++\n");
    validate_nit();
    
    System.out.println("Enter your first name");
    usersDatabase[userRow][1] = keyboardInput.nextLine();
    
    System.out.println("Enter your last name");
    usersDatabase[userRow][2] = keyboardInput.nextLine();
    
    System.out.println("\n 👤 This is your user Data 👤\n");
    for (int i = userRow; i < (userRow + 1); i++) {                           
      System.out.println("Nit | First name | Last name");
      for (int j = 0; j < 3; j++) {
        System.out.print(usersDatabase[i][j] + ", ");
      }
      System.out.println("");
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
        System.out.println("\n❌ Enter a number not a String ❌\n");
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
        if(carInventory[i][5] == "Rented"){
          System.out.println("--------------------------");
        }else{
          System.out.print((i+1)+") " + carInventory[i][0] + ", ");
          System.out.print(carInventory[i][1] + ", ");
          System.out.print(carInventory[i][4] + ", ");
          System.out.print(carInventory[i][3] + " \n");
        }
      }
    }else{
      System.out.println("There are NO cars on inventory");
    }
    
    System.out.println("\n+++ 🛒 Table of current discounts 🛒 ++++");
    if(discountRow > 0) {
      for (int i = 0; i < discountRow; i++) {
        System.out.print((i + 1) + ") " + specialDiscounts[i][0] + " days to apply for a " + specialDiscounts[i][1] + "% discount\n");
      }
    }else{
      System.out.println("There are currently NO discounts available");
    }
    
    System.out.println("\n Options Available \n1)Show current cars you're renting\n2)Reserve a new car\n3)End renting process");
    String rentingOption =  keyboardInput.nextLine();

    switch(rentingOption){                                                                                         // Desplegamos el menu correspondiente al usuario
      case "1" -> show_current_rented_cars();
      case "2" -> reserve_new_car();
      case "3" -> show_bill();
      default -> System.out.println("\n❌ Invalid option, try again ❌\n");
    }
    client_menu();
  }

  
  private static void show_current_rented_cars() {
    if(carOrderRow > 0) {
      System.out.println("\nThis is your current list of cars rented");
      for (int i = 0; i < carOrderRow; i++) {                           
        System.out.print((i + 1) +") ");
        for (int j = 0; j < 5; j++) {
          System.out.print(currentCarOrder[i][j] + ", ");
        }
        System.out.println();
      }
    }else{
      System.out.println("YOU HAVE NO CURRENT RENTED CARS");
    }
  }
  
  
  private static void reserve_new_car() {
    int carIndex = exists_license_plate();
    
    check_car_discount(carIndex);
    make_reservation();
  }
  
  
  public static int exists_license_plate() {
    boolean isLicenseFound;
    boolean validLicense = false;
    int carIndex = -1;
    int daysOfRent;
    
    do {
      System.out.println("\nEnter the license plate of the car you want to rent");
      String licenseTarget = keyboardInput.nextLine(); 
      isLicenseFound = contains_value(carInventory, licenseTarget);

      if (isLicenseFound) {
        System.out.println("\n✅ The license plate '" + licenseTarget + "' exists on inventory. ✅\n");
        carIndex = find_car_by_license(licenseTarget);
        carInventory[carIndex][5] = "";
        
        if (carInventory[carIndex][5].equals("Rented")){
          System.out.println("❌❌❌ But this car is already rented, try renting another one ❌❌❌");
          continue;
        }else{
          System.out.println("✅ The license plate '" + licenseTarget + "' is for rent. ✅\n");
          carInventory[carIndex][5] = "Rented";
        }
        
        do {
          System.out.println("Enter the number of days you want to rent the car '" + licenseTarget + "'");
          try {
            daysOfRent = parseInt(keyboardInput.nextLine());
            report_most_rented_car(carInventory[carIndex][0], daysOfRent);
            report_most_rented_year(carInventory[carIndex][2], daysOfRent);
            break;
          } catch(NumberFormatException e) {
            System.out.println("\n❌ Enter a number not a String ❌\n");
          }
        }while(true);
        
        System.out.println("\n✅ This car has been rented succesfully ✅\n ");
        currentCarOrder[carOrderRow][0] = carInventory[carIndex][0];
        currentCarOrder[carOrderRow][1] = carInventory[carIndex][1];
        currentCarOrder[carOrderRow][2] = carInventory[carIndex][2];
        currentCarOrder[carOrderRow][3] = carInventory[carIndex][3];
        currentCarOrder[carOrderRow][4] = Integer.toString(daysOfRent);      // Dias agendados
        carOrderRow++;
        
        String pricePerDayString = carInventory[carIndex][4];
        int pricePerDay = Double.valueOf(pricePerDayString).intValue();
        int carTotalCost = (daysOfRent * pricePerDay);
        System.out.println("Total cost of this car: Q"+ pricePerDay +" per day for " + daysOfRent + " days = Q"+ carTotalCost);
        carCostSubTotal[subTotalIndex] = carTotalCost;
        subTotalIndex++;
        
        validLicense = true;
      }else{
        System.out.println("\n❌ The license plate '" + licenseTarget + "' is not registered on inventory, try entering another ❌\n");
      }
    } while(!validLicense);
    return carIndex;
  }
  
  
  public static boolean check_car_discount(int carIndex) {
    int daysToCheck = parseInt(currentCarOrder[carOrderRow - 1][4]);            // Number of days to check
    boolean appliesForDiscount = false;
    int discountPercentage = -1;  
    int subTotal;
    double decimalDiscount;
    double totalThisCar;
    double amountDiscount;
    
    for (int[] entry : specialDiscounts) {
      int daysInArray = entry[0];
      int percentage = entry[1];
      if (daysToCheck >= daysInArray && percentage > discountPercentage) {
        appliesForDiscount = true;
        discountPercentage = percentage;
      }
    }
    
    if (appliesForDiscount) {
      System.out.println("\n✅ " + daysToCheck + " days are enough to get you a discount. 🥳🥳🥳 ✅");
    }else{
      System.out.println("\n❌ The amount of days you reserve (" + daysToCheck + ") doesn't apply for a discount. ❌");
    }
    
    if (discountPercentage != -1) {
      System.out.println("\nFor " + daysToCheck + " days, the discount percentage is " + discountPercentage + "%.");
    }else{
      System.out.println("\nNo applicable discount percentage found for " + daysToCheck + " days.");
    }
    
    appliedPercentages[appliedPercentagesIndex] = discountPercentage;
    appliedPercentagesIndex++;
    
    subTotal = carCostSubTotal[subTotalIndex-1];
    System.out.println("\nSubtotal of this car before discout: Q" + subTotal);    
    System.out.println("Percentage of discount: " + appliedPercentages[appliedPercentagesIndex - 1] + "%");

    decimalDiscount = (double) appliedPercentages[subTotalIndex - 1] / 100;
    amountDiscount = (subTotal * decimalDiscount);
    System.out.println("Amount of discount: Q" + amountDiscount);
    
    totalThisCar =  subTotal - amountDiscount;
    totalThisCar = convert_to_decimal(totalThisCar);
    System.out.println("Total of this car: Q" + totalThisCar);
    
    carTotalAfterDiscount[carTotalAfterDiscountIndex] = totalThisCar;
    carTotalAfterDiscountIndex++;
    return appliesForDiscount; 
  }
  
  
  private static void show_bill() {
    int userIndex = find_user_by_index(parseInt(nitUser));
    double subTotalBill = 0;
    double TotalBillAfterDiscount = 0;
    
    String currentDateTime = print_current_date_time();
    String userNit= usersDatabase[userIndex][0];
    String loggedInUserName = usersDatabase[userIndex][1];
    String loggedInUserLastName = usersDatabase[userIndex][2];
    
    System.out.println("\n\n\n****** 🚘 ByteCar 🚘 ******\n\n"
                        + "\nClient name: " + loggedInUserName + ' ' + loggedInUserLastName
                        + "\nNit: " + userNit
                        + "\nCurrent date and time: " + currentDateTime
                        + "\nList of cars to rent:"
                        + "\n----------------------------------");
    for (int i = 0; i < carOrderRow; i++) {                           
      System.out.println("Brand: " + currentCarOrder[i][0] 
                          + "\nModel: " + currentCarOrder[i][1] 
                          + "\nLicense: " + currentCarOrder[i][3] 
                          + "\nDays rented: " + currentCarOrder[i][4] 
                          + "\nCost before discount: Q" + carCostSubTotal[i] 
                          + "\nThis car has a discount of: " + appliedPercentages[i] +"%" 
                          + "\nCost after discount: Q" + carTotalAfterDiscount[i]);
      System.out.println("----------------------------------");
      
      subTotalBill = subTotalBill + carCostSubTotal[i];
      TotalBillAfterDiscount = TotalBillAfterDiscount + carTotalAfterDiscount[i];
    }
        
    System.out.println("\nSubtotal: Q" + subTotalBill);
    TotalBillAfterDiscount = convert_to_decimal(TotalBillAfterDiscount);
    System.out.println("Total to pay: Q" + TotalBillAfterDiscount);
    System.out.println("\n\n\n****** 🚘 ByteCar 🚘 ******\n\n");
  }
  
  // Utils
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
  
  
  public static boolean is_confirming(String phraseToShow) {
    boolean isContinued = true;
    boolean breakLoop = true;
    do {
    System.out.println(phraseToShow);
    System.out.println("1. Yes\n"                                                                    
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
  
  
  public static int find_user_by_index(int nit) {
    for (int i = 0; i < usersDatabase.length; i++) {
      if (nit == Integer.parseInt(usersDatabase[i][0])) {
        return i;
      }
    }
    return -1; // User not found
  }
  
  
  public static int find_car_by_license(String license) {
    for (int i = 0; i < carInventory.length; i++) {
      if (license.equals(carInventory[i][3])) {
        return i;
      }
    }
    return -1; 
  }
  
  
  public static double convert_to_decimal(double manyDecimalsNumber) {
    String stringValue = Double.toString(manyDecimalsNumber);
    int decimalIndex = stringValue.indexOf('.');
    String wholeNumberPart = stringValue.substring(0, decimalIndex);
    String decimalPart = stringValue.substring(decimalIndex + 1);
    
    if (decimalPart.length() < 1) {
        decimalPart = decimalPart + "00";
    } else if (decimalPart.length() < 2) {
        decimalPart = decimalPart + "0";
    } else {
        decimalPart = decimalPart.substring(0, 2);
    }
    
    String formattedValue = wholeNumberPart + "." + decimalPart;
    System.out.println("decimalPart: " + decimalPart);
    System.out.println("Formatted Value: " + formattedValue);
    
    double formattedValueDouble = Double.parseDouble(formattedValue);
    return formattedValueDouble;
  }
}

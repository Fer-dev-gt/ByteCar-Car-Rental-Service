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
  static String nitUser;
  static int carRow = 0;
  static int discountRow = 0;
  static int userRow = 0;
  static int carOrderRow = 0;
  static int subTotalIndex = 0;
  static int appliedPercentagesIndex = 0;
  static int carTotalAfterDiscountIndex = 0;
  static int mostRentedCarsByBrandRowNew = 1;
  static int mostRentedCarsByYearRowNew = 1;

  
  public static void main(String[] args) {                                                // Se emplea loop infinito para mostrar siempre el menu principal
    while (true) {
      main_menu();
    }
  }  
  
  
  public static void main_menu() {                                                        // Pregunta si es usuario o admin y despliega la sección de login correspondiente
    System.out.println("\nWho are you? \n1)Admin\n2)Client");
    String typeOfUser =  keyboardInput.nextLine();

    switch(typeOfUser){                                                                                         
      case "1" -> admin_login();
      case "2" -> client_login();
      default -> System.out.println("\n❌ Invalid option, try again ❌\n");
    }
  }
  
  
  
  
  // METODOS DEL ADMIN
  public static void admin_login() {                                                      // Solicita el username y password, si son las credenciales correctas muestra el menu de admin                        
    System.out.println("\n--> Enter username: ");
    String adminUser = keyboardInput.nextLine();                                                                   
    
    System.out.println("\n--> Enter password: ");
    String password = keyboardInput.nextLine();                                                               
    
    if(adminUser.equals("admin_123") && password.equals("123")){                      
      admin_menu();                                                                       // Abre el menu de admin
    }else{
      System.out.println("\n❌ WRONG CREDENTIALS, TRY AGAIN ❌\n");
    } 
  }
  
  
  public static void admin_menu() {                                                       // Muestra opciones que tiene el admin y dentro de un ciclo while recibe el input de que función quiere realizar el admin
    System.out.println("\n‍💼👨🏽 Welcome to the Admin Menu 👩🏽‍💼\n"
                        + "\n1. Add new vehicles to inventory.\n"
                        + "2. Add special discounts.\n"
                        + "3. Make 'Most Rented' car report.\n"
                        + "4. Show users.\n"
                        + "5. Back to Main Menu.");
    while(true){
      String adminOption = keyboardInput.nextLine();
      switch(adminOption){                                                                // Dirige a la función correspondiente a lo que quiere hacer el admin
        case "1" -> add_new_vehicle();
        case "2" -> add_special_discount();
        case "3" -> make_reports();
        case "4" -> show_users();
        case "5" -> main_menu();
        default -> System.out.println("\n❌ Invalid option, try again ❌\n");
      }
    }
  }
  
  
  public static void add_new_vehicle() {                                                                  // Agrega un nuevo vehiculo a la matriz de 'carInventory' despues de verificar que los datos cumplen con los requisitos
    boolean addNewCar;
    do {                                                                                                  // Usamos ciclo 'do-while' para pedir constantemente los datos del carro a registrar
      System.out.println("\nEnter your car brand");
      carInventory[carRow][0] = keyboardInput.nextLine();
      
      System.out.println("Enter your car model");
      carInventory[carRow][1] = keyboardInput.nextLine();
      
      System.out.println("Enter your car year");
      carInventory[carRow][2] = keyboardInput.nextLine();
      
      validate_license_plate();                                                                           // Ejecutamos función para pedir y guardar placas del carro verificando que no esten ya registradas
      
      double dailyPrice = validate_price();                                                               // Pedimos y guardamos el precio por dia del carro en formato valido
      System.out.println("\nPrice per day set: " + dailyPrice);
      carInventory[carRow][4] = Double.toString(dailyPrice);
      
      boolean changeSomeValue = is_confirming("\nDo you want to change any value? (except license plate)"); 
   
      if(changeSomeValue) {                                                                               // Si el usuario desea cambiar un dato del registro que acaba de ingresar ejecutamos la función para hacerlo 
        change_specific_value();                                                                          // No se permite modificar las placas del carro
      }else{
        System.out.println("\n 💾💾 Recording entered values... 💾💾");
      }
      
      addNewCar = is_confirming("\nDo you want to add new register?");                           // Le preguntamos al admin si quiere registrar otro carro, guardamos la respuesta en 'addNewCar' si es false salimos del ciclo y regresa al menu de admin
      if(addNewCar) {                                                                                      // Si desea agregar otro carro le sumamos 1 al valor de 'carRow' para registrar los datos en la siguiete fila de la matriz
        carRow++;                     
      }else{
        carRow++;
        System.out.println("\n🚘🚘 Cars on inventory 🚘🚘");                                              // Si ya no se desea agregar más carros se procede a mostrar los carros registrados en 'carInventroy' al iterar por la matriz usando ciclos for anidados
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
  
   
  public static void change_specific_value() {                                            // Pregunta al admin que dato quiere modificar y lo asigna a la matriz 'carInventory' en la fila del registro actual
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
    for (int i = carRow; i < (carRow + 1); i++) {                                       // Imprimos el valor del carro actualizado
      System.out.println(carInventory[i][0] + ", " + carInventory[i][1] + ", " 
                          + carInventory[i][2] + ", " + carInventory[i][4] + ", ");
    }
  }
  
  
  public static void add_special_discount() {                                           // Agrega los datos de descuento especial a la matriz 'specialDiscounts'
    int minimunDays;
    int percentageDiscount;
    boolean addNewDiscount;
    boolean repeatDiscountInput;
    
    System.out.println("\n٪ Special discounts ٪\n");
    while(true) {                                                                                   // Usando un ciclo whiel pedimos los datos de los descuento
      try {
        System.out.println("Add minimun days to quailified for a discount: ");
        minimunDays = parseInt(keyboardInput.nextLine());
        repeatDiscountInput = check_repeated_discount(minimunDays);                     // Pedimos los dias minimos para aplicar a un descuento y verificamos si ya esta registrado ese número de dias
        if(repeatDiscountInput) continue;                                                             // Si ya esta repetido el número de dias pedimos de nuevo que ingresa una nueva cantidad
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
    
    while(true) {                                                                                     // Dentro de un ciclo while pedimos el porcentaje de descuento tiene que ser valores int entre 1 a 99
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
    
    addNewDiscount = is_confirming("\nDo you want to add new register?");                   // Le preguntasmo al admin si quiere agregar un nuevo descuento
    if(addNewDiscount) {                                                                              // Si acepta nos movemos a la siguiente fila de la matriz 'addNewDiscoutn' y usamos recursividad para volvel a preguntar los datos
      discountRow++;                                                                    
      add_special_discount();
    } else {                                                                                          // Si ya no desea agregar nuevos descuentos le mostramos la lista de descuentos ya registrados
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
  
  
  public static boolean check_repeated_discount(int minimunDaysTarget) {                  // Revisa si el valor de dias ingresado ya esta ingresado en la matriz 'special Discount' para eso usamo el método 'contains_value_int()'
    boolean isDiscountFound;
    isDiscountFound = contains_value_int(specialDiscounts, minimunDaysTarget);

    if (isDiscountFound) {
      System.out.println("\n❌ The amount of '" + minimunDaysTarget + "' days is already registered, try another amount of days. ❌\n");
      return true;
    }else{
      System.out.println("\n✅ The amount of '" + minimunDaysTarget + "' is not registered on discounts, you're good to go. ✅");
      specialDiscounts[discountRow][0] = minimunDaysTarget;  
      return false;
    }
  }
  
    
  public static void make_reports() {                                                     // Imprime los reportes de carros más rentados por marca y por año
    bubble_sort_matrix_brand();                                                           // Ordenamos los carros mas rentados por marca de mayor a menor número de dias rentados, usamos el método de burbuja
    System.out.println("\n\n🚛🚛🚛 Most Rented Cars by Brand 🚛🚛🚛");
    System.out.println("Brand    |    Total of rented days");
    System.out.println("----------------------------------");
    
    for (int i = 0; i < mostRentedCarsByBrandRowNew-1; i++) {                           
      System.out.print((i + 1) +") ");
      for (int j = 0; j < 2; j++) {
        System.out.print(mostRentedCarsByBrand[i][j] + " | ");
      }
      System.out.println();
    }
    
    bubble_sort_matrix_year();                                                            // Ordenamos los carros mas rentados por año de mayor a menor número de dias rentados, usamos el método de burbuja
    System.out.println("\n\n🚙🚙🚙 Most Rented Cars by Year 🚙🚙🚙");
    System.out.println("Model    |    Total of rented days");
    System.out.println("----------------------------------");
    
    for (int i = 0; i < mostRentedCarsByYearRowNew-1; i++) {                           
      System.out.print((i + 1) +") ");
      for (int j = 0; j < 2; j++) {
        System.out.print(mostRentedCarsByYear[i][j] + " | ");
      }
      System.out.println();
    }
    admin_menu();
  }
  
    
  public static void show_users() {                                                     // Mostramos los datos de los usuarios registrados en la matriz 'userDatabase'
    System.out.println("\nList of registered users");
    System.out.println("------------------------------");
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
  
  
  public static double validate_price() {                                               // Verifica que el precio ingresado sea un número ya sea tipo int o double, en caso de no serlo usamos un try/catch para manejar le Exception y pedir el dato nuevamente
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
  
  
  public static void validate_license_plate() {                                         // Valida que las placas no estan ya registradas en la matriz 'carInventory'
    boolean isLicenseFound;
    do {
      System.out.println("Enter your car license plate");
      String licenseTarget = keyboardInput.nextLine(); 
      isLicenseFound = contains_value(carInventory, licenseTarget);         // Ejecutamos función que recorre la matriz enviada como argumento y devuelve un boolean para saber si ese valor ya esta registrado

      if (isLicenseFound) {
        System.out.println("\n❌ The license plate '" + licenseTarget + "' is already registered on inventory. ❌\n");
      }else{
        System.out.println("\n✅ The license plate '" + licenseTarget + "' is not registered on inventory, you're good to go. ✅\n");
        carInventory[carRow][3] = licenseTarget;  
      }
    } while(isLicenseFound);
  }
  
  
  public static void make_report_most_rented_car(String brand, int daysRented){         // Agrega datos de los carros mas rentados por marca, se ejecuta cada vez que se renta un nuevo carro, los datos los guarda en la matriz 'mostRentedCarsByBrandRowNew'
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
      System.out.println("New car brand registered");
      mostRentedCarsByBrandRowNew++;
    }
  }
  
  
  public static void bubble_sort_matrix_brand() {                                      // Ordena los registros de carros más vendidos por marca usando el método de la burbuja
    int n = mostRentedCarsByBrandRowNew-1;                                             // Usamos el número de los registros de carros más vendidos y le restamos 1
    boolean swapped;
        
    for (int i = 0; i < n - 1; i++) {
      swapped = false;
      for (int j = 0; j < n - i - 1; j++) {
        int currentCount = Integer.parseInt(mostRentedCarsByBrand[j][1]);
        int nextCount = Integer.parseInt(mostRentedCarsByBrand[j + 1][1]);
        
        if (currentCount < nextCount) {                                               // Si el siguiente valor es menor que el anterior intercambia su posición en la matriz
          String[] temp = mostRentedCarsByBrand[j];
          mostRentedCarsByBrand[j] = mostRentedCarsByBrand[j + 1];
          mostRentedCarsByBrand[j + 1] = temp;
          swapped = true;
        }
      }
        
      if (!swapped) {                                                             
        break;
      }
    }
  }
  
  
  public static void make_report_most_rented_year(String carYear, int daysRented){    // Agrega datos de los carros mas rentados por años, se ejecuta cada vez que se renta un nuevo carro, los datos los guarda en la matriz 'mostRentedCarsByBrandRowNew'  
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
      System.out.println("New car year registered");
      mostRentedCarsByYearRowNew++;
    }
  }
  
  
  public static void bubble_sort_matrix_year() {                                      // Ordena los registros de carros más vendidos por marca usando el método de la burbuja
    int n = mostRentedCarsByYearRowNew-1;
    boolean swapped;
        
    for (int i = 0; i < n - 1; i++) {
      swapped = false;
      for (int j = 0; j < n - i - 1; j++) {
        int currentCount = Integer.parseInt(mostRentedCarsByYear[j][1]);
        int nextCount = Integer.parseInt(mostRentedCarsByYear[j + 1][1]);
        
        if (currentCount < nextCount) {                                               // Si el siguiente valor es menor que el anterior intercambia su posición en la matriz
          String[] temp = mostRentedCarsByYear[j];
          mostRentedCarsByYear[j] = mostRentedCarsByYear[j + 1];
          mostRentedCarsByYear[j + 1] = temp;
          swapped = true;
        }
      }
        
      if (!swapped) {                                                           
        break;
      }
    }
  }
  
  
  
  
  // METODOS DEL CLIENTE
  public static void client_login() {                                                  // Pregunta al cliente si ya tiene cuenta registrada
    boolean haveExistingAccount;
    boolean accountExists;
    haveExistingAccount = is_confirming("\nDo you have an account?"); 
    
    if(haveExistingAccount){
      System.out.println("\n--> Enter NIT: ");
      nitUser = keyboardInput.nextLine();  
      
      accountExists = contains_value(usersDatabase, nitUser);              // Revisamos si ya existe la cuenta del cliente en la matriz 'usersDatabase' y usamo el nitUser para identificarlo
      if(accountExists) {                                                              // Si ya tiene cuenta le desplegamos el menú de cliente
        client_menu();                                                                                             
      }else{ 
        System.out.println("\n❌ WRONG CREDENTIALS, TRY AGAIN ❌");
      } 
    }else{                                                                             // Si no tiene cuenta lo mandamos a la sección de crear nueva cuenta
      register_new_client();
      client_login();
    }
  }
  
  
  public static void client_menu() {                                                   // Le muestra al cliente la opción de reservar un nuevo carro o cerrar sesión
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
  
  
  public static void register_new_client() {                                            // Registra nuevos clientes en la matriz 'usersDatabase'
    System.out.println("\n+++ Register your new Account +++\n");
    validate_nit();                                                                     // Pedimos y validamos que el NIT no este ya registrado
    
    System.out.println("Enter your first name");
    usersDatabase[userRow][1] = keyboardInput.nextLine();
    
    System.out.println("Enter your last name");
    usersDatabase[userRow][2] = keyboardInput.nextLine();
    
    System.out.println("\n 👤 This is your user Data 👤\n");                          // Le mostramos al usuario los datos de su cuenta recien creada
    for (int i = userRow; i < (userRow + 1); i++) {                           
      System.out.println("Nit | First name | Last name");
      for (int j = 0; j < 3; j++) {
        System.out.print(usersDatabase[i][j] + ", ");
      }
      System.out.println("");
    }
    userRow++;                                                                         // Pasamos a la siguiente fila de la matriz 'usersDatabase' para registrar al proximo usuario
  }
  
  
  public static void validate_nit() {                                                  // Pide y valida que el NIT del cliente no este ya registrado
    boolean isNitFound = true;
    String nitTarget;
    int inputNIT;
    do {
      try {
        System.out.println("Enter your NIT");
        inputNIT = parseInt(keyboardInput.nextLine()); 
        nitTarget = Integer.toString(inputNIT);
        isNitFound = contains_value(usersDatabase, nitTarget);            // Revisamos si el NIT ya esta registrado
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
    System.out.println("\n+++ 🚖 Available Cars 🚖 +++");
    if(carRow > 0) {                                                                  // Mostramos los carros disponibles para rentar del inventario
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
    
    System.out.println("\n+++ 🛒 Table of current discounts 🛒 ++++");              // Mostramos los discuentos disponibles que puede aplicar el cliente si renta X cantidad de dia un carro
    if(discountRow > 0) {
      for (int i = 0; i < discountRow; i++) {
        System.out.print((i + 1) + ") " + specialDiscounts[i][0] + " days to apply for a " + specialDiscounts[i][1] + "% discount\n");
      }
    }else{
      System.out.println("There are currently NO discounts available");
    }
    
    System.out.println("\n Options Available \n1)Show current cars you're renting\n2)Reserve a new car\n3)End renting process");
    String rentingOption =  keyboardInput.nextLine();

    switch(rentingOption){                                                           // Le mostramos un menu al cliente de que acciones puede tomar
      case "1" -> show_current_rented_cars();
      case "2" -> reserve_new_car();
      case "3" -> show_bill();
      default -> System.out.println("\n❌ Invalid option, try again ❌\n");
    }
    client_menu();
  }

  
  private static void show_current_rented_cars() {                                  // Muestra la lista de carros que actualmente esta rentando el cliente, esto es unico por cada ciclo de compra
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
      System.out.println("\nYOU HAVE NO CARS ON YOUR SHOPPING CART");
    }
  }
  
  
  private static void reserve_new_car() {                                          // Conjunto de funciones que ayudan en el proceso de reservar un nuevo carro
    int carIndex = exists_license_plate();
    check_car_discount(carIndex);
    make_reservation();
  }
  
  
  public static int exists_license_plate() {                                                                  // Revisa que las placas que ingresa el cliente coinciden con un carro que esta disponble para rentar
    boolean isLicenseFound;
    boolean validLicense = false;
    int carIndex = -1;
    int daysOfRent;
    
    do {
      System.out.println("\nEnter the license plate of the car you want to rent");
      String licenseTarget = keyboardInput.nextLine(); 
      isLicenseFound = contains_value(carInventory, licenseTarget);                               // Verificamos que las placas estan disponibles

      if (isLicenseFound) {
        System.out.println("\n✅ The license plate '" + licenseTarget + "' exists on inventory. ✅\n");
        carIndex = find_car_by_license(licenseTarget);                                                 // Encontramos la posición del carro a rentar 
        carInventory[carIndex][5] = "";
        
        if (carInventory[carIndex][5].equals("Rented")){                                               // Verificamos si el carro ya esta siendo rentado
          System.out.println("❌❌❌ But this car is already rented, try renting another one ❌❌❌");
          continue;                                                                                           // Si ya esta rentado le pedimos que ingrese otras placas
        }else{
          System.out.println("✅ The license plate '" + licenseTarget + "' is for rent. ✅\n");
          carInventory[carIndex][5] = "Rented";                                                               // Si se puede rentar le cambiamos el estado del carro a 'rented'
        }
        
        do {                                                                                                  // Le pedimos al cliente cuantos dias desea rentar el carro
          System.out.println("Enter the number of days you want to rent the car '" + licenseTarget + "'");
          try {
            daysOfRent = parseInt(keyboardInput.nextLine());
            make_report_most_rented_car(carInventory[carIndex][0], daysOfRent);                       // Mandamos datos para generar el reporte de los carros más vendidos por marca
            make_report_most_rented_year(carInventory[carIndex][2], daysOfRent);                      // Mandamos datos para generar el reporte de los carros más vendidos por año
            break;
          } catch(NumberFormatException e) {
            System.out.println("\n❌ Enter a number not a String ❌\n");
          }
        }while(true);
        
        System.out.println("\n✅ This car has been rented succesfully ✅\n ");                               // La reservación ha sido exitosa y guardamos los datos correspondiente a la matriz 'currentCarOrder'
        currentCarOrder[carOrderRow][0] = carInventory[carIndex][0];
        currentCarOrder[carOrderRow][1] = carInventory[carIndex][1];
        currentCarOrder[carOrderRow][2] = carInventory[carIndex][2];
        currentCarOrder[carOrderRow][3] = carInventory[carIndex][3];
        currentCarOrder[carOrderRow][4] = Integer.toString(daysOfRent);      
        carOrderRow++;
        
        String pricePerDayString = carInventory[carIndex][4];                                                 // Calculamos el subtotal que tiene que pagar el cliente por este carro 
        int pricePerDay = Double.valueOf(pricePerDayString).intValue();
        int carTotalCost = (daysOfRent * pricePerDay);
        System.out.println("Total cost of this car: Q"+ pricePerDay +" per day for " + daysOfRent + " days = Q"+ carTotalCost);
        carCostSubTotal[subTotalIndex] = carTotalCost;                                                        // Agregamos este subTotal al Array 'carCostSubTotal' para luego calcular el total final en la factura
        subTotalIndex++;
        
        validLicense = true;
      }else{
        System.out.println("\n❌ The license plate '" + licenseTarget + "' is not registered on inventory, try entering another ❌\n");
      }
    } while(!validLicense);
    return carIndex;
  }
  
  
  public static boolean check_car_discount(int carIndex) {                                                    // Revisa si el carro registrado para rentar aplica para un descuento dependiendo de los dias que se va a rentar
    int daysToCheck = parseInt(currentCarOrder[carOrderRow - 1][4]);                                          // Obtenemos el valor de dias que se ha rentado el carro
    boolean appliesForDiscount = false;
    int discountPercentage = -1;  
    int subTotal;
    double decimalDiscount;
    double totalThisCar;
    double amountDiscount;
    
    for (int[] entry : specialDiscounts) {                                                                    // Iteramos la matriz 'specialDiscounts' para ver a que descuento califica y obtener su porcetage de descuento
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
    
    appliedPercentages[appliedPercentagesIndex] = discountPercentage;                                         // Guardamos el valor del descuento para este carro en el Array 'appliedPercentages'
    appliedPercentagesIndex++;
    
    subTotal = carCostSubTotal[subTotalIndex-1];                                                              // Calculamos y mostramos los datos de la cantidad sin descuento, el descuento que aplica y lo que tiene que pagar despues del descuento
    System.out.println("\nSubtotal of this car before discout: Q" + subTotal);    
    System.out.println("Percentage of discount: " + appliedPercentages[appliedPercentagesIndex - 1] + "%");

    decimalDiscount = (double) appliedPercentages[subTotalIndex - 1] / 100;
    amountDiscount = (subTotal * decimalDiscount);
    System.out.println("Amount of discount: Q" + amountDiscount);
    
    totalThisCar =  subTotal - amountDiscount;
    totalThisCar = convert_to_decimal(totalThisCar);
    System.out.println("Total of this car: Q" + totalThisCar);
    
    carTotalAfterDiscount[carTotalAfterDiscountIndex] = totalThisCar;                                         // Guardamos el subtotal despues de descuento y lo guardamos en el Array 'carTotalAfterDiscount'
    carTotalAfterDiscountIndex++;
    return appliesForDiscount; 
  }
  
  
  private static void show_bill() {                                                           // Genera una factura con los datos de los carros rentados y cuanto tiene que pagar el cliente al final incluyendo los descuentos
    int userIndex = find_user_by_index(parseInt(nitUser));
    double subTotalBill = 0;
    double TotalBillAfterDiscount = 0;
    
    String currentDateTime = print_current_date_time();                                       // Imprime la hora actual al genera la factura
    String userNit= usersDatabase[userIndex][0]; 
    String loggedInUserName = usersDatabase[userIndex][1];
    String loggedInUserLastName = usersDatabase[userIndex][2];
    
    System.out.println("\n\n\n****** 🚘 ByteCar 🚘 ******\n\n"
                        + "\nClient name: " + loggedInUserName + ' ' + loggedInUserLastName
                        + "\nNit: " + userNit
                        + "\nCurrent date and time: " + currentDateTime
                        + "\nList of cars to rent:"
                        + "\n----------------------------------");
    for (int i = 0; i < carOrderRow; i++) {                                                   // Iteramos la matriz 'currentCarOrder' y mostramos los datos de la orden de renta actual
      System.out.println("Brand: " + currentCarOrder[i][0] 
                          + "\nModel: " + currentCarOrder[i][1] 
                          + "\nLicense: " + currentCarOrder[i][3] 
                          + "\nDays rented: " + currentCarOrder[i][4] 
                          + "\nCost before discount: Q" + carCostSubTotal[i] 
                          + "\nThis car has a discount of: " + appliedPercentages[i] +"%" 
                          + "\nCost after discount: Q" + carTotalAfterDiscount[i]);
      System.out.println("----------------------------------");
      
      subTotalBill = subTotalBill + carCostSubTotal[i];
      TotalBillAfterDiscount = TotalBillAfterDiscount + carTotalAfterDiscount[i];             // Calculamos el total final de lo que tiene que pagar el cliente
    }
        
    System.out.println("\nSubtotal: Q" + subTotalBill);
    TotalBillAfterDiscount = convert_to_decimal(TotalBillAfterDiscount);
    System.out.println("Total to pay: Q" + TotalBillAfterDiscount);
    System.out.println("\n\n\n****** 🚘 ByteCar 🚘 ******\n\n");
    
    clear_matrix();                                                                           // Vaciamos la matriz de 'currentOrder' para preparar la siguiente orden
    carOrderRow = 0;
  }
  
  
  
  
  // Utils
  public static boolean contains_value(String[][] matrix, String target) {                    // Recorre la matriz recibida como parámetro y devuelve un booleano para saber si el valor recibido como 'target' ya esta registrado, trabaja con datos tipo String
    for (String[] row : matrix) {
      for (String element : row) {
        try {
          if (element.equals(target)) {
            return true;
          }
        } catch (NullPointerException e){                                                     // Manejamos el error de recorrer un valor null dentro de la matriz
        }
      }
    }
    return false;
  }
  
  
  public static boolean contains_value_int(int[][] matrix, int target) {                      // Recorre la matriz recibida como parámetro y devuelve un booleano para saber si el valor recibido como 'target' ya esta registrado, trabaja con datos tipo int
    for (int[] row : matrix) {
      for (int element : row) {
        try {
          if (element == target) {
            return true;
          }
        } catch (NullPointerException e){                                                     // Manejamos el error de recorrer un valor null dentro de la matriz
        }
      }
    }
    return false;
  }
  
  
  public static boolean is_confirming(String phraseToShow) {                                  // Pregunta al usario cualquier frase que se mande como parametro y acepto respuesta de Yes y No, retornado el valor escogido como un booleano
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
  
  
  public static String print_current_date_time() {                                    // Imprime la fecha y hora actual en el momento que se genera una nueva factura
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatter);
    return formattedDateTime;
  }
  
  
  public static int find_user_by_index(int nit) {                                     // Busca y retorna en Index en la posición del usuario en la matriz 'userDatabase' esto con tal de mostrar información y mensajes personalizados
    for (int i = 0; i < usersDatabase.length; i++) {
      if (nit == Integer.parseInt(usersDatabase[i][0])) {
        return i;
      }
    }
    return -1;                                                                        // Retornamos -1 si no se encontro el usuario en la matriz
  }
  
  
  public static int find_car_by_license(String license) {                             // Busca y retorna en Index en la posición del carro registrado en la matriz 'carInventory' esto con tal de mostrar información y mensajes personalizados
    for (int i = 0; i < carInventory.length; i++) {
      if (license.equals(carInventory[i][3])) {
        return i;
      }
    }
    return -1;                                                                        // Retornamos -1 si no se encontro el usuario en la matriz
  }
  
  
  public static double convert_to_decimal(double manyDecimalsNumber) {                // Le da formato a los numeros double para que solo muestren 2 valores decimales
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
    double formattedValueDouble = Double.parseDouble(formattedValue);
    
    return formattedValueDouble;
  }
  
  
  public static void clear_matrix() {                                               // Limpia los datos dentro de la matriz 'currentCarOrder' para que este listo para la siguiente orden
    for (int i = 0; i < carOrderRow; i++) {
      for (int j = 0; j < 5; j++) {
        currentCarOrder[i][j] = null;
      }
    }
  }
}

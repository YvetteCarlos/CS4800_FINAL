import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

interface Member {
    String getName();
    String getAddress();
    String getCounty();

}

class Customers implements Member {
    private String name;
    private String county;
    private String address;
    private String dietaryRestrictions;

    public Customers(String name, String address, String county, String dietaryRestrictions) {
        this.name = name;
        this.county = county;
        this.address = address;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getCounty() {
        return county;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }
}

class Restaurants implements Member {
    private String name;
    private String address;
    private String county;
    private String operatingHours;
    private Date startHours;
    private Date endHours;
    private String cuisineType;
    private Menu menu;

    public Restaurants(String name, String address, String county, String operatingHours, String cuisineType, Menu menu) {
        this.name = name;
        this.address = address;
        this.county = county;
        this.operatingHours = operatingHours;
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        try {
            this.startHours = parser.parse(operatingHours.substring(0, operatingHours.indexOf("-")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            this.endHours = parser.parse(operatingHours.substring(operatingHours.indexOf("-")+1));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.cuisineType = cuisineType;
        this.menu = menu;
    }

    public Boolean checkTime(String time) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date pickup = parser.parse(time);
        Date start = this.startHours;
        Date end = this.endHours;
        if (start.before(pickup))
            return Boolean.FALSE;
        if (end.after(pickup))
            return Boolean.FALSE;
        return Boolean.TRUE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getCounty() {
        return county;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public Menu getMenu() {
        return menu; }

    public Date getStartHours() {
        return startHours;
    }

    public Date getEndHours() {
        return endHours;
    }
}

class Drivers implements Member {
    private String name;
    private String operatingCounty;
    private String address;
    private Shift shift;

    public Drivers(String name, String address, String operatingCounty, String shift) {
        this.name = name;
        this.address = address;
        this.operatingCounty = operatingCounty;
        this.shift = Shift.valueOf(shift);
    }

    public Boolean checkTime(String time) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date pickup = parser.parse(time);
        Date start = shift.getStart();
        Date end = shift.getEnd();
        if (start.before(pickup))
            return Boolean.FALSE;
        if (end.after(pickup))
            return Boolean.FALSE;
        return Boolean.TRUE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getCounty() {
        return operatingCounty;
    }

    public Shift getShift() {
        return shift;
    }
}

enum Shift {
    FIRST ("8:00", "15:59"),
    SECOND ("16:00", "23:59"),
    THIRD ("0:00", "7:59");

    private String startTime;
    private String endTime;

    Shift(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    Date getStart() throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        return parser.parse(this.startTime);
    }

    Date getEnd() throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        return parser.parse(this.endTime);
    }
}

//factory design pattern
interface memberFactory {
    Member createMember(String name, String address, String county, String infoHolder1, String infoHolder2, Menu menu);
}

class customerFactory implements memberFactory {
    @Override
    public Member createMember(String name, String address, String county, String dietaryRestrictions, String empty2, Menu empty3) {
        return new Customers(name, address, county, dietaryRestrictions);
    }
}

class restaurantFactory implements memberFactory {
    @Override
    public Member createMember(String name, String address, String county, String operatingHours, String cuisineType, Menu menu) {
        return new Restaurants(name, address, county, operatingHours, cuisineType, menu);
    }
}

class driverFactory implements memberFactory {
    @Override
    public Member createMember(String name, String address, String county, String shift, String empty2, Menu empt3) {
        return new Drivers(name, address, county, shift);
    }
}

class OrderManagementSystem {
    private static OrderManagementSystem order;
    private List<OrderInfo> orderInfoList;

    public OrderManagementSystem() {
        orderInfoList = new ArrayList<>();
    }

    //singleton design for ordermanagementsystem
    public static synchronized OrderManagementSystem getOrder() {
        if (order == null) {
            order = new OrderManagementSystem();
        }
        return order;
    }

    public void controlOrder(OrderInfo orderInfo) {
        orderInfoList.add(orderInfo);
        System.out.println("Ordered from" + orderInfo.getRestaurant().getName());
        Drivers driver = orderInfo.getDriver();
        if (driver == null) {
            System.out.println("Cannot find driver for the order");
        }
        System.out.println(driver.getName() + " will deliver your order.");
        orderInfo.setOrderTime("Ordered at:" + getCurrentTime());
        orderInfo.setPickupTime("Pickup time:" + getPickupTime());
        orderInfo.setDeliveryTime("Delivery time:" + getDeliveryTime());
    }

    public String getCurrentTime() {
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatting = DateTimeFormatter.ofPattern("hh:mm a");
        return current.format(formatting);
    }

    public String getPickupTime() {
        LocalDateTime pickupTime = LocalDateTime.now().plusMinutes(15);
        DateTimeFormatter formatting = DateTimeFormatter.ofPattern("hh:mm a");
        return pickupTime.format(formatting);
    }

    private String getDeliveryTime() {
        LocalDateTime deliveryTime = LocalDateTime.now().plusMinutes(45);
        DateTimeFormatter formatting = DateTimeFormatter.ofPattern("hh:mm a");
        return deliveryTime.format(formatting);
    }


}

class OrderInfo {
    private Customers customer;
    private Restaurants restaurant;
    private List<Meal> foodItems;
    private Drivers driver;
    private String OrderTime;
    private String PickupTime;
    private String DeliveryTime;


    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }


    public Restaurants getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurants restaurant) {
        this.restaurant = restaurant;
    }


    public List<Meal> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<Meal> foodItems) {
        this.foodItems = foodItems;
    }

    public Drivers getDriver() {
        return driver;
    }

    public void setDriver(Drivers driver) {
        this.driver = driver;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getPickupTime() {
        return PickupTime;
    }

    public void setPickupTime(String pickupTime) {
        PickupTime = pickupTime;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }
}

//decorator pattern:Aaron
class CPPFoodDelivery { //Aaron: please implement methods that register the restaurants customers and drivers.
    private List<Restaurants> restaurants;
    private List<Customers> customers;
    private List<Drivers> drivers;

    public CPPFoodDelivery() {
        this.restaurants = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.drivers = new ArrayList<>();
    }

    public Drivers lookForDriver(String time, String county) throws ParseException {
        for(Drivers d : drivers) {
            if (d.checkTime(time) && d.getCounty().equals(county)) {
                return d;
            }
        }
        return null;
    }

    public void registerRestaurant(Restaurants restaurant) {
        this.restaurants.add(restaurant);
    }

    public void registerCustomer(Customers customer) {
        this.customers.add(customer);
    }

    public void registerDriver(Drivers driver) {
        this.drivers.add(driver);
    }

    public List<Restaurants> getRestaurants() {
        return restaurants;
    }

    public List<Customers> getCustomers() {
        return customers;
    }

    public List<Drivers> getDrivers() {
        return drivers;
    }
}
//facade pattern:Aaron  #interface name for decorator is FoodItem!
// Facade's purpose to simplify customer interactions
class FoodDeliveryFacade {
    private CPPFoodDelivery cppFoodDelivery;
    private OrderManagementSystem orderManagementSystem;

    public FoodDeliveryFacade(CPPFoodDelivery cppFoodDelivery, OrderManagementSystem orderManagementSystem) {
        this.cppFoodDelivery = cppFoodDelivery;
        this.orderManagementSystem = orderManagementSystem;
    }
    public OrderInfo orderMeal(Customers customer, Restaurants restaurant, int mealNum, Boolean drink, Boolean sauce, Boolean side) {
        // Creates orderInfo to return
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCustomer(customer);
        orderInfo.setRestaurant(restaurant);
        // Gets list of restaurants from cppFoodDelivery
        List<Restaurants> restaurants = cppFoodDelivery.getRestaurants();
        // Check if the restaurant is registered
        if(!restaurants.contains(restaurant)) {
            System.out.println("Restaurant not registered");
            System.exit(0);
        }
        // Check if restaurant is open using restaurant.checkTime();
        String orderTime = orderManagementSystem.getCurrentTime();
        try {
            restaurant.checkTime(orderTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // Get the menu from the restaurant and order the specific meal with changes according to diet and add ons
        Menu menu = restaurants.get(restaurants.indexOf(restaurant)).getMenu();
        FoodItem food = menu.chooseMeal(mealNum);
        food = food.checkDietary(customer.getDietaryRestrictions());
        Meal meal = new Meal(food);
        if (drink)
            meal = new Drink(meal);
        if (sauce)
            meal = new Sauce(meal);
        if (side)
            meal = new Side(meal);
        // Add all info to the orderInfo
        orderInfo.setCustomer(customer);
        orderInfo.setRestaurant(restaurant);
        String pickupTime = orderManagementSystem.getPickupTime();
        try {
            orderInfo.setDriver(cppFoodDelivery.lookForDriver(pickupTime, restaurant.getCounty())); // Implement method that gets a driver during their shift that's in the county
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // I am not sure if you wanted the possibility of adding multiple meals to one order or not but it is not implemented here
        List<Meal> mealList = new ArrayList<>();
        mealList.add(meal);
        orderInfo.setFoodItems(mealList);
        orderManagementSystem.controlOrder(orderInfo);
        return orderInfo;
    }
}

class Menu {
    private List<FoodItem> meals = new ArrayList<>();

    // Creates 4 random meals and 3 pre-determined toppings for a restaurant's menu
    void createRandomMenu() {
        for(int i=0; i<4; i++) {
            FoodItem foodItem = new FoodItem();
            foodItem.createRandomFood();
            meals.add(foodItem);
        }
    }

    void displayMenu() {
        for(int i=1; i<=4; i++) {
            System.out.println("Meal " + i + ": " + meals.get(i-1));
        }
    }

    FoodItem chooseMeal(int mealNum) {
        return meals.get(mealNum-1);
    }

    void displayAddOns() {
        System.out.println("The AddOns include: Drink, Sauce, and Side");
    }

}

class FoodItem implements MealInt{
    private String fats;
    private String carbs;
    private String protein;

    // Creates a random meal
    void createRandomFood() {
        MacroAbstractFactory carbFactory = MacroFactory.createFactory("Carbs");
        assert carbFactory != null;
        this.carbs = carbFactory.getRandomFood("None");

        MacroAbstractFactory proteinFactory = MacroFactory.createFactory("Protein");
        assert proteinFactory != null;
        this.protein = proteinFactory.getRandomFood("None");

        MacroAbstractFactory fatsFactory = MacroFactory.createFactory("Fats");
        assert fatsFactory != null;
        this.fats = fatsFactory.getRandomFood("None");
    }

    FoodItem checkDietary(String dietPlan) {
        FoodItem newFoodItem = new FoodItem();

        MacroAbstractFactory carbFactory = MacroFactory.createFactory("Carbs");
        List<String> carbsList = carbFactory.getFoodList(dietPlan);
        if (!carbsList.contains(carbs))
            newFoodItem.setCarbs("without " + carbs);
        else
            newFoodItem.setCarbs(carbs);

        MacroAbstractFactory proteinFactory = MacroFactory.createFactory("Protein");
        List<String> proteinList = proteinFactory.getFoodList(dietPlan);
        if (!proteinList.contains(protein))
            newFoodItem.setProtein("without " + protein);
        else
            newFoodItem.setProtein(protein);

        MacroAbstractFactory fatsFactory = MacroFactory.createFactory("Fats");
        List<String> fatsList = fatsFactory.getFoodList(dietPlan);
        if (!fatsList.contains(fats))
            newFoodItem.setFats("without " + fats);
        else
            newFoodItem.setFats(fats);

        return newFoodItem;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    @Override
    public String getAddOn() {
        return null;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "fats='" + fats + '\'' +
                ", carbs='" + carbs + '\'' +
                ", protein='" + protein + '\'' +
                '}';
    }
}

// Abstract Factory and Factory design
abstract class MacroAbstractFactory {
    abstract List<String> getFoodList(String dietPlan);
    abstract String getRandomFood(String dietPlan);
}

class MacroFactory {
    private static MacroFactory macroFactory = null;

    MacroFactory() {
    }

    public static MacroFactory getInstance() {
        if (macroFactory == null)
            macroFactory = new MacroFactory();
        return macroFactory;
    }


    public static MacroAbstractFactory createFactory(String type) {
        switch (type) {
            case "Carbs":
                return CarbsFactory.getInstance();

            case "Protein":
                return ProteinFactory.getInstance();

            case "Fats":
                return FatsFactory.getInstance();

            default:
                return null;
        }
    }
}

class CarbsFactory extends MacroAbstractFactory {

    private static CarbsFactory carbsFactory = null;

    CarbsFactory(){}

    public static CarbsFactory getInstance() {
        if (carbsFactory == null)
            carbsFactory = new CarbsFactory();
        return carbsFactory;
    }

    @Override
    public List<String> getFoodList(String dietPlan) {
        Carbs carbs = new Carbs();
        return carbs.getFoodList(dietPlan);
    }

    @Override
    public String getRandomFood(String dietPlan) {
        Carbs carbs = new Carbs();
        return carbs.getRandomFood(dietPlan);
    }
}
class ProteinFactory extends MacroAbstractFactory {

    private static ProteinFactory proteinFactory = null;

    ProteinFactory(){}

    public static ProteinFactory getInstance() {
        if (proteinFactory == null)
            proteinFactory = new ProteinFactory();
        return proteinFactory;
    }

    @Override
    public List<String> getFoodList(String dietPlan) {
        Protein protein = new Protein();
        return protein.getFoodList(dietPlan);
    }

    @Override
    public String getRandomFood(String dietPlan) {
        Protein protein = new Protein();
        return protein.getRandomFood(dietPlan);
    }
}

class FatsFactory extends MacroAbstractFactory {

    private static FatsFactory fatsFactory = null;

    FatsFactory(){}

    public static FatsFactory getInstance() {
        if (fatsFactory == null)
            fatsFactory = new FatsFactory();
        return fatsFactory;
    }

    @Override
    public List<String> getFoodList(String dietPlan) {
        Fats fats = new Fats();
        return fats.getFoodList(dietPlan);
    }

    @Override
    public String getRandomFood(String dietPlan) {
        Fats fats = new Fats();
        return fats.getRandomFood(dietPlan);
    }
}

abstract class Macro {
    abstract List<String> getFoodList(String dietPlan);
    abstract String getRandomFood(String dietPlan);
}
class Carbs extends Macro {

    @Override
    List<String> getFoodList(String dietPlan) {
        List<String> foodList = new ArrayList<>();
        switch (dietPlan) {
            case "Paleo":
                foodList = Arrays.asList("Pistachio");
                return foodList;

            case "Vegan":
                foodList = Arrays.asList("Bread", "Lentils", "Pistachio");
                return foodList;

            case "Nut Allergy":
                foodList = Arrays.asList("Cheese", "Bread", "Lentils");
                return foodList;

            default:
                foodList = Arrays.asList("Cheese", "Bread", "Lentils", "Pistachio");
                return foodList;
        }
    }

    @Override
    String getRandomFood(String dietPlan) {
        List<String> foodList = getFoodList(dietPlan);
        Random rand = new Random();
        return foodList.get(rand.nextInt(foodList.size()));
    }
}

class Protein extends Macro {

    @Override
    List<String> getFoodList(String dietPlan) {
        List<String> foodList = new ArrayList<>();
        switch (dietPlan) {
            case "Paleo":
                foodList = Arrays.asList("Fish", "Chicken", "Beef");
                return foodList;

            case "Vegan":
                foodList = Arrays.asList("Tofu");
                return foodList;

            case "Nut Allergy":
                foodList = Arrays.asList("Fish", "Chicken", "Beef", "Tofu");
                return foodList;

            default:
                foodList = Arrays.asList("Fish", "Chicken", "Beef", "Tofu");
                return foodList;
        }
    }

    @Override
    String getRandomFood(String dietPlan) {
        List<String> foodList = getFoodList(dietPlan);
        Random rand = new Random();
        return foodList.get(rand.nextInt(foodList.size()));
    }
}

class Fats extends Macro {
    List<String> getFoodList(String dietPlan) {
        List<String> foodList = new ArrayList<>();
        switch (dietPlan) {
            case "Paleo":
                foodList = Arrays.asList("Avacado", "Tuna", "Peanuts");
                return foodList;

            case "Vegan":
                foodList = Arrays.asList("Avacado", "Peanuts");
                return foodList;

            case "Nut Allergy":
                foodList = Arrays.asList("Avacado", "Sour cream", "Tuna");
                return foodList;

            default:
                foodList = Arrays.asList("Avacado", "Sour cream", "Tuna", "Peanuts");
                return foodList;
        }
    }

    @Override
    String getRandomFood(String dietPlan) {
        List<String> foodList = getFoodList(dietPlan);
        Random rand = new Random();
        return foodList.get(rand.nextInt(foodList.size()));
    }
}

// Decorator Design
interface MealInt {
    public String getAddOn();
    public String toString();
}

// Using AddOns instead of Meal Toppings
class Meal implements MealInt {
    private MealInt meal;
    public Meal(){this.meal = new Meal();}
    public Meal(MealInt meal) {this.meal = meal;}

    public String getAddOn() {
        return "Test";
    }

    public String toString() {
        String mealStr = "";
        mealStr += meal.toString();
        return mealStr;
    }
}

class Drink extends Meal {
    public Drink(MealInt meal) {super(meal);}
    public String getAddOn() {return super.getAddOn() + withDrink();}

    public String withDrink() {return "Drink ";}

    public String toString() {
        return super.toString() + withDrink();
    }
}

class Sauce extends Meal {
    public Sauce(MealInt meal) {super(meal);}
    public String getAddOn() {return super.getAddOn() + withSauce();}

    public String withSauce() {return "Sauce ";}

    public String toString() {
        return super.toString() + withSauce();
    }
}

class Side extends Meal {
    public Side(MealInt meal) {super(meal);}
    public String getAddOn() {return super.getAddOn() + withSide();}

    public String withSide() {return "Side ";}

    public String toString() {
        return super.toString() + withSide();
    }
}


class Main{
    public static void main(String[] args){
        CPPFoodDelivery cppFoodDelivery = new CPPFoodDelivery();
        OrderManagementSystem orderManagementSystem = new OrderManagementSystem();
        Customers customer1 = new Customers("customer1", "address1", "LA County", "None");
        Customers customer2 = new Customers("customer2", "address2", "LA County", "Paleo");
        Customers customer3 = new Customers("customer3", "address3", "LA County", "Vegan");
        Customers customer4 = new Customers("customer4", "address4", "LA County", "Nut Allergy");
        Customers customer5 = new Customers("customer5", "address5", "Orange County", "None");
        Customers customer6 = new Customers("customer6", "address6", "Orange County", "Paleo");
        Customers customer7 = new Customers("customer7", "address7", "Orange County", "Vegan");
        Customers customer8 = new Customers("customer8", "address8", "San Bernardino County", "Nut Allergy");
        Customers customer9 = new Customers("customer9", "address9", "San Bernardino County", "None");
        Customers customer10 = new Customers("customer10", "address10", "San Bernardino County", "None");

        Menu menu1 = new Menu();
        menu1.createRandomMenu();
        Restaurants restaurant1 = new Restaurants("restaurant1", "address", "LA County", "8:00-20:00", "cuisineType", menu1);
        Menu menu2 = new Menu();
        menu2.createRandomMenu();
        Restaurants restaurant2 = new Restaurants("restaurant2", "address", "LA County", "8:00-12:00", "cuisineType", menu2);
        Menu menu3 = new Menu();
        menu3.createRandomMenu();
        Restaurants restaurant3 = new Restaurants("restaurant3", "address", "Orange County", "12:00-16:00", "cuisineType", menu3);
        Menu menu4 = new Menu();
        menu4.createRandomMenu();
        Restaurants restaurant4 = new Restaurants("restaurant4", "address", "San Bernardino County", "8:00-20:00", "cuisineType", menu4);

        Drivers driver1 = new Drivers("driver1", "address", "county", "FIRST");
        Drivers driver2 = new Drivers("driver2", "address", "county", "SECOND");
        Drivers driver3 = new Drivers("driver3", "address", "county", "THIRD");
        Drivers driver4 = new Drivers("driver4", "address", "county", "FIRST");
        Drivers driver5 = new Drivers("driver5", "address", "county", "SECOND");
        Drivers driver6 = new Drivers("driver6", "address", "county", "THIRD");
        Drivers driver7 = new Drivers("driver7", "address", "county", "FIRST");
        Drivers driver8 = new Drivers("driver8", "address", "county", "SECOND");

        cppFoodDelivery.registerCustomer(customer1);
        cppFoodDelivery.registerCustomer(customer2);
        cppFoodDelivery.registerCustomer(customer3);
        cppFoodDelivery.registerCustomer(customer4);
        cppFoodDelivery.registerCustomer(customer5);
        cppFoodDelivery.registerCustomer(customer6);
        cppFoodDelivery.registerCustomer(customer7);
        cppFoodDelivery.registerCustomer(customer8);
        cppFoodDelivery.registerCustomer(customer9);
        cppFoodDelivery.registerCustomer(customer10);
        cppFoodDelivery.registerRestaurant(restaurant1);
        cppFoodDelivery.registerRestaurant(restaurant2);
        cppFoodDelivery.registerRestaurant(restaurant3);
        cppFoodDelivery.registerRestaurant(restaurant4);
        cppFoodDelivery.registerDriver(driver1);
        cppFoodDelivery.registerDriver(driver2);
        cppFoodDelivery.registerDriver(driver3);
        cppFoodDelivery.registerDriver(driver4);
        cppFoodDelivery.registerDriver(driver5);
        cppFoodDelivery.registerDriver(driver6);
        cppFoodDelivery.registerDriver(driver7);
        cppFoodDelivery.registerDriver(driver8);



        FoodDeliveryFacade facade = new FoodDeliveryFacade(cppFoodDelivery, orderManagementSystem);
        OrderInfo order1 = facade.orderMeal(customer1, restaurant1, 1, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
        OrderInfo order2 = facade.orderMeal(customer2, restaurant1, 2, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
        OrderInfo order3 = facade.orderMeal(customer3, restaurant2, 3, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
        OrderInfo order4 = facade.orderMeal(customer4, restaurant3, 4, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
        OrderInfo order5 = facade.orderMeal(customer5, restaurant4, 1, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);
    }
}


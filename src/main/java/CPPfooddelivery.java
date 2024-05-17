import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    private String cuisineType;
    private Menu menu;

    public Restaurants(String name, String address, String county, String operatingHours, String cuisineType, Menu menu) {
        this.name = name;
        this.address = address;
        this.county = county;
        this.operatingHours = operatingHours;
        this.cuisineType = cuisineType;
        this.menu = menu;
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
}

class Drivers implements Member {
    private String name;
    private String operatingCounty;
    private String address;

    public Drivers(String name, String address, String operatingCounty) {
        this.name = name;
        this.address = address;
        this.operatingCounty = operatingCounty;

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
    public Member createMember(String name, String address, String county, String empty1, String empty2, Menu empt3) {
        return new Drivers(name, address, county);
    }
}

class orderManagementSystem {
    private static orderManagementSystem order;
    private List<OrderInfo> orderInfoList;

    private orderManagementSystem() {
        orderInfoList = new ArrayList<>();
    }

    //singleton design for ordermanagementsystem
    public orderManagementSystem getOrder() {
        if (order == null) {
            order = new orderManagementSystem();
        }
        return order;
    }

    public void controlOrder(OrderInfo orderInfo) {
        orderInfoList.add(orderInfo);
        System.out.println("Ordered from" + orderInfo.getRestaurant().getName());
        Drivers driver = orderInfo.getDriver();
        System.out.println(driver.getName() + " will deliver your order.");
        orderInfo.setOrderTime("Ordered at:" + getCurrentTime());
        orderInfo.setPickupTime("Pickup time:" + getPickupTime());
        orderInfo.setDeliveryTime("Delivery time:" + getDeliveryTime());
    }

    private String getCurrentTime() {
        return "1:00PM";
    }

    private String getPickupTime() {
        return "1:15PM";
    }

    private String getDeliveryTime() {
        return "1:45PM";
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

    public FoodDeliveryFacade(CPPFoodDelivery cppFoodDelivery) {
        this.cppFoodDelivery = cppFoodDelivery;
    }
    public OrderInfo orderMeal(Customers customer, Restaurants restaurant, int mealNum, String dietary, Boolean drink, Boolean sauce, Boolean side) {
        OrderInfo orderInfo = new OrderInfo();
        List<Restaurants> restaurants = cppFoodDelivery.getRestaurants();
        if(!restaurants.contains(restaurant)) {
            System.out.println("Restaurant not registered");
            System.exit(0);
        }
        Menu menu = restaurants.get(restaurants.indexOf(restaurant)).getMenu();
        FoodItem food = menu.chooseMeal(mealNum);
        food = food.checkDietary(dietary);
        Meal meal = new Meal(food);
        if (drink)
            meal = new Drink(meal);
        if (sauce)
            meal = new Sauce(meal);
        if (side)
            meal = new Side(meal);
        orderInfo.setCustomer(customer);
        orderInfo.setRestaurant(restaurant);
        orderInfo.setDriver(cppFoodDelivery.getDrivers().get(0)); // Implement method that gets a driver during their shift that's in the county
        List<Meal> mealList = new ArrayList<>();
        mealList.add(meal);
        orderInfo.setFoodItems(mealList);
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
        Customers customer1 = new Customers("name", "address", "county", "None");
        Menu menu1 = new Menu();
        menu1.createRandomMenu();
        Restaurants restaurant1 = new Restaurants("name", "address", "county", "hours", "cuisineType", menu1);
        Drivers driver1 = new Drivers("name", "address", "county");

        cppFoodDelivery.registerCustomer(customer1);
        cppFoodDelivery.registerRestaurant(restaurant1);
        cppFoodDelivery.registerDriver(driver1);

        FoodDeliveryFacade facade = new FoodDeliveryFacade(cppFoodDelivery);
        OrderInfo order = facade.orderMeal(customer1, restaurant1, 1, "Paleo", Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
        System.out.println(order);
    }
}


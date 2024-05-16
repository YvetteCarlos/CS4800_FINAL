import java.util.ArrayList;
import java.util.List;

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
        public Customers(String name, String county, String address, String dietaryRestrictions){
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

        public String getDietaryRestrictions(){
            return dietaryRestrictions;
    }

    class Restaurants implements Member {
        private String name;
        private String address;
        private String county;
        private String operatingHours;
        private String cuisineType;
        public Restaurants(String name, String address, String county, String operatingHours, String cuisineType){
            this.name = name;
            this.address = address;
            this.county = county;
            this.operatingHours = operatingHours;
            this.cuisineType = cuisineType;
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

        public String getOperatingHours(){
            return  operatingHours;
        }

        public String getCuisineType(){
            return  cuisineType;
        }
    }

    class Drivers implements Member {
        private String name;
        private String operatingCounty;
        private String address;

        public Drivers(String name, String operatingCounty, String address){
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
    interface memberFactory{
        Member createMember(String name, String address, String county, String infoHolder1, String infoHolder2);
    }

    class customerFactory implements memberFactory{
        @Override
        public Member createMember(String name, String address, String county, String dietaryRestrictions, String empty2) {
            return new Customers(name, address, county, dietaryRestrictions);
        }
    }

    class restaurantFactory implements memberFactory{
        @Override
        public Member createMember(String name, String address, String county, String operatingHours, String cuisineType) {
            return new Restaurants(name, address, county, operatingHours, cuisineType);
        }
    }

    class driverFactory implements memberFactory{
        @Override
        public Member createMember(String name, String address, String county, String empty1, String empty2) {
            return new Drivers(name, address, county);
        }
    }
        class orderManagementSystem{
            private static orderManagementSystem order;
            private List<OrderInfo> orderInfoList;

            private orderManagementSystem(){
                orderInfoList = new ArrayList<>();
            }

     //singleton design for ordermanagementsystem
     public orderManagementSystem getOrder(){
                if(order == null){
                    order = new orderManagementSystem();
                }
                return order;
            }
            public void controlOrder(OrderInfo orderInfo){
                orderInfoList.add(orderInfo);
                System.out.println("Ordered from" + orderInfo.getRestaurant().getName());
                Drivers driver = orderInfo.getDriver();
                System.out.println(driver.getName()+ " will deliver your order.");
                orderInfo.setOrderTime("Ordered at:"+ getCurrentTime());
                orderInfo.setPickupTime("Pickup time:" + getPickupTime());
                orderInfo.setDeliveryTime("Delivery time:" + getDeliveryTime());
            }

            private String getCurrentTime(){
                return "1:00PM";
            }
            private String getPickupTime(){
                return "1:15PM";
            }

            private String getDeliveryTime(){
                return  "1:45PM";
            }


        }

        class OrderInfo{
            private Customers customer;
            private Restaurants restaurant;
            private List<FoodItem> foodItems;
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


            public List<FoodItem> getFoodItems() {
                return foodItems;
            }

            public void setFoodItems(List<FoodItem> foodItems) {
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
        class CPPfooddelivery{ //Aaron: please implement methods that register the restaurants customers and drivers.
            private List<Restaurants> restaurants;
            private List<Customers> customers;
            private List<Drivers> drivers;

            public CPPfooddelivery(){
                this.restaurants = new ArrayList<>();
                this.customers = new ArrayList<>();
                this.drivers = new ArrayList<>();
            }
        }
    //facade pattern:Aaron  #interface name for decorator is FoodItem!


        class Main{
            public static void main(String[] args){

            }
        }
}

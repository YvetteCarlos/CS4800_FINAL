
    class orderManagementSystem{
        private static orderManagementSystem order;

        private orderManagementSystem(){
            //prevents instantiation
        }

        public static orderManagementSystem getOrder(){
            if(order == null){
                order = new orderManagementSystem();
            }
            return order;
        }


    }

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

    interface memberFactory{
        Member createMember();
    }

    class customerFactory implements memberFactory{

        @Override
        public Member createMember() {
            return null;
        }
    }

    class restaurantFactory implements memberFactory{

        @Override
        public Member createMember() {
            return null;
        }
    }

    class driverFacory implements memberFactory{

        @Override
        public Member createMember() {
            return null;
        }
    }

    //facade pattern:Aaron
    //decorator pattern:Aaron

}

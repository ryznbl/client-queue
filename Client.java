public class Client {
    //initialize variables
    private static int lastAssignedID = 0;
    private int id;
    private String firstName;
    private String lastName;
    private int yearOfBirth;
    private Request[] requests;
    private int arrivalTime;
    private int waitingTime;
    private int timeInQueue;
    private int serviceTime;
    private int departureTime;
    private int patience;
    private Gender gender;

    //setters and getters
    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    public Request[] getRequests() {
        return requests;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getWaitingTime() {
        return waitingTime;
    }
    public int getTimeInQueue() {
        return timeInQueue;
    }
    public int getServiceTime() {
        return serviceTime;
    }
    public int getDepartureTime() {
        return departureTime;
    }
    public int getPatience() {
        return patience;
    }
    public Gender getGender() {
        return gender;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
    public void setRequests(Request[] requests) {
        this.requests = requests;
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    public void setTimeInQueue(int timeInQueue) {
        this.timeInQueue = timeInQueue;
    }
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }
    public void setPatience(int patience) {
        this.patience = patience;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    //constructor
    public Client(String firstName, String lastName, int yearOfBirth, String gender, int arrivalTime, int patience, Request[] requests) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.requests = requests;
        this.patience = patience;
        if(QueueSystem.isCoffeeInWaitingArea()) { //check if we need to increase patience
            if((2023 - yearOfBirth) >= 18) {
                this.patience += 15;
            }
        }
        if (QueueSystem.isTvInWaitingArea()) { //check if we need to increase patience
            this.patience += 20;
        }
        this.gender = Gender.valueOf(gender);
        this.id = ++lastAssignedID; //increment based on how many users there are
        this.waitingTime = 0;
        this.timeInQueue = 0;
        this.serviceTime = 0;
        this.departureTime = 0;
        if(arrivalTime > 0) { //if it is greater than 0 then assign it, otherwise do not
            this.arrivalTime = arrivalTime;
        } else {
            this.arrivalTime = 1 + QueueSystem.getClock();
        }
    }

    //constructor
    public Client(String firstName, String lastName, int yearOfBirth, int patience, String gender, Request[] requests) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.patience = patience;
        this.gender = Gender.valueOf(gender);
        this.id = ++lastAssignedID; //increment based on how many users there are
        this.arrivalTime = 1 + QueueSystem.getClock(); //automatically assign this one
        this.waitingTime = 0;
        this.timeInQueue = 0;
        this.serviceTime = 0;
        this.departureTime = 0;
    }

    //constructor
    public Client(String firstName, String lastName, int yearOfBirth, String gender, int patience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.gender = Gender.valueOf(gender);
        this.patience = patience;
        this.id = ++lastAssignedID; //increment based on how many users there are
        this.arrivalTime = 1 + QueueSystem.getClock(); //automatically assign this one
        this.waitingTime = 0;
        this.timeInQueue = 0;
        this.serviceTime = 0;
        this.departureTime = 0;
    }

    public int estimateServiceLevel() {
        if (this.departureTime == 0) { //check first
            return -1;
        }
        int service = 10; //initialize before calculating
        service = getService(service, waitingTime);
        service = getService(service, timeInQueue);
        return service;
    }

    private int getService(int service, int value) { //separate method to make it more organized when calculating the estimated service level
        if(value > 4) {
            service --;
        }
        if(value > 6) {
            service --;
        }
        if(value > 8) {
            service --;
        }
        if(value > 10) {
            service --;
        }
        if(value > 12) {
            service --;
        }
        return service;
    }

    //useful for printing the servername
    private Queue servingQueue;
    public Queue getServingQueue() {
        return servingQueue;
    }
    public void setServingQueue(Queue servingQueue) {
        this.servingQueue = servingQueue;
    }

    //make information presentable to be printed
    public String toString() {
        String serverName = servingQueue != null ? servingQueue.getServerName() : "Not served yet";
        return "Client: " + lastName + ", " +
                firstName + "\n** Arrival Time : " +
                arrivalTime + "\n** Waiting Time : " +
                waitingTime + "\n** Time In Queue : " +
                timeInQueue + "\n** Service Time : " +
                serviceTime + "\n** Departure Time : " +
                departureTime + "\n** Served By Server: " +
                serverName + "\n** Service Level : " +
                estimateServiceLevel();
    }
}
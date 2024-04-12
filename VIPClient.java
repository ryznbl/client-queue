public class VIPClient extends Client implements Prioritizable {
    //initialize variables
    private int memberSince;
    private int priority;

    //setters and getters
    public int getMemberSince() {
        return memberSince;
    }
    public void setMemberSince(int memberSince) {
        this.memberSince = memberSince;
    }
    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
    @Override
    public int getPriority() {
        return priority;
    }

    //constructor
    public VIPClient(String firstName, String lastName, int birthYear, String gender, int arrivalTime, int patience, Request[] requests, int memberSince, int priority) {
        super(firstName, lastName, birthYear, gender, arrivalTime, patience,requests);
        this.memberSince = memberSince;
        this.priority = priority;
    }

    //to make the information presentable for printing
    @Override
    public String toString() {
        Queue servingQueue = getServingQueue();
        String serverName = servingQueue != null ? servingQueue.getServerName() : "Not served yet";
        return "Client: " + this.getLastName() +
                ", " + this.getFirstName() +
                "\n** Arrival Time : " + this.getArrivalTime() +
                "\n** Waiting Time : " + this.getWaitingTime() +
                "\n** Time In Queue : " + this.getTimeInQueue() +
                "\n** Service Time : " + this.getServiceTime() +
                "\n** Departure Time : " + this.getDepartureTime() +
                "\n** Served By Server: " + serverName +
                "\n** Service Level : " + this.estimateServiceLevel() +
                "\n** VIP since : " + this.getMemberSince() +
                "\n** priority : " + this.getPriority();
    }
}

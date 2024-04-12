public class Queue {
    //initialize variables
    private String serverName;
    private int queueSize;
    private Client clientBeingServed;
    private Request requestInProgress;
    private int processingStartTime;
    private Client[] clientsHistory;
    private Client[] clientsInQueue;

    //initialize getters and setters
    public String getServerName() {
        return serverName;
    }
    public int getQueueSize() {
        return queueSize;
    }
    public Client getClientBeingServed() {
        return clientBeingServed;
    }
    public Request getRequestInProgress() {
        return requestInProgress;
    }
    public int getProcessingStartTime() {
        return processingStartTime;
    }
    public Client[] getClientsHistory() {
        return clientsHistory;
    }
    public Client[] getClientsInQueue() {
        return clientsInQueue;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
    public void setClientBeingServed(Client clientBeingServed) {
        this.clientBeingServed = clientBeingServed;
    }
    public void setRequestInProgress(Request requestInProgress) {
        this.requestInProgress = requestInProgress;
    }
    public void setProcessingStartTime(int processingStartTime) {
        this.processingStartTime = processingStartTime;
    }
    public void setClientsHistory(Client[] clientsHistory) {
        this.clientsHistory = clientsHistory;
    }
    public void setClientsInQueue(Client[] clientsInQueue) {
        this.clientsInQueue = clientsInQueue;
    }

    private int clientsHistoryIndex;
    public void addToClientsHistory(Client client) { //method to add a client to clientsHistory
        if (clientsHistoryIndex < clientsHistory.length) { //make sure it is in the array
            clientsHistory[clientsHistoryIndex] = client;
            clientsHistoryIndex++; // Increment index for the next client
        } else {
            System.out.println("Clients history is full, unable to add more clients."); //exception
        }
    }
    public void addToClientsInQueue(Client client) { //method to add a client to the queue
        if(clientsInQueue != null) { //check if there is a value
            Client[] newClientsInQueue = new Client[clientsInQueue.length + 1]; //make a new array
            for (int i = 0; i < clientsInQueue.length; i++) { //for loop th at goes through clientsInQueue
                newClientsInQueue[i] = clientsInQueue[i]; //assign for each in array
            }
            newClientsInQueue[clientsInQueue.length] = client; //assign client to the end of the array
            clientsInQueue = newClientsInQueue; //replace arrays
        } else {
            clientsInQueue = new Client[1]; //otherwise make an array 1 space big and assign client to it
            clientsInQueue[0] = client;
        }
    }

    public Queue(String serverName, int queueSize) { //constructor
        this.serverName = serverName;
        this.queueSize = queueSize;
        this.clientsHistory = new Client[queueSize]; //assigns clientHistory with queueSize
        this.clientsHistoryIndex = 0; //initializes the index
    }

    public String toString() { //makes the arrays presentable
        StringBuilder output = new StringBuilder();
        output.append("[Queue:").append(serverName).append("]");
        if (clientBeingServed != null) { //check for errors
            output.append("[").append(String.format("%02d", clientBeingServed.getId())).append("]");
        }
        output.append("-----");
        if (clientsInQueue != null) { //check for errors
            for (Client client : clientsInQueue) {
                if (client != null) { //check for errors
                    output.append("[").append(String.format("%02d", client.getId())).append("]");
                }
            }
        }
        return output.toString();
    }
    public String toString(boolean showID) { //make arrays presentable
        StringBuilder output = new StringBuilder();
        output.append("[Queue:").append(serverName).append("]");
        if (clientBeingServed != null) { //check for errors
            if (showID) { //if true then print with id
                output.append("[").append(String.format("%02d", clientBeingServed.getId())).append("]");
            } else { //otherwise print with estimate
                output.append("[").append(String.format("%02d", clientBeingServed.estimateServiceLevel())).append("]");
            }
        }
        output.append("-----");
        if (clientsInQueue != null) { //check for errors
            for (Client client : clientsInQueue) {
                if (client != null) { //check for errors
                    if (showID) { //if true then print with id
                        output.append("[").append(String.format("%02d", client.getId())).append("]");
                    } else { //otherwise print with estimate
                        output.append("[").append(String.format("%02d", client.estimateServiceLevel())).append("]");
                    }
                }
            }
        }
        return output.toString();
    }
}

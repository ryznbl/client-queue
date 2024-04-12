public class QueueSystem {
    //initializing all the variables
    private static int clock; //clock is static because it is unchanging throughout the whole QueueSystem
    private int totalWaitingTime;
    private Client[] clientsWorld;
    private int totalClientsInSystem;
    private int waitingLineSize;
    private Client[] waitingLine;
    private static boolean tvInWaitingArea; //tv is static because it is unchanging throughout the whole QueueSystem
    private static boolean coffeeInWaitingArea; //coffee is static because it is unchanging throughout the whole QueueSystem
    private Queue[] queues;

    //getters and setters
    public static int getClock() {
        return clock;
    }
    public int getTotalWaitingTime() {
        return totalWaitingTime;
    }
    public Client[] getClientsWorld() {
        return clientsWorld;
    }
    public int getTotalClientsInSystem() {
        return totalClientsInSystem;
    }
    public int getWaitingLineSize() {
        return waitingLineSize;
    }
    public Client[] getWaitingLine() {
        return waitingLine;
    }
    public static boolean isTvInWaitingArea() {
        return tvInWaitingArea;
    }
    public Queue[] getQueues() {
        return queues;
    }
    public static boolean isCoffeeInWaitingArea() { return coffeeInWaitingArea; }
    public void setClock(int clock) {
        QueueSystem.clock = clock;
    }
    public void setTotalWaitingTime(int totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }
    public void setClientsWorld(Client[] clientsWorld) {
        this.clientsWorld = clientsWorld;
    }
    public void setTotalClientsInSystem(int totalClientsInSystem) {
        this.totalClientsInSystem = totalClientsInSystem;
    }
    public void setWaitingLineSize(int waitingLineSize) {
        this.waitingLineSize = waitingLineSize;
    }
    public void setWaitingLine(Client[] waitingLine) {
        this.waitingLine = waitingLine;
    }
    public void setTvInWaitingArea(boolean tvInWaitingArea) {
        QueueSystem.tvInWaitingArea = tvInWaitingArea;
    }
    public void setQueues(Queue[] queues) {
        this.queues = queues;
    }
    public void setCoffeeInWaitingArea(boolean coffeeInWaitingArea) {
        QueueSystem.coffeeInWaitingArea = coffeeInWaitingArea;
    }

    //constructor for QueueSystem
    public QueueSystem(int waitingLineSize, boolean tvInWaitingArea, boolean coffeeInWaitingArea) {
        this.waitingLineSize = waitingLineSize;
        QueueSystem.tvInWaitingArea = tvInWaitingArea; //if this is true, increase patience by 20
        QueueSystem.coffeeInWaitingArea = coffeeInWaitingArea; //if this is true, increase patience of adult by 15
        clock = 0;
        this.waitingLine = new Client[waitingLineSize];
        this.totalClientsInSystem = 0;
        this.clientsWorld = new Client[20]; //temporary size
        this.totalWaitingTime = 0;
    }

    //this will be called everytime a client is taken from the waitingLine
    public void removeClientFromWaitingLine(Client client) {
        int waitingTime = clock - client.getArrivalTime();
        totalWaitingTime = totalWaitingTime + waitingTime; //first things first is to increase the totalWaitingTime variable

        for (int i = 0; i < waitingLine.length; i++) { //going through the waitingLine, finding where the client is, and removing them
            if (waitingLine[i] == client) {
                waitingLine[i] = null;
                for (int j = i; j < waitingLine.length - 1; j++) {
                    waitingLine[j] = waitingLine[j + 1];
                }
                waitingLine[waitingLine.length - 1] = null;
                waitingLineSize--;
                break;
            }
        }
    }

    public void step1() {
        for (Queue queue : queues) { //loop through all the queues
            Client clientBeingServed = queue.getClientBeingServed(); //assign the client to a variable
            if (clientBeingServed != null) { //check for errors
                clientBeingServed.setServingQueue(queue); //assign queue for client
                Request requestInProgress = queue.getRequestInProgress(); //get the request currently in progress
                if (requestInProgress != null) { //check for errors
                    if (requestInProgress.getStartTime() == -1) { //check if the request has been started yet
                        requestInProgress.setStartTime(clock);
                    }
                    int startTime = requestInProgress.getStartTime(); //assign startTime variable
                    int processingTime = requestInProgress.calculateProcessingTime(); //get processingTime of request
                    if (clock >= startTime) {
                        requestInProgress.setStatus(Status.IN_PROGRESS); //Request is in progress
                        //request in progress, update state based on progress?
                    }
                    if (clock >= (startTime + processingTime)) { //done
                        requestInProgress.setStatus(Status.PROCESSED); //request is processed
                        requestInProgress.setEndTime(clock); //set end time of request

                        Request[] currentRequests = clientBeingServed.getRequests(); //get list of requests
                        if (currentRequests.length > 1) { //if there is any requests left, make that the clients new request
                            Request[] updatedRequests = new Request[currentRequests.length - 1];
                            int j = 0;
                            for (int i = 0; i < currentRequests.length; i++) {
                                if (currentRequests[i] != requestInProgress) {
                                    updatedRequests[j++] = currentRequests[i];
                                }
                            }
                            clientBeingServed.setRequests(updatedRequests);
                        } else {
                            int waitingTime = clock - clientBeingServed.getArrivalTime();
                            int serviceTime = clock - clientBeingServed.getArrivalTime() - clientBeingServed.getTimeInQueue();
                            int timeInQueue = waitingTime + serviceTime;
                            clientBeingServed.setWaitingTime(waitingTime); //set variables for the client as they leave
                            clientBeingServed.setServiceTime(serviceTime);
                            clientBeingServed.setTimeInQueue(timeInQueue);
                            clientBeingServed.setDepartureTime(clock);
                            queue.addToClientsHistory(clientBeingServed); //add client to the history

                            clientBeingServed.setServingQueue(null); //clear out the serving queue for the client
                            queue.setClientBeingServed(null); //empty clientBeingServed Variable
                        }
                    }
                } //something here if client has no requests
            }//there has to be something here if there is no clients in queue
        }
    }
    public void step2() {
        for (Queue queue : queues) { //loop through all the queues
            Client[] clientsInQueue = queue.getClientsInQueue();
            if(clientsInQueue != null) {
                if (clientsInQueue.length < queue.getQueueSize()) { //check for free spaces
                    if (clientsInQueue.length > 0) { //check for errors
                        Client nextClient = waitingLine[0]; //assign first client from waiting line to nextClient
                        if (nextClient != null) { //check for errors
                            queue.addToClientsInQueue(nextClient); //add client to queue

                            removeClientFromWaitingLine(nextClient); //remove the client from the waiting line
                        }
                    }
                }
            }
        }
    }
    public void step3() { //this has to be going through the queues as well
        for (Client client : waitingLine) { //go through the waitingLine
            if (client != null) { //check for errors
                if (client.getServingQueue() == null) { // Check if client is still in waiting line
                    client.setPatience(client.getPatience() - 1); //reduce patience by 1
                    if (client.getPatience() < 0) { //if the patience is negative
                        removeClientFromWaitingLine(client); //remove them from the waitingLine
                    }
                }
            }
        }
    }
    public void step4() {
        for (Client client : waitingLine) { //go through waitingLine
            if(client != null) { //check for errors
                Queue selectedQueue = findAvailableQueue(); //
                if (selectedQueue != null) {
                    selectedQueue.addToClientsInQueue(client);
                    removeClientFromWaitingLine(client);
                }
            }
        }
    }
    public Queue findAvailableQueue() {
        Queue selectedQueue = null;
        int availableSpots = -1;

        for (Queue queue : queues) {
            if(queue.getClientsInQueue() != null) {
                if (queue.getClientsInQueue().length < queue.getQueueSize()) {
                    if (queue.getQueueSize() - queue.getClientsInQueue().length > availableSpots) {
                        selectedQueue = queue;
                        availableSpots = queue.getQueueSize() - queue.getClientsInQueue().length;
                    }
                }
            } else {
                selectedQueue = queue;
                availableSpots = queue.getQueueSize();
            }
        }

        return selectedQueue;
    }
    public void increaseTime() { //this is called every second
        clock++; //firstly increment the clock

        if(clientsWorld != null) { //check for errors
            int index = 0;
            for (int i = 0; i < clientsWorld.length; i++) { //go through clientsWorld and add to waitingLine based on FIFO
                if (clientsWorld[i].getArrivalTime() == clock) { //check all clients in clientsWorld, and if it has the arrival time the same as clock, then put them into the waitingLine
                    //find next spot in waiting line
                    waitingLine[index] = clientsWorld[i];
                    index++;
                    //remove person from clients world
                }
            }
        }

        step1(); //call helper methods to make increaseTime more neat
        step2();
        step3();
        step4();
    }

    public void increaseTime(int time) {
        for (int i = 0; i < time; i++) { //run increaseTime() based on the time
            increaseTime();
        }
    }
    public Client[] getClientsBeingServed() { //method that gets clients being served in the queue
        //loop through the queues and get the client being served in each queue?
        int numClientsBeingServed = 0; //initialize a variable
        for (Queue queue: queues) { //loop through queues
            if (queue.getClientBeingServed() != null) { //check for queues that aren't null
                numClientsBeingServed++; //increment since there is a client being served
            }
        }
        Client[] clientsBeingServed = new Client[numClientsBeingServed]; //create array based on this
        int currentIndex = 0; //create an index

        for (Queue queue: queues) { //loop through queues
            if (queue.getClientBeingServed() != null) { //check if it's not null
                clientsBeingServed[currentIndex] = queue.getClientBeingServed(); //assign to the array made based on index
                currentIndex++; //increment index
            }
        }
        return clientsBeingServed;
    }

    public String toString() { //make System printable
        StringBuilder output = new StringBuilder();
        // Print Waiting Line
        output.append("[WaitingLine]-");
        for (Client client : waitingLine) {
            if (client != null) {
                output.append(String.format("[%02d]", client.estimateServiceLevel()));
            }
        }
        output.append("\n---\n");
        // Print Queues
        for (int i = 0; i < queues.length; i++) {
            output.append("[Queue:").append(i + 1).append("]");
            if (queues[i].getClientBeingServed() != null) {
                Client client = queues[i].getClientBeingServed();
                if (client != null) {
                    output.append(String.format("[%02d]", client.estimateServiceLevel()));
                }
            } else {
                output.append("-----");
            }
            output.append("[");
            for (Client client : queues[i].getClientsHistory()) {
                if (client != null) {
                    output.append(String.format("%02d", client.estimateServiceLevel()));
                }
            }
            output.append("]\n");
        }

        return output.toString();
    }
    public String toString(boolean showID) { //make System printable, giving the option to print while showing the ID
        if (showID) {
            StringBuilder output = new StringBuilder();
            output.append("[WaitingLine]-");
            for (Client client : waitingLine) {
                if (client != null) {
                    output.append("[").append(String.format("%02d", client.getId())).append("]");
                }
            }
            output.append("\n---\n");

            // Print Queues with IDs
            for (int i = 0; i < queues.length; i++) {
                output.append("[Queue:").append(i + 1).append("]");
                if (queues[i].getClientBeingServed() != null) {
                    Client client = queues[i].getClientBeingServed();
                    if (client != null) {
                        output.append("[").append(String.format("%02d", client.getId())).append("]");
                    }
                } else {
                    output.append("-----");
                }
                output.append("[");
                for (Client client : queues[i].getClientsHistory()) {
                    if (client != null) {
                        output.append("[").append(String.format("%02d", client.getId())).append("]");
                    }
                }
                output.append("]\n");
            }
            return output.toString();
        } else { //otherwise just do the normal toString method
            return toString();
        }
    }
}

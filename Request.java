abstract class Request implements Prioritizable {
    //initialize variables
    private String description;
    private int priority;
    private int difficulty;
    private int factor;
    private int startTime;
    private int endTime;
    private Status status;

    //setters and getters
    public String getDescription() {
        return description;
    }
    @Override
    public int getPriority() {
        return priority;
    }
    public int getDifficulty() {
        return difficulty;
    }
    public int getFactor() {
        return factor;
    }
    public int getStartTime() {
        return startTime;
    }
    public int getEndTime() {
        return endTime;
    }
    public Status getStatus() {
        return status;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    public void setFactor(int factor) {
        this.factor = factor;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    //used in the ReturningItems, BuyingItems, and InformationRequest classes
    public abstract int calculateProcessingTime();
}

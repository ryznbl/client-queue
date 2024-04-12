public class ReturningItems extends Request{
    //initialize variable
    private String[] itemsToReturn;

    //getter and setter
    public String[] getItemsToReturn() {
        return itemsToReturn;
    }
    public void setItemsToReturn(String[] itemsToReturn) {
        this.itemsToReturn = itemsToReturn;
    }

    //constructor
    public ReturningItems(String description, int priority, int difficulty, String[] itemsToReturn) {
        setDescription(description);
        setPriority(priority);
        setDifficulty(difficulty);
        this.itemsToReturn = itemsToReturn;
        setFactor(3);
        setStatus(Status.NEW);
        //all other fields have default values
    }

    @Override
    public int calculateProcessingTime() {
        if (itemsToReturn == null) { //check for errors
            return 0;
        } else {
            return getDifficulty() * getFactor() * itemsToReturn.length; //based on formula
        }
    }
}

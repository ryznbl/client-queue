public class BuyingProducts extends Request {
    //initialize variable
    private String[] itemsToBuy;
    //getter and setter
    public String[] getItemsToBuy() {
        return itemsToBuy;
    }
    public void setItemsToBuy(String[] itemsToBuy) {
        this.itemsToBuy = itemsToBuy;
    }

    public BuyingProducts(String description, int priority, int difficulty, String[] itemsToBuy) { //constructor
        setDescription(description);
        setPriority(priority);
        setDifficulty(difficulty);
        this.itemsToBuy = itemsToBuy;
        setFactor(2); //set to 2
        setStatus(Status.NEW); //set to new
        //all other fields have default
    }

    @Override
    public int calculateProcessingTime() {
        if(itemsToBuy == null) { //check for errors
            return 0;
        } else {
            return getDifficulty() * getFactor() * itemsToBuy.length; //formula for the processing time
        }
    }
}

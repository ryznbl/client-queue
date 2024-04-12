public class InformationRequest extends Request{
    //initialize array variable
    private String[] questions;

    //getter and setter
    public String[] getQuestions() {
        return questions;
    }
    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    //constructor
    public InformationRequest(String description, int priority, int difficulty, String[] questions) {
        setDescription(description);
        setPriority(priority);
        setDifficulty(difficulty);
        this.questions = questions;
        setFactor(1);
        setStatus(Status.NEW);
    }

    @Override
    public int calculateProcessingTime() {
        if (questions == null) { //check for errors
            return 0;
        } else {
            return getDifficulty() * getFactor() * questions.length; //based on formula
        }
    }
}

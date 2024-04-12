public class Main {
    public static void main(String[] args) {
        QueueSystem Dentist = new QueueSystem(4, false, true);

        Queue Queue1 = new Queue("Server A", 1);
        //Queue Queue2 = new Queue("Server B", 4);
        //Queue Queue3 = new Queue("Server C", 1);
        VIPQueue VIPQueue4 = new VIPQueue("Server Important", 1);
        Queue[] queues = {Queue1, VIPQueue4};
        Dentist.setQueues(queues); //is this allowed?

        String[] newString = {"one", "two", "thre", "thoufr"};
        String[] newString2 = {"one", "two", "thre", "thoufr"};
        String[] newString3 = {"one", "two", "thre", "thoufr"};
        InformationRequest Help1 = new InformationRequest("Help", 2, 1, newString);
        InformationRequest Help2 = new InformationRequest("Help2", 3, 2, newString2);
        InformationRequest Help3 = new InformationRequest("Help3", 1, 5, newString3);

        String[] returnString = {"apple", "book", "card"};
        String[] returnString2 = {"apple", "book", "card"};
        String[] returnString3 = {"apple", "book", "card"};
        ReturningItems Return1 = new ReturningItems("Returns", 2, 1, returnString);
        ReturningItems Return2 = new ReturningItems("Returns2", 2, 3, returnString2);
        ReturningItems Return3 = new ReturningItems("Returns3", 4, 2, returnString3);

        String[] buyString = {"asdasdxczx", "book", "card"};
        String[] buyString2 = {"apple", "asdaz", "card"};
        String[] buyString3 = {"apple", "as", "asd"};
        BuyingProducts buy1 = new BuyingProducts("buy", 1, 1, buyString);
        BuyingProducts buy2 = new BuyingProducts("buy2", 2, 3, buyString2);
        BuyingProducts buy3 = new BuyingProducts("buy3", 1, 2, buyString3);

        Request[] leilaRequests = {buy1, buy2, buy3};
        Request[] ramiRequests = {Return1, Return2, Return3};
        Request[] reemaRequests = {Help1, Help2, Help3};

        Client Yazan = new Client("Yazan", "Alshoroogi", 2002, "MALE", 4);
        Client Leila = new Client("Leila", "Alshsoor", 2006, 5, "FEMALE", leilaRequests);
        Client Rami = new Client("Rami", "jlkaser", 2005, 8, "MALE", ramiRequests);
        VIPClient Reema = new VIPClient("Reema", "Alnizami", 1973, "FEMALE", 3, 10, reemaRequests, 1990, 3);

        Client[] clientWorld = {Yazan, Leila, Rami, Reema};
        Dentist.setClientsWorld(clientWorld);
        Dentist.increaseTime(1);

        System.out.println(Dentist);
        //System.out.println(Yazan);
        //System.out.println(Leila);
    }
}

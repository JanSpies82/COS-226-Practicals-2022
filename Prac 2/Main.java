public class Main {
    public static void main(String[] args) {
	    Transport[] buses = new Transport[5];

        Venue destination = new Venue();
        Filter filter = new Filter();
        Bakery bakery = new Bakery();

        for(int i = 0; i < 5; i++)
            buses[i] = new Transport(destination, filter, bakery);

        for(Transport bus : buses)
            bus.start();
    }
}

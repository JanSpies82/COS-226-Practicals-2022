// import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        int size = 5;
	    Transport[] buses = new Transport[size];

        Venue destination = new Venue();
        Filter filter = new Filter(size);
        Bakery bakery = new Bakery();

        for(int i = 0; i < size; i++)
            buses[i] = new Transport(destination, filter, bakery);


        for(Transport bus : buses)
            bus.start();

        // HashMap<String, Integer> r = new HashMap();
        // r.put("a", 1);
        // r.put("b", 2);
        // for (String s : r.keySet()) {
        //     System.out.println(s + ": " + r.get(s));
        // }
        // System.out.println(r.containsKey("a"));
        // System.out.println(r.containsKey("c"));

    }
}

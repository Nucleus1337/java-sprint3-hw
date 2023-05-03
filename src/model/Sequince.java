package model;

public class Sequince {
    private static long id = 1;
    public static long getNextId() {
        return id++;
    }
}

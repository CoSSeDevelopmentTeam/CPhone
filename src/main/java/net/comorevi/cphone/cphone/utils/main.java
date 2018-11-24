package net.comorevi.cphone.cphone.utils;

public class main {

    public static void main(String[] args) {
        System.out.println(StringLoader.loadString(main.class.getClassLoader().getResourceAsStream("strings-ja.xml")));
    }
}

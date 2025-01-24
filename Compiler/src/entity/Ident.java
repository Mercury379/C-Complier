package entity;

import compiler.Main;

public class Ident {
    public String name;
    public String type;
    public int val;
    public int level;
    public int adr;
    public int size;

    public Ident(String name) {
        this.name = name;
    }

    public static void enter(String name, String type, int adr){
        for(int i = 0; i< Main.identTable.size(); i++){
            if(Main.identTable.get(i).name.equals(name)){
                Main.identTable.get(i).type=type;
                Main.identTable.get(i).adr=adr;
            }
        }
    }
    public static void generate(String name){
        Main.identTable.add(new Ident(name));
    }

    public static int getValue(String name){
        for(int i = 0; i< Main.identTable.size(); i++){
            if(Main.identTable.get(i).name.equals(name)){
                return Main.identTable.get(i).val;
            }
        }
        return 0;
    }
    public static void setValue(String name,int val){
        for(int i = 0; i< Main.identTable.size(); i++){
            if(Main.identTable.get(i).name.equals(name)){
                Main.identTable.get(i).val=val;
            }
        }
    }
    public static Boolean isInTable(String name){
        for(int i = 0; i< Main.identTable.size(); i++){
            if(Main.identTable.get(i).name.equals(name)){
                return true;
            }
        }
        return false;
    }
}

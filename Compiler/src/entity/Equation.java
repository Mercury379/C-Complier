package entity;

import compiler.Main;

public class Equation {
    public String operation;
    public String opnum1;
    public String opnum2;
    public String tarnum;

    public static void generate(String operation, String opnum1, String opnum2, String tarnum){
        Equation e=new Equation();
        e.operation=operation;
        e.opnum1=opnum1;
        e.opnum2=opnum2;
        e.tarnum=tarnum;
        Main.equationList.add(e);
    }
    public static int getMaxnum(){
        int num=-1;
        for(int i = 0; i< Main.equationList.size(); i++){
            if(Main.equationList.get(i).tarnum.contains("t")){
                int m=Integer.valueOf(Main.equationList.get(i).tarnum.substring(1));
                if(m>num){
                    num=m;
                }
            }
        }
        return num+1;
    }
    public static int getEquationsSize(){
        return Main.equationList.size();
    }
    public static void setEquations(String operation,int loc,String value){
        int len= Main.equationList.size();
        for(int i=0;i<len;i++){
            if(Main.equationList.get(len-1-i).operation.equals(operation)){
                if(loc==1){
                    Main.equationList.get(len-1-i).opnum1=value;
                }else if(loc==2){
                    Main.equationList.get(len-1-i).opnum2=value;
                }else{
                    Main.equationList.get(len-1-i).tarnum=value;
                }
                break;
            }
        }
    }
}

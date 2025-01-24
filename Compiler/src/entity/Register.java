package entity;

import compiler.TargetCodeGenerate;

public class Register {
    public String name;
    public int status;
    public String val;
    public String otherVal;
    public Register(String name){
        this.name=name;
        status=0;
    }
    public static Register getEmptyRegister(){
        for(int i = 0; i< TargetCodeGenerate.registerList.size(); i++){
            if(TargetCodeGenerate.registerList.get(i).status==0){
                return TargetCodeGenerate.registerList.get(i);
            }
        }
        return TargetCodeGenerate.registerList.get(0);
    }
    public static void useRegister(String name,String val){
        for(int i = 0; i< TargetCodeGenerate.registerList.size(); i++){
            if(TargetCodeGenerate.registerList.get(i).name.equals(name)){
                TargetCodeGenerate.registerList.get(i).status=1;
                TargetCodeGenerate.registerList.get(i).val=val;
            }
        }
    }
    public static String getByVal(String val){
        for(int i = 0; i< TargetCodeGenerate.registerList.size(); i++){
            if(TargetCodeGenerate.registerList.get(i).val!=null&& TargetCodeGenerate.registerList.get(i).val.equals(val)){
                return TargetCodeGenerate.registerList.get(i).name;
            }
        }
        return val;
    }
    public static void releaseRegister(String name){
        for(int i = 0; i< TargetCodeGenerate.registerList.size(); i++){
            if(TargetCodeGenerate.registerList.get(i).name.equals(name)){
                TargetCodeGenerate.registerList.get(i).status=0;
                TargetCodeGenerate.registerList.get(i).val=null;
            }
        }
    }
    public static void setOterVal(String name,String otherVal){
        for(int i = 0; i< TargetCodeGenerate.registerList.size(); i++){
            if(TargetCodeGenerate.registerList.get(i).name.equals(name)){
                TargetCodeGenerate.registerList.get(i).otherVal=otherVal;
            }
        }
    }
    public static String getOtherVal(String name){
        for(int i = 0; i< TargetCodeGenerate.registerList.size(); i++){
            if(TargetCodeGenerate.registerList.get(i).name.equals(name)){
                return TargetCodeGenerate.registerList.get(i).otherVal;
            }
        }
        return null;
    }
}

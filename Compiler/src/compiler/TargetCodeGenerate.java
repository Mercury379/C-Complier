package compiler;

import compiler.Main;
import entity.Equation;
import entity.Ident;
import entity.Register;
import entity.Target;

import java.util.ArrayList;
import java.util.List;

public class TargetCodeGenerate {
    public static List<Register> registerList;
    public static void initRegister(){
        registerList=new ArrayList<>();
        registerList.add(new Register("AL"));
        registerList.get(0).status=1;
        registerList.add(new Register("BL"));
        registerList.add(new Register("BH"));
        registerList.add(new Register("CL"));
        registerList.add(new Register("CH"));
        registerList.add(new Register("DH"));
    }
    public static void setDatasSegment(){
        if(!Main.identTable.isEmpty()){
            Main.targetList.add(new Target(-1,"DATAS SEGMENT\n"));
            for(int i=0;i<Main.identTable.size();i++){
                Main.targetList.add(new Target(-1,"    "+Main.identTable.get(i).name+"    DB    "+0+"\n"));
            }
            Main.targetList.add(new Target(-1,"DATAS ENDS\n"));
        }
    }
    public static void setCodeSegment(){
        Main.targetList.add(new Target(-1,"CODES SEGMENT\n"));
        Main.targetList.add(new Target(-1,"    ASSUME CS:CODES,DS:DATAS\n"));
        Main.targetList.add(new Target(-1,"START:\n"));
        Main.targetList.add(new Target(-1,"    MOV AX,DATAS\n" + "    MOV DS,AX\n"));
        getTarget();
        Main.targetList.add(new Target(-1,"    MOV AH,4CH\n" +
                "    INT 21H\n" +
                "CODES ENDS\n" +
                "    END START"));
    }
    public static void getTarget(){
        for(int i=0;i<Main.equationList.size();i++){
            Equation e=Main.equationList.get(i);
            if(e.operation.equals("=")){
                Main.targetList.add(new Target(i,"    MOV " + e.tarnum + "," + Register.getByVal(e.opnum1) + "\n"));
                Register.releaseRegister(Register.getByVal(e.opnum1));
            }else if(e.operation.equals("printf")) {
                Main.targetList.add(new Target(i,"    MOV AH,02H\n" +
                        "    MOV DL," + e.opnum1 + "\n" +"    ADD DL,30H\n"+
                        "    INT 21H\n"));
            }else if(e.operation.equals("+")){
                if(e.opnum1.contains("t")||e.opnum2.contains("t")){
                    Main.targetList.add(new Target(i,"    ADD "+Register.getByVal(e.opnum1)+","+Register.getByVal(e.opnum2)+"\n"));
                    Register.useRegister(Register.getByVal(e.opnum1),e.tarnum);
                }else {
                    Register r=Register.getEmptyRegister();
                    Register.useRegister(r.name,e.tarnum);
                    Main.targetList.add(new Target(i,"    MOV "+r.name+","+e.opnum1+"\n"));
                    Main.targetList.add(new Target(i,"    ADD "+r.name+","+e.opnum2+"\n"));
                }
            }
            else if(e.operation.equals("-")){
                if(e.opnum1.contains("t")||e.opnum2.contains("t")){
                    Main.targetList.add(new Target(i,"    SUB "+Register.getByVal(e.opnum1)+","+Register.getByVal(e.opnum2)+"\n"));
                    Register.useRegister(Register.getByVal(e.opnum1),e.tarnum);
                }else {
                    Register r=Register.getEmptyRegister();
                    Register.useRegister(r.name,e.tarnum);
                    Main.targetList.add(new Target(i,"    MOV "+r.name+","+e.opnum1+"\n"));
                    Main.targetList.add(new Target(i,"    SUB "+r.name+","+e.opnum2+"\n"));
                }
            } else if (e.operation.equals("+=")) {
                Main.targetList.add(new Target(i,"    ADD " + e.tarnum + "," + Register.getByVal(e.opnum1) + "\n"));
                Register.releaseRegister(Register.getByVal(e.opnum1));
            }else if(e.operation.equals("-=")){
                Main.targetList.add(new Target(i,"    SUB " + e.tarnum + "," + Register.getByVal(e.opnum1) + "\n"));
                Register.releaseRegister(Register.getByVal(e.opnum1));
            } else if (e.operation.equals("*")) {
                Main.targetList.add(new Target(i,"    MOV AL," + Register.getByVal(e.opnum1) + "\n"));
                Register.releaseRegister(Register.getByVal(e.opnum1));
                Register r;
                if(e.opnum2.contains("t")|| Ident.isInTable(e.opnum2)) {
                    Main.targetList.add(new Target(i,"    MUL " + Register.getByVal(e.opnum2) + "\n"));
                    r = Register.getEmptyRegister();
                    Register.useRegister(r.name, e.tarnum);
                    Main.targetList.add(new Target(i,"    MOV " + r.name + ",AL\n"));
                }else{
                    r = Register.getEmptyRegister();
                    Register.useRegister(r.name,e.opnum2);
                    Main.targetList.add(new Target(i,"    MOV "+r.name+","+e.opnum2+"\n"));
                    Main.targetList.add(new Target(i,"    MUL " + Register.getByVal(e.opnum2) + "\n"));
                    r = Register.getEmptyRegister();
                    Register.useRegister(r.name, e.tarnum);
                    Main.targetList.add(new Target(i,"    MOV " + r.name + ",AL\n"));
                }
                Register.releaseRegister(Register.getByVal(e.opnum2));
            }else if(e.operation.equals("/")) {
                Main.targetList.add(new Target(i,"    MOV AL," + Register.getByVal(e.opnum1) + "\n"));
                Main.targetList.add(new Target(i,"    CBW\n"));
                Register.releaseRegister(Register.getByVal(e.opnum1));
                Register r;
                if (e.opnum2.contains("t") || Ident.isInTable(e.opnum2)) {
                    Main.targetList.add(new Target(i,"    DIV " + Register.getByVal(e.opnum2) + "\n"));
                    r = Register.getEmptyRegister();
                    Register.useRegister(r.name, e.tarnum);
                    Main.targetList.add(new Target(i,"    MOV " + r.name + ",AL\n"));
                } else {
                    r = Register.getEmptyRegister();
                    Register.useRegister(r.name, e.opnum2);
                    Main.targetList.add(new Target(i,"    MOV " + r.name + "," + e.opnum2 + "\n"));
                    Main.targetList.add(new Target(i,"    DIV " + Register.getByVal(e.opnum2) + "\n"));
                    r = Register.getEmptyRegister();
                    Register.useRegister(r.name, e.tarnum);
                    Main.targetList.add(new Target(i,"    MOV " + r.name + ",AL\n"));
                }
                Register.releaseRegister(Register.getByVal(e.opnum2));
            }else if(e.operation.equals(">")||e.operation.equals("<")||e.operation.equals(">=")||e.operation.equals("<=")||e.operation.equals("==")||e.operation.equals("!=")){
                if(e.opnum1.contains("t")||e.opnum2.contains("t")){
                    Main.targetList.add(new Target(i,"    CMP "+Register.getByVal(e.opnum1)+","+Register.getByVal(e.opnum2)+"\n"));
                    Register.useRegister(Register.getByVal(e.opnum1),e.tarnum);
                    Register.setOterVal(Register.getByVal(e.opnum1),">");
                }else {
                    Register r=Register.getEmptyRegister();
                    Register.useRegister(r.name,e.tarnum);
                    Main.targetList.add(new Target(i,"    MOV "+r.name+","+e.opnum1+"\n"));
                    Main.targetList.add(new Target(i,"    CMP "+r.name+","+e.opnum2+"\n"));
                    Register.useRegister(r.name,e.tarnum);
                    Register.setOterVal(r.name,e.operation);
                }
            }else if(e.operation.equals("if")){
                String logic=Register.getOtherVal(Register.getByVal(e.opnum1));
                if(logic.equals(">")){
                    Main.targetList.add(new Target(i,"    JBE "+e.opnum2+" "+e.tarnum+"\n"));
                }else if(logic.equals(">=")){
                    Main.targetList.add(new Target(i,"    JB "+e.opnum2+" "+e.tarnum+"\n"));
                }else if(logic.equals("<")){
                    Main.targetList.add(new Target(i,"    JAE "+e.opnum2+" "+e.tarnum+"\n"));
                }else if(logic.equals("<=")){
                    Main.targetList.add(new Target(i,"    JA "+e.opnum2+" "+e.tarnum+"\n"));
                }else if(logic.equals("==")){
                    Main.targetList.add(new Target(i,"    JNZ "+e.opnum2+" "+e.tarnum+"\n"));
                }else{
                    Main.targetList.add(new Target(i,"    JZ "+e.opnum2+" "+e.tarnum+"\n"));
                }

            }
        }
        setCodeReBack();
    }
    public static void setCodeReBack(){
        for(int i=0;i<Main.targetList.size();i++){
            Target target=Main.targetList.get(i);
            if(target.target.contains("J")&&target.equationId!=-2){
                String[] label=target.target.trim().split(" ");
                int id1=Integer.parseInt(label[1]);
                int id2=Integer.parseInt(label[2]);
                int indexStart=getTargetLocStart(id1);
                int indexEnd=getTargetLocStart(id2);
                String l="L"+getMaxLabel();
                Main.targetList.add(indexStart,new Target(-2,"L"+getMaxLabel()+":\n"));
                Main.targetList.get(i).target="    "+label[0]+" "+l+"\n";
                l="L"+getMaxLabel();

                Main.targetList.add(indexEnd+1,new Target(-2,l+":"+"\n"));
                Main.targetList.add(indexStart,new Target(-2,"    JMP "+l+"\n"));
            }
        }
    }
    public static int getMaxLabel(){
        int max=-1;
        for(int i=0;i<Main.targetList.size();i++){
            if(Main.targetList.get(i).target.contains(":")&&Main.targetList.get(i).equationId!=-1){
                String label=Main.targetList.get(i).target.trim();
                max=Integer.parseInt(label.substring(1,label.length()-1));
            }
        }
        return max+1;
    }
    public static int getTargetLocStart(int id){
        for(int i=0;i<Main.targetList.size();i++){
            if(Main.targetList.get(i).equationId==id){
                return i;
            }
        }
        return Main.targetList.size();
    }
//    public static int getTargetLocEnd(int id){
//        int len=0;
//        for(int i=0;i<compiler.Main.targetList.size();i++){
//            if(compiler.Main.targetList.get(i).equationId==id){
//                len++;
//            }
//        }
//        return getTargetLocStart(id)+len;
//    }
}

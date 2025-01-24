package compiler;

import entity.Equation;
import entity.Ident;
import entity.Symbol;

import java.util.ArrayList;
import java.util.List;

public class IntermediateCodeGernerate {
    public static Symbol actionDefine(int Grammar, List<Symbol> source, Symbol target){
        switch (Grammar){
            case 0:
                break;
            case 1:
                List<Ident> identTable= Main.identTable;
                for(int i=0;i<source.get(0).lists.size();i++){
                    Ident.enter((String)source.get(0).lists.get(i),source.get(1).name,0);
                }
                break;
            case 2:
                target.val=source.get(0).val;
                Ident.generate((String) target.val);
                target.lists=new ArrayList<>();
                target.lists.add(target.val);
                for(int i=0;i<source.get(2).lists.size();i++){
                    target.lists.add(source.get(2).lists.get(i));
                }
                break;
            case 3:
                target.val=source.get(0).val;
                target.lists=new ArrayList<>();
                target.lists.add(source.get(0).val);
                Ident.generate((String)target.val);
                break;
            case 4:
                break;
            case 5:
                target.val=source.get(0).name;
                break;
            default:

        }
        return target;
    }
    public static Symbol actionAssign(int grammar, List<Symbol> source,Symbol target){
        String temp;
        switch (grammar){
            case 0:
                break;
            case 1:
//                if(source.get(1).name.contains("+")){
//
//                }
                Equation.generate(source.get(1).name,(String)source.get(0).val,"null",source.get(2).name);
                break;
            case 2:
                temp="t"+Equation.getMaxnum();
                Equation.generate(source.get(1).name,(String)source.get(2).val,(String)source.get(0).val,temp);
                target.val=temp;
                break;
            case 3:
                target.val=source.get(0).val;
                break;
            case 4:
                temp="t"+Equation.getMaxnum();
                Equation.generate(source.get(1).name,(String)source.get(2).val,(String)source.get(0).val,temp);
                target.val=temp;
                break;
            case 5:
                target.val=source.get(0).val;
                break;
            case 6:
                target.val=source.get(1).val;
                break;
            case 7:
//                if(source.get(0).id==0){
//                    target.val = entity.Ident.getValue(source.get(0).name);
//                }else {
//                    target.val = source.get(0).name;
//                }
                target.val = source.get(0).name;
                break;
            default:

        }
        return target;
    }
    public static Symbol actionBool(int grammar, List<Symbol> source,Symbol target){
        String temp;
        switch (grammar){
            case 0:
                target.val=source.get(0).val;
                break;
            case 1:
                temp="t"+Equation.getMaxnum();
                Equation.generate(source.get(1).name,(String)source.get(2).val,(String)source.get(0).val,temp);
                target.val=temp;
                break;
            case 2:
                temp="t"+Equation.getMaxnum();
                Equation.generate(source.get(1).name,(String)source.get(0).val,"null",temp);
                target.val=temp;
                break;
            case 3:
                target.val=source.get(0).val;
                break;
            case 4:
                target.val=source.get(0).val;
                break;
            case 5:
                temp="t"+Equation.getMaxnum();
                Equation.generate(source.get(1).name,(String)source.get(2).val,(String)source.get(0).val,temp);
                target.val=temp;
                break;
            case 6:
                target.val=source.get(0).val;
                break;
            case 7:
                target.val=source.get(1).val;
                break;
            case 8:
                target.val = source.get(0).name;
                break;
            default:

        }
        return target;
    }
    public static void actionPrintf(String ident){
        Equation.generate("printf",ident,"null","null");
    }
    public static void actionIfElse(int ifElse,String register){
        switch (ifElse){
            //0是if
            case 0:
                Equation.generate("if",register,"null",String.valueOf(Equation.getEquationsSize()+1));
                break;
            case 1:
                Equation.setEquations("if",2,String.valueOf(Equation.getEquationsSize()));
                break;
            case 2:
                Equation.setEquations("if",3,String.valueOf(Equation.getEquationsSize()));
                break;
            default:
                break;
        }
    }
}

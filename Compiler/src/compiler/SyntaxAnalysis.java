package compiler;

import compiler.IntermediateCodeGernerate;
import compiler.Main;
import entity.Grammar;
import entity.Symbol;
import entity.Word;

import java.util.*;

public class SyntaxAnalysis {
    static Stack statusStack;
    static Stack symbolStack;
    static Stack inputStack;
    static Stack temp;
    static List<Boolean> booleanList;
    static List<Word> wordList;
//    static entity.Grammar assignGrammar;
//    static entity.Grammar defineGrammar;
//    static entity.Grammar boolGrammar;
//    static {
//        assignGrammar=new entity.Grammar();
//        assignGrammar.setAssign();
//        defineGrammar=new entity.Grammar();
//        defineGrammar.setDefine();
//        boolGrammar=new entity.Grammar();
//        boolGrammar.setBool();
//    }
    public static List<Boolean> syntaxAnalysis(List<Word> wordList){
        statusStack=new Stack();
        symbolStack=new Stack();
        inputStack=new Stack();
        booleanList=new ArrayList<>();
        List<Word> temp=new ArrayList<>();
        int len=wordList.size();
        for(int i=0;i<len;i++){
            if(wordList.get(i).getId()==9){
                temp.add(wordList.get(i));
                while(wordList.get(i).getId()!=43){
                    i++;
                    temp.add(wordList.get(i));
                }
                i++;
                temp.add(wordList.get(i));
                while(wordList.get(i).getId()!=43){
                    i++;
                    temp.add(wordList.get(i));
                }
                cutSentences(temp);
                temp.clear();
            }else if(wordList.get(i).getId()==38){
                cutSentences(temp);
                temp=new ArrayList<>();
            }else {
                temp.add(wordList.get(i));
            }
        }
        return booleanList;
    }
    public static void cutSentences(List<Word> wordList1){
        wordList=wordList1;
        if(syntaxAssign()||syntaxDefine()||sytaxIfElse()||syntaxPrint()){
            booleanList.add(new Boolean(true));
        }else{
            booleanList.add(new Boolean(false));
        }
    }
    public static void initStack(){
        statusStack.clear();
        symbolStack.clear();
        inputStack.clear();
        statusStack.push(new Integer(0));
//        symbolStack.push(new String("#"));
        symbolStack.push(new Symbol(51,"#"));
        inputStack.push(new Word(51,"#"));
        int len=wordList.size();
        for(int i=0;i<len;i++){
            inputStack.push(wordList.get(len-1-i));
        }
    }
    public static Boolean syntaxBool(){
        initStack();
        String result="";
        Boolean bool=null;
        while(!result.equals("#")&&!result.equals("acc")){
            Integer status=(Integer) statusStack.peek();
            Word word=(Word)inputStack.peek();
            int index=0;
            if(word.getId()==0||word.getId()==44||word.getId()==45){
                index=Main.boolGrammar.getSymbol().indexOf("i");
            }else if(word.getWord().equals("&&")||word.getWord().equals("||")){
                index=Main.boolGrammar.getSymbol().indexOf("&");
            }else if(word.getWord().equals(">")||word.getWord().equals("<")||word.getWord().equals(">=")||word.getWord().equals("<=")||word.getWord().equals("==")||word.getWord().equals("!=")){
                index=Main.boolGrammar.getSymbol().indexOf(">");
            }else{
                index=Main.boolGrammar.getSymbol().indexOf(word.getWord());
            }
            if(index==-1){
                bool=false;
                break;
            }else{
                result=Main.boolGrammar.getTable()[status.intValue()][index];
            }
            bool=action(result,Main.boolGrammar);
        }
        return bool;
    }
    public static Boolean syntaxDefine(){
        initStack();
        String result="";
        Boolean bool=null;
        while(!result.equals("#")&&!result.equals("acc")){
            Integer status=(Integer) statusStack.peek();
            Word word=(Word)inputStack.peek();
            int index=0;
            if(word.getId()==0||word.getId()==44||word.getId()==45){
                index=Main.defineGrammar.getSymbol().indexOf("i");
            }else if(word.getId()>=1&&word.getId()<=8){
                index= Main.defineGrammar.getSymbol().indexOf("k");
            }else{
                index=Main.defineGrammar.getSymbol().indexOf(word.getWord());
            }
            if(index==-1){
                bool=false;
                break;
            }else{
                result=Main.defineGrammar.getTable()[status.intValue()][index];
            }
            bool=action(result,Main.defineGrammar);
        }
        return bool;
    }
    public static Boolean syntaxAssign(){
        initStack();
        String result="";
        Boolean bool=null;
        while(!result.equals("#")&&!result.equals("acc")){
            Integer status=(Integer) statusStack.peek();
            Word word=(Word)inputStack.peek();
            int index=0;
            if(word.getId()==0||word.getId()==44||word.getId()==45){
                index=Main.assignGrammar.getSymbol().indexOf("i");
            }else if(word.getWord().equals("=")||word.getWord().equals("+=")||word.getWord().equals("-=")||word.getWord().equals("*=")||word.getWord().equals("/=")){//有点问题的
                index=Main.assignGrammar.getSymbol().indexOf("=");
            }else if(word.getWord().equals("+")||word.getWord().equals("-")){
                index=Main.assignGrammar.getSymbol().indexOf("+");
            }else if(word.getWord().equals("*")||word.getWord().equals("/")){
                index=Main.assignGrammar.getSymbol().indexOf("*");
            }else{
                index=Main.assignGrammar.getSymbol().indexOf(word.getWord());
            }
            if(index==-1){
                bool=false;
                break;
            }else{
                result=Main.assignGrammar.getTable()[status.intValue()][index];
            }
            bool=action(result,Main.assignGrammar);
        }
        return bool;
    }
    public static boolean syntaxPrint(){
        initStack();
        Word word=(Word)inputStack.pop();
        if(word.getWord().equals("#")){
            return true;
        }
        if(word.getId()==11){
            word=(Word)inputStack.pop();
            if(word.getId()==35){
                word=(Word)inputStack.pop();
                if(word.getId()==40){
                    word=(Word)inputStack.pop();
                    if(word.getId()==49){
                        word=(Word)inputStack.pop();
                        if(word.getId()==40){
                            word=(Word)inputStack.pop();
                            if(word.getId()==37){
                                word=(Word)inputStack.pop();
                                if(word.getId()==0||word.getId()==44||word.getId()==45){
                                    IntermediateCodeGernerate.actionPrintf(word.getWord());
                                    word=(Word)inputStack.pop();
                                    if(word.getId()==36){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return false;
    }
    public static boolean sytaxIfElse(){
        initStack();
        temp=(Stack) inputStack.clone();
        List<Word> wordList1=new ArrayList<>();
        for(int i=0;i<wordList.size();i++){
            wordList1.add(wordList.get(i));
        }
        Word word=(Word)temp.pop();
        if(word.getId()==9){
            word=(Word) temp.pop();
            if(word.getId()==35){
                wordList.clear();
                while(word.getId()!=36){
                    word=(Word) temp.pop();
                    if(word.getId()!=36){
                        wordList.add(word);
                    }
                }
                if(syntaxBool()){
                    IntermediateCodeGernerate.actionIfElse(0,(String)((Symbol)symbolStack.peek()).val);
                    if(isPrintf()){
                        word=(Word) temp.pop();
                        if(word.getId()==10){
                            IntermediateCodeGernerate.actionIfElse(1,(String)((Symbol)symbolStack.peek()).val);
                            if(isPrintf()){
                                IntermediateCodeGernerate.actionIfElse(2,(String)((Symbol)symbolStack.peek()).val);
                                wordList=wordList1;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        wordList=wordList1;
        return false;
    }
    public static Boolean isPrintf(){
        Word word=(Word) temp.pop();
        if(word.getId()==42) {
            wordList.clear();
            while (word.getId() != 43) {
                word = (Word) temp.pop();
                if (word.getId() != 43 && word.getId() != 38) {
                    wordList.add(word);
                }
            }
            return syntaxPrint()||syntaxDefine()||syntaxAssign();
        }
        return false;

    }
    public static Boolean action(String result, Grammar syntaxGrammar){
        if(result.equals("#")){
            return false;
        }else if(result.equals("acc")){
            return true;
        }else{
            if(result.contains("S")||result.contains("s")){
                Word word=(Word)inputStack.pop();
                statusStack.push(new Integer(result.substring(1)));
                symbolStack.push(new Symbol(word.getId(),word.getWord()));
            }else if(result.contains("r")){
                int index=Integer.parseInt(result.substring(1));
                String grammar=syntaxGrammar.getGrammar()[index];
                String[] str=grammar.split(" ");
                List<Symbol> temp=new ArrayList<>();
                for(int i=0;i<str[1].length();i++){
                    temp.add((Symbol) symbolStack.pop());
                    statusStack.pop();
                }
//                symbolStack.push(str[0]);
//                symbolStack.push(new entity.Symbol(52,str[0]));
                if(syntaxGrammar.getName().equals("define")){
                    symbolStack.push(IntermediateCodeGernerate.actionDefine(index,temp,new Symbol(52,str[0])));
                }else if(syntaxGrammar.getName().equals("assign")){
                    symbolStack.push(IntermediateCodeGernerate.actionAssign(index,temp,new Symbol(52,str[0])));
                }else if(syntaxGrammar.getName().equals("bool")){
                    symbolStack.push(IntermediateCodeGernerate.actionBool(index,temp,new Symbol(52,str[0])));
                }
                index=syntaxGrammar.getSymbol().indexOf(((Symbol)symbolStack.peek()).getName());
                statusStack.push(new Integer(syntaxGrammar.getTable()[(Integer)statusStack.peek()][index]));
            }
            return null;
        }
    }
}

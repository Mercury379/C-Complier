package compiler;

import entity.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexicalAnalysis {
    static int index;
    static String sentences;
    static List<Word> wordList;
    static Word word;
    static char ch;
    public static List<Word> lexicalAnalysis(String s){
        wordList=new ArrayList<>();
        sentences=s;
        int len=sentences.length();
        index=0;
        getChar();
        while(index<=len){
            if((ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z')||ch=='_'||ch=='$'){
                word=new Word();
                word.addWord(ch);
                if(!isIdent()){
                    word=Error(index-1,0);
                }
                wordList.add(word);
            }else if(ch=='>'||ch=='<'||ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='!'||ch=='='||ch=='|'||ch=='&'||ch=='%'){
                word=new Word();
                word.addWord(ch);
                if(!isMathSymbol()){
                    word=Error(index-1,1);
                }
                wordList.add(word);
            }else if(ch>='0'&&ch<='9'){
                word=new Word();
                if(!isNumber()){
                    word=Error(index-1,0);
                }
                wordList.add(word);
            } else if(ch!=' '&&ch!='\n'&&ch!='\t'){
                word=new Word();
                word.addWord(ch);
                if(!isDelimiter()){
                    word=Error(index-1,2);
                    wordList.add(word);
                }
            }
            getChar();
        }
        return wordList;
    }
    public static void getChar(){
        if(index<sentences.length()) {
            ch=sentences.charAt(index++);
        }else{
            index++;
            ch=' ';
        }
    }
    public static void backChar(){//有点问题
        index--;
    }
    public static boolean isIdent(){
        getChar();
        while((ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z')||ch=='_'||ch=='$'||(ch>='0'&&ch<='9')){
            word.addWord(ch);
            getChar();
        }
        backChar();
        if(isKeyWord()){
            return true;
        }
        word.setId(0);
        return true;
    }
    public static boolean isKeyWord(){
        List<String> keyWords= Arrays.asList("int", "short", "long","void","char","float","double","bool","if","else","printf","main","void");
        if(word.getWord().equals("long")){
            if(!wordList.isEmpty()){
                if(wordList.get(wordList.size()-1).getWord().equals("long")){
                    word.setId(13);
                    word.setWord("long long");
                    wordList.remove(wordList.size()-1);
                    return true;
                }
            }
        }
        if(word.getWord().equals("true")){
            word.setId(46);
            return true;
        }
        if(word.getWord().equals("false")){
            word.setId(47);
            return true;
        }
        for(int i=0;i<keyWords.size();i++){
            if(word.getWord().equals(keyWords.get(i))){
                word.setId(i+1);
                return true;
            }
        }
        return false;
    }
    public static boolean isMathSymbol(){
        getChar();
        List<String> keyWords= Arrays.asList("+", "-", "*","/",">","<","!","=","%");
        List<String> keyWords2=Arrays.asList("++","--","||","&&","!=","==","+=","-=","*=","/=",">=","<=");
        if(ch=='='||ch=='|'||ch=='&'||ch=='+'||ch=='-'||ch=='/'){
            word.addWord(ch);
            for(int i=0;i<keyWords2.size();i++){
                if(keyWords2.get(i).equals(word.getWord())){
                    word.setId(i+23);
                    return true;
                }
            }
            if(word.getWord().equals("//")){
                word.setId(39);
                while(ch!='\n'){
                    getChar();
                }
                return true;
            }
            return false;
        }else{
            backChar();
            for(int i=0;i<keyWords.size();i++){
                if(keyWords.get(i).equals(word.getWord())){
                    word.setId(i+14);
                    return true;
                }
            }
            return false;
        }
    }
    //没写完
    public static boolean isDelimiter(){
        switch (ch){
            case '{':
                word.setId(42);
                wordList.add(word);
                break;
            case '}':
                word.setId(43);
                wordList.add(word);
                break;
            case '(':
                word.setId(35);
                wordList.add(word);
                break;
            case ')':
                word.setId(36);
                wordList.add(word);
                break;
            case ',':
                word.setId(37);
                wordList.add(word);
                break;
            case ';':
                word.setId(38);
                wordList.add(word);
                break;
            case '\"':
                word.setId(40);
                wordList.add(word);
                getChar();
                boolean temp=true;
                if(ch=='%'){
                    Word word1=new Word();
                    word1.addWord(ch);
                    getChar();
                    if(ch=='d'||ch=='f'||ch=='c'){
                        word1.setId(49);
                        word1.addWord(ch);
                        wordList.add(word1);
                    }else{
                        temp= false;
                    }
                }else{
                    temp= false;
                }
                getChar();
                if(ch=='\"'){
                    Word word2=new Word();
                    word2.setId(40);
                    word2.addWord(ch);
                    wordList.add(word2);
                }else{
                    temp= false;
                }
                if(!temp){
                    return false;
                }
                break;
            case '\'':
                word.setId(41);
                wordList.add(word);
                getChar();
                Word word1=new Word();
                word1.setId(48);
                word1.addWord(ch);
                wordList.add(word1);
                getChar();
                if(ch=='\''){
                    Word word2=new Word();
                    word2.setId(41);
                    word2.addWord(ch);
                    wordList.add(word2);
                }else{
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }
    public static boolean isNumber(){
        int N=0,P=0,j=0;
        int e=1;
        int d;
        do {
            d = Integer.valueOf(ch+"");
            N = N * 10 + d;
            getChar();
        }while (ch>='0'&&ch<='9');
        if(ch=='.'){
            getChar();
            if(ch<='0'||ch>='9'){
                return false;
            }
            while(ch>='0'&&ch<='9'){
                d=Integer.valueOf(ch+"");
                N = N * 10 + d;
                j=j+1;
                getChar();
            }
            word.setId(45);
            if(ch=='e'||ch=='E'){
                getChar();
                if(ch=='-'){
                    e=-1;
                    getChar();
                }
                if(ch>='0'&&ch<='9'){
                    d=Integer.valueOf(ch+"");
                    P=P*10+d;
                    getChar();
                    while(ch>='0'&&ch<='9'){
                        d=Integer.valueOf(ch+"");
                        P=P*10+d;
                        getChar();
                    }
                    backChar();
                }else{
                    return false;
                }
            }
            word.setWord(String.valueOf(N*Math.pow(10,e*P-j)));
            return true;
        }else if((ch!='e'&&ch!='E')&&((ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z'))){
            return false;
        }else{
            if(ch=='e'||ch=='E'){
                getChar();
                if(ch=='-'){
                    e=-1;
                    getChar();
                }
                if(ch>='0'&&ch<='9'){
                    d=Integer.valueOf(ch+"");
                    P=P*10+d;
                    getChar();
                    while(ch>='0'&&ch<='9'){
                        d=Integer.valueOf(ch+"");
                        P=P*10+d;
                        getChar();
                    }
                }else{
                    return false;
                }
            }
            backChar();
            word.setWord(String.valueOf((int) Double.parseDouble(""+N*Math.pow(10,e*P-j))));
            word.setId(44);
            return true;
        }
    }
    public static Word Error(int index,int errorCode){
        Word word1=new Word();
        word1.setId(50);
        word1.setErrorIndex(index);
        switch (errorCode){
            case 0:
                word1.setWord("indent input error！");
                break;
            case 1:
                word1.setWord("math symbol input error！");
                break;
            case 2:
                word1.setWord("delimiter symbol input error！");
                break;
            default:
                word1.setWord("undifine error!");
        }
        return word1;
    }
}

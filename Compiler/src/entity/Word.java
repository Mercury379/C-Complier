package entity;

public class Word {
    private int id;
    private String word;
    private int errorIndex;
    public Word(){
        word="";
    }

    public Word(int id, String word) {
        this.id = id;
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    public void addWord(char ch){
        StringBuilder stringBuilder = new StringBuilder(word);
        stringBuilder.append(ch);
        word=stringBuilder.toString();
    }

    public int getErrorIndex() {
        return errorIndex;
    }

    public void setErrorIndex(int errorIndex) {
        this.errorIndex = errorIndex;
    }
}

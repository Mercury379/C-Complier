package compiler;

import compiler.LexicalAnalysis;
import compiler.Main;
import compiler.SyntaxAnalysis;
import compiler.TargetCodeGenerate;
import entity.Equation;
import entity.Ident;
import entity.Word;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CompilerUI {
    private JButton complierButton;
    private JButton backButton;
    private JTextArea textAreaSource;
    private JTextArea textAreaTarget;
    private JTextArea textAreaError;
    private JButton wordButton;
    private JButton syntaxButton;
    private JButton codeButton;
    private JPanel contentPanel;
    private JButton mediumButton;
    private JFrame jFrame;
    private List<Word> wordList;
    private List<Boolean> syntaxList;

    public CompilerUI(JFrame jFrame){
        this.jFrame=jFrame;
        jFrame.setContentPane(contentPanel);
        jFrame.setTitle("编译器");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLocation(200,100);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);
        wordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wordList= LexicalAnalysis.lexicalAnalysis(textAreaSource.getText());
                textAreaTarget.setText(getWordTarget());
                textAreaError.setText(getErrorInfo());
            }
        });
        syntaxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.identTable.clear();
                Main.equationList.clear();
                for(int i=0;i<6;i++){
                    wordList.remove(0);
                }
                wordList.remove(wordList.size()-1);
                syntaxList= SyntaxAnalysis.syntaxAnalysis(wordList);
                textAreaError.setText(getSyntaxTarget());
            }
        });
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaTarget.setText(
                        "----------符号表----------\n"
                                +getSymbolTable()
                                +"----------四元式----------\n"
                                +getEquationList());
            }
        });
        codeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TargetCodeGenerate.initRegister();
                Main.targetList.clear();
                TargetCodeGenerate.setDatasSegment();
                TargetCodeGenerate.setCodeSegment();
                textAreaTarget.setText(getCode());
            }
        });
        complierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wordList=LexicalAnalysis.lexicalAnalysis(textAreaSource.getText());
                Main.identTable.clear();
                Main.equationList.clear();
                for(int i=0;i<6;i++){
                    wordList.remove(0);
                }
                wordList.remove(wordList.size()-1);
                syntaxList= SyntaxAnalysis.syntaxAnalysis(wordList);
                TargetCodeGenerate.initRegister();
                Main.targetList.clear();
                TargetCodeGenerate.setDatasSegment();
                TargetCodeGenerate.setCodeSegment();
                textAreaTarget.setText(getCode());
                textAreaError.setText(getSyntaxTarget());
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    public String getWordTarget(){
        String words="";
        for(int i=0;i<wordList.size();i++){
            if(wordList.get(i).getId()!=50) {
                words += "( " + wordList.get(i).getId() + " , " + wordList.get(i).getWord() + " )" + "\n";
            }
        }
        return words;
    }
    public String getErrorInfo(){
        String words="";
        for(int i=0;i<wordList.size();i++){
            if(wordList.get(i).getId()==50) {
                words+=textAreaSource.getText().substring(wordList.get(i).getErrorIndex())+": ";
                words += wordList.get(i).getWord() + "\n";
            }
        }
        return words;
    }

    public String getSyntaxTarget(){
        String syntaxs="";
        for(int i=0;i<syntaxList.size();i++){
            if(syntaxList.get(i)==true){
                syntaxs+="syntax success!\n";
            }else{
                syntaxs+="error present and syntax default!\n";
            }
        }
        return syntaxs;
    }
    public String getSymbolTable(){
        String tables="";
        for(int i = 0; i< Main.identTable.size(); i++){
            Ident ident=Main.identTable.get(i);
            tables+="("+ident.name+","+ident.type+","+ident.val+")"+"\n";
        }
        return tables;
    }
    public String getEquationList(){
        String equations="";
        for(int i=0;i<Main.equationList.size();i++){
            Equation equation=Main.equationList.get(i);
            equations+=i+"、"+"("+equation.operation+","+equation.opnum1+","+equation.opnum2+","+equation.tarnum+")"+"\n";
        }
        return equations;
    }
    public String getCode(){
        String codes="";
        for(int i=0;i<Main.targetList.size();i++){
            codes+=Main.targetList.get(i).target;
        }
        return codes;
    }
}

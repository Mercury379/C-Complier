package compiler;

import entity.Equation;
import entity.Grammar;
import entity.Ident;
import entity.Target;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static Grammar assignGrammar;
    public static Grammar defineGrammar;
    public static Grammar boolGrammar;
    public static List<Ident> identTable;
    public static List<Equation> equationList;
    public static List<Target> targetList;
    static {
        assignGrammar=new Grammar();
        assignGrammar.setAssign();
        defineGrammar=new Grammar();
        defineGrammar.setDefine();
        boolGrammar=new Grammar();
        boolGrammar.setBool();
        identTable=new ArrayList<>();
        equationList=new ArrayList<>();
        targetList=new ArrayList<>();
    }
    public static void main(String[] args) throws SQLException {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JFrame jFrame = new JFrame();
                    CompilerUI compilerUI=new CompilerUI(jFrame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                //在此处添加退出应用程序前需完成工作，如：关闭网络连接、关闭数据库连接等
                System.out.println("编译器程序退出！");
            }
        });
    }
}

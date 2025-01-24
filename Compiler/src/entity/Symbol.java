package entity;

import java.util.List;

public class Symbol {
    public int id;
    public String name;
    public Object val;
    public List<Object> lists;

    public Symbol(int id,String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }
}

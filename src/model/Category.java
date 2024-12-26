package model;

import java.io.Serializable;

public class Category implements Serializable {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}

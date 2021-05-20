package com.paocu.xviss.model;

public class GeneritToUserRolWeatherOrCategory {
    private boolean check;
    private String name;
    private String path;

    public GeneritToUserRolWeatherOrCategory(){}

    public GeneritToUserRolWeatherOrCategory(boolean check, String name, String path) {
        this.check = check;
        this.name = name;
        this.path = path;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "GeneritToUserRolWeatherOrCategory{" +
                "check=" + check +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}

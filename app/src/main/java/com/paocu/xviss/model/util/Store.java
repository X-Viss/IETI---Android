package com.paocu.xviss.model.util;

import java.util.List;

public class Store {
    private String pathImage;
    private String name;
    private String url;
    private List<String> tagCategories;
    public String getPathImage() {
        return pathImage;
    }
    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public List<String> getTagCategories() {
        return tagCategories;
    }
    public void setTagCategories(List<String> tagCategories) {
        this.tagCategories = tagCategories;
    }
    @Override
    public String toString() {
        return "Store [name=" + name + ", pathImage=" + pathImage + ", tagCategories=" + tagCategories + ", url=" + url
                + "]";
    }
}
package com.example.myapplication;

public class Resource {
    private String id;
    private String name;
    private String link;
    private String icon;

    public Resource(String id, String name, String link, String icon) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getIcon() {
        return icon;
    }
}
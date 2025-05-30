package org.library.model;

public class Author {
    private Integer id;
    private String name;

    public Author() {}
    public Author(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }

    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return id + ": " + name;
    }
}

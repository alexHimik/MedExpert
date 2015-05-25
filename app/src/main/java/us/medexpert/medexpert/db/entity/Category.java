package us.medexpert.medexpert.db.entity;

public class Category {

    private int id;
    private String catName;
    private String firstLetter;
    private int type;

    public Category() {
    }

    public Category(int id, String catName, int type) {
        this.id = id;
        this.catName = catName;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }
}

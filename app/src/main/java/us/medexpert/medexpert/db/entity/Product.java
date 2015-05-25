package us.medexpert.medexpert.db.entity;

public class Product {

    private int id;
    private int id_category;
    private String name;
    private String price;
    private String linc;
    private int liked;
    private String descr;
    private String img;
    private long date_v;
    private String nameCat;

    public Product() {
    }

    public Product(int id, String n, String p, String l, int liked, String d, long date, int c) {
        this.id = id;
        this.name = n;
        this.price = p;
        this.linc = l;
        this.liked = liked;
        this.descr = d;
        this.date_v = date;
        this.id_category = c;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLinc() {
        return linc;
    }

    public void setLinc(String linc) {
        this.linc = linc;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getDate_v() {
        return date_v;
    }

    public void setDate_v(long date_v) {
        this.date_v = date_v;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getNameCat() {
        return nameCat;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }
}

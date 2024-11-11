package entities;

import java.util.List;

public class Movie {
    private int id;
    private String title;
    private String description;

    private int categoryId;

    private int[]  actorIDS;

    public Movie() {}
    public Movie( String title, String description, int categoryId,int[] ids) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.actorIDS = ids;
    }

    public Movie(int id, String title, String description, int categoryId,int[] ids) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.actorIDS = ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int[] getActorIDS() {
        return actorIDS;
    }

    public void setActorIDS(int[] actorIDS) {
        this.actorIDS = actorIDS;
    }
}

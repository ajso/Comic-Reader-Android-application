package net.icraftsystems.app.reader.comic.comicreader.model;

public class Comic {

    private int ID;
    private String Name;
    private String image;

    public Comic() {

    }

    public Comic(int ID, String name, String image) {
        this.ID = ID;
        Name = name;
        this.image = image;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

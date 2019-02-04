package net.icraftsystems.app.reader.comic.comicreader.model;

public class Banner {

    private int ID;
    private String Link;


    public Banner(){


    }
    public Banner(int ID, String link){

        this.ID = ID;
        Link = link;


    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}

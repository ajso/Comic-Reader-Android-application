package net.icraftsystems.app.reader.comic.comicreader.model;

public class Chapter {

    private int ID;
    private String Name;
    private int MangaID;

    public Chapter() {

    }

    public Chapter(int ID, String name, int mangaID) {
        this.ID = ID;
        Name = name;
        MangaID = mangaID;
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

    public int getMangaID() {
        return MangaID;
    }

    public void setMangaID(int mangaID) {
        MangaID = mangaID;
    }
}

package net.icraftsystems.app.reader.comic.comicreader.model;

public class Link {


    private int ID;
    private String Link;
    private int ChapterID;

    public Link() {

    }

    public Link(int ID, String link, int chapterID) {
        this.ID = ID;
        Link = link;
        ChapterID = chapterID;
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

    public int getChapterID() {
        return ChapterID;
    }

    public void setChapterID(int chapterID) {
        ChapterID = chapterID;
    }
}

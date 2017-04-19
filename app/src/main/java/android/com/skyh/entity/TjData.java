package android.com.skyh.entity;

/**
 * Created by Administrator on 2017-02-23.
 */

public class TjData {
    private  String title;
    private  String name;
    private  String date;
    private  boolean issh;

    public TjData(String title, String name, String date, boolean issh) {
        this.title = title;
        this.name = name;
        this.date = date;
        this.issh = issh;
    }

    public boolean issh() {
        return issh;
    }

    public void setIssh(boolean issh) {
        this.issh = issh;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

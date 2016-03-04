package com.addtw.aweino1;

/**
 * Created by awei on 2016/2/19.
 */
public class SongInfoHolder {

    private String _id;
    private String decade;
    private String artist;
    private String song;
    private String weeksAtOne;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDecade() {
        return decade;
    }

    public void setDecade(String decade) {
        this.decade = decade;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getWeeksAtOne() {
        return weeksAtOne;
    }

    public void setWeeksAtOne(String weeksAtOne) {
        this.weeksAtOne = weeksAtOne;
    }


}

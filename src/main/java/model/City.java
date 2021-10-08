package model;
import com.squareup.moshi.Json;

public class City {
    @Json(name = "title")
    private String title;
    @Json(name = "woeid")
    private Long woeid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getWoeid() {
        return woeid;
    }

    public void setWoeid(Long woeid) {
        this.woeid = woeid;
    }
}

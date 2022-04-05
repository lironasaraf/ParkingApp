package utils;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Post implements Serializable {

    private String city, street, houseNum, dataFrom, timeFrom, dateTo, timeTo;
//    private List<Integer> photo;
//    private int photoW, photoH;
    private int id;
    private boolean weakly;
    private User user;
    private double price;
    public Post(){ }

    public Post(String city, String street, String houseNum, double price,
                String dataFrom, String timeFrom, String dateTo, String timeTo,
                boolean weakly, User user) {
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.price = price;
        this.dataFrom = dataFrom;
        this.timeFrom = timeFrom;
        this.dateTo = dateTo;
        this.timeTo = timeTo;
//        this.photo = Arrays.stream(photo).boxed().collect(Collectors.toList());
//        this.photoW = photoW;
//        this.photoH = photoH;
        this.weakly = weakly;
        this.user = user;
        this.id = this.hashCode();
    }

    // getters
    public String getCity() { return city; }
    public String getStreet() { return street; }
    public String getHouseNum() { return houseNum; }
    public double getPrice() { return price; }
    public String getDataFrom() { return dataFrom; }
    public String getTimeFrom() { return timeFrom; }
    public String getDateTo() { return dateTo; }
    public String getTimeTo() { return timeTo; }
//    public List<Integer> getPhoto() { return photo; }
//    public int getPhotoW() { return photoW; }
//    public int getPhotoH() { return photoH; }
    public boolean isWeakly() { return weakly; }
    public User getUser() { return user; }
    public int getID() { return id; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return weakly == post.weakly && Double.compare(post.price, price) == 0 &&
                Objects.equals(city, post.city) && Objects.equals(street, post.street) &&
                Objects.equals(houseNum, post.houseNum) && Objects.equals(dataFrom, post.dataFrom) &&
                Objects.equals(timeFrom, post.timeFrom) && Objects.equals(dateTo, post.dateTo) &&
                Objects.equals(timeTo, post.timeTo) && Objects.equals(user, post.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, houseNum, dataFrom, timeFrom, dateTo, timeTo, weakly,
                            user, price);
    }

    @Override
    public String toString() {
        return "Post{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", dataFrom='" + dataFrom + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", id=" + id +
                ", weakly=" + weakly +
                ", user=" + user +
                ", price=" + price +
                '}';
    }
}

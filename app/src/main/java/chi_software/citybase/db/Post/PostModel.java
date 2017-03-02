package chi_software.citybase.db.Post;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 02.03.2017.
 */

public class PostModel implements Parcelable {


    private long id;
    private String price;
    private String comment;

    public PostModel() {
    }

    protected PostModel(Parcel in) {
        this.id = in.readLong();
        this.price = in.readString();
        this.comment = in.readString();
    }

    public static final Creator<PostModel> CREATOR = new Creator<PostModel>() {
        @Override
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        @Override
        public PostModel[] newArray(int size) {
            return new PostModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.price);
        dest.writeString(this.comment);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

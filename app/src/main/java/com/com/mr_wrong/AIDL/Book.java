package com.com.mr_wrong.AIDL;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mr_Wrong on 15/9/27.
 */
public class Book implements Parcelable {
    public  int bookid;
    public String bookName;


    public Book(int bookid,String bookName) {
        this.bookid = bookid;
        this.bookName = bookName;
    }

    private Book(Parcel in){
        bookid = in.readInt();
        bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookid);
        dest.writeString(bookName);
    }
}

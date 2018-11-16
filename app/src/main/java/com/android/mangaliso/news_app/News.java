package com.android.mangaliso.news_app;

public class News {
    private String mSectionName;
    private String mPublicationDate;
    private String mTitle;
    private String mUrl;

    public News(String mSectionName,String mPublicationDate, String mTitle, String mUrl) {
        this.mSectionName = mSectionName;
        this.mTitle = mTitle;
        this.mPublicationDate = mPublicationDate;
        this.mUrl = mUrl;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public String getUrl() {
        return mUrl;
    }
}

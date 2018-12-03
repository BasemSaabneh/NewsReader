package com.devschema.sh4d0w.newsreader;

public class News {
    private String newsTitle;
    private String newsSectionName;
    private String newsPublicationDate;
    private String newsUrl;
    private String newsAuthor;

    public News(String newsTitle, String newsSectionName, String newsPublicationDate, String newsUrl, String newsAuthor) {
        this.newsTitle = newsTitle;
        this.newsSectionName = newsSectionName;
        this.newsPublicationDate = newsPublicationDate;
        this.newsUrl = newsUrl;
        this.newsAuthor = newsAuthor;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsSectionName() {
        return newsSectionName;
    }

    public String getNewsPublicationDate() {
        return newsPublicationDate;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getNewsAuthor() { return newsAuthor; }
}

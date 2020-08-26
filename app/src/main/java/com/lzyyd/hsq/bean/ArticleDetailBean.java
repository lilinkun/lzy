package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/8/26
 * Describe:
 */
public class ArticleDetailBean {
    private String ArticleId;
    private String CategoryId;
    private String Title;
    private String Author;
    private boolean IsAuditing;
    private String SortRank;
    private String CreateDate;
    private String Link;
    private String CategoryName;

    public String getArticleId() {
        return ArticleId;
    }

    public void setArticleId(String articleId) {
        ArticleId = articleId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public boolean isAuditing() {
        return IsAuditing;
    }

    public void setAuditing(boolean auditing) {
        IsAuditing = auditing;
    }

    public String getSortRank() {
        return SortRank;
    }

    public void setSortRank(String sortRank) {
        SortRank = sortRank;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}

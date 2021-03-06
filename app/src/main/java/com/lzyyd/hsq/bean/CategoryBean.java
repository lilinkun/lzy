package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class CategoryBean {
    private String CategoryID;
    private String CategoryParentId;
    private String CategoryName;
    private String CategoryImg;
    private int CategoryLevel;
    private String CategoryPath;
    private String CategoryLevelName;
    private boolean IsUse;
    private boolean IsLeaf;
    private int SortRank;

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryParentId() {
        return CategoryParentId;
    }

    public void setCategoryParentId(String categoryParentId) {
        CategoryParentId = categoryParentId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryImg() {
        return CategoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        CategoryImg = categoryImg;
    }

    public int getCategoryLevel() {
        return CategoryLevel;
    }

    public void setCategoryLevel(int categoryLevel) {
        CategoryLevel = categoryLevel;
    }

    public String getCategoryPath() {
        return CategoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        CategoryPath = categoryPath;
    }

    public String getCategoryLevelName() {
        return CategoryLevelName;
    }

    public void setCategoryLevelName(String categoryLevelName) {
        CategoryLevelName = categoryLevelName;
    }

    public boolean isUse() {
        return IsUse;
    }

    public void setUse(boolean use) {
        IsUse = use;
    }

    public boolean isLeaf() {
        return IsLeaf;
    }

    public void setLeaf(boolean leaf) {
        IsLeaf = leaf;
    }

    public int getSortRank() {
        return SortRank;
    }

    public void setSortRank(int sortRank) {
        SortRank = sortRank;
    }
}

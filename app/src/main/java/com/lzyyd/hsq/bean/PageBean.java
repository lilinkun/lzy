package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/8/14
 * Describe:
 */
public class PageBean {
    String SizeCount;
    int PageIndex;
    int MaxPage;
    String PageCount;

    public String getSizeCount() {
        return SizeCount;
    }

    public void setSizeCount(String sizeCount) {
        SizeCount = sizeCount;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public int getMaxPage() {
        return MaxPage;
    }

    public void setMaxPage(int maxPage) {
        MaxPage = maxPage;
    }

    public String getPageCount() {
        return PageCount;
    }

    public void setPageCount(String pageCount) {
        PageCount = pageCount;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "SizeCount='" + SizeCount + '\'' +
                ", PageIndex='" + PageIndex + '\'' +
                ", MaxPage='" + MaxPage + '\'' +
                ", PageCount='" + PageCount + '\'' +
                '}';
    }
}



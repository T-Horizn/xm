package com.offcn.utils;

public class PageTool {
	
    // 此类中需要外界传入的数据其实只有两个, 总个数, 当前页
    public PageTool(int totalCount, String currentPage){
        this.totalCount = totalCount;
        initCurrentPage(currentPage);
        this.pageSize = 4;// 每页显示的个数
        initTotalPage();
        initPrePage();
        initNextPage();
        initStartIndex();
    }

    // 定义得到当前页码的方法
    private void initCurrentPage(String currentPage){
        if (currentPage == null){
            this.currentPage = 1;
        }else{
            this.currentPage = Integer.parseInt(currentPage);
        }
    }
    // 定义计算总页码的方法
    private void initTotalPage() {
        if (totalCount % pageSize == 0) {
            this.totalPage = totalCount / pageSize;  
        }else{
            this.totalPage = (totalCount / pageSize) + 1;  
        }
    }
    // 定义计算上一页的方法
    private void initPrePage() {
        if (currentPage == 1) { // 1  
            this.prePage = currentPage;
        } else{
            this.prePage = currentPage - 1;
        }
    }

    // 定义下一页计算方法
    private void initNextPage() {
        if (currentPage == totalPage) {
            this.nextPage = currentPage;
        }else{
            this.nextPage = currentPage+1;
        }
    }
    // 计算分页查询时起始索引
    private void initStartIndex() {
        this.startIndex = (currentPage - 1) * pageSize;
    }

    private int pageSize; // 每页展示个数
    private int totalCount; // 表中数据的总个数
    private int totalPage; // 总页数
    private int currentPage;// 当前页
    private int prePage; // 上一页
    private int nextPage; // 下一页
    private int startIndex; // 分页查询的起始索引

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }
    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public String toString() {
        return "PageTool{" +
                "pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", prePage=" + prePage +
                ", nextPage=" + nextPage +
                ", startIndex=" + startIndex +
                '}';
    }
}

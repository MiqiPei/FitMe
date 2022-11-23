package com.fitme.community.entity;

public class Page {

    //current page
    private int current = 1 ;
    //upper limit per page
    private int limit = 10;
    //total # of posts
    private  int rows;
    //path to pages
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current >=1){
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >=1 & limit <= 100){
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //get starting row number for current page
    public int getOffset(){
        return (current-1)*limit;
    }

    //get # of total pages
    public int getTotal() {
        if(rows % limit ==0){
            return rows / limit;
        } else{
            return rows / limit + 1;
        }
    }

    public int getFrom(){
        int from = current -2;
        return from<1 ? 1 : from;
    }

    public int getTo(){
        int to = current +2;
        int total = getTotal();
        return to > total ? total : to;
    }
}

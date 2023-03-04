package com.wizeline.maven.learningjavamaven.model;

import java.util.List;


    public class UsersResponse {
        private List<UserGorest> data;
        private Integer total;
        private Integer pages;
        private Integer page;

        public UsersResponse() {

        }

        public List<UserGorest> getData() {
            return data;
        }

        public void setData(List<UserGorest> data) {
            this.data = data;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getPages() {
            return pages;
        }

        public void setPages(Integer pages) {
            this.pages = pages;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }
    }


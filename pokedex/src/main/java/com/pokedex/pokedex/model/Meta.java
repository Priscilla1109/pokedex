package com.pokedex.pokedex.model;

import lombok.Data;

//Classe que rerpesenta os metadados da página
@Data
public class Meta {
    private int page;
    private int pageSize;
    private int totalPage;
    private long pageRecors;

    public Meta(int page, int pageSize, int totalPages, long totalElements) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalPage = totalPages;
        this.pageRecors = totalElements;
    }
}

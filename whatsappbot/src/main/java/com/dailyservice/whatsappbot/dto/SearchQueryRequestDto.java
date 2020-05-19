package com.dailyservice.whatsappbot.dto;

public class SearchQueryRequestDto {

	String query;
	String sortBy;
	String sortType;
	int page;
	int limit;
	
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	@Override
	public String toString() {
		return "SearchQueryRequestDto [query=" + query + ", sortBy=" + sortBy + ", sortType=" + sortType + ", page="
				+ page + ", limit=" + limit + "]";
	}
	
	
	
	
	
	
}

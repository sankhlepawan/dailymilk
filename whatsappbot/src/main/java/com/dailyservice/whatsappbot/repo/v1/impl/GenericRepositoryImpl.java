/*
 * @Author: Pawan Sankhle
 * @Date: 09-10-2019
*/

package com.dailyservice.whatsappbot.repo.v1.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.repo.v1.IGenericRepository;
import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class GenericRepositoryImpl<T> implements IGenericRepository<T>{

	

	@PersistenceContext
	protected EntityManager entityManager;

	private Class<T> type;

	public GenericRepositoryImpl(){
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}
	

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public long count() {
		final StringBuffer queryString = new StringBuffer("SELECT count(o) from ");
		queryString.append(type.getSimpleName()).append(" o ");
		// queryString.append(this.getQueryClauses(params, null));
		final Query query = this.entityManager.createQuery(queryString.toString());

		return (Long) query.getSingleResult();

	}
	
	@Override
	public List<T> findAll() {
		final StringBuffer queryString = new StringBuffer("SELECT o from ");
		queryString.append(type.getSimpleName()).append(" o ");
		final Query query = this.entityManager.createQuery(queryString.toString());
		return query.getResultList();
		

	}



	private Object getQueryClauses(Map<String, Object> params, Object object) {

		return null;
	}

	@Override
	public T save(final T t) {
		return this.entityManager.merge(t);
	}

	@Override
	public void delete(Object id) {
		this.entityManager.remove(this.entityManager.getReference(type, id));

	}

	@Override
	public T find(Object id) {
		return this.entityManager.find(type, id);
	}

	@Override
	public T update(final T t) {
		return this.entityManager.merge(t);
	}
	
	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}
	
	
	
	private SearchQueryRequestDto transferSearchQuery(SearchQueryRequestDto searchQuery) {
		
		if(searchQuery.getPage() == 0) {
			 searchQuery.setPage(1);
		}else {
			searchQuery.setPage(searchQuery.getPage() + 1);
		}
		if(searchQuery.getLimit() == 0) {
			searchQuery.setLimit(10);
		}
		return searchQuery;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public SearchResponseDTO<T> search(SearchQueryRequestDto searchQuery) {

		searchQuery =  transferSearchQuery(searchQuery);
		String queryString = searchQuery.getQuery();
		String orderBy = searchQuery.getSortBy();
		String orderType = searchQuery.getSortType();
		int page = searchQuery.getPage();
		int limit = searchQuery.getLimit();
		log.info("inside search @params query = {}, orderBy = {},orderType= {}, page = {}, limit ={} ",queryString,orderBy,orderType,page,limit);
		
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(getType());
		From root = criteriaQuery.from(getType());
		
		RSQLVisitor<Predicate, EntityManager> visitor = new JpaPredicateVisitor<T>().defineRoot(root);

		if(queryString != null && !queryString.isEmpty()) {

			CriteriaQuery<T> query = criteriaQuery.where(getCriteriaQuery(queryString, visitor));
			
			
			if(orderType != null && orderType.equalsIgnoreCase("desc")){
				query.orderBy(builder.asc(root.get(orderBy)));
			}else if(orderType != null && orderType.equalsIgnoreCase("asc")){
				query.orderBy(builder.desc(root.get(orderBy)));
			}
			
			
			TypedQuery<T> typedQuery = entityManager.createQuery(query);
			

			Long count =  (long) (typedQuery.getResultList() != null ? typedQuery.getResultList().size() : 0L);
			Integer lowerLimit = (page - 1) * limit;
			Integer upperLimit = page * limit;

			if(lowerLimit >= 0) {
				typedQuery.setFirstResult(lowerLimit);
			}

			if(upperLimit >= 0) {
				typedQuery.setMaxResults(upperLimit);
			}
			
			List<T> resultList = typedQuery.getResultList();

			if (resultList == null || resultList.isEmpty()){
				return new  SearchResponseDTO<T>(Collections.emptyList(), 0L);
			}

			return new  SearchResponseDTO<T>(resultList, count);


		}else {

			if(orderBy != null && orderBy.equalsIgnoreCase("desc")){
				criteriaQuery.select(root).orderBy(builder.desc(root.get(orderBy)));
			}else if(orderBy != null && orderBy.equalsIgnoreCase("asc")){
				criteriaQuery.select(root).orderBy(builder.asc(root.get(orderBy)));
			}
			TypedQuery<T> typedQuery = getEntityManager().createQuery(criteriaQuery);

			Long count =  (long) (typedQuery.getResultList() != null ? typedQuery.getResultList().size() : 0L);
			Integer lowerLimit = (page - 1) * limit;
			Integer upperLimit = page * limit;

			if(lowerLimit>=0) {
				typedQuery.setFirstResult(lowerLimit);
			}

			if(upperLimit>=0) {
				typedQuery.setMaxResults(upperLimit);
			}

			List<T> resultList = typedQuery.getResultList();

			if (resultList == null || resultList.isEmpty()){
				return new  SearchResponseDTO<T>(Collections.emptyList(), 0L);
			}

			return new  SearchResponseDTO<T>(resultList, count);

		}

	}
	
	private <T> Predicate getCriteriaQuery(String queryString, RSQLVisitor<Predicate, EntityManager> visitor) {
	      
		Node rootNode;
		Predicate predicate;
        
        try {
            	rootNode = new RSQLParser().parse(queryString);
            	predicate  = rootNode.accept(visitor,getEntityManager());
        }catch (Exception e){
        	e.printStackTrace();
            log.error("An error happened while executing RSQL query", e);
            throw new IllegalArgumentException(e.getMessage());
        }
        return predicate;
    }

	

}


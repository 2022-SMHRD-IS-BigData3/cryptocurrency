package com.test.news;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
	
	List<News>findAll() ;

	@Query("SELECT n FROM News n WHERE n.newsTitle LIKE %:keyword%")
	List<News> findByKeyword(@Param("keyword") String keyword);
}


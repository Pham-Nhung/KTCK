package com.java;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoteRepository extends JpaRepository<Notes, Long> {
	 	@Query("SELECT n FROM Notes n WHERE n.tieude lIKE %?1%")
	 	public List<Notes>findAll(String keyword);
		public Notes findByTieude(String keyword);
}

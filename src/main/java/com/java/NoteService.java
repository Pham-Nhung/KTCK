package com.java;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class NoteService{
	@Autowired
	private NoteRepository note4;
	
	public List<Notes>listAll(String keyword){
		if(keyword != null) {
			return note4.findAll(keyword);
		}
		return note4.findAll();
	}
	
	public void save(Notes note) {
		note4.save(note);
	}
}

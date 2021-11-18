package com.leo.tennis.service;

import java.util.List;

import com.leo.tennis.dto.EntrenadorDTO;




public interface EntrenadorService {
  
	public List<EntrenadorDTO> listAll();
	
	EntrenadorDTO getById (Long id);
	
}

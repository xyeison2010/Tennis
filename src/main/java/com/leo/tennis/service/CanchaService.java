package com.leo.tennis.service;



import java.util.List;

import com.leo.tennis.dto.CanchaDTO;

public interface CanchaService {
	List<CanchaDTO> listAll();

	CanchaDTO getById(Long id);

	CanchaDTO save(CanchaDTO cancha);

	CanchaDTO update(CanchaDTO cancha);

	void delete(Long id);

}

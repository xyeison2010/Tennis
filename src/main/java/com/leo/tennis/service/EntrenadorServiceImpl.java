package com.leo.tennis.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.tennis.dto.EntrenadorDTO;
import com.leo.tennis.mapper.EntrenadorMapper;
import com.leo.tennis.repository.EntrenadorRepository;


@Service
public class EntrenadorServiceImpl implements EntrenadorService {

	public static final String FIELD_WITH_ID = "Field with id = ";
	public static final String DOES_NOT_EXIST = " does not exist.";

	private EntrenadorRepository entrenadorRepository;
	
	private EntrenadorMapper entrenadorMapper;

	
	@Autowired
	public EntrenadorServiceImpl(EntrenadorRepository entrenadorRepository, EntrenadorMapper entrenadorMapper) {
	
		this.entrenadorRepository = entrenadorRepository;
		this.entrenadorMapper = entrenadorMapper;
	}

	@Override
	public List<EntrenadorDTO> listAll() {

		return entrenadorRepository.findAll().stream().map(this.entrenadorMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public EntrenadorDTO getById(Long id) {
		return entrenadorMapper.toDTO(entrenadorRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(FIELD_WITH_ID + id + DOES_NOT_EXIST)));
	}

}

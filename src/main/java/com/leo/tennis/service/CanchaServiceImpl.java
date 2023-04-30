package com.leo.tennis.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.tennis.dto.CanchaDTO;
import com.leo.tennis.mapper.CanchaMapper;
import com.leo.tennis.repository.CanchaRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CanchaServiceImpl implements CanchaService {

    public static final String FIELD_WITH_ID = "Field with id = ";
    public static final String DOES_NOT_EXIST = " does not exist.";
    public static final String ALREADY_EXISTS = " already exists.";

   
    private  CanchaRepository canchaRepository;
   
    private CanchaMapper canchaMapper;
        
    @Autowired
    public CanchaServiceImpl(CanchaRepository canchaRepository, CanchaMapper canchaMapper) {
        this.canchaRepository = canchaRepository;
        this.canchaMapper = canchaMapper;
    }
    /*                  */

    @Override
    public List<CanchaDTO> listAll() {
        return canchaRepository.findAll()
                .stream()
                .map(this.canchaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CanchaDTO getById(Long id) {
        return canchaMapper.toDTO(canchaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(FIELD_WITH_ID + id + DOES_NOT_EXIST)));
    }

    @Override
    public CanchaDTO save(CanchaDTO cancha) {
        boolean exists = cancha.getId() != null && canchaRepository.existsById(cancha.getId());
        if (exists) {
            throw new IllegalArgumentException(FIELD_WITH_ID + cancha.getId() + ALREADY_EXISTS);
        }
        return this.canchaMapper
                .toDTO(
                        canchaRepository.save(this.canchaMapper.fromDTO(cancha)));
    }

    @Override
    public CanchaDTO update(CanchaDTO cancha) {
        boolean exists = canchaRepository.existsById(cancha.getId());
        if (!exists) {
            throw new NoSuchElementException(FIELD_WITH_ID + cancha.getId() + DOES_NOT_EXIST);
        }
        return this.canchaMapper
                .toDTO(
                        canchaRepository.save(this.canchaMapper.fromDTO(cancha)));
    }

    @Override
    public void delete(Long id) {
        boolean exists = canchaRepository.existsById(id);
        if (!exists) {
            throw new NoSuchElementException(FIELD_WITH_ID + id + DOES_NOT_EXIST);
        }
        canchaRepository.deleteById(id);
    }

}

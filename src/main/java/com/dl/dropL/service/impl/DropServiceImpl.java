package com.dl.dropL.service.impl;

import com.dl.dropL.model.Drop;
import com.dl.dropL.repository.DropRepository;
import com.dl.dropL.service.DropService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DropServiceImpl implements DropService {

    public DropRepository dropRepository;

    DropServiceImpl(DropRepository dropRepository) {this.dropRepository = dropRepository;}

    @Override
    public Drop saveDrop(Drop drop) {
        return dropRepository.save(drop);
    }

    @Override
    public List<Drop> findAll() {
        return dropRepository.findAll();
    }

    @Override
    public Optional<Drop> getDropById(Long id) {
        return dropRepository.findById(id);
    }

    @Override
    public Drop updateDrop(Drop updatedDrop) {
        return dropRepository.save(updatedDrop);
    }

    @Override
    public void deleteDrop(Long id) { dropRepository.deleteById(id); }
}

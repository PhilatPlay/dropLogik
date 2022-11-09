package com.dl.dropL.service;

import com.dl.dropL.model.Drop;

import java.util.List;
import java.util.Optional;

public interface DropService {
    Drop saveDrop(Drop drop);
    List<Drop> findAll();
    Optional<Drop> getDropById(Long id);
    Drop updateDrop(Drop updatedDrop);
    void deleteDrop(Long id);

}

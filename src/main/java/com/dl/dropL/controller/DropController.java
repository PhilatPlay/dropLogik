package com.dl.dropL.controller;

import com.dl.dropL.model.Drop;
import com.dl.dropL.service.DropService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/drops")
public class DropController {

    public DropService dropService;

    public DropController(DropService dropService) {
        this.dropService = dropService;
    }

    @PostMapping
    public Drop createDrop(@RequestBody Drop drop) {
        return dropService.saveDrop(drop);
    }

    @GetMapping
    public List<Drop> getAllDrops() {
        return dropService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Drop> getDropById(@PathVariable("id") Long dropId) {
        return dropService.getDropById(dropId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Drop> updateDrop(@PathVariable("id") Long dropId,
                                           @RequestBody Drop drop) {
        return dropService.getDropById(dropId).map(savedDrop -> {
            savedDrop.setDropTitle(drop.getDropTitle());
            savedDrop.setDemandDesc(drop.getDemandDesc());
            savedDrop.setLatdrop(drop.getLatdrop());
            savedDrop.setLongdrop(drop.getLongdrop());
            savedDrop.setLatpickup(drop.getLatpickup());
            savedDrop.setLongpickup(drop.getLongpickup());
            savedDrop.setPayOffer(drop.getPayOffer());
            savedDrop.setDropByDate(drop.getDropByDate());
            savedDrop.setLastModified(drop.getLastModified());

            Drop updatedDrop = dropService.updateDrop(savedDrop);
            return new ResponseEntity<>(updatedDrop, HttpStatus.OK);
        })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDrop(@PathVariable("id") Long dropId) {
        dropService.deleteDrop(dropId);
        return new ResponseEntity<>("Drop deleted successfully.", HttpStatus.OK);
    }
}

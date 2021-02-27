package com.digital.crud.saladereuniao.saladereuniao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.digital.crud.saladereuniao.saladereuniao.exception.ResouceNotFoundException;
import com.digital.crud.saladereuniao.saladereuniao.model.Room;
import com.digital.crud.saladereuniao.saladereuniao.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable(value="id") long roomId)
    throws ResouceNotFoundException{
        Room r = roomRepository.findById(roomId)
        .orElseThrow(() -> new ResouceNotFoundException("Room not found:: "+roomId));
        return ResponseEntity.ok().body(r);
    }

    @PostMapping("/rooms")
    public Room createRoom(@Valid @RequestBody Room room){
        return roomRepository.save(room);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value="id") long roomId,
                                           @Valid @RequestBody Room room) throws ResouceNotFoundException{
        Room r = roomRepository.findById(roomId)
        .orElseThrow(() -> new ResouceNotFoundException("Room not found for this id:: "+roomId));
        r.setName(room.getName());
        r.setDate(room.getDate());
        r.setStartHour(room.getStartHour());
        r.setEndHour(room.getEndHour());

        final Room updatedRoom = roomRepository.save(r);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/rooms/{id}")
    public Map<String, Boolean> updateRoom(@PathVariable(value="id") long roomId) throws ResouceNotFoundException{
        Room r = roomRepository.findById(roomId)
        .orElseThrow(() -> new ResouceNotFoundException("Room not found for this id:: "+roomId));

        roomRepository.delete(r);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}

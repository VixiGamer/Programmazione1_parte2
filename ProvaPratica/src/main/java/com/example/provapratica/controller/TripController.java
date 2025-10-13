package com.example.provapratica.controller;

import com.example.provapratica.entity.Trip;
import com.example.provapratica.entity.User;
import com.example.provapratica.service.ProjectTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ProjectTripService tripService;

    // ELENCA TUTTE LE CORSE (pubblico)
    // GET http://localhost:8086/trips
    @GetMapping
    public Iterable<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    // DETTAGLIO CORSA PER ID (pubblico)
    // GET http://localhost:8086/trips/{id}
    @GetMapping("/{id}")
    public Optional<Trip> getTripById(@PathVariable Integer id) {
        return tripService.getTripById(id);
    }

    // CREA CORSA (solo admin)
    // POST http://localhost:8086/trips
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip);
    }

    // ACQUISTA CORSA (autenticato)
    // POST http://localhost:8086/trips/{tripId}/buy
    @PostMapping("/{tripId}/buy")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> buyTrip(@PathVariable Integer tripId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return tripService.purchaseTrip(user.getId(), tripId);
    }

    // ELIMINA CORSA (solo admin)
    // DELETE http://localhost:8086/trips/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteTrip(@PathVariable Integer id) {
        return tripService.deleteTrip(id);
    }
}

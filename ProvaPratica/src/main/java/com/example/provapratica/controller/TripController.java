package com.example.provapratica.controller;

import com.example.provapratica.entity.Trip;
import com.example.provapratica.entity.User;
import com.example.provapratica.entity.UserRepository;
import com.example.provapratica.entity.TripRepository;
import com.example.provapratica.service.ProjectTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ProjectTripService tripService;

    // 🟩 CREA UNA NUOVA CORSA
    // POST http://localhost:8086/trips/create
    @PostMapping("/create")
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip);
    }

    // 🔍 OTTIENI TUTTE LE CORSE
    // GET http://localhost:8086/trips/all
    @GetMapping("/all")
    public Iterable<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    // 🔍 OTTIENI UNA CORSA PER ID
    // GET http://localhost:8086/trips/{id}
    @GetMapping("/{id}")
    public Optional<Trip> getTripById(@PathVariable Integer id) {
        return tripService.getTripById(id);
    }

    // ❌ ELIMINA UNA CORSA
    // DELETE http://localhost:8086/trips/{id}
    @DeleteMapping("/{id}")
    public String deleteTrip(@PathVariable Integer id) {
        return tripService.deleteTrip(id);
    }

    // 💳 ACQUISTA UNA CORSA
    // 🟩 POST http://localhost:8086/trip/purchase?userId=1&tripId=2
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTrip(Integer userId, Integer tripId) {
        // Trova utente e corsa
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Trip> tripOpt = tripRepository.findById(tripId);

        if (userOpt.isEmpty() || tripOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Utente o corsa non trovata.");
        }

        User user = userOpt.get();
        Trip trip = tripOpt.get();

        BigDecimal credit = user.getCredit(); // BigDecimal
        BigDecimal price = trip.getPrice();   // BigDecimal

        // Controllo credito sufficiente
        if (credit.compareTo(price) < 0) { // compareTo per BigDecimal
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Credito insufficiente.");
        }

        // Scala il credito
        BigDecimal newCredit = credit.subtract(price); // subtract per BigDecimal
        user.setCredit(newCredit);
        userRepository.save(user);

        // Crea la ricevuta
        Map<String, Object> receipt = new HashMap<>();
        receipt.put("userId", user.getId());
        receipt.put("tripId", trip.getId());
        receipt.put("addebito", price);
        receipt.put("saldoResiduo", user.getCredit());

        return ResponseEntity.ok(receipt);
    }

}

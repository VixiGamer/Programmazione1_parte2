package com.example.provapratica.service;

import com.example.provapratica.entity.Trip;
import com.example.provapratica.entity.TripRepository;
import com.example.provapratica.entity.User;
import com.example.provapratica.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProjectTripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    // CREA UNA NUOVA CORSA
    public Trip createTrip(Trip trip) {
        if (trip.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Il prezzo non può essere negativo");
        }
        return tripRepository.save(trip);
    }

    // OTTIENI TUTTE LE CORSE
    public Iterable<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    // OTTIENI UNA CORSA PER ID
    public Optional<Trip> getTripById(Integer id) {
        return tripRepository.findById(id);
    }

    // ELIMINA UNA CORSA
    public String deleteTrip(Integer id) {
        tripRepository.deleteById(id);
        return "Trip with id " + id + " has been deleted!";
    }

    // ACQUISTA UNA CORSA
    public ResponseEntity<?> purchaseTrip(Integer userId, Integer tripId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Trip> tripOpt = tripRepository.findById(tripId);

        // Controllo esistenza
        if (userOpt.isEmpty() || tripOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Utente o corsa non trovata.");
        }

        User user = userOpt.get();
        Trip trip = tripOpt.get();

        BigDecimal credit = user.getCredit();
        BigDecimal price = trip.getPrice();

        // Controllo credito sufficiente
        if (credit.compareTo(price) < 0) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Credito insufficiente.");
        }

        // Scala il credito e salva
        BigDecimal newCredit = credit.subtract(price);
        user.setCredit(newCredit);
        userRepository.save(user);


        // Genera ricevuta
        Map<String, Object> receipt = new HashMap<>();
        receipt.put("userId", user.getId());
        receipt.put("tripId", trip.getId());
        receipt.put("addebito", price);
        receipt.put("saldoResiduo", user.getCredit());

        return ResponseEntity.ok(receipt);
    }
}

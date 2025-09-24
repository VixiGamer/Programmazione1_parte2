package controller;

import org.springframework.web.bind.annotation.*;
import service.CalcolatriceService;

@RestController
@RequestMapping("/calcolatrice") // prefisso per tutti gli endpoint
public class CalcolatriceController {

    private final CalcolatriceService calcolatriceService;

    public CalcolatriceController(CalcolatriceService calcolatriceService) {
        this.calcolatriceService = calcolatriceService;
    }

    // Somma
    @GetMapping("/add/{a}/{b}")
    public Integer add(@PathVariable int a, @PathVariable int b) {
        return calcolatriceService.add(a, b);
    }

    // Sottrazione
    @GetMapping("/sub/{a}/{b}")
    public Integer sub(@PathVariable int a, @PathVariable int b) {
        return calcolatriceService.sub(a, b);
    }

    // Moltiplicazione
    @GetMapping("/mul/{a}/{b}")
    public Integer mul(@PathVariable int a, @PathVariable int b) {
        return calcolatriceService.mul(a, b);
    }

    // Divisione
    @GetMapping("/div/{a}/{b}")
    public Integer div(@PathVariable int a, @PathVariable int b) {
        return calcolatriceService.div(a, b);
    }
}

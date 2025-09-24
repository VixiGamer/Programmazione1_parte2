package Controller;

import Service.CalcolatriceRequestService;
import dto.OperazioneRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calcolatricerequest")

public class CalcolatriceRequestController {

    private final CalcolatriceRequestService calcolatriceRequestService;

    public CalcolatriceRequestController(CalcolatriceRequestService calcolatriceRequestService) {
        this.calcolatriceRequestService = calcolatriceRequestService;
    }

    // Somma
    @GetMapping("/add/{a}/{b}")
    public Integer add(@PathVariable int a, @PathVariable int b) {
        return calcolatriceRequestService.add(a, b);
    }

    // Sottrazione
    @GetMapping("/sub")
    public Integer sub(@RequestParam int a, @RequestParam int b) {
        return calcolatriceRequestService.sub(a, b);
    }

    // Moltiplicazione, con JSON
    @PostMapping("/mul")
    public Integer mulJson(@RequestBody OperazioneRequest request) {
        return calcolatriceRequestService.mul(request.getA(), request.getB());
    }

    // Divisione, con JSON
    @PostMapping("/div")
    public Integer divJson(@RequestBody OperazioneRequest request) {
        return calcolatriceRequestService.div(request.getA(), request.getB());
    }
}


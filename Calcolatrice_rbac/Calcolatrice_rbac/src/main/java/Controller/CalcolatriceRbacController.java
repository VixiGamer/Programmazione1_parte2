package Controller;

import Service.CalcolatriceRbacService;
import dto.OperazioneRbac;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calcolatricerbac")

public class CalcolatriceRbacController {

    private final CalcolatriceRbacService calcolatriceRbacService;

    public CalcolatriceRbacController(CalcolatriceRbacService calcolatriceRbacService) {
        this.calcolatriceRbacService = calcolatriceRbacService;
    }


    // Somma
    @GetMapping("/add/{a}/{b}")
    public Integer add(@PathVariable int a, @PathVariable int b) {
        return calcolatriceRbacService.add(a, b);
    }

    // Sottrazione
    @GetMapping("/sub")
    public Integer sub(@RequestParam int a, @RequestParam int b) {
        return calcolatriceRbacService.sub(a, b);
    }

    // Moltiplicazione, con JSON
    @PostMapping("/mul")
    public Integer mulJson(@RequestBody OperazioneRbac request) {
        return calcolatriceRbacService.mul(request.getA(), request.getB());
    }

    // Divisione, con JSON
    @PostMapping("/div")
    public Integer divJson(@RequestBody OperazioneRbac request) {
        return calcolatriceRbacService.div(request.getA(), request.getB());
    }

}

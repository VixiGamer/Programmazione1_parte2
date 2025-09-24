package Service;

import org.springframework.stereotype.Service;

@Service

public class CalcolatriceRequestService {
    public Integer add(int a, int b){
        return a+b;
    }

    public Integer sub(int a, int b){
        return a-b;
    }

    public Integer mul(int a, int b){
        return a*b;
    }

    public Integer div(int a, int b){
        return a/b;
    }
}

package service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Service

public class CalcolatriceService {
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
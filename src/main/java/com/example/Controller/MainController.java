package com.example.Controller;

import com.example.Model.RequestDTO;
import com.example.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();
    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO){
        try{
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            String currency;
            String RqUID = requestDTO.getRqUID();
            BigDecimal randFromDouble = new BigDecimal(Math.random());
            BigDecimal randomBigDecimal;

            if(firstDigit == '8'){
                maxLimit = new BigDecimal(2000);
                currency = "US";
                randomBigDecimal = randFromDouble.multiply(maxLimit);
            }
            else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000);
                currency = "EU";
                randomBigDecimal = randFromDouble.multiply(maxLimit);
            }
            else{
                maxLimit = new BigDecimal(10000);
                currency = "RUB";
                randomBigDecimal = randFromDouble.multiply(maxLimit);
            }
            randomBigDecimal = randomBigDecimal.setScale(2, BigDecimal.ROUND_DOWN);

            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(RqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setBalance(randomBigDecimal);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setMaxLimit(maxLimit);

            log.error("********* Request DTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("********* Response DTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;
            }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


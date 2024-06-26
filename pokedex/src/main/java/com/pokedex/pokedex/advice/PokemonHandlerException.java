package com.pokedex.pokedex.advice;

import com.pokedex.pokedex.exception.BadRequestException;
import com.pokedex.pokedex.exception.InternalServerErrorException;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class PokemonHandlerException {
    //Erro 404
    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<String> pokemonNotFoundException(PokemonNotFoundException e){
        return new ResponseEntity<>("Pokemon not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    //Erro 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> badResquestException(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request. Please check your request parameters");
    }


    //Erro 500
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<String> internalServerErrorException(InternalServerErrorException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error. Please try again later");
    }
}

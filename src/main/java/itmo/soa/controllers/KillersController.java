package itmo.soa.controllers;

import itmo.soa.dto.DefaultDto;
import itmo.soa.dto.ErrorDto;
import itmo.soa.exceptions.DragonsServiceException;
import itmo.soa.exceptions.CaveNotFoundException;
import itmo.soa.exceptions.IllegalIdException;
import itmo.soa.services.KillersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@RestController
@RequestMapping(value = "/killers")
@CrossOrigin(origins = "http://localhost:3000")
public class KillersController extends BaseController{

    @Autowired
    private KillersService killersService;

    @GetMapping(value = "/move-to-cave")
    public ResponseEntity<DefaultDto> getCaveId(){
        return new ResponseEntity<>(new DefaultDto(HttpStatus.OK.value(), killersService.getCaveId()), HttpStatus.OK);
    }

    @PostMapping(value = "/dragons/{dragonId}/kill")
    public ResponseEntity<DefaultDto> killDragon(@PathVariable String dragonId) throws IOException, DragonsServiceException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return new ResponseEntity<>(killersService.killDragon(dragonId), HttpStatus.OK);
    }

    @PostMapping(value = "/move-to-cave/{caveId}")
    public ResponseEntity<DefaultDto> moveToCave(@PathVariable String caveId) throws IOException, DragonsServiceException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return new ResponseEntity<>(killersService.moveToCave(caveId), HttpStatus.OK);
    }

    /*exception handlers*/

    @ExceptionHandler({IllegalIdException.class})
    public ResponseEntity<ErrorDto> handleIllegalIdException() {
        return new ResponseEntity<>(new ErrorDto(HttpStatus.METHOD_NOT_ALLOWED.value(), "Invalid id supplied"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({CaveNotFoundException.class })
    public ResponseEntity<ErrorDto> handleCaveNotFoundException() {
        return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_FOUND.value(), "Cave not found"), HttpStatus.NOT_FOUND);
    }

}

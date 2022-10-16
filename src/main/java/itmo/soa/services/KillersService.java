package itmo.soa.services;

import com.google.gson.Gson;
import itmo.soa.dto.*;
import itmo.soa.exceptions.BadRequestException;
import itmo.soa.exceptions.CaveNotFoundException;
import itmo.soa.exceptions.IllegalIdException;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Service
public class KillersService {

    private static Long currentCaveId;

    @Autowired
    private RequestService requestService;

    public String getCaveId(){
        if (currentCaveId==null){
            return "";
        }
        return String.valueOf(currentCaveId);
    }

    public DefaultDto killDragon(String id) throws IOException, BadRequestException {
        DragonDto dragonDto = requestService.sendRequest("https://localhost:8080/dragons/" + id, GET, DragonDto.class);
        if (currentCaveId!=dragonDto.getCave().getId()){
            return new DefaultDto(HttpStatus.OK.value(), "Dragon is in another cave: " + dragonDto.getCave().getId());
        }
        requestService.sendRequest("https://localhost:8080/dragons/" + id, DELETE, DragonDto.class);
        return new DefaultDto(HttpStatus.OK.value(), "Dragon " + dragonDto.getName() + "(id: " + id + ")" +  " is killed");
    }

    public DefaultDto moveToCave(String moveCaveId) throws IOException, BadRequestException {
        try{
            Long.parseLong(moveCaveId);
        }
        catch (NumberFormatException e){
            return new DefaultDto(HttpStatus.OK.value(), "Id is not valid");
        }
        AllDragonCavesDto allDragonCavesDto = requestService.sendRequest("https://localhost:8080/caves/", GET, AllDragonCavesDto.class);
        List<DragonCaveDto> caves = allDragonCavesDto.getCaves();
        for (DragonCaveDto dragonCaveDto : caves) {
            if (dragonCaveDto.getId()== Long.parseLong(moveCaveId)){
                currentCaveId = Long.parseLong(moveCaveId);
                return new DefaultDto(HttpStatus.OK.value(), "You are now in cave with id " + moveCaveId);
            }
        }
        return new DefaultDto(HttpStatus.OK.value(), "Cave is not present");
    }



}

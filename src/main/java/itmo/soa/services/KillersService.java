package itmo.soa.services;

import itmo.soa.dto.*;
import itmo.soa.exceptions.DragonsServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Service
public class KillersService {

    private static Long currentCaveId = 0L;

    @Autowired
    private RequestService requestService;

    public String getCaveId(){
        return String.valueOf(currentCaveId);
    }

    public DefaultDto killDragon(String id) throws IOException, DragonsServiceException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        DragonDto dragonDto = requestService.sendRequest("http://localhost:2740/dragons/" + id, GET, DragonDto.class);
        if (currentCaveId == null){
            return new DefaultDto(HttpStatus.OK.value(), "You are NOT IN Any CAVE ");
        }
        if (currentCaveId!=dragonDto.getCave().getId()){
            return new DefaultDto(HttpStatus.OK.value(), "Dragon is in another cave: " + dragonDto.getCave().getId());
        }
        requestService.sendRequest("http://localhost:2740/dragons/" + id, DELETE, DragonDto.class);
        return new DefaultDto(HttpStatus.OK.value(), "Dragon " + dragonDto.getName() + "(id: " + id + ")" +  " is killed");
    }

    public DefaultDto moveToCave(String moveCaveId) throws IOException, DragonsServiceException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        try{
            Long.parseLong(moveCaveId);
        }
        catch (NumberFormatException e){
            return new DefaultDto(HttpStatus.OK.value(), "Id is not valid");
        }
        AllDragonCavesDto allDragonCavesDto = requestService.sendRequest("http://localhost:2740/caves/", GET, AllDragonCavesDto.class);
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

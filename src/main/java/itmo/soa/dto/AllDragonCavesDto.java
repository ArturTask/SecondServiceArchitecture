package itmo.soa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllDragonCavesDto {

    List<DragonCaveDto> caves;
}

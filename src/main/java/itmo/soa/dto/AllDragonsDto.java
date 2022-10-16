package itmo.soa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllDragonsDto {

    private List<DragonDto> dragons;
}

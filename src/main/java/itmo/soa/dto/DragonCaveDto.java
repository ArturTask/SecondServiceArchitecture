package itmo.soa.dto;

import itmo.soa.entity.DragonCave;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DragonCaveDto {
    private long id;
    private Integer numberOfTreasures;

    public DragonCaveDto(Integer numberOfTreasures) {
        this.numberOfTreasures = numberOfTreasures;
    }

    public DragonCaveDto(DragonCave dragonCave) {
        this.id = dragonCave.getId();
        this.numberOfTreasures = dragonCave.getNumberOfTreasures();
    }
}
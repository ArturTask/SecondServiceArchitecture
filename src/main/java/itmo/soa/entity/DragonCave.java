package itmo.soa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DragonCave {
    private long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным
    private Integer numberOfTreasures; //Поле не может быть null, Значение поля должно быть больше 0

    public DragonCave(Integer numberOfTreasures) {
        this.numberOfTreasures = numberOfTreasures;
    }
}


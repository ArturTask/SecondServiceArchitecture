package itmo.soa.entity;

import itmo.soa.dto.DragonDto;
import itmo.soa.enums.Color;
import itmo.soa.enums.DragonCharacter;
import itmo.soa.enums.DragonType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@NoArgsConstructor
@Data
public class Dragon { // entity that we use in our service
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long age; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonType type; //Поле не может быть null
    private DragonCharacter character; //Поле может быть null
    private DragonCave cave; //Поле не может быть null

    public Dragon(Long id, String name, Coordinates coordinates, String creationDate, long age, Color color, DragonType type, DragonCharacter character, DragonCave cave) throws InstantiationException {

        if (!checkInputValues(id, name, coordinates, creationDate, age, color, type, character, cave)) {
            throw new InstantiationException("Dragon cannot be instantiated : wrong arguments");
        }
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        setCreationDate(creationDate);
        this.age = age;
        this.color = color;
        this.type = type;
        this.character = character;
        this.cave = cave;
    }

    public Dragon(DragonDto dragonDto) throws InstantiationException {
        if (dragonDto.getId()!=null) { // check with id
            if (!checkInputValues(dragonDto.getId(), dragonDto.getName(), dragonDto.getCoordinates(), dragonDto.getCreationDate(), dragonDto.getAge(), dragonDto.getColor(), dragonDto.getType(), dragonDto.getCharacter(), dragonDto.getCave())) {
                throw new InstantiationException("Dragon cannot be instantiated : wrong arguments");
            }
        }
        else{
            if (!checkInputValues(dragonDto.getName(), dragonDto.getCoordinates(), dragonDto.getCreationDate(), dragonDto.getAge(), dragonDto.getColor(), dragonDto.getType(), dragonDto.getCharacter(), dragonDto.getCave())) {
                throw new InstantiationException("Dragon cannot be instantiated : wrong arguments");
            }
        }
        this.id = dragonDto.getId();
        this.name = dragonDto.getName();
        this.coordinates = dragonDto.getCoordinates();
        setCreationDate(FORMAT.format(LocalDateTime.now()));
        this.age = dragonDto.getAge();
        this.color = dragonDto.getColor();
        this.type = dragonDto.getType();
        this.character = dragonDto.getCharacter();
        this.cave = dragonDto.getCave();
    }

    public String getCreationDate() {
        return FORMAT.format(this.creationDate);
    }

    public void setCreationDate(String creationDateUsingFormat) throws DateTimeParseException {
        LocalDateTime ldt = LocalDate.parse(creationDateUsingFormat, FORMAT).atStartOfDay();
        this.creationDate = ldt.atZone(ZoneId.of("Europe/Moscow"));
    }

    public boolean setAge(long age) {
        if (age > 0) {
            this.age = age;
            return true;
        }
        return false;
    }

    public boolean setName(String name) {
        if (!name.equals("")) {
            this.name = name;
            return true;
        }
        return false;
    }

    private boolean checkCreationDate(String creationDateUsingFormat) {
        try {
            LocalDateTime ldt = LocalDate.parse(creationDateUsingFormat, FORMAT).atStartOfDay();
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean checkInputValues(Long id, String name, Coordinates coordinates, String creationDate, long age, Color color, DragonType type, DragonCharacter character, DragonCave cave) {
        if (id <= 0 || name == null || name.equals("") || age <= 0) {
            return false;
        }
        if (creationDate==null){
            return true;
        }
        return checkCreationDate(creationDate);
    }

    private boolean checkInputValues(String name, Coordinates coordinates, String creationDate, long age, Color color, DragonType type, DragonCharacter character, DragonCave cave) {
        if (name == null || name.equals("") || age <= 0) {
            return false;
        }
        if (creationDate==null){
            return true;
        }
        return checkCreationDate(creationDate);
    }

}


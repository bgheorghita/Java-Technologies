package ro.uaic.info.l7.mappers;

public interface DtoMapper<O, DTO> {
    O fromDto(DTO dto);
    DTO toDto(O o);
}

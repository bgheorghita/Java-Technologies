package ro.uaic.info.l7.mappers;

import ro.uaic.info.l7.dtos.PreferenceDto;
import ro.uaic.info.l7.entities.Preference;
import ro.uaic.info.l7.services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PreferenceDtoMapper implements DtoMapper<Preference, PreferenceDto> {
    @Inject
    private UserService userService;

    @Override
    public Preference fromDto(PreferenceDto preferenceDto) {
        Preference preference = new Preference();
        preference.setId(preferenceDto.getId());
        preference.setRegistrationNumber(preferenceDto.getRegistrationNumber());
        preference.setContent(preferenceDto.getContent());
        preference.setUser(userService.findByUsername(preferenceDto.getUsername()));
        return preference;
    }

    @Override
    public PreferenceDto toDto(Preference o) {
        PreferenceDto preferenceDto = new PreferenceDto();
        preferenceDto.setId(o.getId());
        preferenceDto.setContent(o.getContent());
        preferenceDto.setUsername(o.getUser().getUsername());
        preferenceDto.setRegistrationNumber(o.getRegistrationNumber());
        return preferenceDto;
    }
}

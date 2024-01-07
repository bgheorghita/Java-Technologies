package org.acme.mappers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dtos.PreferenceDto;
import org.acme.models.Preference;
import org.acme.models.User;
import org.acme.repositories.UserRepository;

@ApplicationScoped
public class PreferenceMapper {
    @Inject
    private UserRepository userRepository;

    public Preference fromDto(PreferenceDto preferenceDto) {
        String username = preferenceDto.getUsername();
        String content = preferenceDto.getContent();
        String registrationNumber = preferenceDto.getRegistrationNumber();

        Preference preference = new Preference();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("User must not be null."));
        preference.setUser(user);
        preference.setContent(content);
        preference.setRegistrationNumber(registrationNumber);

        return preference;
    }

    public PreferenceDto toDto(Preference preference) {
        Long id = preference.getId();
        User user = preference.getUser();
        String username = user == null ? null : user.getUsername();
        String content = preference.getContent();
        String registrationNumber = preference.getRegistrationNumber();

        return new PreferenceDto(id, username, content, registrationNumber);
    }
}

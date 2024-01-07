package org.acme.services;


import org.acme.models.Preference;

import java.util.List;

public interface PreferenceService {
    List<Preference> findAll();
}

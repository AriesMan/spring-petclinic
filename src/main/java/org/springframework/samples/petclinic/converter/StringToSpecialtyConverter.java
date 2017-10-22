package org.springframework.samples.petclinic.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
final class StringToSpecialtyConverter implements Converter<String, Set<Specialty>> {

    @Override
    public Set<Specialty> convert(String source) {
        Specialty spec  = new Specialty();
        spec.setName(source);
        Set<Specialty> set = new HashSet<Specialty>();
        set.add(spec);
        return set;
    }
}





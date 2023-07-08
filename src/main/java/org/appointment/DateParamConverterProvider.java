package org.appointment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

/**
 * @author Julius Krah
 */
@Provider
public class DateParamConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        System.out.println("Attempting to parse date");
         if (LocalDate.class.equals(rawType)) {
            @SuppressWarnings("unchecked")
            ParamConverter<T> paramConverter = (ParamConverter<T>) new DateParameterConverter();
            return paramConverter;
        }
        return null;
    }

    static class DateParameterConverter implements ParamConverter<LocalDate> {

        @Override
        public LocalDate fromString(String date) {
            return LocalDate.parse(date);

        }

        @Override
        public String toString(LocalDate date) {
            return date.toString();
        }
    }

}

package ru.kuznetsova.topjava.lunchVotingSystem.util;

import javassist.NotFoundException;
import ru.kuznetsova.topjava.lunchVotingSystem.model.AbstractEntity;

public class ValidationUtil {

    public static <T> T checkNotFoundWithId(T object, int id) throws NotFoundException {
        return checkNotFound(object, "id=" + id);
    }

    private ValidationUtil() {
    }

    public static <T> T checkNotFound(T object, String msg) throws NotFoundException {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) throws NotFoundException {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(AbstractEntity entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

}
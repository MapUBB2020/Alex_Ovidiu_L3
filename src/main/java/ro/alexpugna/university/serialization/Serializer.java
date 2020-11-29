package ro.alexpugna.university.serialization;

public interface Serializer<E> {
    String serialize(E entity);

    E deserialize(String entityString);
}

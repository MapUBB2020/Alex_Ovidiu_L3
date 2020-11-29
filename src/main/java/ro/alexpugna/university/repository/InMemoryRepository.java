package ro.alexpugna.university.repository;

import ro.alexpugna.university.model.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryRepository<E extends Entity> implements ICrudRepository<E> {
    protected List<E> items = new ArrayList<>();

    @Override
    public E findOne(Long id) {
        return items.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Iterable<E> findAll() {
        return items.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public E save(E entity) {
        if (items.stream().anyMatch(item -> item.getId().equals(entity.getId()))) {
            return entity;
        }
        items.add(entity);
        return null;
    }

    @Override
    public E delete(Long id) {
        if (items.stream().noneMatch(item -> item.getId().equals(id))) {
            return null;
        }
        E entity = findOne(id);
        items.remove(entity);
        return entity;
    }

    @Override
    public E update(E entity) {
        if (items.stream().noneMatch(item -> item.getId().equals(entity.getId()))) {
            return entity;
        }
        delete(entity.getId());
        save(entity);
        return null;
    }
}

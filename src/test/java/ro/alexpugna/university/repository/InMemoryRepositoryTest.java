package ro.alexpugna.university.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.alexpugna.university.model.Entity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {
    private InMemoryRepository<Entity> repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryRepository<>();
    }

    @Test
    public void testFindOne() {
        Entity entity = repository.findOne(123L);
        assertNull(entity);

        repository.save(new Entity(123L));
        Entity entity2 = repository.findOne(123L);
        assertNotNull(entity2);
        assertEquals(new Entity(123L), entity2);
    }

    @Test
    public void testFindAll() {
        Iterable<Entity> all = repository.findAll();
        assertFalse(all.iterator().hasNext());

        repository.save(new Entity(1L));
        repository.save(new Entity(2L));
        repository.save(new Entity(3L));
        Iterable<Entity> all1 = repository.findAll();
        List<Entity> entities = StreamSupport.stream(all1.spliterator(), false).collect(Collectors.toList());
        assertEquals(3, entities.size());
        assertEquals(new Entity(1L), entities.get(0));
        assertEquals(new Entity(2L), entities.get(1));
        assertEquals(new Entity(3L), entities.get(2));
    }

    @Test
    public void testSave() {
        Iterable<Entity> all = repository.findAll();
        assertFalse(all.iterator().hasNext());

        repository.save(new Entity(1L));
        repository.save(new Entity(2L));
        repository.save(new Entity(3L));
        Iterable<Entity> all1 = repository.findAll();
        List<Entity> entities = StreamSupport.stream(all1.spliterator(), false).collect(Collectors.toList());
        assertEquals(3, entities.size());
        assertEquals(new Entity(1L), entities.get(0));
        assertEquals(new Entity(2L), entities.get(1));
        assertEquals(new Entity(3L), entities.get(2));
    }

    @Test
    public void testUpdate() {
        repository.save(new Entity(1L));

        Entity entity = repository.update(new Entity(2L));
        assertEquals(new Entity(2L), entity);
        Entity entity1 = repository.update(new Entity(1L));
        assertNull(entity1);
    }

    @Test
    public void testDelete() {
        repository.save(new Entity(1L));

        Entity entity = repository.delete(2L);
        assertNull(entity);
        Entity entity1 = repository.delete(1L);
        assertEquals(new Entity(1L), entity1);
    }
}

package ro.alexpugna.university.repository;

import ro.alexpugna.university.model.Entity;
import ro.alexpugna.university.serialization.Serializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileRepository<E extends Entity> extends InMemoryRepository<E> {
    private Serializer<E> serializer;
    private String filePath;

    public FileRepository(Serializer<E> serializer, String filePath) {
        this.serializer = serializer;
        this.filePath = filePath;
    }

    private void loadFromFile() {
        items.clear();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        while (scanner.hasNextLine()) {
            items.add(serializer.deserialize(scanner.nextLine()));
        }

        scanner.close();
    }

    private void writeToFile() {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintWriter finalPrintWriter = printWriter;
        items.forEach(item -> finalPrintWriter.write(serializer.serialize(item) + "\n"));
        printWriter.close();
    }

    @Override
    public E findOne(Long id) {
        loadFromFile();
        return super.findOne(id);
    }

    @Override
    public Iterable<E> findAll() {
        loadFromFile();
        return super.findAll();
    }

    @Override
    public E save(E entity) {
        loadFromFile();
        E e = super.save(entity);
        writeToFile();
        return e;
    }

    @Override
    public E delete(Long id) {
        loadFromFile();
        E e = super.delete(id);
        writeToFile();
        return e;
    }

    @Override
    public E update(E entity) {
        loadFromFile();
        E e = super.update(entity);
        writeToFile();
        return e;
    }
}

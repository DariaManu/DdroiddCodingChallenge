package shopping.service;

import shopping.model.Item;
import shopping.repo.InMemoryRepo;

import java.util.List;

public class Service {
    private InMemoryRepo repo;

    public Service(InMemoryRepo repo) {
        this.repo = repo;
    }

    public List<Item> getAllItems() {
        return repo.getAllItems();
    }
}

package shopping;

import shopping.model.Item;
import shopping.repo.InMemoryRepo;

public class Main {
    public static void main(String[] args) {
        InMemoryRepo repo = new InMemoryRepo();
        for(Item item: repo.getAllItems()) {
            System.out.println(item);
        }
    }
}

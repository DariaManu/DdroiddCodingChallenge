package shopping;

import shopping.model.Item;
import shopping.model.ShippingRate;
import shopping.repo.InMemoryRepo;
import shopping.service.Service;
import shopping.ui.UI;

public class Main {
    public static void main(String[] args) {
        InMemoryRepo repo = new InMemoryRepo();
        Service service = new Service(repo);
        UI ui = new UI(service);
        ui.run();
    }
}

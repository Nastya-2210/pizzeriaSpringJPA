package com.academy.pizzeria;
import com.academy.pizzeria.Model.DishType;
import com.academy.pizzeria.Model.Orders;
import com.academy.pizzeria.Service.MenuService;
import com.academy.pizzeria.Service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;

@SpringBootApplication
public class PizzeriaApplication implements CommandLineRunner {
	private final OrderService orderService;
	private final MenuService menuService;

	public PizzeriaApplication(OrderService orderService, MenuService menuService) {
		this.orderService = orderService;
		this.menuService = menuService;
	}

	public static void main(String[] args) {
		SpringApplication.run(PizzeriaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Выводим меню по категориям
		menuService.printMenuByType(DishType.PIZZA);
		menuService.printMenuByType(DishType.SUSHI);


		// Создаем новый заказ
		System.out.println("\n=== СОЗДАНИЕ НОВОГО ЗАКАЗА ===");
		try {
			Orders newOrder = orderService.placeOrder(List.of(1L, 3L), 1L);
			orderService.printOrderDetails(newOrder.getId());
		} catch (RuntimeException e) {
			System.err.println("Не удалось создать заказ: " + e.getMessage());
			System.out.println("\nДоступные блюда:");
			menuService.printFullMenu();
		}

		// Показываем существующие заказы
		System.out.println("\n=== СУЩЕСТВУЮЩИЕ ЗАКАЗЫ ===");
		List<Orders> orders = orderService.getCustomerOrders(1L);
		if (orders.isEmpty()) {
			System.out.println("Заказов нет");
		} else {
			orders.forEach(order -> orderService.printOrderDetails(order.getId()));
		}
	}


}

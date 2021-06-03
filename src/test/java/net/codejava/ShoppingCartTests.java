package net.codejava;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import net.codejava.product.Product;
import net.codejava.shoppingcart.CartItem;
import net.codejava.shoppingcart.CartItemRepository;
import net.codejava.user.Role;
import net.codejava.user.User;
import net.codejava.user.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ShoppingCartTests {
	
	@Autowired
	private CartItemRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testAddItemFromDatabase() {
		Product product = entityManager.find(Product.class, 8);
		User user = entityManager.find(User.class, 1);
		
		CartItem item = new CartItem(1, product, user);
		
		repo.save(item);
	}
	
	@Test
	public void testAddItemByIds() {
		Product product = new Product(9);
		User user = new User(5);
		
		CartItem item = new CartItem(2, product, user);
		
		repo.save(item);
	}
	
	@Test
	public void testAddMultipleItems() {
		User user = new User(1);
		Product product1 = new Product(8);
		Product product2 = new Product(9);
		Product product3 = new Product(10);
		
		CartItem item1 = new CartItem(1, product1, user);
		CartItem item2 = new CartItem(4, product2, user);
		CartItem item3 = new CartItem(5, product3, user);
		
		repo.saveAll(List.of(item1, item2, item3));
	}
	
	@Test
	public void testListItems() {
		List<CartItem> listItems = repo.findAll();
		listItems.forEach(System.out::println);
	}
	
	@Test
	public void testUpdateItem() {
		CartItem cartItem = repo.findById(1).get();
		cartItem.setQuantity(10);
		cartItem.setProduct(new Product(3));
	}
	
	@Test
	public void testRemoveItem() {
		repo.deleteById(1);
	}
}

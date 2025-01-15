package exam;

import org.junit.jupiter.api.Test;
import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Grade
public class SantaInventoryTest {

	@Test
	public void testSimple() {
		SantaInventory inventory = new SantaInventory();
		inventory.put(20, 4);
		inventory.put(1, 10);
		inventory.put(35, 2);
		inventory.put(40, 1);
		inventory.put(5, 8);
		assertEquals(5, inventory.median());
		inventory.put(1, 10);
		assertEquals(1, inventory.median());
	}

	@Test
	public void testEmpty() {
		SantaInventory inventory = new SantaInventory();
		assertThrows(IllegalArgumentException.class, () -> inventory.median());
	}


	@Test
	public void testLeft() {
		SantaInventory inventory = new SantaInventory();
		inventory.put(4, 5);
		inventory.put(2, 2);
		inventory.put(1, 1);
		inventory.put(3, 10);
		inventory.put(20, 3);
		inventory.put(6, 2);
		assertEquals(3, inventory.median());
	}

	@Test
	public void testRight() {
		SantaInventory inventory = new SantaInventory();
		inventory.put(4, 5);
		inventory.put(2, 2);
		inventory.put(1, 1);
		inventory.put(3, 1);
		inventory.put(10, 3);
		inventory.put(5, 3);
		assertEquals(4, inventory.median());
	}

	@Test
	public void testOneByOne() {
		SantaInventory inventory = new SantaInventory();
		inventory.put(1, 1);
		inventory.put(2, 1);
		inventory.put(1, 1);
		inventory.put(2, 1);
		inventory.put(3, 1);
		inventory.put(1, 1);
		inventory.put(1, 1);
		inventory.put(4, 1);
		inventory.put(4, 1);
		inventory.put(3, 1);
		inventory.put(3, 1);
		inventory.put(5, 1);
		inventory.put(4, 1);
		// [1, 1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 5] : 13
		assertEquals(3, inventory.median());
	}
}

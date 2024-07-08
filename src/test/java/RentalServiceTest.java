

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import tool_rental_app.RentalAgreement;
import tool_rental_app.RentalService;

public class RentalServiceTest {
	
	@InjectMocks	
	RentalService rentalService = new RentalService();
	
    @Test
    public void testCheckout() {
        

        // Test case 1: Invalid discount
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
        });
        assertEquals("Discount percent must be in the range 0-100", exception.getMessage());

        // Test case 2: Valid rental
        RentalAgreement agreement = rentalService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        assertNotNull(agreement);
        assertEquals("LADW", agreement.getTool().getCode());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
        assertEquals(10, agreement.getDiscountPercent());

    }
}


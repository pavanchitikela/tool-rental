package tool_rental_app;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        RentalService rentalService = new RentalService();

        try {
            RentalAgreement agreement = rentalService.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
            System.out.println(agreement);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            RentalAgreement agreement = rentalService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
            System.out.println(agreement);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
               
    }

}

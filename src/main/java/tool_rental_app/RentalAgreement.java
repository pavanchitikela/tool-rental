package tool_rental_app;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalAgreement {
    private Tool tool;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private double dailyRentalCharge;
    private int chargeDays;
    private double preDiscountCharge;
    private int discountPercent;
    private double discountAmount;
    private double finalCharge;
    

    public Tool getTool() {
		return tool;
	}
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	public int getRentalDays() {
		return rentalDays;
	}
	public void setRentalDays(int rentalDays) {
		this.rentalDays = rentalDays;
	}
	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public double getDailyRentalCharge() {
		return dailyRentalCharge;
	}
	public void setDailyRentalCharge(double dailyRentalCharge) {
		this.dailyRentalCharge = dailyRentalCharge;
	}
	public int getChargeDays() {
		return chargeDays;
	}
	public void setChargeDays(int chargeDays) {
		this.chargeDays = chargeDays;
	}
	public double getPreDiscountCharge() {
		return preDiscountCharge;
	}
	public void setPreDiscountCharge(double preDiscountCharge) {
		this.preDiscountCharge = preDiscountCharge;
	}
	public int getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getFinalCharge() {
		return finalCharge;
	}
	public void setFinalCharge(double finalCharge) {
		this.finalCharge = finalCharge;
	}
	public RentalAgreement(Tool tool, int rentalDays, LocalDate checkoutDate, int discountPercent) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.discountPercent = discountPercent;
        calculateRentalAgreement();
    }
    private String formatDate(LocalDate date) {
           // date format pattern
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

           // Formatting the date
           String formattedDate = date.format(formatter);
           
           return formattedDate;
      }
    private void calculateRentalAgreement() {
        // Calculate due date
        this.dueDate = checkoutDate.plusDays(rentalDays);

        // Calculate charge days
        this.chargeDays = calculateChargeDays(checkoutDate, dueDate, tool);

        // Calculate pre-discount charge
        this.dailyRentalCharge = tool.getDailyCharge();
        this.preDiscountCharge = dailyRentalCharge * chargeDays;

        // Calculate discount amount
        this.discountAmount = preDiscountCharge * discountPercent / 100;

        // Calculate final charge
        this.finalCharge = preDiscountCharge - discountAmount;
    }
    
  

    private int calculateChargeDays(LocalDate start, LocalDate end, Tool tool) {
        int chargeDays = 0;
        for (LocalDate date = start.plusDays(1); !date.isAfter(end); date = date.plusDays(1)) {
            if (isChargeableDay(date, tool)) {
                chargeDays++;
            }
        }
        return chargeDays;
    }

    private boolean isChargeableDay(LocalDate date, Tool tool) {
        // holiday check logic
        if (isHoliday(date)) {
            return tool.isHolidayCharge();
        }
        // weekend check logic
        if (date.getDayOfWeek().getValue() >= 6) { // Saturday=6, Sunday=7
            return tool.isWeekendCharge();
        }
        return tool.isWeekdayCharge();
    }

    private boolean isHoliday(LocalDate date) {
        // Implementing holiday check logic for Independence Day and Labor Day
    	
    	 // Check for Independence Day (July 4th)
        if (date.getMonthValue() == 7 && date.getDayOfMonth() == 4) {
            return true; // July 4th is always a holiday
        }

        // Check for Labor Day (First Monday in September)
        if (date.getMonthValue() == 9) { // September
            LocalDate firstMonday = LocalDate.of(date.getYear(), 9, 1);
            if (firstMonday.getDayOfWeek() == DayOfWeek.MONDAY && date.isEqual(firstMonday)) {
                return true; // First Monday of September is Labor Day
            }
        }

        // If Independence Day falls on a weekend, observe it on the closest weekday
        if (date.getMonthValue() == 7 && date.getDayOfWeek() == DayOfWeek.MONDAY &&
                (date.getDayOfMonth() == 3 || date.getDayOfMonth() == 5)) {
            return true;
        }

        // If Labor Day falls on a weekend, observe it on the closest weekday
        if (date.getMonthValue() == 9 && date.getDayOfWeek() == DayOfWeek.MONDAY &&
                (date.getDayOfMonth() == 1 || date.getDayOfMonth() == 7)) {
            return true;
        }

        // If none of the above conditions are met, it's not a holiday
        return false;
    }


    @Override
    public String toString() {
        return String.format(
                "Tool code: %s%nTool type: %s%nTool brand: %s%nRental days: %d%nCheck out date: %s%nDue date: %s%nDaily rental charge: $%.2f%nCharge days: %d%nPre-discount charge: $%.2f%nDiscount percent: %d%%%nDiscount amount: $%.2f%nFinal charge: $%.2f",
                tool.getCode(), tool.getType(), tool.getBrand(), rentalDays, formatDate(checkoutDate), formatDate(dueDate), dailyRentalCharge,
                chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }
}

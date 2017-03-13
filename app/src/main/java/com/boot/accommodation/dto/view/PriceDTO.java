package com.boot.accommodation.dto.view;

/**
 * PriceDTO
 *
 * @author tuanlt
 * @since 11:53 AM 1/24/17
 */
public class PriceDTO extends BaseDTO {

    private double from;
    private double to;
    private String currency;

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

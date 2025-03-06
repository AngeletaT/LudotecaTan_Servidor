package com.ccsw.tutorial.dto;

/**
 * @author ccsw
 *
 */
public class LoanDto {

    private Long id;

    private String rentalDate;

    private String returnDate;

    private GameDto game;

    private ClientDto client;

    /**
     * @return id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {

        this.id = id;
    }

    /**
     * @return rentalDate
     */
    public String getRentalDate() {

        return this.rentalDate;
    }

    /**
     * @param rentalDate new value of {@link #getRentalDate}.
     */
    public void setRentalDate(String rentalDate) {

        this.rentalDate = rentalDate;
    }

    /**
     * @return returnDate
     */
    public String getReturnDate() {

        return this.returnDate;
    }

    /**
     * @param returnDate new value of {@link #getReturnDate}.
     */
    public void setReturnDate(String returnDate) {

        this.returnDate = returnDate;
    }

    /**
     * @return game
     */
    public GameDto getGame() {

        return this.game;
    }

    /**
     * @param game new value of {@link #getGame}.
     */
    public void setGame(GameDto game) {

        this.game = game;
    }

    /**
     * @return client
     */
    public ClientDto getClient() {

        return this.client;
    }

    /**
     * @param client new value of {@link #getClient}.
     */
    public void setClient(ClientDto client) {

        this.client = client;
    }

}

package com.parkit.parkingsystem;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;

public class TicketDaoTest {
	
	@Test
	public void givenAnEmptyParkingLot_whenACarCallsForSlotOne_thenUpdateParkingShouldReturnTrue() {
		
		ParkingSpotDAO emptyParking = new ParkingSpotDAO();
		emptyParking.updateToEmptyParking();
		
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
						
		boolean result = emptyParking.updateParking(parkingSpot);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void givenAnEmptyParkingLot_whenNoCallsForASlot_thenUpdateParkingShouldReturnFalse() {
		
		ParkingSpotDAO emptyParking = new ParkingSpotDAO();
		emptyParking.updateToEmptyParking();
		
		ParkingSpot parkingSpot = new ParkingSpot(0, ParkingType.CAR,false);
						
		boolean result = emptyParking.updateParking(parkingSpot);
		
		assertThat(result).isFalse();
	}
	
	//@Disabled
	@Test
	public void givenARecurringUser_whenHeAsksForHisStatus_thenRecurringUserTicketShouldReturnTrue() {
		
		DataBaseConfig dataBaseConfig = new DataBaseConfig();
		
		Connection con = null;
        try {
            con = dataBaseConfig.getConnection();  
            PreparedStatement ps = con.prepareStatement(DBConstants.INSERT_TWO_TICKET_SAME_AA_REGISTRATION_NUMBER_TEST);
            ps.execute();        
        }catch (Exception ex){
            System.out.println("Error saving ticket info" + ex);
        }finally {
            dataBaseConfig.closeConnection(con);
        }
		
		String vehicleRegNumber = "AA";
		TicketDAO ticketDao = new TicketDAO();
						
		boolean result = ticketDao.recurringUserTicket(vehicleRegNumber);
		
		assertThat(result).isTrue();
	}
}

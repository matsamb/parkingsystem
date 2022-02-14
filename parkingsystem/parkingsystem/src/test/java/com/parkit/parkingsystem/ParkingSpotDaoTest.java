package com.parkit.parkingsystem;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.model.ParkingSpot;


public class ParkingSpotDaoTest {
	
	@Test
	public void givenAnEmptyParkingLot_whenACarCallsForASlot_thenTheSlotIdShouldBeOne() {
		
		ParkingSpotDAO emptyParking = new ParkingSpotDAO();
		emptyParking.updateToEmptyParking();
						
		int result = emptyParking.getNextAvailableSlot(ParkingType.CAR);
		
		assertThat(result).isEqualTo(1);
	}
	
	@Test
	public void givenAnEmptyParkingLot_whenABikeCallsForASlot_thenTheSlotIdShouldBeFour() {
		
		ParkingSpotDAO emptyParking = new ParkingSpotDAO();
		emptyParking.updateToEmptyParking();
						
		int result = emptyParking.getNextAvailableSlot(ParkingType.BIKE);
		
		assertThat(result).isEqualTo(4);
	}
	
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
	
	
}

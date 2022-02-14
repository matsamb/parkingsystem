package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

	private static ParkingService parkingServiceUnderTest;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;
	// private static Ticket ticket;
	// private static ParkingSpot parkingSpot;
	@Disabled
	@BeforeEach
	private void setUpPerTestForCarsForOneHour() {
		try {
			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
			when (inputReaderUtil.readSelection()).thenReturn(1);

			ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
			Ticket ticket = new Ticket();

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis() - (60 * 60 * 1000));// 1 hour stay

			ticket.setInTime(cal);
			ticket.setParkingSpot(parkingSpot);
			ticket.setVehicleRegNumber("ABCDEF"); 

			// when(ticket.getPrice()).thenReturn(Fare.CAR_RATE_PER_HOUR);

			when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
			when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
			//when(parkingSpotDAO.updateToEmptyParking());


			parkingServiceUnderTest = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	}

	@Disabled
	@Test // check fare
	public void processExitingVehicleTest() {
		//double result = parkingServiceUnderTest.processExitingVehicle();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
		assertThat(result).isEqualTo(Fare.CAR_RATE_PER_HOUR);
	}

	// check availability +1
	
	//@Disabled
	@Test //check availability -1
	public void givenAnEmptyParkingLot_whenACarEnters_thenTheSlotShouldBeOne() {
		
		ParkingSpotDAO emptyParking = new ParkingSpotDAO();
		emptyParking.updateToEmptyParking();
		//when (inputReaderUtil.readSelection()).thenReturn(1);
		
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		parkingSpotDAO.updateParking(parkingSpot);
		
		parkingServiceUnderTest = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		
		int result = parkingServiceUnderTest.processIncomingVehicle() ;
		
		//verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
		
		assertThat(result).isEqualTo(1);
	}
	@Disabled
	@Test //check availability -1
	public void givenAnEmptyParkingLot_whenABikeEnters_thenTheSlotShouldBeFour() {

		ParkingSpotDAO emptyParking = new ParkingSpotDAO();
		emptyParking.updateToEmptyParking();
		when (inputReaderUtil.readSelection()).thenReturn(2);
		
		parkingServiceUnderTest = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		
		int result = parkingServiceUnderTest.processIncomingVehicle() ;
		
		//verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
		assertThat(result).isEqualTo(4);
	}
}

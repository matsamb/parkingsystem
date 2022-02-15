package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeEach
    private void setUpPerTest() {
        try {
            when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
            Ticket ticket = new Ticket();
            
            Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis() - (60 * 60 * 1000));// 1 hour stay
            
            ticket.setInTime(cal);
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");
            when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);

            when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }
    }

    @Test
    public void processExitingVehicleTest(){
        parkingService.processExitingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }

}
			parkingServiceUnderTest = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	}

	@Disabled
	@Test 
	public void processExitingVehicleTest() {
		//double result = parkingServiceUnderTest.processExitingVehicle();
		parkingServiceUnderTest = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		parkingServiceUnderTest.processExitingVehicle();
		
		//verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
		//verify(ticketDAO, Mockito.times(2)).updateTicket(any(Ticket.class));
		verify(ticketDAO, Mockito.times(1)).getTicket(any(String.class));
		//verify(ticketDAO, Mockito.times(1)).recurringUserTicket(any(String.class));
	}
	
	@Disabled
	@Test 
	public void givenAnEmptyParkingLot_whenACarEnters_thenTheSlotShouldBeOne() {		
		parkingServiceUnderTest = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		
		int result = parkingServiceUnderTest.processIncomingVehicle() ;
		
		//verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
		verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSlot(any(ParkingType.class));
		//verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
		assertThat(result).isEqualTo(1);
	}
/*	@Disabled
	@Test //check availability -1
	public void givenAnEmptyParkingLot_whenABikeEnters_thenTheSlotShouldBeFour() {

		ParkingSpotDAO emptyParking = new ParkingSpotDAO();
		emptyParking.updateToEmptyParking();
		when (inputReaderUtil.readSelection()).thenReturn(2);
		
		parkingServiceUnderTest = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		
		int result = parkingServiceUnderTest.processIncomingVehicle() ;
		
		//verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
		assertThat(result).isEqualTo(4);
	}*/
}

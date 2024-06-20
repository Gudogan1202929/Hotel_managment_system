package com.example.HotelManagementSystem;

import com.example.HotelManagementSystem.entity.*;
import com.example.HotelManagementSystem.repository.*;
import com.example.HotelManagementSystem.user.dto.Role;
import com.example.HotelManagementSystem.user.entity.User;
import com.example.HotelManagementSystem.user.repositorie.UserRepo;
import com.example.HotelManagementSystem.utils.constant.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepository;
    private final CustomerRepo customerRepository;
    private final RoomReservationRepo reservationRepository;
    private final RoomRepo roomRepository;
    private final InvoiceRepo invoiceRepository;
    private final HouseKeppingRepo housekeepingRepository;
    private final EmployeeRepo employeeRepository;
    private final CancellationRequestRepo cancellationRequestRepository;

    @Autowired
    public DataInitializer(UserRepo userRepository,
                           CustomerRepo customerRepository,
                           RoomReservationRepo reservationRepository,
                           RoomRepo roomRepository,
                           InvoiceRepo invoiceRepository,
                           HouseKeppingRepo housekeepingRepository,
                           EmployeeRepo employeeRepository,
                           CancellationRequestRepo cancellationRequestRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.invoiceRepository = invoiceRepository;
        this.housekeepingRepository = housekeepingRepository;
        this.employeeRepository = employeeRepository;
        this.cancellationRequestRepository = cancellationRequestRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        // Initialize Users
//        User admin = User.builder()
//                .username("admin")
//                .password("password")
//                .email("admin@example.com")
//                .role(Role.ADMIN)
//                .build();
//        userRepository.save(admin);
//
//        User user = User.builder()
//                .username("user")
//                .password("password")
//                .email("user@example.com")
//                .role(Role.USER)
//                .build();
//        userRepository.save(user);
//
//        // Initialize Customers
//        Customer customer1 = Customer.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .phone("1234567890")
//                .address("123 Main St")
//                .build();
//        customerRepository.save(customer1);
//
//        Customer customer2 = Customer.builder()
//                .firstName("Jane")
//                .lastName("Smith")
//                .phone("0987654321")
//                .address("456 Elm St")
//                .build();
//        customerRepository.save(customer2);
//
//        // Initialize Rooms
//        Room room1 = Room.builder()
//                .floorNumber(1)
//                .roomClass(standardClass)
//                .status(availableStatus)
//                .roomNumber(101)
//                .build();
//        roomRepository.save(room1);
//
//        Room room2 = Room.builder()
//                .floorNumber(1)
//                .roomClass(deluxeClass)
//                .status(occupiedStatus)
//                .roomNumber(102)
//                .build();
//        roomRepository.save(room2);
//
//        // Initialize Reservations
//        Reservation reservation1 = Reservation.builder()
//                .customer(customer1)
//                .room(room1)
//                .checkInDate(new Date())
//                .checkOutDate(new Date())
//                .status(ReservationStatus.CONFIRMED)
//                .createdAt(new Date())
//                .build();
//        reservationRepository.save(reservation1);
//
//        // Initialize Invoices
//        Invoice invoice1 = Invoice.builder()
//                .user(admin)
//                .reservation(reservation1)
//                .amount(new BigDecimal("100.00"))
//                .status(InvoiceStatus.PAID)
//                .createdAt(new Date())
//                .build();
//        invoiceRepository.save(invoice1);
//
//        // Initialize Employees
//        Employee employee1 = Employee.builder()
//                .firstName("Alice")
//                .lastName("Johnson")
//                .position("Housekeeper")
//                .phone("1122334455")
//                .address("789 Oak St")
//                .build();
//        employeeRepository.save(employee1);
//
//        // Initialize Housekeeping
//        Housekeeping housekeeping1 = Housekeeping.builder()
//                .room(room1)
//                .employee(employee1)
//                .taskDate(new Date())
//                .status(HousekeepingStatus.COMPLETED)
//                .build();
//        housekeepingRepository.save(housekeeping1);
//
//        // Initialize CancellationRequests
//        CancellationRequest cancellationRequest1 = CancellationRequest.builder()
//                .reservation(reservation1)
//                .admin(admin)
//                .status(CancellationStatus.PENDING)
//                .requestedAt(new Date())
//                .build();
//        cancellationRequestRepository.save(cancellationRequest1);
    }
}

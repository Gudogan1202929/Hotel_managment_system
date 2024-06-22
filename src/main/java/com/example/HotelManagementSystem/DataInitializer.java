package com.example.HotelManagementSystem;

import com.example.HotelManagementSystem.entity.*;
import com.example.HotelManagementSystem.repository.*;
import com.example.HotelManagementSystem.user.dto.Role;
import com.example.HotelManagementSystem.user.entity.User;
import com.example.HotelManagementSystem.user.repositorie.UserRepo;
import com.example.HotelManagementSystem.utils.constant.SystemConstants;
import com.example.HotelManagementSystem.utils.encryption.Encryption;
import com.example.HotelManagementSystem.utils.encryption.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
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

        //add an admin and employee
        //for admin
        String password = "12345678";
        System.out.println("Encrypted password: " + Encryption.Encrypt(password) + "the password is: " + password);
        //encrypt password
        String encryptedPassword = Hash.hashing(password);
        User admin = User.builder()
                .username("mohammadnmosleh123@gmail.com")
                .password(encryptedPassword)
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);

        //for ordinary customer
        password = "123123123";
        System.out.println("Encrypted password: " + Encryption.Encrypt(password) + "the password is: " + password);
        //encrypt password
        encryptedPassword = Hash.hashing(password);
        User customer = User.builder()
                .username("omarqalaweh@gmail.com")
                .password(encryptedPassword)
                .role(Role.USER)
                .build();

        userRepository.save(customer);

        //add rooms
        Room room1 = Room.builder()
                .roomNumber(101)
                .roomInfo("Single Room")
                .roomStatus("AVAILABLE")
                .bedNumber(1)
                .floorNumber(1)
                .build();

        Room room2 = Room.builder()
                .roomNumber(102)
                .roomInfo("Double Room")
                .roomStatus("AVAILABLE")
                .bedNumber(2)
                .floorNumber(1)
                .build();

        Room room3 = Room.builder()
                .roomNumber(201)
                .roomInfo("Single Room")
                .roomStatus("AVAILABLE")
                .bedNumber(1)
                .floorNumber(2)
                .build();

        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);


        //add customers

        User user = userRepository.findByUsername("customer");

        Customer customer1 = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .phone("1234567890")
                .address("123, Main Street, New York")
                .user(user)
                .build();

        customerRepository.save(customer1);

        //add employee
        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .position("Housekeeper")
                .phone("0987654321")
                .address("456, Main Street, New York")
                .build();

        employeeRepository.save(employee);

        //add housekeeping tasks
        Housekeeping housekeeping = Housekeeping.builder()
                .room(room1)
                .status(Housekeeping.Status.PENDING)
                .employee(employee)
                .taskDate(new Date())
                .build();

        housekeepingRepository.save(housekeeping);

        //add reservation
        Room room = roomRepository.findByRoomNumber(101);
        Customer customer_to_reserve = customerRepository.findByFirstName("John");
        Reservation reservation = Reservation.builder()
                .room(room)
                .customer(customer_to_reserve)
                .createdAt(LocalDateTime.now())
                .checkInDate(null)
                .checkOutDate(null)
                .expectedArrivalTime(new Date(2021, Calendar.NOVEMBER, 10, 12, 0))
                .expectedLeavingTime(new Date(2021, Calendar.NOVEMBER, 15, 12, 0))
                .build();

        reservationRepository.save(reservation);

        //add invoice
        Invoice invoice = Invoice.builder()
                .reservation(reservation)
                .createdAt(LocalDateTime.now())
                .status(Invoice.Status.PENDING)
                .amount(500.50)
                .build();

        invoiceRepository.save(invoice);
    }
}

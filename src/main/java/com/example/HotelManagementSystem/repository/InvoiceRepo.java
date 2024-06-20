package com.example.HotelManagementSystem.repository;

import com.example.HotelManagementSystem.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long> , JpaSpecificationExecutor<Invoice> {

    //find invoice by reservation id
    Invoice findByReservationId(Long reservationId);

}

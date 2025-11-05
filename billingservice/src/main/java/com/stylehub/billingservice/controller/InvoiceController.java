package com.stylehub.billingservice.controller;

import com.stylehub.billingservice.entity.InvoiceRequest;
import com.stylehub.billingservice.entity.InvoiceResponse;
import com.stylehub.billingservice.service.InvoiceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping
    public InvoiceResponse create(@RequestBody InvoiceRequest request) {
        return service.createInvoice(request);
    }
}

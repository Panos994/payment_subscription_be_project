package payment_subscription_management_system.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payment_subscription_management_system.demo.dto.InvoiceResponseDTO;
import payment_subscription_management_system.demo.entity.Invoice;
import payment_subscription_management_system.demo.entity.Subscription;
import payment_subscription_management_system.demo.service.InvoiceService;

import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/create")
    public ResponseEntity<InvoiceResponseDTO> createInvoice(@RequestParam UUID subscriptionId){
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(invoiceService.generateInvoice(subscriptionId)));
    }

    @PatchMapping("{invoiceId}")
    public ResponseEntity<Void> markInvoicePaid(@PathVariable UUID invoiceId){
        invoiceService.markInvoicePaid(invoiceId);
       return ResponseEntity.ok().build();
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<Page<Invoice>> getInvoiceBySubscription(@PathVariable UUID subscriptionId, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getInvoicesBySubscription(subscriptionId, pageable));
    }

    private InvoiceResponseDTO mapToResponse(Invoice invoice){
        return  InvoiceResponseDTO.builder()
                .invoiceNumber(invoice.getInvoiceNumber())
                .status(invoice.getStatus().toString())
                .totalAmount(invoice.getTotalAmount())
                .build();
    }
}

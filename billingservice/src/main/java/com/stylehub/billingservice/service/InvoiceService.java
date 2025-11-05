package com.stylehub.billingservice.service;

import com.stylehub.billingservice.entity.InvoiceRequest;
import com.stylehub.billingservice.entity.InvoiceResponse;
import com.stylehub.billingservice.entity.ProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class InvoiceService {
    private final RestClient restClient;
    private final String inventoryBaseUrl;

    public InvoiceService(RestClient.Builder builder,
                          @Value("${inventory.url}") String inventoryBaseUrl) {
        this.restClient = builder.build();
        this.inventoryBaseUrl = inventoryBaseUrl;
    }

    public InvoiceResponse createInvoice(InvoiceRequest request) {
        // llama al microservicio Inventory para obtener el producto
        ProductDTO product = restClient.get()
                .uri(inventoryBaseUrl + "/products/" + request.productId())
                .retrieve()
                .body(ProductDTO.class);

        if (product == null) {
            return new InvoiceResponse(null, null,"Producto no encontrado", 0);
        }

        if (request.quantity() > product.stock()) {
            return new InvoiceResponse(null, product, "Stock insuficiente", 0);
        }

        double total = product.price() * request.quantity();
        return new InvoiceResponse(1001, product,"Factura creada para " + product.name(), total);
    }
}

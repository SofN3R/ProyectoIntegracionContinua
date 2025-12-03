package com.stylehub.inventoryservice.json;

import com.stylehub.inventoryservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ProductJsonTest {

    @Autowired JacksonTester<Product> json;

    @Test
    void serialize_hasExpectedFields() throws Exception {
        var p = new Product(1L,"Camiseta básica","M",19.99,10);
        var content = json.write(p);

        assertThat(content).hasJsonPathNumberValue("$.id");
        assertThat(content).extractingJsonPathStringValue("$.name").isEqualTo("Camiseta básica");
        assertThat(content).extractingJsonPathStringValue("$.size").isEqualTo("M");
        assertThat(content).extractingJsonPathNumberValue("$.price").isEqualTo(19.99);
        assertThat(content).extractingJsonPathNumberValue("$.stock").isEqualTo(10);
    }

    @Test
    void deserialize_buildsProduct() throws Exception {
        var jsonText = """
      {"id":3,"name":"Chaqueta","size":"M","price":59.99,"stock":2}
    """;
        var obj = json.parse(jsonText).getObject();

        assertThat(obj.id()).isEqualTo(3L);
        assertThat(obj.name()).isEqualTo("Chaqueta");
        assertThat(obj.price()).isEqualTo(59.99);
    }
}

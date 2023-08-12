package com.drunar.coincorner.unittest;

import com.drunar.coincorner.dto.PageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageResponseTest {

    @Test
    void testOf() {
        List<String> content = new ArrayList<>();
        content.add("Item 1");
        content.add("Item 2");
        content.add("Item 3");

        Page<String> page = new PageImpl<>(content, PageRequest.of(0, 10), 3);

        PageResponse<String> pageResponse = PageResponse.of(page);

        assertEquals(content, pageResponse.getContent());
        assertEquals(0, pageResponse.getMetadata().getPage());
        assertEquals(10, pageResponse.getMetadata().getSize());
        assertEquals(3, pageResponse.getMetadata().getTotalElements());
    }
}
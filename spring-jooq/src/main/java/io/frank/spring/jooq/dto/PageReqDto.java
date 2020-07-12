package io.frank.spring.jooq.dto;

import lombok.Data;

/**
 * @author jinjunliang
 **/
@Data
public class PageReqDto {
    int size;
    int page;

    public int limit() {
        return size * page;
    }
}

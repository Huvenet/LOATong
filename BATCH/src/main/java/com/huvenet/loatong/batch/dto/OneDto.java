package com.huvenet.loatong.batch.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OneDto {
    private String one;

    @Override
    public String toString() {
        return one;
    }
}

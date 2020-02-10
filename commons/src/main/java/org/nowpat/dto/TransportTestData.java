package org.nowpat.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransportTestData {

    private Integer numericalValue;
    private String textValue;
    private TransportTestSubData subData;
}

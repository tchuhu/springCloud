package entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment implements Serializable {
    private Long id;

    private String serial;

    private static final long serialVersionUID = 1L;
}
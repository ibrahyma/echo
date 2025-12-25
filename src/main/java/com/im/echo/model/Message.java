package com.im.echo.model;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Builder.Default
    private UUID id = UUID.randomUUID();

    private String content;
    private User sender;

    @Builder.Default
    private boolean error = false;

}

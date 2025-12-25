package com.im.echo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Builder.Default
    private UUID id = UUID.randomUUID();

    private String name;
}

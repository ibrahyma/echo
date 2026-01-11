package com.im.echo.model;

import lombok.*;

import java.util.UUID;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Builder.Default
    private UUID id = UUID.randomUUID();

    @EqualsAndHashCode.Include
    private String name;
}

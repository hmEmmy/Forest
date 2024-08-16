package me.emmy.hub.feature.cosmetic.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 16:45
 */
@Getter
@RequiredArgsConstructor
public enum CosmeticType {
    PARTICLE("Particle"),
    HAT("Hat"),

    ;

    private String name;

    CosmeticType(String name) {
        this.name = name;
    }
}

package me.emmy.hub.feature.parkour;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Emmy
 * @project Forest
 * @date 21/08/2024 - 19:20
 */
@Getter
@Setter
public class Parkour {
    private long startTime;
    private long endTime;
    private long timeTaken;

    public Parkour() {
        this.startTime = System.currentTimeMillis();
        this.endTime = 0;
        this.timeTaken = 0;
    }
}

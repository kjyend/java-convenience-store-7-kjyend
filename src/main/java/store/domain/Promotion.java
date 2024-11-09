package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

public class Promotion {

    private String name;
    private int buy;
    private int free;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion(String name, String buy, String free, String startDate, String endDate) {
        this.name = name;
        this.buy = strToInt(buy);
        this.free = strToInt(free);
        this.startDate = date(startDate);
        this.endDate = date(endDate);
    }

    private LocalDateTime date(String startDate) {
        return LocalDateTime.parse(startDate);
    }

    private int strToInt(String buy) {
        return Integer.parseInt(buy);
    }

    public boolean promotionDate() {
        if (this.startDate.isAfter(DateTimes.now()) && this.endDate.isBefore(DateTimes.now())) {
            return true;
        }
        return false;
    }

    public boolean sameName(String name) {
        return this.name.equals(name);
    }

    public int getBuy() {
        return this.buy;
    }

    public int getFree() {
        return this.free;
    }
}

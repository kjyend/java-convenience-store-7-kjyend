package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(startDate, formatter);
        return date.atStartOfDay();
    }

    private int strToInt(String buy) {
        return Integer.parseInt(buy);
    }

    public boolean promotionDate() {
        return this.startDate.isBefore(DateTimes.now()) && this.endDate.isAfter(DateTimes.now());
    }

    public String getName() {
        return this.name;
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

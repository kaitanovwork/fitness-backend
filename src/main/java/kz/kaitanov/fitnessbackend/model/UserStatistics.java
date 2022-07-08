package kz.kaitanov.fitnessbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_statistics")
public class UserStatistics {

    @Id
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "step_count",
                    joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "date")
    @Column(name = "steps_amount")
    private Map<String, Integer> stepCount;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "water_count",
                    joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "date")
    @Column(name = "water_amount")
    private Map<String, Integer> waterCount;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "running_kilometer_count",
                    joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "date")
    @Column(name = "running_kilometer")
    private Map<String, Integer> runningKilometerCount;

    @OneToOne
    @MapsId
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatistics that = (UserStatistics) o;
        return Objects.equals(id, that.id) && Objects.equals(stepCount, that.stepCount) && Objects.equals(waterCount, that.waterCount) && Objects.equals(runningKilometerCount, that.runningKilometerCount) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stepCount, waterCount, runningKilometerCount, user);
    }
}
package ac.inu.noisemaster.core.noise.domain.repository.noise;

import ac.inu.noisemaster.core.noise.domain.model.Noise;
import ac.inu.noisemaster.core.noise.domain.model.QDevice;
import ac.inu.noisemaster.core.noise.util.DecibelUtils;
import ac.inu.noisemaster.core.noise.util.LocalDateTimeUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ac.inu.noisemaster.core.noise.domain.model.QNoise.noise;
import static ac.inu.noisemaster.core.noise.domain.model.QPlace.place;


@Repository
public class NoiseRepositoryImpl extends QuerydslRepositorySupport implements NoiseRepositoryCustom {
    public NoiseRepositoryImpl() {
        super(Noise.class);
    }

    @Override
    public Page<Noise> findDynamicPagingQueryAdvance(String device, Double decibel, String temperature, String tag, String date, Pageable pageable) {
        JPQLQuery<Noise> query = super.from(noise)
                .innerJoin(noise.device, QDevice.device).fetchJoin()
                .innerJoin(noise.place, place).fetchJoin()
                .where(eqDevice(device),
                        eqTemperature(temperature),
                        betweenDecibel(decibel),
                        betweenDate(date))
                .orderBy(noise.createdTime.desc());

        List<Noise> result = Objects.requireNonNull(getQuerydsl())
                .applyPagination(pageable, query)
                .fetch();

        return new PageImpl<>(result, pageable, query.fetchCount());
    }

    private BooleanExpression eqDevice(String device) {
        if (StringUtils.isEmpty(device)) {
            return null;
        }
        return noise.device.name.eq(device);
    }

    private BooleanExpression betweenDecibel(Double decibel) {
        if (decibel == null) {
            return null;
        }
        double from = DecibelUtils.down(decibel);
        double to = DecibelUtils.upperMinusOne(decibel);
        return noise.decibel.between(from, to);
    }

    private BooleanExpression eqTemperature(String temperature) {
        if (StringUtils.isEmpty(temperature)) {
            return null;
        }
        return noise.temperature.eq(temperature);
    }

    private BooleanExpression betweenDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        LocalDateTime from = LocalDateTimeUtils.convertZeroTime(date);
        LocalDateTime to = from.plusHours(24);
        return noise.createdTime.between(from, to);
    }
}

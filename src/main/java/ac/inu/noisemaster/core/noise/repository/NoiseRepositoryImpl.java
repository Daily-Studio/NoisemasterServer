package ac.inu.noisemaster.core.noise.repository;

import ac.inu.noisemaster.core.noise.domain.Noise;
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

import static ac.inu.noisemaster.core.noise.domain.QNoise.noise;


@Repository
public class NoiseRepositoryImpl extends QuerydslRepositorySupport implements NoiseRepositoryCustom {
    public NoiseRepositoryImpl() {
        super(Noise.class);
    }

    @Override
    public Page<Noise> findDynamicPagingQueryAdvance(String device, String decibel, String temperature, String tag, String date, Pageable pageable) {
        JPQLQuery<Noise> query = super.from(noise)
                .where(eqDevice(device),
                        eqTemperature(temperature),
                        eqDecibel(decibel),
                        betweenDate(date));

        List<Noise> result = Objects.requireNonNull(getQuerydsl())
                .applyPagination(pageable, query)
                .fetch();

        return new PageImpl<>(result, pageable, query.fetchCount());
    }

    private BooleanExpression eqDevice(String device) {
        if (StringUtils.isEmpty(device)) {
            return null;
        }
        return noise.device.eq(device);
    }

    private BooleanExpression eqDecibel(String decibel) {
        if (StringUtils.isEmpty(decibel)) {
            return null;
        }
        return noise.decibel.eq(decibel);
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

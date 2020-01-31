package ac.inu.noisemaster.core.noise.repository;

import ac.inu.noisemaster.core.noise.domain.Noise;
import ac.inu.noisemaster.core.noise.domain.QNoise;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;


@Repository
public class NoiseRepositoryImpl extends QuerydslRepositorySupport implements NoiseRepositoryCustom {
    public NoiseRepositoryImpl() {
        super(Noise.class);
    }

    @Override
    public Page<Noise> findDynamicPagingQueryAdvance(String device, String decibel, String temperature, String tag, Pageable pageable) {
        JPQLQuery<Noise> query = super.from(QNoise.noise)
                .where(eqDevice(device),
                        eqTemperature(temperature),
                        eqDecibel(decibel));

        List<Noise> result = Objects.requireNonNull(getQuerydsl())
                .applyPagination(pageable, query)
                .fetch();

        return new PageImpl<>(result, pageable, query.fetchCount());
    }

    private BooleanExpression eqDevice(String device) {
        if (StringUtils.isEmpty(device)) {
            return null;
        }
        return QNoise.noise.device.eq(device);
    }

    private BooleanExpression eqDecibel(String decibel) {
        if (StringUtils.isEmpty(decibel)) {
            return null;
        }
        return QNoise.noise.decibel.eq(decibel);
    }

    private BooleanExpression eqTemperature(String temperature) {
        if (StringUtils.isEmpty(temperature)) {
            return null;
        }
        return QNoise.noise.temperature.eq(temperature);
    }
}

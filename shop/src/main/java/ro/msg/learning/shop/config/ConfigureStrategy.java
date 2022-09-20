package ro.msg.learning.shop.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.exception.ProductsCantBeShipped;
import ro.msg.learning.shop.strategy.MostAbundantStrategy;
import ro.msg.learning.shop.strategy.SingleLocationStrategy;
import ro.msg.learning.shop.strategy.StrategyEnum;
import ro.msg.learning.shop.strategy.StrategyInterface;

@Configuration
@Data
@RequiredArgsConstructor
public class ConfigureStrategy {
    @Value("${strategy}")
    private StrategyEnum strategy;

    private final SingleLocationStrategy singleLocation;
    private final MostAbundantStrategy mostAbundantLocation;

    @Bean
    public StrategyInterface decideStrategy() {
        switch (strategy) {
            case SINGLE_LOCATION:
                return singleLocation;
            case MOST_ABUNDANT:
                return mostAbundantLocation;
            default:
                throw new ProductsCantBeShipped("Can't get place your order. We are sorry!");
        }
    }
}
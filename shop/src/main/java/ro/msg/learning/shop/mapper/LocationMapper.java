package ro.msg.learning.shop.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.LocationDTO;
import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.utils.Address;

@Component
@RequiredArgsConstructor
public class LocationMapper {
    public LocationDTO locationToLocationDTO(Location location) {
        return LocationDTO.builder()
                .address(location.getAddress())
                .name(location.getName())
                .build();

    }

    public Location locationDTOToLocation(LocationDTO locationDTO) {
        return Location.builder()
                .address(locationDTO.getAddress())
                .name(locationDTO.getName())
                .build();
    }
}
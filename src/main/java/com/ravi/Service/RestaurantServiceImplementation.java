package com.ravi.Service;

import com.ravi.dto.RestaurantDto;
import com.ravi.model.Address;
import com.ravi.model.Restaurant;
import com.ravi.model.User;
import com.ravi.repository.AddressRepository;
import com.ravi.repository.RestaurantRepository;
import com.ravi.repository.UserRepository;
import com.ravi.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImplementation implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        restaurant.setDescription(updatedRestaurant.getDescription());
        restaurant.setName(updatedRestaurant.getName());
        restaurant.setOpeningHours(updatedRestaurant.getOpeningHours());
        restaurant.setContactInformation(updatedRestaurant.getContactInformation());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurantRepository.delete(restaurant);

    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String query) {
        return restaurantRepository.findBySearchQuery(query);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(restaurantId);

        if (opt.isEmpty()) {
            throw new Exception("Restaurant not found with id: " + restaurantId);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        if (restaurant == null) {
            throw new Exception("Restaurant not found with owner: " + userId);
        }

        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavourited = false;
        List<RestaurantDto> favourites = user.getFavourites();
        for(RestaurantDto favourite: favourites) {
            if (favourite.getId().equals(restaurantId)) {
                isFavourited = true;
                break;
            }
        }

        if (!isFavourited) {
            favourites.add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public RestaurantDto removeFromFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavourited = false;
        List<RestaurantDto> favourites = user.getFavourites();
        for(RestaurantDto favourite: favourites) {
            if (favourite.getId().equals(restaurantId)) {
                isFavourited = true;
                break;
            }
        }

        if (isFavourited) {
            favourites.removeIf(favourite -> favourite.getId().equals(restaurantId));
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantHeartBeat(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}

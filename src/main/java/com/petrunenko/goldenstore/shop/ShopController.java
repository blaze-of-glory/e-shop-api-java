package com.petrunenko.goldenstore.shop;

import com.petrunenko.goldenstore.shared.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private ShopRepository shopRepository;

    @GetMapping()
    public Iterable<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not exist with id :" + id));
        return ResponseEntity.ok(shop);
    }

    @PostMapping()
    public Shop createShop(@RequestBody Shop shop) {
        return shopRepository.save(shop);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShopById(@PathVariable Long id, @RequestBody Shop shopDetails) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not exist with id :" + id));

        shop.setImg(shopDetails.getImg());
        shop.setAddress(shopDetails.getAddress());
        shop.setOpenTime(shopDetails.getOpenTime());
        shop.setCloseTime(shopDetails.getCloseTime());

        Shop updatedShop = shopRepository.save(shop);
        return ResponseEntity.ok(updatedShop);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteShop(@PathVariable Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not exist with id :" + id));

        shopRepository.delete(shop);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

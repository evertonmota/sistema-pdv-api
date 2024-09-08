package com.sistemas.pdv.service;

import com.sistemas.pdv.dto.ProductDTO;
import com.sistemas.pdv.dto.SaleDTO;
import com.sistemas.pdv.entity.ItemSale;
import com.sistemas.pdv.entity.Product;
import com.sistemas.pdv.entity.Sale;
import com.sistemas.pdv.entity.User;
import com.sistemas.pdv.repository.ItemSaleRepository;
import com.sistemas.pdv.repository.ProductRepository;
import com.sistemas.pdv.repository.SaleRepository;
import com.sistemas.pdv.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SaleService {
    // Ao utilizar o LOMBOK poderemos utilizar @RequiredArgsConstructor nao havendo a necessidade de criar instâncias de UserRepository
    // private final UserRepository userRepository;
    /* ublic SaleService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;

    @Transactional
    public Long save(SaleDTO saleDTO){
        User user = userRepository.findById(saleDTO.getUserId()).get();

        Sale newSale = new Sale();
             newSale.setUser(user);
             newSale.setSaleDate(LocalDate.now());
        List<ItemSale> item = getItemSale(saleDTO.getItems());

        newSale = saleRepository.save(newSale);

        saveItemSale(item, newSale);

        return newSale.getId();
    }

    private void saveItemSale(List<ItemSale> items, Sale newSale) {
        for(ItemSale item : items ){
            item.setSale(newSale);
            itemSaleRepository.save(item);
        }
    }

    /* O MAP ele transforma uma COLEÇÃO em OUTRA. Neste exemplo eu tenho uma coleção de products e o retorno sera uma coleção de ItemSale. */
    private List<ItemSale> getItemSale(List<ProductDTO> products){
        return products.stream().map( item -> {
            Product product = productRepository.getReferenceById(item.getProductId());
            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());
            return itemSale;
        }).collect(Collectors.toList());
    }

}

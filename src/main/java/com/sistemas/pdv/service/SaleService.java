package com.sistemas.pdv.service;

import com.sistemas.pdv.dto.ProductDTO;
import com.sistemas.pdv.dto.ProductInfoDTO;
import com.sistemas.pdv.dto.SaleDTO;
import com.sistemas.pdv.dto.SaleInfoDTO;
import com.sistemas.pdv.entity.ItemSale;
import com.sistemas.pdv.entity.Product;
import com.sistemas.pdv.entity.Sale;
import com.sistemas.pdv.entity.User;
import com.sistemas.pdv.exceptions.InvalidOperationException;
import com.sistemas.pdv.exceptions.NoItemException;
import com.sistemas.pdv.repository.ItemSaleRepository;
import com.sistemas.pdv.repository.ProductRepository;
import com.sistemas.pdv.repository.SaleRepository;
import com.sistemas.pdv.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
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

    public List<SaleInfoDTO> findAll(){
        return saleRepository.findAll().stream().map( sale -> getSaleInfo(sale)).collect(Collectors.toList());
    }
    private SaleInfoDTO getSaleInfo(Sale sale) {
/*      SaleInfoDTO dto = new SaleInfoDTO();
        dto.setUser(sale.getUser().getName());
        dto.setData(sale.getSaleDate().format(DateTimeFormatter.ofPattern("dd/MM//yyyy")));
        dto.setProducts(getProductInfo(sale.getItems()));
*/
        //PADRÃO BUILDER

        return SaleInfoDTO.builder()
                .user(sale.getUser().getName())
                .data(sale.getSaleDate().format(DateTimeFormatter.ofPattern("dd/MM//yyyy")))
                .products(getProductInfo(sale.getItems()))
                .build();
    }
    private List<ProductInfoDTO> getProductInfo(List<ItemSale> items) {

        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }
        //USO DO PADRAO BUILDER
        return items.stream().map(
                 item -> ProductInfoDTO
                .builder()
                .id(item.getProduct().getId())
                .name(item.getProduct().getName())
                         .quantity(item.getQuantity()).build()
        ).collect(Collectors.toList());

    /*  return items.stream().map(item -> {
                ProductInfoDTO dto = new ProductInfoDTO();
                dto.setId(item.getProduct().getId());
                dto.setName(item.getProduct().getName());
                dto.setQuantity(item.getQuantity());
            return dto;
        }).collect(Collectors.toList()); */
    }
    @Transactional
    public Long save(SaleDTO saleDTO){

            User user  = userRepository.findById((saleDTO.getUserId())).orElseThrow( () -> new NoItemException("Usuário não encontrado."));

            // get() é quando voce tem certeza que vai retornar um usuario se nao vai estourar o erro.
            //User user = userRepository.findById(saleDTO.getUserId()).get();
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

        if(products.isEmpty()){
            throw new InvalidOperationException("Não foi possível adicionar a venda sem Itens.");
        }

        return products.stream().map( item -> {
            Product product = productRepository.findById(item.getProductId()).orElseThrow( () -> new NoItemException("Item da venda não encontrado."));

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            if(product.getQuantity() == 0){
                throw new NoItemException("Produto sem Estoque.");
            }else if(product.getQuantity() < item.getQuantity()){
                throw new InvalidOperationException(
                        String.format("A quantidade de Item (%d) é maior que a quantidade disponível no estoque (%d)", item.getQuantity(), product.getQuantity()));
            }
            int total = product.getQuantity() - item.getQuantity();
            product.setQuantity(total);
            productRepository.save(product);

            return itemSale;
        }).collect(Collectors.toList());
    }

    public SaleInfoDTO findByID(long id) {
        Sale sale =  saleRepository.findById(id).orElseThrow( () -> new NoItemException("Venda não encontrada.") );
        return getSaleInfo(sale);
    }
}

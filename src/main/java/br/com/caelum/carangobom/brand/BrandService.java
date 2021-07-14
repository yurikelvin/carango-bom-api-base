package br.com.caelum.carangobom.brand;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BrandService {

    private BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand findById(Long id) throws Exception {
        Optional<Brand> optional = brandRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundException("Brand not found");
    }
}

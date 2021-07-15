package br.com.caelum.carangobom.brand;

import br.com.caelum.carangobom.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand findById(Long id) throws Exception {
        Optional<Brand> optional = brandRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundException("Brand not found");
    }
}

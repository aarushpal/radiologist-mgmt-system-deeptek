package com.rmssecurity.RMS.repository;

import com.rmssecurity.RMS.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}

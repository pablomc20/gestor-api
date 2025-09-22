package com.gestor.dominator.service;

import com.gestor.dominator.dto.ImageResponse;
import com.gestor.dominator.dto.ProductRequest;
import com.gestor.dominator.dto.ProductResponse;
import com.gestor.dominator.dto.RoomResponse;
import com.gestor.dominator.dto.tag.TagResponse;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.model.Image;
import com.gestor.dominator.model.Product;
import com.gestor.dominator.model.Room;
import com.gestor.dominator.model.Tag;
import com.gestor.dominator.repository.ImageRepository;
import com.gestor.dominator.repository.ProductRepository;
import com.gestor.dominator.repository.RoomRepository;
import com.gestor.dominator.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ImageRepository imageRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> getProductById(String id) {
        return productRepository.findById(id)
                .map(this::convertToResponse);
    }

    public List<ProductResponse> getProductsByCategory(String categoryId) {
        // Validar que la categoría existe
        if (!roomRepository.existsById(categoryId)) {
            throw new DataValidationException("La categoría especificada no existe");
        }
        return productRepository.findByCategory(categoryId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchProducts(String title) {
        return productRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByTag(String tagId) {
        // Validar que el tag existe
        if (!tagRepository.existsById(tagId)) {
            throw new DataValidationException("El tag especificado no existe");
        }
        return productRepository.findByTagsContaining(tagId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse createProduct(ProductRequest request) {
        // Validar que la categoría existe
        if (request.getCategory() != null && !roomRepository.existsById(request.getCategory())) {
            throw new DataValidationException("La categoría especificada no existe");
        }

        // Validar que los tags existen
        if (request.getTags() != null) {
            for (String tagId : request.getTags()) {
                if (!tagRepository.existsById(tagId)) {
                    throw new DataValidationException("Uno de los tags especificados no existe: " + tagId);
                }
            }
        }

        // Validar campos requeridos
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new DataValidationException("El título del producto es requerido");
        }
        if (request.getPrice() == null || request.getPrice() < 0) {
            throw new DataValidationException("El precio debe ser mayor o igual a cero");
        }

        Product product = new Product(
                request.getTitle(),
                request.getCategory(),
                request.getDescription(),
                request.getImages(),
                request.getTags(),
                request.getDimensions(),
                request.getColor(),
                request.getPrice()
        );

        Product savedProduct = productRepository.save(product);
        return convertToResponse(savedProduct);
    }

    public Optional<ProductResponse> updateProduct(String id, ProductRequest request) {
        return productRepository.findById(id)
                .map(product -> {
                    // Validar que la categoría existe
                    if (request.getCategory() != null && !roomRepository.existsById(request.getCategory())) {
                        throw new DataValidationException("La categoría especificada no existe");
                    }

                    // Validar que los tags existen
                    if (request.getTags() != null) {
                        for (String tagId : request.getTags()) {
                            if (!tagRepository.existsById(tagId)) {
                                throw new DataValidationException("Uno de los tags especificados no existe: " + tagId);
                            }
                        }
                    }

                    // Validar campos requeridos
                    if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
                        throw new DataValidationException("El título del producto es requerido");
                    }
                    if (request.getPrice() == null || request.getPrice() < 0) {
                        throw new DataValidationException("El precio debe ser mayor o igual a cero");
                    }

                    product.setTitle(request.getTitle());
                    product.setCategory(request.getCategory());
                    product.setDescription(request.getDescription());
                    product.setImages(request.getImages());
                    product.setTags(request.getTags());
                    product.setDimensions(request.getDimensions());
                    product.setColor(request.getColor());
                    product.setPrice(request.getPrice());
                    product.setUpdatedAt(LocalDateTime.now());

                    Product updatedProduct = productRepository.save(product);
                    return convertToResponse(updatedProduct);
                });
    }

    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ProductResponse convertToResponse(Product product) {
        // Obtener la categoría (Room) relacionada
        RoomResponse category = null;
        if (product.getCategory() != null) {
            Optional<Room> roomOpt = roomRepository.findById(product.getCategory());
            if (roomOpt.isPresent()) {
                category = convertRoomToResponse(roomOpt.get());
            }
        }

        // Obtener los tags relacionados
        List<TagResponse> tags = new ArrayList<>();
        if (product.getTags() != null) {
            for (String tagId : product.getTags()) {
                tagRepository.findById(tagId).ifPresent(tag -> {
                    tags.add(convertTagToResponse(tag));
                });
            }
        }

        List<ImageResponse> images = new ArrayList<>();
        if (product.getImages() != null) {
            for (String imageId : product.getImages()) {
                imageRepository.findById(imageId).ifPresent(image -> {
                    images.add(convertImageToResponse(image));
                });
            }
        }

        return new ProductResponse(
                product.getId(),
                product.getTitle(),
                category,
                product.getDescription(),
                images,
                tags,
                product.getDimensions(),
                product.getColor(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getVersion()
        );
    }

    private RoomResponse convertRoomToResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getSlug(),
                room.getVersion(),
                room.getCreatedAt(),
                room.getUpdatedAt()
        );
    }

    private TagResponse convertTagToResponse(Tag tag) {
        return new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getSlug(),
                tag.getType(),
                tag.getGroup()
        );
    }

    private ImageResponse convertImageToResponse(Image image) {
        return new ImageResponse(
                image.getId(),
                image.getFilename(),
                "/images/file/" + image.getFilename(),
                image.getSize(),
                image.getMimeType(),
                image.getCreatedAt()
        );
    }

}
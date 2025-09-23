package com.gestor.dominator.business;

import com.gestor.dominator.components.ObjectIdConverter;
import com.gestor.dominator.dto.image.ImageResponse;
import com.gestor.dominator.dto.product.ProductRequest;
import com.gestor.dominator.dto.product.ProductResponse;
import com.gestor.dominator.dto.product.ProductWithReferencesResponse;
import com.gestor.dominator.dto.room.RoomResponse;
import com.gestor.dominator.dto.tag.TagResponse;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.model.Product;
import com.gestor.dominator.model.Room;
import com.gestor.dominator.model.Tag;
import com.gestor.dominator.model.Image;
import com.gestor.dominator.repository.ImageRepository;
import com.gestor.dominator.repository.ProductRepository;
import com.gestor.dominator.repository.RoomRepository;
import com.gestor.dominator.repository.TagRepository;
import com.gestor.dominator.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductBusiness implements ProductService {
    
    private final ProductRepository productRepository;

    private final RoomRepository roomRepository;

    private final TagRepository tagRepository;

    private final ImageRepository imageRepository;

    private final ObjectIdConverter objectIdConverter;


    @Override
    public List<ProductWithReferencesResponse> getAllProducts() {
        return productRepository.findAllWithRelations();
    }

    @Override
    public Optional<ProductResponse> getProductById(String id) {
        return productRepository.findById(objectIdConverter.stringToObjectId(id))
                .map(this::convertToResponse);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(String categoryId) {
        if (!roomRepository.existsById(objectIdConverter.stringToObjectId(categoryId))) {
            throw new DataValidationException("La categoría especificada no existe");
        }
        return productRepository.findByCategory(objectIdConverter.stringToObjectId(categoryId)).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> searchProducts(String title) {
        return productRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByTag(String tagId) {
        if (!tagRepository.existsById(objectIdConverter.stringToObjectId(tagId))) {
            throw new DataValidationException("El tag especificado no existe");
        }
        return productRepository.findByTagsContaining(objectIdConverter.stringToObjectId(tagId)).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if (request.category() != null && !roomRepository.existsById(new ObjectId(request.category()))) {
            throw new DataValidationException("La categoría especificada no existe");
        }
        if (request.tags() != null) {
            for (String tagId : request.tags()) {
                if (!tagRepository.existsById(new ObjectId(tagId))) {
                    throw new DataValidationException("Uno de los tags especificados no existe: " + tagId);
                }
            }
        }
        if (request.title() == null || request.title().trim().isEmpty()) {
            throw new DataValidationException("El título del producto es requerido");
        }
        if (request.price() == null || request.price() < 0) {
            throw new DataValidationException("El precio debe ser mayor o igual a cero");
        }

        ObjectId categoryId = request.category() != null ? new ObjectId(request.category()) : null;
        List<ObjectId> tagIds = request.tags() != null
                ? request.tags().stream().map(ObjectId::new).toList()
                : null;
        List<ObjectId> imageIds = request.images() != null
                ? request.images().stream().map(ObjectId::new).toList()
                : null;

        Product product = Product.builder()
        .title(request.title())
        .category(categoryId)
        .description(request.description())
        .images(imageIds)
        .tags(tagIds)
        .dimensions(request.dimensions())
        .color(request.color())
        .price(request.price())
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .version(0)
        .build();


        Product savedProduct = productRepository.save(product);
        return convertToResponse(savedProduct);
    }

    @Override
    public Optional<ProductResponse> updateProduct(String id, ProductRequest request) {
        return productRepository.findById(objectIdConverter.stringToObjectId(id))
                .map(product -> {

                    ObjectId categoryId = request.category() != null ? new ObjectId(request.category()) : null;
                    List<ObjectId> tagIds = request.tags() != null
                            ? request.tags().stream().map(ObjectId::new).toList()
                            : null;
                    List<ObjectId> imageIds = request.images() != null
                            ? request.images().stream().map(ObjectId::new).toList()
                            : null;

                    Product productToUpdate = Product.builder()
                        .title(request.title())
                        .category(categoryId)
                        .description(request.description())
                        .images(imageIds)
                        .tags(tagIds)
                        .dimensions(request.dimensions())
                        .color(request.color())
                        .price(request.price())
                        .updatedAt(LocalDateTime.now())
                        .build();

                    Product updatedProduct = productRepository.save(productToUpdate);
                    return convertToResponse(updatedProduct);
                });
    }

    @Override
    public boolean deleteProduct(String id) {
        if (productRepository.existsById(objectIdConverter.stringToObjectId(id))) {
            productRepository.deleteById(objectIdConverter.stringToObjectId(id));
            return true;
        }
        return false;
    }

    private ProductResponse convertToResponse(Product product) {
        RoomResponse category = null;
        if (product.category() != null) {
            Optional<Room> roomOpt = roomRepository.findById(product.category());
            if (roomOpt.isPresent()) {
                category = convertRoomToResponse(roomOpt.get());
            }
        }
        List<TagResponse> tags = new ArrayList<>();
        if (product.tags() != null) {
            for (ObjectId tagId : product.tags()) {
                tagRepository.findById(tagId).ifPresent(tag -> {
                    tags.add(convertTagToResponse(tag));
                });
            }
        }
        List<ImageResponse> images = new ArrayList<>();
        if (product.images() != null) {
            for (ObjectId imageId : product.images()) {
                imageRepository.findById(imageId).ifPresent(image -> {
                    images.add(convertImageToResponse(image));
                });
            }
        }
        return new ProductResponse(
                product.id(),
                product.title(),
                category,
                product.description(),
                images,
                tags,
                product.dimensions(),
                product.color(),
                product.price(),
                product.createdAt(),
                product.updatedAt(),
                product.version()
        );
    }

    private RoomResponse convertRoomToResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .slug(room.getSlug())
                .version(room.getVersion())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .build();
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

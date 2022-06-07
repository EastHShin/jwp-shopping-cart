package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ThumbnailImageDto;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> products() {
        return ResponseEntity.ok(productService.findProducts());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> add(@RequestBody ProductRequest productRequest) {
        final ThumbnailImageDto thumbnailImageRequest = productRequest.getThumbnailImage();
        final ProductResponse productResponse = productService
                .addProduct(productRequest.getName(), productRequest.getPrice(), productRequest.getStockQuantity(),
                        thumbnailImageRequest.getUrl(), thumbnailImageRequest.getAlt());
        return ResponseEntity.created(URI.create("/api/products/" + productResponse.getId())).body(productResponse);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> product(@PathVariable final Long productId) {
        final ProductResponse productResponse = productService.findProductById(productId);
        return ResponseEntity.ok(productResponse);
    }
}

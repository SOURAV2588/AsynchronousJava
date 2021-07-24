package com.sourav.thread;

import com.sourav.domain.Product;
import com.sourav.domain.ProductInfo;
import com.sourav.domain.Review;
import com.sourav.service.ProductInfoService;
import com.sourav.service.ReviewService;

import static com.sourav.util.CommonUtil.stopWatch;
import static com.sourav.util.LoggerUtil.log;

public class ProductServiceUsingThread {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingThread(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();

        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        Thread productInfoThread = new Thread(productInfoRunnable);
        
        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);
        Thread reviewThread = new Thread(reviewRunnable);
        
        productInfoThread.start();
        reviewThread.start();
        
        productInfoThread.join();
        reviewThread.join();
        
        ProductInfo productInfo = productInfoRunnable.getProductInfo();
        Review review = reviewRunnable.getReview();
        
//        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
//        Review review = reviewService.retrieveReviews(productId); // blocking call

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingThread productService = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
    
    private class ProductInfoRunnable implements Runnable {
    	
    	private ProductInfo productInfo;
    	private String productId;
    	
    	public ProductInfoRunnable(String productId) {
    		this.productId = productId;
    	}
    	@Override
    	public void run() {
    		productInfo = productInfoService.retrieveProductInfo(productId);
    	}
		public ProductInfo getProductInfo() {
			return productInfo;
		}
		public void setProductInfo(ProductInfo productInfo) {
			this.productInfo = productInfo;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
    	
    }
    
    private class ReviewRunnable implements Runnable {
    	
    	private Review review;
    	private String productId;
    	
    	public ReviewRunnable(String productId) {
    		this.productId = productId;
    	}
    	@Override
    	public void run() {
    		review = reviewService.retrieveReviews(productId);
    	}
		public Review getReview() {
			return review;
		}
		public void setReview(Review review) {
			this.review = review;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
    	
    }
}

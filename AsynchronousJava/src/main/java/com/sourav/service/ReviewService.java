package com.sourav.service;

import com.sourav.domain.Review;

import static com.sourav.util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        return new Review(200, 4.5);
    }
}

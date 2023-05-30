package com.unzilemedet.controller;


import com.unzilemedet.repository.entity.Favorite;
import com.unzilemedet.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.unzilemedet.constant.ApiUrls.FAVORITE;


@RestController
@RequestMapping(FAVORITE)
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;



}

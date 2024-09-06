package com.example.mediatracker.controller;

import com.example.mediatracker.dto.MediaItemRecordDto;
import com.example.mediatracker.model.MediaItemModel;
import com.example.mediatracker.service.MediaItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/media-item")
public class MediaItemController {

    private final MediaItemService mediaItemService;

    public MediaItemController(MediaItemService mediaItemService) {
        this.mediaItemService = mediaItemService;
    }

    @GetMapping
    public ResponseEntity<List<MediaItemModel>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(name = "size", defaultValue = "20") Integer size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<MediaItemModel> mediaItemsListPage = mediaItemRepository.findAll(pageable);
//        if(!mediaItemsListPage.isEmpty()) {
//            for(MediaItemModel mediaItem : mediaItemsListPage) {
//                Long id = mediaItem.getId();
//                mediaItem.add(linkTo(methodOn(MediaItemController.class).findById(id)).withSelfRel());
//            }
//        }
        List<MediaItemModel> mediaItemListPage = mediaItemService.findAllMediaItem(page, size);
        return ResponseEntity.ok(mediaItemListPage);
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Object> findById(@PathVariable Long requestedId) {
//        Optional<MediaItemModel> mediaItem = mediaItemRepository.findById(requestedId);
//        if(mediaItem.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        mediaItem.get().add(linkTo(methodOn(MediaItemController.class).findAll(0, 20)).withRel("MediaItemsList"));
        Optional<MediaItemModel> mediaItem = mediaItemService.findMediaItemById(requestedId);
        if(mediaItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mediaItem.get());
    }

    @PostMapping
    public ResponseEntity<Void> saveMediaItem(@RequestBody MediaItemRecordDto mediaItemRecordDto,
                                         UriComponentsBuilder ucb) {
        MediaItemModel savedMediaItem = mediaItemService.saveMediaItem(mediaItemRecordDto);

        URI locationOfNewMediaItem = ucb
                .path("media-item/{id}")
                .buildAndExpand(savedMediaItem.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewMediaItem).build();
    }

    @PutMapping("/{requestedId}")
    public ResponseEntity<Void> updateMediaItem(@PathVariable Long requestedId,
                                           @RequestBody @Valid MediaItemRecordDto mediaItemRecordDto) {
//        Optional<MediaItemModel> mediaItem = mediaItemRepository.findById(requestedId);
//        if(mediaItem.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        MediaItemModel mediaItemToUpdate = mediaItem.get();
//        BeanUtils.copyProperties(mediaItemRecordDto, mediaItemToUpdate);
//        mediaItemRepository.save(mediaItemToUpdate);
        if(mediaItemService.updateMediaItem(requestedId, mediaItemRecordDto)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deleteMediaItem(@PathVariable Long requestedId) {
//        Optional<MediaItemModel> mediaItemToDelete = mediaItemRepository.findById(requestedId);
//        if(mediaItemToDelete.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        mediaItemRepository.delete(mediaItemToDelete.get());
        if(mediaItemService.deleteMediaItem(requestedId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

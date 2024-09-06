package com.example.mediatracker.repository;

import com.example.mediatracker.model.MediaItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaItemRepository extends JpaRepository<MediaItemModel, Long> {
}

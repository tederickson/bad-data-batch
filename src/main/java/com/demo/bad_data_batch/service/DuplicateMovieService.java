package com.demo.bad_data_batch.service;

import com.demo.bad_data_batch.domain.DuplicateMovieDigest;
import com.demo.bad_data_batch.mapper.DuplicateModelMapper;
import com.demo.bad_data_batch.model.DuplicateMovie;
import com.demo.bad_data_batch.repository.DuplicateMovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DuplicateMovieService {
    private final DuplicateMovieRepository duplicateMovieRepository;

    public Page<DuplicateMovieDigest> findMismatchedTitles(final Pageable pageable) {
        Page<DuplicateMovie> pages = duplicateMovieRepository.findMismatchedTitles(pageable);

        long totalElements = pages.getTotalElements();
        List<DuplicateMovieDigest> content = pages.getContent().stream()
                .map(DuplicateModelMapper::toRest)
                .toList();

        return new PageImpl<>(content, pageable, totalElements);
    }
}

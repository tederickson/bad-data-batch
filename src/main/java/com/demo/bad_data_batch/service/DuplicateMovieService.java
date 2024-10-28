package com.demo.bad_data_batch.service;

import com.demo.bad_data_batch.domain.DuplicateMovieDigest;
import com.demo.bad_data_batch.domain.DuplicateMoviePageDigest;
import com.demo.bad_data_batch.mapper.DuplicateModelMapper;
import com.demo.bad_data_batch.model.DuplicateMovie;
import com.demo.bad_data_batch.model.IDuplicateMovieCount;
import com.demo.bad_data_batch.repository.DuplicateMovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DuplicateMovieService {
    private final DuplicateMovieRepository duplicateMovieRepository;
    private final MovieService movieService;

    public Page<DuplicateMovieDigest> findMismatchedTitles(final Pageable pageable) {
        Page<DuplicateMovie> page = duplicateMovieRepository.findMismatchedTitles(pageable);

        long totalElements = page.getTotalElements();
        List<DuplicateMovieDigest> content = page.getContent().stream()
                .map(DuplicateModelMapper::toRest)
                .toList();

        return new PageImpl<>(content, pageable, totalElements);
    }

    public Page<DuplicateMoviePageDigest> findDuplicateTitles(final Pageable pageable) {
        Page<IDuplicateMovieCount> page = duplicateMovieRepository.findDuplicateTitles(pageable);
        long totalElements = page.getTotalElements();
        List<IDuplicateMovieCount> duplicateMovieCounts = page.getContent();
        List<DuplicateMoviePageDigest> content = new ArrayList<>(duplicateMovieCounts.size());

        for (IDuplicateMovieCount duplicateMovieCount : duplicateMovieCounts) {
            DuplicateMoviePageDigest digest = new DuplicateMoviePageDigest();

            digest.setCount(duplicateMovieCount.getMovieCount());
            digest.setOriginalMovie(movieService.getMovie(duplicateMovieCount.getMovieKey()));
            List<DuplicateMovieDigest> duplicates =
                    duplicateMovieRepository.findByMovieId(duplicateMovieCount.getMovieKey()).stream()
                            .map(DuplicateModelMapper::toRest)
                            .toList();
            digest.setDuplicates(duplicates);

            content.add(digest);
        }

        return new PageImpl<>(content, pageable, totalElements);
    }
}

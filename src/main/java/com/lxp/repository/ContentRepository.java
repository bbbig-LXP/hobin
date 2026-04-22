package com.lxp.repository;

import com.lxp.domain.Content;
import java.util.List;
import java.util.Optional;

public interface ContentRepository {

    Content save(Content content);

    Optional<Content> findById(Long id);

    List<Content> findAll(Long sectionId);

    void update(Content content);

    boolean softDelete(Long id);


}

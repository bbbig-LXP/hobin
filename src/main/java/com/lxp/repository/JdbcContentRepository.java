package com.lxp.repository;

import com.lxp.common.DBConnectionManager;
import com.lxp.domain.Content;
import com.lxp.domain.Content.ContentStatus;
import com.lxp.domain.Content.ContentType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcContentRepository implements ContentRepository {

    @Override
    public Content save(Content content) {
        String sql = "INSERT INTO contents (section_id , title , content_type , status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, content.getSectionId());
            pstmt.setString(2, content.getTitle());
            pstmt.setString(3, content.getContentType().name());
            pstmt.setString(4, content.getContentStatus().name());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    return new Content(
                        id,
                        content.getSectionId(),
                        content.getTitle(),
                        content.getContentType(),
                        content.getContentStatus(),
                        LocalDateTime.now(),
                        LocalDateTime.now()

                    );
                } else {
                    throw new SQLException("콘텐츠는 저장 했으나 생성된 ID를 가져오지 못함");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("콘텐츠 저장 에러", e);
        }

    }

    @Override
    public Optional<Content> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("조회할 ID는 1 이상이여야 합니다");
        }
        String sql = "SELECT * FROM contents WHERE id = ? AND status != 'HIDDEN'";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Content content = new Content(
                        rs.getLong("id"),
                        rs.getLong("section_id"),
                        rs.getString("title"),
                        ContentType.valueOf(rs.getString("content_type")),
                        ContentStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                    );
                    return Optional.of(content);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("콘텐트 단일 조회 에러", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Content> findAll(Long sectionId) {
        if (sectionId == null || sectionId <= 0) {
            throw new IllegalArgumentException("조회할 ID는 1 이상이여야 합니다");
        }
        String sql = "SELECT * FROM contents WHERE section_id = ? AND status != 'HIDDEN'";
        List<Content> contents = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, sectionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Content content = new Content(
                        rs.getLong("id"),
                        rs.getLong("section_id"),
                        rs.getString("title"),
                        ContentType.valueOf(rs.getString("content_type")),
                        ContentStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                    );
                    contents.add(content);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("콘텐츠 전체 조회 에러", e);
        }

        return contents;
    }

    @Override
    public void update(Content content) {
        if (content == null || content.getId() == null) {
            throw new IllegalArgumentException("수정 실패 아직 저장되지 않은 강좌 입니다(ID null");
        }
        String sql = "UPDATE contents SET title = ?, content_type = ?, status = ? , updated_at = NOW() WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, content.getTitle());
            pstmt.setString(2, content.getContentType().name());
            pstmt.setString(3, content.getContentStatus().name());
            pstmt.setLong(4, content.getId());

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("콘텐츠 수정 실패 : ID 못찾음");
            }
            System.out.println("콘텐츠 수정 완료");
        } catch (SQLException e) {
            throw new RuntimeException("콘텐츠 수정 에러", e);
        }


    }

    @Override
    public boolean softDelete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("삭제할 ID는 1 이상이여야 합니다");
        }
        String sql = "UPDATE contents SET status = 'HIDDEN' WHERE id = ? AND status != 'HIDDEN'";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            int deleteCount = pstmt.executeUpdate();

            if (deleteCount == 0) {
                return false;

            }


        } catch (SQLException e) {
            throw new RuntimeException("컨텐츠 삭제 실패", e);
        }
        return true;
    }
}

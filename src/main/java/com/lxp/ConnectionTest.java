package com.lxp;

import com.lxp.common.DBConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) {

        try (Connection conn = DBConnectionManager.getConnection()) {
            if (conn != null) {
                System.out.println("✅ 축하합니다! DB 연결에 성공했습니다.");
                System.out.println("연결된 DB 정보: " + conn.getMetaData().getDriverName());
            }
        } catch (SQLException e) {
            System.err.println("❌ 연결 실패!");
            System.err.println("에러 이유: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
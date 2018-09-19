package com.dao;

import java.util.List;

public interface AdminDao {
    List<String> findRoleByAdminId(int id);
}

package com.sgl.model.DAO;

import java.util.List;

public abstract class DAOClass <T>{
    protected String sqlStatement;

    public String getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }
    
    public abstract boolean register(T object);
    public abstract boolean edit(T object);
    public abstract boolean delete(T object);
    public abstract List<T> search(T object);
    public abstract List<T> searchAll();
}

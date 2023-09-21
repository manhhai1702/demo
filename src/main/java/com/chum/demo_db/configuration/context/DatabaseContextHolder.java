package com.chum.demo_db.configuration.context;

public class DatabaseContextHolder {
    private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<DbType>();

    public static void setDbType(DbType dbType) {
        if(dbType == null){
            throw new NullPointerException();
        }
        contextHolder.set(dbType);
    }

    public static DbType getDbType() {
        return (DbType) contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}

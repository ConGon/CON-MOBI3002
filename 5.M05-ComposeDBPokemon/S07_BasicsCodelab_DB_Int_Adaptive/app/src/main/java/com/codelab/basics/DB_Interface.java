package com.codelab.basics;

import java.util.List;

public interface DB_Interface {

    public int count();

    public int save(Pokemon pokemon);

    public int update(Pokemon pokemon);  // Not implemented

    public int deleteById(Long id);  // Not implemented

    public List<Pokemon> findAll();

    public String getNameById(Long id);  // Not implemented

    public Pokemon getMax();

    public void incAccessCount(long id);

    public long getMostAccessed();

}

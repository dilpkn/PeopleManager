package org.example.dao;

import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
       return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
               //первый аргумент sql запрос, второй роумаппер - отображает строки из таблицы в сущности(здесь в персон)
    }

    public Person show(int id){                                         //список элементов, которые заменяются на вопросы
        return jdbcTemplate.query("select * from person where id = ?",  new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);

    }

    public void save(ыPerson person)  {
        jdbcTemplate.update("insert into person values(1,?,?,?)", person.getName(),person.getAge(),person.getEmail());
    }

    public void update(int id, Person updatePerson){
        jdbcTemplate.update("update person set name =?,age=?,email = ? where id = ?", updatePerson.getName(), updatePerson.getAge(), updatePerson.getEmail(), id);

    }

    public void delete(int id){
        jdbcTemplate.update("delete from person where id = ?", id);
    }

}

package com.maddev.coozy.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ChoreTest {
    private Chore chore;
    @BeforeEach
    public void setUp() {
        LocalDate dueDate =LocalDate.of(2024,10,10);
        chore = new Chore(1, 1, "clean house", "clean bath", 10, "1", "HOME", dueDate,false);
    }

    @Test
    void setId() {
        chore.setId(2);
        assertEquals(2,chore.getId());
    }

    @Test
    void setUserId() {
        chore.setUserId(2);
        assertEquals(2, chore.getUserId());
    }

    @Test
    void setName() {
        chore.setName("clean floor");
        assertEquals("clean floor", chore.getName());
    }

    @Test
    void setDescription() {
        chore.setDescription("clean room");
        assertEquals("clean room", chore.getDescription());
    }

    @Test
    void setReward() {
        chore.setReward(20);
        assertEquals(20,chore.getReward());
    }

    @Test
    void setHome() {
        chore.setHome("2");
        assertEquals("2", chore.getHome());
    }

    @Test
    void setIcon() {
        chore.setIcon("ANCHOR");
        assertEquals("ANCHOR", chore.getIcon());
    }

    @Test
    void setDueDate() {
        LocalDate dueDate =LocalDate.of(2024,10,20);
        chore.setDueDate(dueDate);
        assertEquals(dueDate, chore.getDueDate());
    }

    @Test
    void setCompleted() {
        chore.setCompleted(true);
        assertTrue(chore.isCompleted());
    }
}
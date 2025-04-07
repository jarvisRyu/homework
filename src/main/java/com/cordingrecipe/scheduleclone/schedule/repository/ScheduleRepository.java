package com.cordingrecipe.scheduleclone.schedule.repository;

import com.cordingrecipe.scheduleclone.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
                                             //관리할 entity, entity 기본키
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}

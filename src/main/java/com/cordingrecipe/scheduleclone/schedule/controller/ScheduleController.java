package com.cordingrecipe.scheduleclone.schedule.controller;

import com.cordingrecipe.scheduleclone.schedule.dto.request.ScheduleSaveRequestDto;
import com.cordingrecipe.scheduleclone.schedule.dto.request.ScheduleUPdateRequestDto;
import com.cordingrecipe.scheduleclone.schedule.dto.response.SchedulePageResponseDto;
import com.cordingrecipe.scheduleclone.schedule.dto.response.ScheduleUpdateResponseDto;
import com.cordingrecipe.scheduleclone.schedule.dto.response.ScheduleResponseDto;
import com.cordingrecipe.scheduleclone.schedule.dto.response.ScheduleSaveResponseDto;
import com.cordingrecipe.scheduleclone.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//controller bean등록,view없음. Controller+ResponseBody
@RequiredArgsConstructor//final로 선언된 필드 자동으로 생성자 만들어줌.
public class ScheduleController {
    //의존 객체 생성
    private final ScheduleService scheduleService;

    //일정생성
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleSaveResponseDto> save( //save 파라미터(user id,dto)
                                                         //HTTP 세션에 저장된 값을 파라미터로 쉽게 꺼내 쓰기위한 어노테이션
                                                         @SessionAttribute(name = Const.LOGIN_USER) Long userId,//세션에 저장되어 있는 userId를 꺼내옴
                                                         @Valid @RequestBody ScheduleSaveRequestDto dto) { //사용자가 입력한 일정제목,내용
        return ResponseEntity.ok(scheduleService.save(userId, dto));
    }

    //전체 일정조회
    @GetMapping("/schedules/{id}")
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    //id로 일정조회
    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> findOne(@PathVariable Long id) {//요청 헤더에 URL 읽음
        return ResponseEntity.ok(scheduleService.findeOne(id));
    }

    //일정 수정
    @PutMapping("/schedules/{id}")
    public ResponseEntity<ScheduleUpdateResponseDto> update(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUPdateRequestDto dto) { //dto:수정제목,수정내용
        return ResponseEntity.ok(scheduleService.update(id, userId, dto));
    }

    //일정 삭제
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable Long id) {
        scheduleService.deleteById(id, userId);
        return ResponseEntity.ok().build();
    }

//    //일정 페이지 API
//    @GetMapping("/schedules/page")
//    public ResponseEntity<Page<SchedulePageResponseDto>> findAllPage(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        Page<SchedulePageResponseDto> result = scheduleService.findAllPage(page, size);
//        return ResponseEntity.ok(result);
//    }


}

